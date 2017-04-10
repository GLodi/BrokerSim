package giuliolodi.financegame.ui.market

import giuliolodi.financegame.di.scope.PerActivity
import giuliolodi.financegame.ui.base.BaseContract
import yahoofinance.Stock

interface MarketContract {

    interface View : BaseContract.View {

        fun showLoading()

        fun hideLoading()

        fun showContent(stocks: List<Stock>)

    }

    @PerActivity
    interface Presenter<V: MarketContract.View> : BaseContract.Presenter<V> {

        fun subscribe()

    }

}