package giuliolodi.financegame.data.db

import giuliolodi.financegame.models.StockDb
import io.reactivex.Observable
import yahoofinance.Stock

interface DbHelper {

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
     * Returns list of bought stocks
     */
    fun getStocks(): Observable<List<StockDb>>

    /**
     * Returns stock bought with a specific symbol
     */
    fun getStockWithSymbol(symbol: String): Observable<StockDb>

    /**
     * Update stockDbs with downloaded info
     */
    fun updateListOfStockDb(stocks: List<Stock>, stockDbList: List<StockDb>)

}