package giuliolodi.financegame.ui.main

import giuliolodi.financegame.di.scope.PerActivity
import giuliolodi.financegame.model.StockDb
import giuliolodi.financegame.ui.base.BaseContract
import yahoofinance.Stock

interface MainContract {

    interface View : BaseContract.View {

        fun showLoading()

        fun hideLoading()

        fun showFragment()

        fun showContent(stocks: List<StockDb>)

        fun showError(error: String)

    }

    @PerActivity
    interface Presenter<V: MainContract.View> : BaseContract.Presenter<V> {

        fun subscribe()

        fun addStock()

    }
}