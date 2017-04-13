package giuliolodi.financegame.data

import android.content.Context
import android.util.Log
import giuliolodi.financegame.data.api.ApiHelper
import giuliolodi.financegame.data.db.DbHelper
import giuliolodi.financegame.di.scope.AppContext
import giuliolodi.financegame.models.StockDb
import giuliolodi.financegame.models.StockDbBought
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

    override fun downloadStock(stockSymbol: String): Observable<Stock> {
        return mApiHelper.downloadStock(stockSymbol)
    }

    override fun downloadStockList(stockList: Array<String>): Observable<Map<String, Stock>> {
        return mApiHelper.downloadStockList(stockList)
    }

    override fun getStockWithSymbol(symbol: String): Observable<StockDb> {
        return mDbHelper.getStockWithSymbol(symbol)
    }

    override fun getStocks(): Observable<List<StockDb>> {
        return mDbHelper.getStocks()
    }

    override fun updateStockDb(stock: Stock, stockDb: StockDb) {
        return mDbHelper.updateStockDb(stock, stockDb)
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

    override fun getStockAtPosition(position: Int): Observable<StockDb> {
        return mDbHelper.getStockAtPosition(position)
    }

    override fun downloadActiveStocks(): Observable<Map<String, Stock>> {
        return mApiHelper.downloadActiveStocks()
    }

    override fun hasDownloadedActiveStocksToday(): Observable<Boolean> {
        return mDbHelper.hasDownloadedActiveStocksToday()
    }

    override fun getBoughtStocks(): Observable<List<StockDbBought>> {
        return mDbHelper.getBoughtStocks()
    }

    override fun updateStockDbBought(stocks: List<Stock>, stockDbBoughtList: List<StockDbBought>) {
        return mDbHelper.updateStockDbBought(stocks, stockDbBoughtList)
    }

    override fun getBoughtStockWithSymbol(symbol: String): Observable<List<StockDbBought>> {
        return mDbHelper.getBoughtStockWithSymbol(symbol)
    }

    override fun deleteAllStockDbs() {
        return mDbHelper.deleteAllStockDbs()
    }

    override fun storeMultipleStocks(stocks: List<Stock>) {
        return mDbHelper.storeMultipleStocks(stocks)
    }

    override fun setRemainder(boolean: Boolean) {
        return mDbHelper.setRemainder(boolean)
    }

}