package giuliolodi.financegame.ui.assets

import giuliolodi.financegame.di.scope.PerActivity
import giuliolodi.financegame.models.StockDb
import giuliolodi.financegame.models.StockDbBought
import giuliolodi.financegame.ui.base.BaseContract
import yahoofinance.Stock

interface AssetsContract {

    interface View : BaseContract.View {

        fun showLoading()

        fun hideLoading()

        fun showContent(stocks: List<StockDbBought>)

        fun showError(error: String)

        fun updateMoney(money: String)

    }

    @PerActivity
    interface Presenter<V: AssetsContract.View> : BaseContract.Presenter<V> {

        fun subscribe()

        fun addStock()

        fun addMoney()

    }
}