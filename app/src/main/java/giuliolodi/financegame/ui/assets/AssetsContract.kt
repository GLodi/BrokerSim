package giuliolodi.financegame.ui.assets

import giuliolodi.financegame.di.scope.PerActivity
import giuliolodi.financegame.models.StockDb
import giuliolodi.financegame.ui.base.BaseContract

interface AssetsContract {

    interface View : BaseContract.View {

        fun showLoading()

        fun hideLoading()

        fun showContent(stocks: List<StockDb>)

        fun showError(error: String)

        fun updateMoney(money: String)

        fun showNoStocksMessage()

        fun hideNoStocksMessage()

    }

    @PerActivity
    interface Presenter<V: AssetsContract.View> : BaseContract.Presenter<V> {

        fun subscribe()

        fun getMoney()

    }
}