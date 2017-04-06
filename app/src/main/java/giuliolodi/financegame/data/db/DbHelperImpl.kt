package giuliolodi.financegame.data.db

import android.content.Context
import giuliolodi.financegame.di.scope.AppContext
import giuliolodi.financegame.models.Assets
import giuliolodi.financegame.models.StockDb
import io.reactivex.Observable
import io.realm.Realm
import yahoofinance.Stock
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

    override fun updateStock(stock: Stock, stockDb: StockDb) {
        mRealm.beginTransaction()
        stockDb.copy(stock)
        mRealm.insertOrUpdate(stockDb)
        mRealm.commitTransaction()
    }

    override fun storeStock(stock: Stock) {
        val stockDb: StockDb = StockDb(stock.symbol)
        mRealm.beginTransaction()
        stockDb.copy(stock)
        mRealm.insertOrUpdate(stockDb)
        mRealm.commitTransaction()
    }

    override fun addMoney(money: Double) {
        val asset = mRealm.where(Assets::class.java).findFirst()
        mRealm.beginTransaction()
        asset.money = asset.money + money
        mRealm.insertOrUpdate(asset)
        mRealm.commitTransaction()
    }

    override fun getMoney(): Observable<Double> {
        return Observable.just(mRealm.where(Assets::class.java).findFirst().money)
    }

}