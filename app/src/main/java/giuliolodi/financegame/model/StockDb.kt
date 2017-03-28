package giuliolodi.financegame.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import yahoofinance.Stock

open class StockDb (

        @PrimaryKey
        open var symbol: String = "",

        open var name: String = "",

        open var currency: String = ""

) : RealmObject() {

    fun equalsToStock(stock: Stock): Boolean {
        return (name == stock.name
                && currency == stock.currency)
    }

    fun copy(stock: Stock) {
        name = stock.name
        currency = stock.currency
    }

}
