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
                        { throwable -> onError(throwable) }
                ))
        getCompositeDisposable().add(getDataManager()
                .getStoredStocks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { stockDbList -> getView().showContent(stockDbList) },
                        { throwable -> onError(throwable) }
                ))
        getCompositeDisposable().add(getDataManager()
                .getStoredStockBySymbol("AAPL")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { stock -> onSuccessSingle(stock) },
                        { throwable -> onError(throwable) }
                ))
    }

    fun onSuccessSingle(stockDb: StockDb) {
        val a = 0
    }

    fun onSuccess(stock: Map<String, Stock>) {
        val stocks: ArrayList<Stock> = ArrayList()
    }

    fun onError(throwable: Throwable) {
        Log.e(TAG, throwable.message, throwable)
    }

}