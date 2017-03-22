package giuliolodi.financegame.model

import java.math.BigDecimal

open class StockDbQuote (

        open var ask: BigDecimal? = null,

        open var askSize: Long? = null,

        open var avgVolume: Long? = null,

        open var bid: BigDecimal? = null,

        open var bidSize: Long? = null,

        open var dayHigh: BigDecimal? = null,

        open var dayLow: BigDecimal? = null,

        open var lastTradeDateStr: String? = null,

        open var lastTradeSize: Long? = null,

        open var open: BigDecimal? = null,

        open var previousCall: BigDecimal? = null,

        open var price: BigDecimal? = null,

        open var priceAvg200: BigDecimal? = null,

        open var priceAvg50: BigDecimal? = null,

        open var symbol: String? = null,

        open var volume: Long? = null,

        open var yearHigh: BigDecimal? = null,

        open var yearLow: BigDecimal? = null

)