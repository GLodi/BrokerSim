package giuliolodi.financegame.data

import android.content.Context
import giuliolodi.financegame.data.api.ApiHelper
import giuliolodi.financegame.data.db.DbHelper
import giuliolodi.financegame.di.scope.AppContext
import giuliolodi.financegame.models.SellRequest
import giuliolodi.financegame.models.StockDb
import io.reactivex.Completable
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

    override fun storeFirstStock(stock: Stock, amount: Int, price: Double, date: String) {
        return mDbHelper.storeFirstStock(stock, amount, price, date)
    }

    override fun storeSecondStock(stockDb: StockDb, amount: Int, price: Double, date: String) {
        return mDbHelper.storeSecondStock(stockDb, amount, price, date)
    }

    override fun updateMoney(money: Double) {
        return mDbHelper.updateMoney(money)
    }

    override fun getMoney(): Observable<Double> {
        return mDbHelper.getMoney()
    }

    override fun downloadActiveStockSymbols(): Observable<List<String>> {
        return mApiHelper.downloadActiveStockSymbols()
    }

    override fun getStocks(): Observable<List<StockDb>> {
        return mDbHelper.getStocks()
    }

    override fun updateStockDb(stock: Stock, stockDb: StockDb) {
        return mDbHelper.updateStockDb(stock, stockDb)
    }

    override fun updateListOfStockDb(stocks: List<Stock>, stockDbList: List<StockDb>) {
        return mDbHelper.updateListOfStockDb(stocks, stockDbList)
    }

    override fun getStockWithSymbol(symbol: String): Observable<StockDb> {
        return mDbHelper.getStockWithSymbol(symbol)
    }

    override fun sellStock(sellRequest: SellRequest): Completable {
        return mDbHelper.sellStock(sellRequest)
    }

}