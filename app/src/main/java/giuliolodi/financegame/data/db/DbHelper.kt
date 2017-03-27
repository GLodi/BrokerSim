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
     * In order to update a stockDb, you need to pass the stock (finance) from which
     * you extract the data, along with the symbol of the stockDb that you want to update
     */
    fun updateStock(stock: Stock, stockDbSymbol: String)

}