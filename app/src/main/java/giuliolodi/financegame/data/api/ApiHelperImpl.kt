package giuliolodi.financegame.data.api

import android.content.Context
import giuliolodi.financegame.di.AppContext
import javax.inject.Inject

class ApiHelperImpl : ApiHelper {

    private val mContext: Context

    @Inject
    constructor(@AppContext context: Context) {
        mContext = context
    }

}