package giuliolodi.financegame.data.db

import android.content.Context
import giuliolodi.financegame.di.AppContext
import giuliolodi.financegame.model.StockDb
import io.reactivex.Observable
import io.realm.Realm
import javax.inject.Inject

class DbHelperImpl: DbHelper {

    private val mContext: Context
    private val mRealm: Realm

    @Inject
    constructor(@AppContext context: Context, realm: Realm) {
        mContext = context
        mRealm = realm
    }

    override fun getStoredStockBySymbol(symbol: String): Observable<StockDb> {
        return Observable.just(mRealm.where(StockDb::class.java).contains("symbol", symbol).findFirst())
    }

    override fun getStoredStocks(): Observable<List<StockDb>> {
        return Observable.just(mRealm.where(StockDb::class.java).findAllSorted("symbol").toList())
    }

}