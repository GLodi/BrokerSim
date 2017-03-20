package giuliolodi.financegame.ui.main

import android.util.Log
import giuliolodi.financegame.data.DataManager
import giuliolodi.financegame.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import yahoofinance.Stock
import javax.inject.Inject

class MainPresenter<V: MainContract.View> : BasePresenter<V>, MainContract.Presenter<V> {

    val TAG = "MainPresenter"

    @Inject
    constructor(mCompositeDisposable: CompositeDisposable, mDataManager: DataManager, mRealm: Realm):
            super(mCompositeDisposable, mDataManager, mRealm)

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
    }

    fun onSuccess(stock: Map<String, Stock>) {
        val stocks: ArrayList<Stock> = ArrayList()
    }

    fun onError(throwable: Throwable) {
        Log.e(TAG, throwable.message, throwable)
    }

}