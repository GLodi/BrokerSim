package giuliolodi.financegame.ui.main

import android.util.Log
import giuliolodi.financegame.data.DataManager
import giuliolodi.financegame.models.StockDb
import giuliolodi.financegame.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import yahoofinance.Stock
import java.util.*
import javax.inject.Inject

class MainPresenter<V: MainContract.View> : BasePresenter<V>, MainContract.Presenter<V> {

    val TAG = "MainPresenter"

    @Inject
    constructor(mCompositeDisposable: CompositeDisposable, mDataManager: DataManager): super(mCompositeDisposable, mDataManager)

    override fun subscribe() {
        updateStocks()
    }

    override fun addStock() {
        getDataManager().getStock("TIF")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { downloadedStock -> getDataManager().storeStock(downloadedStock) },
                        { throwable -> Log.e(TAG, throwable.message, throwable) }
                )
    }

    override fun addMoney() {
        getDataManager().addMoney(10000.00)
        getDataManager().getMoney()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { money -> getView().updateMoney(money.toString()) }
    }

    fun updateStocks() {
        getView().showLoading()
        var storredStocks: List<StockDb> = ArrayList()
        var storredStocksStrings: ArrayList<String> = ArrayList()
        getDataManager().getStoredStocks()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete { downloadStocks(storredStocksStrings.toTypedArray(), storredStocks) }
                .subscribe(
                        { stocksDb ->
                            storredStocks = stocksDb
                            for (s in stocksDb) {
                                storredStocksStrings.add(s.symbol)

                            }
                        },
                        { throwable -> Log.e(TAG, throwable.message, throwable) }
                )

    }

    fun downloadStocks(storredStocksStrings: Array<String>, storredStocks: List<StockDb>) {
        var downloadedStocks: List<Stock>? = null
        getDataManager().getStockList(storredStocksStrings)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete { checkStocksUpdateView(storredStocks, downloadedStocks!!) }
                .subscribe(
                        { mappedList -> downloadedStocks = mappedList!!.values.toList() },
                        { throwable ->
                            Log.e(TAG, throwable.message, throwable)
                            getView().hideLoading()
                            getView().showError("Error downloading data. Showing storred stocks")
                            getView().showContent(storredStocks)
                        }
                )
    }

    fun checkStocksUpdateView(storredStocks: List<StockDb>, downloadedStocks: List<Stock>) {
        for (stockDb in storredStocks) {
            for (stock in downloadedStocks) {
                if (stockDb.symbol == stock.symbol && !stockDb.equalsToStock(stock))
                    getDataManager().updateStock(stock, stockDb)
            }
        }
        getCompositeDisposable().add(getDataManager()
                .getStoredStocks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { stockDbList -> getView().showContent(stockDbList); getView().hideLoading() },
                        { throwable -> Log.e(TAG, throwable.message, throwable) }
                ))
    }

}