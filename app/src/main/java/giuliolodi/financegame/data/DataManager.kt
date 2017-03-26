package giuliolodi.financegame.data

import giuliolodi.financegame.data.api.ApiHelper
import giuliolodi.financegame.data.db.DbHelper

interface DataManager : ApiHelper, DbHelper {

    fun updateStocks()

}