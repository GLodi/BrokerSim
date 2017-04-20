package giuliolodi.financegame.ui.stock

import giuliolodi.financegame.di.scope.PerActivity
import giuliolodi.financegame.models.SellRequest
import giuliolodi.financegame.models.StockDb
import giuliolodi.financegame.models.StockDbBought
import giuliolodi.financegame.ui.base.BaseContract
import yahoofinance.Stock

interface StockContract {

    interface View : BaseContract.View {

        fun updateViewWithStockDb(stockDb: StockDb)

        fun updateViewWithStock(stock: Stock)

        fun showSuccess(message: String)

        fun showError(error: String)

        fun showLoading()

        fun hideLoading()

        fun showFab()

        fun showContent(stockDbBoughtList: List<StockDbBought>, stock: Stock)

    }

    @PerActivity
    interface Presenter<V: StockContract.View> : BaseContract.Presenter<V> {

        fun getStock(symbol: String)

        fun buyStock(symbol: String)

        fun sellStock(sellRequest: SellRequest)

    }

}