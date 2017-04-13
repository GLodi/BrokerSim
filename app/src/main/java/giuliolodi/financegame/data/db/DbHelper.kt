package giuliolodi.financegame.data.db

import giuliolodi.financegame.models.StockDb
import giuliolodi.financegame.models.StockDbBought
import io.reactivex.Observable
import yahoofinance.Stock

interface DbHelper {

    /**
     * Returns a stockDb with a specific symbol
     */
    fun getStockWithSymbol(symbol: String): Observable<StockDb>

    /**
     * Returns a list of stored stockDb
     */
    fun getStocks(): Observable<List<StockDb>>

    /**
     * Copy a given stock into a stockDb and update the db
     */
    fun updateStockDb(stock: Stock, stockDb: StockDb)

    /**
     * Takes a regular stock object, creates its stockDb version
     * and stored it into Realm
     */
    fun storeStock(stock: Stock)

    /**
     * Stores multiple stockDbs from list of stocks
     */
    fun storeMultipleStocks(stocks: List<Stock>)

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
    fun getStockAtPosition(position: Int): Observable<StockDb>

    /**
     * Returns true if user has already downloaded info of today's most
     * active stocks from wsj.
     */
    fun hasDownloadedActiveStocksToday(): Observable<Boolean>

    /**
     * Returns list of bought stocks
     */
    fun getBoughtStocks(): Observable<List<StockDbBought>>

    /**
     * Returns stock bought with a specific symbol
     */
    fun getBoughtStockWithSymbol(symbol: String): Observable<List<StockDbBought>>

    /**
     * Update stockDbBought
     */
    fun updateStockDbBought(stock: Stock, stockDbBought: StockDbBought)

    /**
     * Deletes all stored stockDb
     */
    fun deleteAllStockDbs()

    /**
     * Sets remainder that today active stocks have been downloaded
     */
    fun setRemainder(boolean: Boolean)

}