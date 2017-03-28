package giuliolodi.financegame.data.api

import android.content.Context
import giuliolodi.financegame.di.AppContext
import io.reactivex.Observable
import io.realm.Realm
import yahoofinance.Stock
import yahoofinance.YahooFinance
import javax.inject.Inject

class ApiHelperImpl : ApiHelper {

    private val mContext: Context
    private val mRealm: Realm

    @Inject
    constructor(@AppContext context: Context, realm: Realm) {
        mContext = context
        mRealm = realm
    }

    override fun getStock(stockSymbol: String): Observable<Stock> {
        return Observable.defer { Observable.just(YahooFinance.get(stockSymbol)) }
    }

    override fun getStockList(stockList: Array<String>): Observable<Map<String,Stock>> {
        return Observable.defer { Observable.just(YahooFinance.get(stockList)) }
    }

}