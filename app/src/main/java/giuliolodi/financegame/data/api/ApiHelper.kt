package giuliolodi.financegame.data.api

import yahoofinance.Stock

interface ApiHelper {

    fun getStock(stockName: String): Stock

}