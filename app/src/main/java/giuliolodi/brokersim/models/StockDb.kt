package giuliolodi.brokersim.models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import yahoofinance.Stock

open class StockDb (

        @PrimaryKey
        open var symbol: String = "",

        open var iconColor: Int = 0,

        open var iconColorDark: Int = 0,

        open var name: String = "",

        open var bought: RealmList<StockDbBought> = RealmList(),

        open var currency: String = "",

        open var ask: Double? = null,

        open var askSize: Long? = null,

        open var avgVolume: Long? = null,

        open var bid: Double? = null,

        open var bidSize: Long? = null,

        open var dayHigh: Double? = null,

        open var dayLow: Double? = null,

        open var lastTradeDateStr: String? = null,

        open var lastTradeSize: Long? = null,

        open var open: Double? = null,

        open var previousClose: Double? = null,

        open var price: Double? = null,

        open var priceAvg200: Double? = null,

        open var priceAvg50: Double? = null,

        open var volume: Long? = null,

        open var yearHigh: Double? = null,

        open var yearLow: Double? = null

) : RealmObject() {

    fun equalsToStock(stock: Stock): Boolean {
        return (name == stock.name
                && currency == stock.currency
                && name == stock.name
                && currency == stock.currency
                && (stock.quote.ask == null || ask == stock.quote.ask.toDouble())
                && (stock.quote.askSize == null || askSize == stock.quote.askSize)
                && (stock.quote.avgVolume == null || avgVolume == stock.quote.avgVolume)
                && (stock.quote.bid == null || bid == stock.quote.bid.toDouble())
                && (stock.quote.bidSize == null || bidSize == stock.quote.bidSize)
                && (stock.quote.dayHigh == null || dayHigh == stock.quote.dayHigh.toDouble())
                && (stock.quote.dayLow == null || dayLow == stock.quote.dayLow.toDouble())
                && (stock.quote.lastTradeTimeStr == null || lastTradeDateStr == stock.quote.lastTradeDateStr)
                && (stock.quote.lastTradeSize == null || lastTradeSize == stock.quote.lastTradeSize)
                && (stock.quote.open == null || open == stock.quote.open.toDouble())
                && (stock.quote.previousClose == null || previousClose == stock.quote.previousClose.toDouble())
                && (stock.quote.price == null || price == stock.quote.price.toDouble())
                && (stock.quote.priceAvg200 == null || priceAvg200 == stock.quote.priceAvg200.toDouble())
                && (stock.quote.priceAvg50 == null || priceAvg50 == stock.quote.priceAvg50.toDouble())
                && (stock.quote.volume == null || volume == stock.quote.volume)
                && (stock.quote.yearHigh == null || yearHigh == stock.quote.yearHigh.toDouble())
                && (stock.quote.yearLow == null || yearLow == stock.quote.yearLow.toDouble()))
    }

    fun copy(stock: Stock) {
        name = stock.name
        currency = stock.currency
        if (stock.quote.ask != null) ask = stock.quote.ask.toDouble()
        if (stock.quote.askSize != null) askSize = stock.quote.askSize
        if (stock.quote.avgVolume != null) avgVolume = stock.quote.avgVolume
        if (stock.quote.bid != null) bid = stock.quote.bid.toDouble()
        if (stock.quote.bidSize!= null) bidSize = stock.quote.bidSize
        if (stock.quote.dayHigh != null) dayHigh = stock.quote.dayHigh.toDouble()
        if (stock.quote.dayLow != null) dayLow = stock.quote.dayLow.toDouble()
        if (stock.quote.lastTradeDateStr != null) lastTradeDateStr = stock.quote.lastTradeDateStr
        if (stock.quote.lastTradeSize!= null) lastTradeSize = stock.quote.lastTradeSize
        if (stock.quote.open != null) open = stock.quote.open.toDouble()
        if (stock.quote.previousClose!= null) previousClose = stock.quote.previousClose.toDouble()
        if (stock.quote.price!= null) price = stock.quote.price.toDouble()
        if (stock.quote.priceAvg200!= null) priceAvg200 = stock.quote.priceAvg200.toDouble()
        if (stock.quote.priceAvg50 != null) priceAvg50 = stock.quote.priceAvg50.toDouble()
        if (stock.quote.volume!= null) volume = stock.quote.volume
        if (stock.quote.yearHigh!= null) yearHigh = stock.quote.yearHigh.toDouble()
        if (stock.quote.yearLow!= null) yearLow = stock.quote.yearLow.toDouble()
    }

}
