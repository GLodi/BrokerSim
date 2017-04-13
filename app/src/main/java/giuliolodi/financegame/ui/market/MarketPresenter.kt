package giuliolodi.financegame.ui.market

import android.util.Log
import giuliolodi.financegame.data.DataManager
import giuliolodi.financegame.models.StockDb
import giuliolodi.financegame.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import yahoofinance.Stock
import java.util.ArrayList
import javax.inject.Inject

class MarketPresenter<V: MarketContract.View> : BasePresenter<V>, MarketContract.Presenter<V> {

    val TAG = "MarketPresenter"

    @Inject
    constructor(mCompositeDisposable: CompositeDisposable, mDataManager: DataManager): super(mCompositeDisposable, mDataManager)

    /**
     * Check if today user has already downloaded most active stocks.
     */
    override fun subscribe() {
        getView().showLoading()
        getCompositeDisposable().add(getDataManager().hasDownloadedActiveStocksToday()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { hasDownloadedData -> if (hasDownloadedData) getStocks() else newDownloadActiveStocks() },
                        { throwable ->
                            Log.e(TAG, throwable.message, throwable)
                            getView().hideLoading()
                            getView().showError("Error checking date")
                        }))
    }

    /**
     * Retrieve list of stockDb from Realm.
     */
    fun getStocks() {
        var storredStocks: List<StockDb> = ArrayList()
        var storredStocksStrings: ArrayList<String> = ArrayList()
        getCompositeDisposable().add(getDataManager().getStocks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete { downloadStocks(storredStocksStrings.toTypedArray(), storredStocks) }
                .subscribe(
                        { stocksDb -> storredStocks = stocksDb; for (s in stocksDb) storredStocksStrings.add(s.symbol) },
                        { throwable -> Log.e(TAG, throwable.message, throwable) }))
    }

    /**
     * Downloads new data of stored stockDbs through their symbols.
     */
    fun downloadStocks(storredStocksStrings: Array<String>, storredStocks: List<StockDb>) {
        var downloadedStocks: List<Stock>? = null
        getCompositeDisposable().add(getDataManager().downloadStockList(storredStocksStrings)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete { checkStocksUpdateView(storredStocks, downloadedStocks!!) }
                .subscribe(
                        { mappedList -> downloadedStocks = mappedList!!.values.toList() },
                        { throwable ->
                            Log.e(TAG, throwable.message, throwable)
                            getView().hideLoading()
                            getView().showError("Error downloading data. Showing storred stocks")
                            getView().showContent(storredStocks)
                        }))
    }

    /**
     * Check and update stored stocks with updated stocks
     */
    fun checkStocksUpdateView(storredStocks: List<StockDb>, downloadedStocks: List<Stock>) {
        for (stockDbBought in storredStocks) {
            for (stock in downloadedStocks) {
                if (stockDbBought.symbol == stock.symbol && !stockDbBought.equalsToStock(stock))
                    getDataManager().updateStockDb(stock, stockDbBought)
            }
        }
        getCompositeDisposable().add(getDataManager().getStocks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { storedStocks -> getView().showContent(storedStocks); getView().hideLoading() },
                        { throwable -> Log.e(TAG, throwable.message, throwable)
                            getView().hideLoading()
                            getView().showError("Error retrieving stored data")
                        }))
    }

    /**
     * Download most active stocks. In case user hasn't already done so today.
     */
    fun newDownloadActiveStocks() {
        getCompositeDisposable().add(getDataManager().downloadActiveStocks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { activeStocks -> deleteOldCreateNew(activeStocks.values.toList()) },
                        { throwable ->
                            Log.e(TAG, throwable.message, throwable)
                            getView().hideLoading()
                            getView().showError("Error downloading most active stocks")
                        }))
    }

    /**
     * If most active stocks have been downloaded, delete all StockDb stored and create
     * new ones from downloaded data. Shows user new stored data.
     */
    fun deleteOldCreateNew(activeStocks: List<Stock>) {
        getDataManager().deleteAllStockDbs()
        getDataManager().storeMultipleStocks(activeStocks)
        getCompositeDisposable().add(getDataManager().getStocks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { storedStocks ->
                            getView().showContent(storedStocks)
                            getView().hideLoading()
                            getDataManager().setRemainder(true)
                        },
                        { throwable ->
                            Log.e(TAG, throwable.message, throwable)
                            getView().hideLoading()
                            getView().showError("Error retrieving stored stocks")
                        }))
    }

}