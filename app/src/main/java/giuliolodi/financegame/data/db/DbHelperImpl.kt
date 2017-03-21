package giuliolodi.financegame.data.db

import android.content.Context
import giuliolodi.financegame.di.AppContext
import io.realm.Realm
import javax.inject.Inject

class DbHelperImpl: DbHelper {

    private val mContext: Context
    private val mRealm: Realm

    @Inject
    constructor(@AppContext context: Context, realm: Realm) {
        mContext = context
        mRealm = realm
    }

}