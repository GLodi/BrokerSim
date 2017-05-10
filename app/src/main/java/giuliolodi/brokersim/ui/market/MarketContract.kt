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

import giuliolodi.brokersim.di.scope.PerActivity
import giuliolodi.brokersim.ui.base.BaseContract
import yahoofinance.Stock

interface MarketContract {

    interface View : BaseContract.View {

        fun showLoading()

        fun hideLoading()

        fun showContent(stocks: List<Stock>)

        fun showMoreContent(stocks: List<Stock>)

        fun showError(error: String)

        fun setSymbolList(symbols: List<String>)

        fun updateMoney(money: String)

    }

    @PerActivity
    interface Presenter<V: MarketContract.View> : BaseContract.Presenter<V> {

        fun subscribe()

        fun getMoreStocks(symbols: List<String>)

        fun getMoney()

    }

}