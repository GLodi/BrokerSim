package giuliolodi.financegame.data.api

import android.content.Context
import giuliolodi.financegame.di.AppContext
import io.reactivex.Observable
import yahoofinance.Stock
import yahoofinance.YahooFinance
import javax.inject.Inject

class ApiHelperImpl : ApiHelper {

    private val mContext: Context

    @Inject
    constructor(@AppContext context: Context) {
        mContext = context
    }

    override fun getStock(stockName: String): Observable<Stock> {
        return Observable.defer { Observable.just(YahooFinance.get(stockName)) }
    }

    override fun getStockList(stockList: Array<String>): Observable<Map<String,Stock>> {
        return Observable.defer { Observable.just(YahooFinance.get(stockList)) }
    }

}