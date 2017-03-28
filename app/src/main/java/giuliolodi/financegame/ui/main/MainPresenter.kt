package giuliolodi.financegame.ui.main

import android.util.Log
import giuliolodi.financegame.data.DataManager
import giuliolodi.financegame.model.StockDb
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
        val ar: Array<String> = arrayOf("GOOGL", "INTC")
        getCompositeDisposable().add(getDataManager()
                .getStockList(ar)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { stock -> onSuccess(stock) },
                        { throwable -> Log.e(TAG, throwable.message, throwable) }
                ))
        updateStocks()
    }

    fun onSuccessSingle(stockDb: StockDb) {
        val a = 0
    }

    fun onSuccess(stock: Map<String, Stock>) {
        val stocks: ArrayList<Stock> = ArrayList()
    }

    fun updateStocks() {
        getView().showLoading()
        var storredStocks: List<StockDb> = ArrayList()
        var storredStocksStrings: ArrayList<String> = ArrayList()
        getDataManager().getStoredStocks()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete { downloadStocks(storredStocksStrings.toTypedArray(), storredStocks) }
                .subscribe (
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
                        { throwable -> Log.e(TAG, throwable.message, throwable) }
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