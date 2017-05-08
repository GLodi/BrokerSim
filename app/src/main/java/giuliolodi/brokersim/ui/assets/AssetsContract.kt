package giuliolodi.brokersim.ui.assets

import giuliolodi.brokersim.di.scope.PerActivity
import giuliolodi.brokersim.models.StockDb
import giuliolodi.brokersim.ui.base.BaseContract

interface AssetsContract {

    interface View : BaseContract.View {

        fun showLoading()

        fun hideLoading()

        fun showContent(stocks: List<StockDb>)

        fun clearAdapter()

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