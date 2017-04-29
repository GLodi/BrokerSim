package giuliolodi.financegame.ui.market

import android.util.Log
import giuliolodi.financegame.data.DataManager
import giuliolodi.financegame.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MarketPresenter<V: MarketContract.View> : BasePresenter<V>, MarketContract.Presenter<V> {

    val TAG = "MarketPresenter"

    @Inject
    constructor(mCompositeDisposable: CompositeDisposable, mDataManager: DataManager): super(mCompositeDisposable, mDataManager)

    override fun subscribe() {
        getView().showLoading()
        getCompositeDisposable().add(getDataManager().downloadActiveStockSymbols()
                .flatMap { activeStockSymbols ->
                    getView().setSymbolList(activeStockSymbols)
                    getDataManager().downloadStockList(activeStockSymbols.toTypedArray().copyOfRange(0,10)) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { activeStocks ->
                            getView().showContent(activeStocks.values.toList())
                            getView().hideLoading()
                        },
                        { throwable ->
                            Log.e(TAG, throwable.message, throwable)
                            getView().hideLoading()
                            getView().showError("Error downloading stocks.\nCheck your internet connection.")
                        }))
    }

    override fun getMoreStocks(symbols: List<String>) {
        getCompositeDisposable().add(getDataManager().downloadStockList(symbols.toTypedArray())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { stocks ->
                            getView().showMoreContent(stocks.values.toList())
                        },
                        { throwable ->
                            Log.e(TAG, throwable.message, throwable)
                            getView().showError("Error downloading stocks.\nCheck your internet connection.")
                        }
                ))
    }

}