package giuliolodi.financegame.ui.buydialog

import giuliolodi.financegame.data.DataManager
import giuliolodi.financegame.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class BuyDialogPresenter<V: BuyDialogContract.View> : BasePresenter<V>, BuyDialogContract.Presenter<V> {

    @Inject
    constructor(mCompositeDisposable: CompositeDisposable, mDataManager: DataManager): super(mCompositeDisposable, mDataManager)

    override fun subscribe() {
    }

}