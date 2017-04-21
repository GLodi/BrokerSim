package giuliolodi.financegame.ui.stock

import android.util.Log
import giuliolodi.financegame.data.DataManager
import giuliolodi.financegame.models.SellRequest
import giuliolodi.financegame.models.StockDb
import giuliolodi.financegame.ui.base.BasePresenter
import giuliolodi.financegame.utils.CommonUtils
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
                                            getView().showError("Error downloading stock.\nCheck your internet connection.")
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
                            getView().showError("Error downloading stock.\nCheck you internet connection.")
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
                            getView().showError("Error retrieving stock.")
                        }))
    }

    override fun buyStock(symbol: String) {
        getView().showLoading()
        getCompositeDisposable().add(getDataManager().getStockWithSymbol(symbol)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    { stockDb ->
                        getDataManager().storeSecondStock(stockDb, 1, stockDb.price!!.toDouble(), CommonUtils.getDate())
                        getDataManager().updateMoney(-stockDb.price!!.toDouble())
                        getView().hideLoading()
                        getView().showSuccess("Another stock bought.")
                    },
                    { getDataManager().downloadStock(symbol)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    { stock ->
                                        getDataManager().storeFirstStock(stock, 1, stock.quote.price.toDouble(), CommonUtils.getDate())
                                        getDataManager().updateMoney(-stock.quote!!.price.toDouble())
                                        getView().hideLoading()
                                        getView().showSuccess("Stock bought.")
                                    },
                                    { throwable ->
                                        Log.e(TAG, throwable.message, throwable)
                                        getView().hideLoading()
                                        getView().showError("Error buying stock.")
                                    })
                    }))
    }

    override fun sellStock(sellRequest: SellRequest) {
        getView().showLoading()
        getDataManager().updateMoney(sellRequest.stock.quote.price.toDouble())
        getDataManager().sellStock(sellRequest)
        getView().hideLoading()
    }

}
