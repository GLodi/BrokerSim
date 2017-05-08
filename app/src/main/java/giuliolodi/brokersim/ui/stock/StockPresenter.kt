package giuliolodi.brokersim.ui.stock

import android.util.Log
import giuliolodi.brokersim.data.DataManager
import giuliolodi.brokersim.models.SellRequest
import giuliolodi.brokersim.models.StockDb
import giuliolodi.brokersim.ui.base.BasePresenter
import giuliolodi.brokersim.utils.CommonUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import yahoofinance.Stock
import javax.inject.Inject

class StockPresenter<V: StockContract.View> : BasePresenter<V>, StockContract.Presenter<V> {

    val TAG = "StockPresenter"

    @Inject
    constructor(mCompositeDisposable: CompositeDisposable, mDataManager: DataManager): super(mCompositeDisposable, mDataManager)

    override fun getStock(symbol: String) {
        getView().showLoading()
        var storredStocks: StockDb? = null
        getCompositeDisposable().add(getDataManager().getStockWithSymbol(symbol)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete { downloadStocks(storredStocks) }
                .subscribe(
                        { stockDb -> storredStocks = stockDb },
                        { getDataManager().downloadStock(symbol)
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        { stock ->
                                            getView().updateViewWithStock(stock)
                                            getView().hideLoading()
                                            getView().showFab()
                                        },
                                        { throwable ->
                                            Log.e(TAG, throwable.message, throwable)
                                            getView().hideLoading()
                                            getView().showError("Error downloading stock\nCheck your internet connection")
                                        })
                        }))
    }

    fun downloadStocks(storredStock: StockDb?) {
        var downloadedStock: Stock? = null
        getCompositeDisposable().add(getDataManager().downloadStock(storredStock!!.symbol)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete { checkStock(storredStock, downloadedStock) }
                .subscribe(
                        { stock -> downloadedStock = stock },
                        { throwable ->
                            Log.e(TAG, throwable.message, throwable)
                            getView().hideLoading()
                            getView().showError("Error downloading stock\nCheck you internet connection")
                        }))
    }

    fun checkStock(storredStock: StockDb?, downloadedStock: Stock?) {
        getDataManager().updateStockDb(downloadedStock!!, storredStock!!)
        getStockDbUpdateView(storredStock.symbol, downloadedStock)
    }

    fun getStockDbUpdateView(symbol: String, downloadedStock: Stock) {
        getCompositeDisposable().add(getDataManager().getStockWithSymbol(symbol)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { stockDb ->
                            getView().updateViewWithStockDb(stockDb)
                            getView().showContent(stockDb.bought, downloadedStock)
                            getView().hideLoading()
                            getView().showFab()
                        },
                        { throwable ->
                            Log.e(TAG, throwable.message, throwable)
                            getView().hideLoading()
                            getView().showError("Error retrieving stock")
                        }))
    }

    fun updateStockDbBought(symbol: String, deleteStockDb: Boolean = false) {
        getCompositeDisposable().add(getDataManager().getStockWithSymbol(symbol)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { stockDb -> getView().updateAdapter(stockDb.bought) },
                        { throwable ->
                            Log.e(TAG, throwable.message, throwable)
                            getView().hideLoading()
                            if (deleteStockDb)
                                getView().updateAdapter(arrayListOf())
                            else
                                getView().showError("Error retrieving stock")
                        }))
    }

    override fun buyStock(symbol: String, amount: Int) {
        getView().showLoading()
        getCompositeDisposable().add(getDataManager().getStockWithSymbol(symbol)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    { stockDb ->
                        getDataManager().storeSecondStock(stockDb, amount, stockDb.price!!.toDouble(), CommonUtils.getDate())
                        getDataManager().updateMoney(-stockDb.price!!.toDouble() * amount)
                        updateStockDbBought(symbol)
                        getView().hideLoading()
                        getView().showSuccess("Stock bought")
                    },
                    { getDataManager().downloadStock(symbol)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    { stock ->
                                        getView().hideLoading()
                                        getDataManager().storeFirstStock(stock, amount, stock.quote.price.toDouble(), CommonUtils.getDate())
                                        getDataManager().updateMoney(-stock.quote!!.price.toDouble() * amount)
                                        getStock(stock.symbol)
                                        getView().showSuccess("Stock bought")
                                    },
                                    { throwable ->
                                        Log.e(TAG, throwable.message, throwable)
                                        getView().hideLoading()
                                        getView().showError("Error buying stock")
                                    })
                    }))
    }

    override fun sellStock(sellRequest: SellRequest) {
        getView().showLoading()
        getDataManager().updateMoney(sellRequest.stock.quote.price.toDouble() * sellRequest.amount)
        getCompositeDisposable().add(getDataManager().sellStock(sellRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    updateStockDbBought(sellRequest.stock.symbol, sellRequest.deleteStockDb)
                    getView().hideLoading()
                    getView().showSuccess("Stock sold") })
    }

}
