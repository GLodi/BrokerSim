package giuliolodi.financegame.ui.main

import giuliolodi.financegame.di.PerActivity
import giuliolodi.financegame.ui.base.BaseContract
import yahoofinance.Stock

interface MainContract {

    interface View : BaseContract.View {

        fun showContent(stocks: List<Stock>)

    }

    @PerActivity
    interface Presenter<V: MainContract.View> : BaseContract.Presenter<V> {

        fun subscribe()

    }
}