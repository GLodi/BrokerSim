package giuliolodi.financegame.ui.main

import giuliolodi.financegame.di.PerActivity
import giuliolodi.financegame.model.StockDb
import giuliolodi.financegame.ui.base.BaseContract
import yahoofinance.Stock

interface MainContract {

    interface View : BaseContract.View {

        fun showContent(stocks: List<StockDb>)

    }

    @PerActivity
    interface Presenter<V: MainContract.View> : BaseContract.Presenter<V> {

        fun subscribe()

        fun addStock()

    }
}