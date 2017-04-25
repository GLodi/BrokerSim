package giuliolodi.financegame.ui.buydialog

import giuliolodi.financegame.di.scope.PerActivity
import giuliolodi.financegame.ui.base.BaseContract

interface BuyDialogContract {

    interface View : BaseContract.View {

        fun showLoading()

        fun hideLoading()

        fun hideDialog()

        fun showError(error: String)

        fun updateSeekbar(money: Double, price: Double)

    }

    @PerActivity
    interface Presenter<V : BuyDialogContract.View> : BaseContract.Presenter<V> {

        fun subscribe(symbol: String)

    }

}