package giuliolodi.financegame.ui.main

import android.util.Log
import giuliolodi.financegame.data.DataManager
import giuliolodi.financegame.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import yahoofinance.Stock
import javax.inject.Inject

class MainPresenter<V: MainContract.View> : BasePresenter<V>, MainContract.Presenter<V> {

    val TAG = "MainPresenter"

    @Inject
    constructor(mCompositeDisposable: CompositeDisposable, mDataManager: DataManager): super(mCompositeDisposable, mDataManager)

    override fun subscribe() {
        getCompositeDisposable().add(getDataManager()
                .getStock("INTC")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { stock -> onSuccess(stock) },
                        { throwable -> onError(throwable) }
                ))
    }

    fun onSuccess(stock: Stock) {
        val stocks: ArrayList<Stock> = ArrayList()
        stocks.add(stock)
        getView().showContent(stocks)
    }

    fun onError(throwable: Throwable) {
        Log.e(TAG, throwable.message, throwable)
    }

}