package giuliolodi.financegame.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * This class checks if today's most active stocks have already been downloaded
 */
open class DateRemainder(@PrimaryKey var date: String = "", var downloaded: Boolean = false) : RealmObject()