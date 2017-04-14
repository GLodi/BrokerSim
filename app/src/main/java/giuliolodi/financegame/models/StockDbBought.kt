package giuliolodi.financegame.models

import io.realm.RealmObject

open class StockDbBought(

        open var dateBought: String = "",

        open var priceWhenBought: Double? = null,

        open var amount: Int? = null

): RealmObject()