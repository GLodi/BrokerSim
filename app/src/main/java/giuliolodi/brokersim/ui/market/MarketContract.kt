package giuliolodi.brokersim.ui.market

import giuliolodi.brokersim.di.scope.PerActivity
import giuliolodi.brokersim.ui.base.BaseContract
import yahoofinance.Stock

interface MarketContract {

    interface View : BaseContract.View {

        fun showLoading()

        fun hideLoading()

        fun showContent(stocks: List<Stock>)

        fun showMoreContent(stocks: List<Stock>)

        fun showError(error: String)

        fun setSymbolList(symbols: List<String>)

        fun updateMoney(money: String)

    }

    @PerActivity
    interface Presenter<V: MarketContract.View> : BaseContract.Presenter<V> {

        fun subscribe()

        fun getMoreStocks(symbols: List<String>)

        fun getMoney()

    }

}