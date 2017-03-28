package giuliolodi.financegame.data

import android.content.Context
import android.util.Log
import giuliolodi.financegame.data.api.ApiHelper
import giuliolodi.financegame.data.db.DbHelper
import giuliolodi.financegame.di.AppContext
import giuliolodi.financegame.model.StockDb
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import yahoofinance.Stock
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataManagerImpl : DataManager {

    private val TAG = "DataManager"

    private val mContext: Context
    private val mApiHelper: ApiHelper
    private val mDbHelper: DbHelper

    @Inject
    constructor(@AppContext context: Context, apiHelper: ApiHelper, dbHelper: DbHelper) {
        mContext = context
        mApiHelper = apiHelper
        mDbHelper = dbHelper
    }

    override fun getStock(stockSymbol: String): Observable<Stock> {
        return mApiHelper.getStock(stockSymbol)
    }

    override fun getStockList(stockList: Array<String>): Observable<Map<String, Stock>> {
        return mApiHelper.getStockList(stockList)
    }

    override fun getStoredStockBySymbol(symbol: String): Observable<StockDb> {
        return mDbHelper.getStoredStockBySymbol(symbol)
    }

    override fun getStoredStocks(): Observable<List<StockDb>> {
        return mDbHelper.getStoredStocks()
    }

    override fun updateStock(stock: Stock, stockDb: StockDb) {
        return mDbHelper.updateStock(stock, stockDb)
    }

}