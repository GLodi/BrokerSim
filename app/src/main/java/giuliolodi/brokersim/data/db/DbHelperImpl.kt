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

package giuliolodi.brokersim.data.db

import android.content.Context
import giuliolodi.brokersim.di.scope.AppContext
import giuliolodi.brokersim.models.Assets
import giuliolodi.brokersim.models.SellRequest
import giuliolodi.brokersim.models.StockDb
import giuliolodi.brokersim.models.StockDbBought
import giuliolodi.brokersim.utils.ColorUtils
import giuliolodi.brokersim.utils.CommonUtils
import io.reactivex.Completable
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
        return Observable.just(mRealm.where(StockDb::class.java).findAllSorted("symbol"))
    }

    override fun getStockWithSymbol(symbol: String): Observable<StockDb> {
        return Observable.just(mRealm.where(StockDb::class.java).equalTo("symbol", symbol).findFirstAsync())
    }

    override fun getMoney(): Observable<Double> {
        return Observable.just(mRealm.where(Assets::class.java).findFirst().money)
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
            val stockDbBought: StockDbBought = StockDbBought(CommonUtils.getRandomString(), stock.symbol, date, price, amount)
            stockDb.bought.add(stockDbBought)
            stockDb.copy(stock)
            realm.insert(stockDb)
        }
    }

    override fun storeSecondStock(stockDb: StockDb, amount: Int, price: Double, date: String) {
        mRealm.executeTransaction { realm ->
            val stockDbBought: StockDbBought = StockDbBought(CommonUtils.getRandomString(), stockDb.symbol, date, price, amount)
            stockDb.bought.add(stockDbBought)
            realm.insertOrUpdate(stockDb)
        }
    }

    override fun updateMoney(money: Double) {
        mRealm.executeTransaction { realm ->
            val asset = realm.where(Assets::class.java).findFirst()
            asset.money = asset.money + money
            realm.insertOrUpdate(asset)
        }
    }

    override fun sellStock(sellRequest: SellRequest): Completable {
        if (sellRequest.deleteStockDb) {
            val stockDb = mRealm.where(StockDb::class.java).equalTo("symbol", sellRequest.stockDbBought.symbol).findAll()
            mRealm.executeTransaction { stockDb.deleteFirstFromRealm() }
            return Completable.complete()
        } else {
            val stockDbBought = mRealm.where(StockDbBought::class.java).equalTo("id", sellRequest.stockDbBought.id).findFirst()
            mRealm.executeTransaction { realm ->
                if (stockDbBought.amount == sellRequest.amount && stockDbBought.isValid) {
                    stockDbBought.deleteFromRealm()
                }
                else {
                    stockDbBought.amount = stockDbBought.amount!! - sellRequest.amount
                    realm.insertOrUpdate(stockDbBought)
                }
            }
            return Completable.complete()
        }
    }

}