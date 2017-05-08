package giuliolodi.brokersim.data.db

import giuliolodi.brokersim.models.SellRequest
import giuliolodi.brokersim.models.StockDb
import io.reactivex.Completable
import io.reactivex.Observable
import yahoofinance.Stock

interface DbHelper {

    /**
     * Takes a regular stock object, creates its stockDb version
     * and stored it into Realm. Used when its the first time
     * user buys a specific stock, this is needed for colors.
     */
    fun storeFirstStock(stock: Stock, amount: Int, price: Double, date: String)

    /**
     * Takes a regular stock object, creates its stockDb version
     * and stored it into Realm
     */
    fun storeSecondStock(stockDb: StockDb, amount: Int, price: Double, date: String)

    /**
     * Adds or subtracts given amount of money to user's assets
     */
    fun updateMoney(money: Double)

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
     * Update single stockDb with downloaded data
     */
    fun updateStockDb(stock: Stock, stockDb: StockDb)

    /**
     * Update stockDbs with downloaded data
     */
    fun updateListOfStockDb(stocks: List<Stock>, stockDbList: List<StockDb>)

    /**
     * Sell stockDbBought. SellRequest holds StockDbBought, the amount
     * of stocks to sell and the original Stock.
     */
    fun sellStock(sellRequest: SellRequest): Completable

}