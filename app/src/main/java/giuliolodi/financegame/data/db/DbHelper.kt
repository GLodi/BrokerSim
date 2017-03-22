package giuliolodi.financegame.data.db

import giuliolodi.financegame.model.StockDb
import io.reactivex.Observable

interface DbHelper {

    fun getStoredStocks(): Observable<List<StockDb>>

}