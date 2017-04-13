package giuliolodi.financegame.data.db

import android.content.Context
import giuliolodi.financegame.di.scope.AppContext
import giuliolodi.financegame.models.Assets
import giuliolodi.financegame.models.DateRemainder
import giuliolodi.financegame.models.StockDb
import giuliolodi.financegame.models.StockDbBought
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
        mRealm.beginTransaction()
        mRealm.where(StockDb::class.java).findAll().deleteAllFromRealm()
        mRealm.commitTransaction()
    }

    override fun updateStockDb(stock: Stock, stockDb: StockDb) {
        mRealm.beginTransaction()
        stockDb.copy(stock)
        mRealm.insertOrUpdate(stockDb)
        mRealm.commitTransaction()
    }

    override fun updateStockDbBought(stock: Stock, stockDbBought: StockDbBought) {
        mRealm.beginTransaction()
        stockDbBought.copy(stock)
        mRealm.insertOrUpdate(stockDbBought)
        mRealm.commitTransaction()
    }

    override fun storeMultipleStocks(stocks: List<Stock>) {
        for (stock in stocks) {
            val stockDb: StockDb = StockDb(stock.symbol)
            mRealm.beginTransaction()
            stockDb.copy(stock)
            mRealm.insertOrUpdate(stockDb)
            mRealm.commitTransaction()
        }
    }

    override fun storeStock(stock: Stock) {
        val stockDb: StockDb = StockDb(stock.symbol)
        mRealm.beginTransaction()
        stockDb.copy(stock)
        mRealm.insertOrUpdate(stockDb)
        mRealm.commitTransaction()
    }

    override fun setRemainder(boolean: Boolean) {
        mRealm.beginTransaction()
        var dateRemaindermRealm = mRealm.where(DateRemainder::class.java).findFirst()
        dateRemaindermRealm.downloaded = boolean
        mRealm.insertOrUpdate(dateRemaindermRealm)
        mRealm.commitTransaction()
    }

    override fun addMoney(money: Double) {
        val asset = mRealm.where(Assets::class.java).findFirst()
        mRealm.beginTransaction()
        asset.money = asset.money + money
        mRealm.insertOrUpdate(asset)
        mRealm.commitTransaction()
    }

}