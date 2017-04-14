package giuliolodi.financegame.data.db

import android.content.Context
import giuliolodi.financegame.di.scope.AppContext
import giuliolodi.financegame.models.Assets
import giuliolodi.financegame.models.StockDb
import giuliolodi.financegame.models.StockDbBought
import giuliolodi.financegame.utils.ColorUtils
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

    override fun updateStockDb(stock: Stock, stockDb: StockDb) {
        mRealm.executeTransaction { realm ->
            if (!stockDb.equalsToStock(stock)) {
                stockDb.copy(stock)
                realm.insertOrUpdate(stockDb)
            }
        }
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

    override fun storeFirstStock(stock: Stock, amount: Int, price: Double, date: String) {
        mRealm.executeTransaction { realm ->
            val colorUtil: ColorUtils = ColorUtils(mContext)
            val stockDb: StockDb = StockDb(stock.symbol, colorUtil.getRandomColor(), colorUtil.getRandomDarkColor())
            val stockDbBought: StockDbBought = StockDbBought(date, price, amount)
            stockDb.bought.add(stockDbBought)
            stockDb.copy(stock)
            realm.insertOrUpdate(stockDb)
        }
    }

    override fun storeSecondStock(stockDb: StockDb, amount: Int, price: Double, date: String) {
        mRealm.executeTransaction { realm ->
            val stockDbBought: StockDbBought = StockDbBought(date, price, amount)
            stockDb.bought.add(stockDbBought)
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