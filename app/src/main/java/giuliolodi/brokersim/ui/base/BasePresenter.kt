package giuliolodi.brokersim.ui.base

import giuliolodi.brokersim.data.DataManager
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

open class BasePresenter<V: BaseContract.View> : BaseContract.Presenter<V> {

    private val mCompositeDisposable: CompositeDisposable
    private val mDataManager: DataManager

    private var mBaseView: V? = null

    @Inject
    constructor(compositeDisposable: CompositeDisposable, dataManager: DataManager) {
        mCompositeDisposable = compositeDisposable
        mDataManager= dataManager
    }

    fun getCompositeDisposable(): CompositeDisposable {
        return mCompositeDisposable
    }

    fun getDataManager(): DataManager {
        return mDataManager
    }

    override fun onAttach(view: V) {
        mBaseView = view
    }

    override fun onDetach() {
        mCompositeDisposable.dispose()
        mBaseView = null
    }

    fun isViewAttached(): Boolean {
        return mBaseView != null
    }

    fun getView(): V {
        return mBaseView!!
    }

}