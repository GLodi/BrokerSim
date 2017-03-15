package giuliolodi.financegame.data

import android.content.Context
import giuliolodi.financegame.data.api.ApiHelper
import giuliolodi.financegame.di.AppContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataManagerImpl : DataManager {

    private val mContext: Context
    private val mApiHelper: ApiHelper

    @Inject
    constructor(@AppContext context: Context, apiHelper: ApiHelper) {
        mContext = context
        mApiHelper = apiHelper
    }

}