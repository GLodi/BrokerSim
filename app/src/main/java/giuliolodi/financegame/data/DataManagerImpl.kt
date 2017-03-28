package giuliolodi.financegame.data

import android.content.Context
import android.util.Log
import giuliolodi.financegame.data.api.ApiHelper
import giuliolodi.financegame.data.db.DbHelper
import giuliolodi.financegame.di.AppContext
import giuliolodi.financegame.model.StockDb
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import yahoofinance.Stock
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataManagerImpl : DataManager {

    private val TAG = "DataManager"

    private val mContext: Context
    private val mApiHelper: ApiHelper
    private val mDbHelper: DbHelper

    @Inject
    constructor(@AppContext context: Context, apiHelper: ApiHelper, dbHelper: DbHelper) {
        mContext = context
        mApiHelper = apiHelper
        mDbHelper = dbHelper
    }

    override fun getStock(stockSymbol: String): Observable<Stock> {
        return mApiHelper.getStock(stockSymbol)
    }

    override fun getStockList(stockList: Array<String>): Observable<Map<String, Stock>> {
        return mApiHelper.getStockList(stockList)
    }

    override fun getStoredStockBySymbol(symbol: String): Observable<StockDb> {
        return mDbHelper.getStoredStockBySymbol(symbol)
    }

    override fun getStoredStocks(): Observable<List<StockDb>> {
        return mDbHelper.getStoredStocks()
    }

    override fun updateStock(stock: Stock, stockDb: StockDb) {
        return mDbHelper.updateStock(stock, stockDb)
    }

    override fun updateStocks() {
        var storredStocks: List<StockDb> = ArrayList()
        var storredStocksStrings: ArrayList<String> = ArrayList()
        mDbHelper.getStoredStocks()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete { downloadStocks(storredStocksStrings.toTypedArray(), storredStocks) }
                .subscribe (
                        { stocksDb ->
                            storredStocks = stocksDb
                            for (s in stocksDb) {
                                storredStocksStrings.add(s.symbol)

                            }
                        },
                        { throwable -> Log.e(TAG, throwable.message, throwable) }
                )

    }

    fun downloadStocks(storredStocksStrings: Array<String>, storredStocks: List<StockDb>) {
        var downloadedStocks: List<Stock>? = null
        mApiHelper.getStockList(storredStocksStrings)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete { checkstocks(storredStocks, downloadedStocks!!) }
                .subscribe(
                        { mappedList -> downloadedStocks = mappedList!!.values.toList() },
                        { throwable -> Log.e(TAG, throwable.message, throwable) }
                )
    }

    fun checkstocks(storredStocks: List<StockDb>, downloadedStocks: List<Stock>) {
        for (stockDb in storredStocks) {
            for (stock in downloadedStocks) {
                if (stockDb.symbol == stock.symbol && !stockDb.equalsToStock(stock))
                    updateStock(stock, stockDb)
            }
        }
    }

}