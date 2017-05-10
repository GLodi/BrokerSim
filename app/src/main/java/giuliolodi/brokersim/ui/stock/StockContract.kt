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

package giuliolodi.brokersim.ui.stock

import giuliolodi.brokersim.di.scope.PerActivity
import giuliolodi.brokersim.models.SellRequest
import giuliolodi.brokersim.models.StockDb
import giuliolodi.brokersim.models.StockDbBought
import giuliolodi.brokersim.ui.base.BaseContract
import yahoofinance.Stock

interface StockContract {

    interface View : BaseContract.View {

        fun updateViewWithStockDb(stockDb: StockDb)

        fun updateViewWithStock(stock: Stock)

        fun showSuccess(message: String)

        fun showError(error: String)

        fun showLoading()

        fun hideLoading()

        fun showFab()

        fun showContent(stockDbBoughtList: List<StockDbBought>, stock: Stock)

        fun updateAdapter(stockDbBoughtList: List<StockDbBought>)

        fun showBuyFragment()

    }

    @PerActivity
    interface Presenter<V: StockContract.View> : BaseContract.Presenter<V> {

        fun getStock(symbol: String)

        fun buyStock(symbol: String, amount: Int)

        fun sellStock(sellRequest: SellRequest)

    }

}