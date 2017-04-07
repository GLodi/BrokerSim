package giuliolodi.financegame.data.db

import giuliolodi.financegame.models.StockDb
import io.reactivex.Observable
import yahoofinance.Stock

interface DbHelper {

    /**
     * Returns a stockDb with a specific symbol
     */
    fun getStoredStockBySymbol(symbol: String): Observable<StockDb>

    /**
     * Returns a list of stored stockDb
     */
    fun getStoredStocks(): Observable<List<StockDb>>

    /**
     * Copy a given stock into a stockDb and update the db
     */
    fun updateStock(stock: Stock, stockDb: StockDb)

    /**
     * Takes a regular stock object, creates its stockDb version
     * and stored it into Realm
     */
    fun storeStock(stock: Stock)

    /**
     * Adds given amount of money to user's assets
     */
    fun addMoney(money: Double)

    /**
     * Returns current amount of money in user's assets
     */
    fun getMoney(): Observable<Double>

    /**
     * Returns StockDb stored at given position
     */
    fun getStockDbAtPosition(position: Int): Observable<StockDb>

}