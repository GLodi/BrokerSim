package giuliolodi.financegame.data.db

import giuliolodi.financegame.model.StockDb
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

}