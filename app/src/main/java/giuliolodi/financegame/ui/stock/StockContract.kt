package giuliolodi.financegame.ui.stock

import giuliolodi.financegame.di.scope.PerActivity
import giuliolodi.financegame.models.StockDb
import giuliolodi.financegame.ui.base.BaseContract
import yahoofinance.Stock

interface StockContract {

    interface View : BaseContract.View {

        fun updateViewWithStockDb(stockDb: StockDb)

        fun updateViewWithStock(stock: Stock)

        fun showMessage(message: String)

        fun showLoading()

        fun hideLoading()

    }

    @PerActivity
    interface Presenter<V: StockContract.View> : BaseContract.Presenter<V> {

        fun getStock(symbol: String, alreadyBought: Boolean)

        fun buyStock(symbol: String, alreadyBought: Boolean)

    }

}