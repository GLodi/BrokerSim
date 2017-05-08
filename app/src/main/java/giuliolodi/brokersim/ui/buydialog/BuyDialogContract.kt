package giuliolodi.brokersim.ui.buydialog

import giuliolodi.brokersim.di.scope.PerActivity
import giuliolodi.brokersim.ui.base.BaseContract

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