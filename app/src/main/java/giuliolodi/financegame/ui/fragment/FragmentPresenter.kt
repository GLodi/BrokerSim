package giuliolodi.financegame.ui.fragment

import giuliolodi.financegame.data.DataManager
import giuliolodi.financegame.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class FragmentPresenter<V: FragmentContract.View> : BasePresenter<V>, FragmentContract.Presenter<V> {

    @Inject
    constructor(mCompositeDisposable: CompositeDisposable, mDataManager: DataManager): super(mCompositeDisposable, mDataManager)

    override fun subscribe() {
    }

}