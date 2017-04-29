package giuliolodi.financegame.data.api

import io.reactivex.Observable
import yahoofinance.Stock

interface ApiHelper {

    /**
     * Returns a stock downloaded through YahooFinance API. Need to pass the symbol.
     */
    fun downloadStock(stockSymbol: String): Observable<Stock>

    /**
     * Returns a list of stock downloaded through YahooFinance API.
     * It needs an array of symbols.
     */
    fun downloadStockList(stockList: Array<String>): Observable<Map<String, Stock>>

    /**
     * Connects to WSJ website, parses HTML and downloads symbols of most active
     * stocks.
     */
    fun downloadActiveStockSymbols(): Observable<List<String>>

}