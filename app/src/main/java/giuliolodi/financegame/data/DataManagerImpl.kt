package giuliolodi.financegame.data

import android.content.Context
import giuliolodi.financegame.data.api.ApiHelper
import giuliolodi.financegame.data.db.DbHelper
import giuliolodi.financegame.di.scope.AppContext
import giuliolodi.financegame.models.StockDb
import io.reactivex.Observable
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

    override fun downloadStock(stockSymbol: String): Observable<Stock> {
        return mApiHelper.downloadStock(stockSymbol)
    }

    override fun downloadStockList(stockList: Array<String>): Observable<Map<String, Stock>> {
        return mApiHelper.downloadStockList(stockList)
    }

    override fun storeStock(stock: Stock) {
        return mDbHelper.storeStock(stock)
    }

    override fun addMoney(money: Double) {
        return mDbHelper.addMoney(money)
    }

    override fun getMoney(): Observable<Double> {
        return mDbHelper.getMoney()
    }

    override fun downloadActiveStocks(): Observable<Map<String, Stock>> {
        return mApiHelper.downloadActiveStocks()
    }

    override fun getStocks(): Observable<List<StockDb>> {
        return mDbHelper.getStocks()
    }

    override fun updateListOfStockDb(stocks: List<Stock>, stockDbBoughtList: List<StockDb>) {
        return mDbHelper.updateListOfStockDb(stocks, stockDbBoughtList)
    }

    override fun getStocksWithSymbol(symbol: String): Observable<List<StockDb>> {
        return mDbHelper.getStocksWithSymbol(symbol)
    }

    override fun storeMultipleStocks(stocks: List<Stock>) {
        return mDbHelper.storeMultipleStocks(stocks)
    }

}