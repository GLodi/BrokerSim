package giuliolodi.financegame.ui.base

import giuliolodi.financegame.data.DataManager
import io.reactivex.disposables.CompositeDisposable
import io.realm.Realm
import javax.inject.Inject

open class BasePresenter<V: BaseContract.View> : BaseContract.Presenter<V> {

    private val mCompositeDisposable: CompositeDisposable
    private val mDataManager: DataManager
    private val mRealm: Realm

    private var mBaseView: V? = null

    @Inject
    constructor(compositeDisposable: CompositeDisposable, dataManager: DataManager, realm: Realm) {
        mCompositeDisposable = compositeDisposable
        mDataManager= dataManager
        mRealm = realm
    }

    fun getCompositeDisposable(): CompositeDisposable {
        return mCompositeDisposable
    }

    fun getDataManager(): DataManager {
        return mDataManager
    }

    fun getRealm(): Realm {
        return mRealm
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