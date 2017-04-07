package giuliolodi.financegame.ui.main

import giuliolodi.financegame.di.scope.PerActivity
import giuliolodi.financegame.models.StockDb
import giuliolodi.financegame.ui.base.BaseContract
import yahoofinance.Stock

interface MainContract {

    interface View : BaseContract.View {

        fun showLoading()

        fun hideLoading()

        fun showContent(stocks: List<StockDb>)

        fun showError(error: String)

        fun updateMoney(money: String)

    }

    @PerActivity
    interface Presenter<V: MainContract.View> : BaseContract.Presenter<V> {

        fun subscribe()

        fun addStock()

        fun addMoney()

    }
}