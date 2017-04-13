package giuliolodi.financegame.data.db

import android.content.Context
import giuliolodi.financegame.di.scope.AppContext
import giuliolodi.financegame.models.Assets
import giuliolodi.financegame.models.DateRemainder
import giuliolodi.financegame.models.StockDb
import giuliolodi.financegame.models.StockDbBought
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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

    override fun getStockWithSymbol(symbol: String): Observable<StockDb> {
        return Observable.just(mRealm.where(StockDb::class.java).contains("symbol", symbol).findFirst())
    }

    override fun getStocks(): Observable<List<StockDb>> {
        return Observable.just(mRealm.where(StockDb::class.java).findAllSorted("symbol"))
    }

    override fun getStockAtPosition(position: Int): Observable<StockDb> {
        return Observable.just(mRealm.where(StockDb::class.java).findAllSorted("symbol").get(position))
    }

    override fun getBoughtStocks(): Observable<List<StockDbBought>> {
        return Observable.just(mRealm.where(StockDbBought::class.java).findAllSorted("symbol"))
    }

    override fun getBoughtStockWithSymbol(symbol: String): Observable<List<StockDbBought>> {
        return Observable.just(mRealm.where(StockDbBought::class.java).contains("symbol", symbol).findAll())
    }

    override fun getMoney(): Observable<Double> {
        return Observable.just(mRealm.where(Assets::class.java).findFirst().money)
    }

    override fun hasDownloadedActiveStocksToday(): Observable<Boolean> {
        return Observable.just(mRealm.where(DateRemainder::class.java).findFirst().downloaded)
    }

    override fun deleteAllStockDbs() {
        mRealm.executeTransaction { realm -> realm.where(StockDb::class.java).findAll().deleteAllFromRealm() }
    }

    override fun updateStockDb(stock: Stock, stockDb: StockDb) {
        mRealm.executeTransaction { realm ->
            stockDb.copy(stock)
            realm.insertOrUpdate(stockDb)
        }
    }

    override fun updateStockDbBought(stocks: List<Stock>, stockDbBoughtList: List<StockDbBought>) {
        mRealm.executeTransaction { realm ->
            for (stockDbBought in stockDbBoughtList) {
                for (stock in stocks) {
                    if (stockDbBought.symbol == stock.symbol && !stockDbBought.equalsToStock(stock)) {
                        stockDbBought.copy(stock)
                        realm.insertOrUpdate(stockDbBought)
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

    override fun setRemainder(boolean: Boolean) {
        mRealm.executeTransaction { realm ->
            val dateRemaindermRealm = realm.where(DateRemainder::class.java).findFirst()
            dateRemaindermRealm.downloaded = boolean
            realm.insertOrUpdate(dateRemaindermRealm)
        }
    }

    override fun addMoney(money: Double) {
        mRealm.executeTransaction { realm ->
            val asset = mRealm.where(Assets::class.java).findFirst()
            asset.money = asset.money + money
            realm.insertOrUpdate(asset)
        }
    }

}