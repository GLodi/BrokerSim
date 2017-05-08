package giuliolodi.brokersim.ui.stock

import giuliolodi.brokersim.di.scope.PerActivity
import giuliolodi.brokersim.models.SellRequest
import giuliolodi.brokersim.models.StockDb
import giuliolodi.brokersim.models.StockDbBought
import giuliolodi.brokersim.ui.base.BaseContract
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

        fun updateAdapter(stockDbBoughtList: List<StockDbBought>)

        fun showBuyFragment()

    }

    @PerActivity
    interface Presenter<V: StockContract.View> : BaseContract.Presenter<V> {

        fun getStock(symbol: String)

        fun buyStock(symbol: String, amount: Int)

        fun sellStock(sellRequest: SellRequest)

    }

}