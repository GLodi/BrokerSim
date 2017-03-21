package giuliolodi.financegame.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class StockDb (
        @PrimaryKey
        open var symbol: String = "",

        open var name: String = "",

        open var currency: String = ""
) : RealmObject()
