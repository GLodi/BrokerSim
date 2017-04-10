package giuliolodi.financegame.ui.market

import android.content.ContentValues.TAG
import android.util.Log
import giuliolodi.financegame.data.DataManager
import giuliolodi.financegame.models.StockList
import giuliolodi.financegame.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MarketPresenter<V: MarketContract.View> : BasePresenter<V>, MarketContract.Presenter<V> {

    @Inject
    constructor(mCompositeDisposable: CompositeDisposable, mDataManager: DataManager): super(mCompositeDisposable, mDataManager)

    override fun subscribe() {
        getView().showLoading()
        getDataManager().getStockList(StockList.getArray().sortedArray().copyOfRange(0,100))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { mappedResult -> getView().showContent(mappedResult.values.toList()); getView().hideLoading() },
                        { throwable -> Log.e(TAG, throwable.message, throwable) }
                )
    }

}