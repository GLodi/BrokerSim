package giuliolodi.financegame.ui.stock

import giuliolodi.financegame.di.scope.PerActivity
import giuliolodi.financegame.models.StockDb
import giuliolodi.financegame.ui.base.BaseContract
import yahoofinance.Stock

interface StockContract {

    interface View : BaseContract.View {

        fun updateViewWithStockDb(stockDb: StockDb)

        fun updateViewWithStock(stock: Stock)

    }

    @PerActivity
    interface Presenter<V: StockContract.View> : BaseContract.Presenter<V> {

        fun getStockDb(position: Int)

        fun getStock(symbol: String)

    }

}