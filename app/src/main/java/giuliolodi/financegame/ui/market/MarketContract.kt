package giuliolodi.financegame.ui.market

import giuliolodi.financegame.di.scope.PerActivity
import giuliolodi.financegame.models.StockDb
import giuliolodi.financegame.ui.base.BaseContract

interface MarketContract {

    interface View : BaseContract.View {

        fun showLoading()

        fun hideLoading()

        fun showContent(stocks: List<StockDb>)

        fun showError(error: String)

    }

    @PerActivity
    interface Presenter<V: MarketContract.View> : BaseContract.Presenter<V> {

        fun subscribe()

    }

}