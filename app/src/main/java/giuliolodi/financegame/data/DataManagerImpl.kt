package giuliolodi.financegame.data

import android.content.Context
import giuliolodi.financegame.data.api.ApiHelper
import giuliolodi.financegame.di.AppContext
import io.reactivex.Observable
import yahoofinance.Stock
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataManagerImpl : DataManager {

    private val mContext: Context
    private val mApiHelper: ApiHelper

    @Inject
    constructor(@AppContext context: Context, apiHelper: ApiHelper) {
        mContext = context
        mApiHelper = apiHelper
    }

    override fun getStock(stockName: String): Observable<Stock> {
        return mApiHelper.getStock(stockName)
    }

    override fun getStockList(stockList: Array<String>): Observable<Map<String, Stock>> {
        return mApiHelper.getStockList(stockList)
    }

}