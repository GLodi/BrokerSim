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

    override fun getStocks(): Observable<List<StockDb>> {
        return Observable.just(mRealm.where(StockDb::class.java).findAllSortedAsync("symbol"))
    }

    override fun getStockWithSymbol(symbol: String): Observable<StockDb> {
        return Observable.just(mRealm.where(StockDb::class.java).contains("symbol", symbol).findFirstAsync())
    }

    override fun getMoney(): Observable<Double> {
        return Observable.just(mRealm.where(Assets::class.java).findFirstAsync().money)
    }

    override fun updateListOfStockDb(stocks: List<Stock>, stockDbList: List<StockDb>) {
        mRealm.executeTransaction { realm ->
            for (stockDb in stockDbList) {
                for (stock in stocks) {
                    if (stockDb.symbol == stock.symbol && !stockDb.equalsToStock(stock)) {
                        stockDb.copy(stock)
                        realm.insertOrUpdate(stockDb)
                    }
                }
            }
        }
    }

    override fun storeMultipleStocks(stocks: List<Stock>) {
        mRealm.executeTransaction { realm ->
            for (stock in stocks) {
                val stockDb: StockDb = StockDb(stock.symbol)
                stockDb.copy(stock)
                realm.insertOrUpdate(stockDb)
            }
        }
    }

    override fun storeStock(stock: Stock) {
        mRealm.executeTransaction { realm ->
            val stockDb: StockDb = StockDb(stock.symbol)
            stockDb.copy(stock)
            realm.insertOrUpdate(stockDb)
        }
    }

    override fun addMoney(money: Double) {
        mRealm.executeTransaction { realm ->
            val asset = realm.where(Assets::class.java).findFirst()
            asset.money = asset.money + money
            realm.insertOrUpdate(asset)
        }
    }

}