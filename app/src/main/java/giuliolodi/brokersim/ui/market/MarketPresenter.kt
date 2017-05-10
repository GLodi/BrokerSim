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

package giuliolodi.brokersim.ui.market

import android.util.Log
import giuliolodi.brokersim.data.DataManager
import giuliolodi.brokersim.ui.base.BasePresenter
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
        getMoney()
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
                            getView().showError("Error downloading stocks\nCheck your internet connection")
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
                            getView().showError("Error downloading stocks\nCheck your internet connection")
                        }
                ))
    }

    override fun getMoney() {
        getCompositeDisposable().add(getDataManager().getMoney()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { money -> getView().updateMoney(String.format("%.2f", money)) })
    }

}