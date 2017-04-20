package giuliolodi.financegame.models

import yahoofinance.Stock

data class SellRequest(var stockDbBought: StockDbBought, var amount: Int, var stock: Stock)