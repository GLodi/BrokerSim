package giuliolodi.financegame.ui.market

import android.util.Log
import giuliolodi.financegame.data.DataManager
import giuliolodi.financegame.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MarketPresenter<V: MarketContract.View> : BasePresenter<V>, MarketContract.Presenter<V> {

    val TAG = "MarketPresenter"

    @Inject
    constructor(mCompositeDisposable: CompositeDisposable, mDataManager: DataManager): super(mCompositeDisposable, mDataManager)

    override fun subscribe() {
        getView().showLoading()
        getDataManager().getMostActiveStocks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { mutableListOfStocks -> getView().showContent(mutableListOfStocks.values.toList()); getView().hideLoading() },
                        { throwable ->
                            Log.e(TAG, throwable.message, throwable)
                            getView().hideLoading()
                            getView().showError("Error downloading data")
                        }
                )
    }

}