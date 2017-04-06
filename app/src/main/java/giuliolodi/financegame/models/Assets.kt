package giuliolodi.financegame.models

import io.realm.RealmList
import io.realm.RealmObject

open class Assets (

        open var money: Double? = null,

        open var boughtStockDb: RealmList<StockDb>? = null

): RealmObject()