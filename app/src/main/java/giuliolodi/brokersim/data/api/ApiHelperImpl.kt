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

package giuliolodi.brokersim.data.api

import android.content.Context
import giuliolodi.brokersim.di.scope.AppContext
import io.reactivex.Observable
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import yahoofinance.Stock
import yahoofinance.YahooFinance
import javax.inject.Inject

class ApiHelperImpl : ApiHelper {

    private val mContext: Context

    @Inject
    constructor(@AppContext context: Context) {
        mContext = context
    }

    override fun downloadStock(stockSymbol: String): Observable<Stock> {
        return Observable.defer { Observable.just(YahooFinance.get(stockSymbol)) }
    }

    override fun downloadStockList(stockList: Array<String>): Observable<Map<String, Stock>> {
        return Observable.defer { Observable.just(YahooFinance.get(stockList)) }
    }

    override fun downloadActiveStockSymbols(): Observable<List<String>> {
        return Observable.defer {
            val symbols: MutableList<String> = arrayListOf()
            val elStrings: MutableList<String> = arrayListOf()
            val doc: Document = Jsoup.connect("http://online.wsj.com/mdc/public/page/2_3021-activnyse-actives.html").get()
            val tags: Elements? = doc.getElementsByTag("tr")
            val elements: MutableList<Elements> = arrayListOf()
            if (tags != null) {
                for (tag in tags) {
                    if (!tag.getElementsByTag("a").isEmpty()) {
                        elements.add(tag.getElementsByTag("a"))
                        elStrings.add(elements.get(elements.lastIndex).toString())
                        symbols.add(elStrings.get(elStrings.lastIndex).substring(elStrings.get(elStrings.lastIndex).lastIndexOf("(") + 1, elStrings.get(elStrings.lastIndex).lastIndexOf(")")))
                    }
                }
            }
            Observable.just(symbols.toList())
        }
    }

}