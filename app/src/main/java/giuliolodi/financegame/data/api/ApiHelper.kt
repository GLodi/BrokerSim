package giuliolodi.financegame.data.api

import io.reactivex.Observable
import yahoofinance.Stock

interface ApiHelper {

    fun getStock(stockName: String): Observable<Stock>

}