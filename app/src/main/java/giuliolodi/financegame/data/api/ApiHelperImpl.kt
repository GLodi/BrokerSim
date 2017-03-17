package giuliolodi.financegame.data.api

import android.content.Context
import giuliolodi.financegame.di.AppContext
import yahoofinance.Stock
import yahoofinance.YahooFinance
import javax.inject.Inject

class ApiHelperImpl : ApiHelper {

    private val mContext: Context

    @Inject
    constructor(@AppContext context: Context) {
        mContext = context
    }

    override fun getStock(stockName: String): Stock {
        return YahooFinance.get(stockName)
    }

}