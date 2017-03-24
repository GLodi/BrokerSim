package giuliolodi.financegame.data.db

import giuliolodi.financegame.model.StockDb
import io.reactivex.Observable

interface DbHelper {

    fun getStoredStockBySymbol(symbol: String): Observable<StockDb>

    fun getStoredStocks(): Observable<List<StockDb>>

}