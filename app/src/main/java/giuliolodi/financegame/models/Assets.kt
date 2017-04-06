package giuliolodi.financegame.models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Assets (

        @PrimaryKey
        open var name: String = "",

        open var money: Double = 10000.00,

        open var boughtStockDb: RealmList<StockDb>? = null

): RealmObject()