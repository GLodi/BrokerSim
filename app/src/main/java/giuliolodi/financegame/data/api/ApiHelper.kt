package giuliolodi.financegame.data.api

import io.reactivex.Observable
import yahoofinance.Stock

interface ApiHelper {

    /**
     * Returns a stock downloaded through YahooFinance API. Need to pass the symbol.
     */
    fun getStock(stockSymbol: String): Observable<Stock>

    /**
     * Returns a list of stock downloaded through YahooFinance API.
     * It needs an array of symbols.
     */
    fun getStockList(stockList: Array<String>): Observable<Map<String, Stock>>

}