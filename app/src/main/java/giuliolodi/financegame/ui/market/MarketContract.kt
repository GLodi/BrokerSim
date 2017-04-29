package giuliolodi.financegame.ui.market

import giuliolodi.financegame.di.scope.PerActivity
import giuliolodi.financegame.models.StockDb
import giuliolodi.financegame.ui.base.BaseContract
import yahoofinance.Stock

interface MarketContract {

    interface View : BaseContract.View {

        fun showLoading()

        fun hideLoading()

        fun showContent(stocks: List<Stock>)

        fun showMoreContent(stocks: List<Stock>)

        fun showError(error: String)

        fun setSymbolList(symbols: List<String>)

    }

    @PerActivity
    interface Presenter<V: MarketContract.View> : BaseContract.Presenter<V> {

        fun subscribe()

        fun getMoreStocks(symbols: List<String>)

    }

}