package giuliolodi.financegame.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class StockDbBought(

        @PrimaryKey
        open var id: String = "",

        open var symbol: String = "",

        open var dateBought: String = "",

        open var priceWhenBought: Double? = null,

        open var amount: Int? = null

): RealmObject()