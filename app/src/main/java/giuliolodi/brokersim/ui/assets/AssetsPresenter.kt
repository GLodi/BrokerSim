/*
 * Copyright 2017 GLodi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package giuliolodi.brokersim.ui.assets

import android.util.Log
import giuliolodi.brokersim.data.DataManager
import giuliolodi.brokersim.models.StockDb
import giuliolodi.brokersim.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import yahoofinance.Stock
import java.util.*
import javax.inject.Inject

class AssetsPresenter<V: AssetsContract.View> : BasePresenter<V>, AssetsContract.Presenter<V> {

    val TAG = "AssetsPresenter"

    @Inject
    constructor(mCompositeDisposable: CompositeDisposable, mDataManager: DataManager): super(mCompositeDisposable, mDataManager)

    /**
     * Retrieve boughtStocks from Realm.
     */
    override fun subscribe() {
        getView().showLoading()
        var storredStocks: List<StockDb> = ArrayList()
        var storredStocksStrings: ArrayList<String> = ArrayList()
        getCompositeDisposable().add(getDataManager().getStocks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete { getMoney() }
                .subscribe(
                        { stocksDb ->
                            storredStocks = stocksDb
                            for (s in stocksDb) storredStocksStrings.add(s.symbol)
                            if (stocksDb.isEmpty()) {
                                getView().showNoStocksMessage()
                                getView().clearAdapter()
                                getView().hideLoading()
                            }
                            else {
                                downloadStocks(storredStocksStrings.toTypedArray(), storredStocks)
                                getView().hideNoStocksMessage()
                            }
                        },
                        { throwable ->
                            Log.e(TAG, throwable.message, throwable)
                            getView().hideLoading()
                            getView().showError("Error downloading data\nShowing storred stocks")
                        }))
    }

    /**
     * Downloads new data of stored StockDbBoughts through their symbols.
     */
    fun downloadStocks(storredStocksStrings: Array<String>, storredStocks: List<StockDb>) {
        var downloadedStocks: List<Stock>? = null
        getCompositeDisposable().add(getDataManager().downloadStockList(storredStocksStrings)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete { checkStocks(storredStocks, downloadedStocks!!) }
                .subscribe(
                        { mappedList -> downloadedStocks = mappedList!!.values.toList() },
                        { throwable ->
                            Log.e(TAG, throwable.message, throwable)
                            getView().hideLoading()
                            getView().showError("Error downloading data\nShowing storred stocks")
                            getView().showContent(storredStocks)
                        }))
    }

    /**
     * Check and update stored StockDbBoughts with updated stocks
     */
    fun checkStocks(storredStocks: List<StockDb>, downloadedStocks: List<Stock>) {
        getDataManager().updateListOfStockDb(downloadedStocks, storredStocks)
        getStocksUpdateView()
    }

    /**
     * Get StockDbBought and show them to the user
     */
    fun getStocksUpdateView() {
        getCompositeDisposable().add(getDataManager().getStocks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { stockDbList -> getView().showContent(stockDbList); getView().hideLoading() },
                        { throwable -> Log.e(TAG, throwable.message, throwable) }
                ))
    }

    override fun getMoney() {
        getCompositeDisposable().add(getDataManager().getMoney()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { money -> getView().updateMoney(String.format("%.2f", money)) })
    }

}