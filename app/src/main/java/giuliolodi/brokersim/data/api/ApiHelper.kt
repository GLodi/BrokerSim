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

import io.reactivex.Observable
import yahoofinance.Stock

interface ApiHelper {

    /**
     * Returns a stock downloaded through YahooFinance API. Need to pass the symbol.
     */
    fun downloadStock(stockSymbol: String): Observable<Stock>

    /**
     * Returns a list of stock downloaded through YahooFinance API.
     * It needs an array of symbols.
     */
    fun downloadStockList(stockList: Array<String>): Observable<Map<String, Stock>>

    /**
     * Connects to WSJ website, parses HTML and downloads symbols of most active
     * stocks.
     */
    fun downloadActiveStockSymbols(): Observable<List<String>>

}