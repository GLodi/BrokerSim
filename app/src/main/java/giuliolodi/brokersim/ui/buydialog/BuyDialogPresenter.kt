package giuliolodi.brokersim.ui.buydialog

import android.util.Log
import giuliolodi.brokersim.data.DataManager
import giuliolodi.brokersim.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BuyDialogPresenter<V: BuyDialogContract.View> : BasePresenter<V>, BuyDialogContract.Presenter<V> {

    val TAG = "BuyDialogPresenter"

    @Inject
    constructor(mCompositeDisposable: CompositeDisposable, mDataManager: DataManager): super(mCompositeDisposable, mDataManager)

    override fun subscribe(symbol: String) {
        getView().showLoading()
        getCompositeDisposable().add(getDataManager().getMoney()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { money -> getStockToUpdateSeekbar(symbol, money) },
                        { throwable ->
                            Log.e(TAG, throwable.message, throwable)
                            getView().hideLoading()
                            getView().showError("Error retrieving money from assets") }))
    }

    fun getStockToUpdateSeekbar(symbol: String, money: Double) {
        getCompositeDisposable().add(getDataManager().downloadStock(symbol)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { stock -> getView().updateSeekbar(money, stock.quote.price.toDouble()); getView().hideLoading() },
                        { throwable ->
                            Log.e(TAG, throwable.message, throwable)
                            getView().hideLoading()
                            getView().showError("Error downloading stock\nCheck you internet connection") }))
    }

}