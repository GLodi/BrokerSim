package giuliolodi.financegame.data.db

import android.content.Context
import giuliolodi.financegame.di.AppContext
import javax.inject.Inject

class DbHelperImpl: DbHelper {

    private val mContext: Context

    @Inject
    constructor(@AppContext context: Context) {
        mContext = context
    }

}