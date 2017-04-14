package giuliolodi.financegame.ui.assets

import giuliolodi.financegame.di.scope.PerActivity
import giuliolodi.financegame.models.StockDb
import giuliolodi.financegame.ui.base.BaseContract

interface AssetsContract {

    interface View : BaseContract.View {

        fun showLoading()

        fun hideLoading()

        fun showContent(stocks: List<StockDb>)

        fun showMessage(message: String)

        fun updateMoney(money: String)

    }

    @PerActivity
    interface Presenter<V: AssetsContract.View> : BaseContract.Presenter<V> {

        fun subscribe()

        fun addMoney()

    }
}