package giuliolodi.financegame.data.api

import android.content.Context
import giuliolodi.financegame.di.AppContext
import io.reactivex.Observable
import io.reactivex.rxkotlin.toMaybe
import io.reactivex.rxkotlin.toSingle
import io.reactivex.schedulers.Schedulers
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
        return YahooFinance.get(stockName).toSingle().toObservable()
    }

}