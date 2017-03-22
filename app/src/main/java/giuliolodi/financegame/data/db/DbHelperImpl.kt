package giuliolodi.financegame.data.db

import android.content.Context
import giuliolodi.financegame.di.AppContext
import giuliolodi.financegame.model.StockDb
import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmResults
import javax.inject.Inject

class DbHelperImpl: DbHelper {

    private val mContext: Context
    private val mRealm: Realm

    @Inject
    constructor(@AppContext context: Context, realm: Realm) {
        mContext = context
        mRealm = realm
    }

    override fun getStoredStocks(): Observable<List<StockDb>> {
        val list: ArrayList<StockDb> = ArrayList()
        Observable.just(mRealm.where(StockDb::class.java).findAll())
                .subscribe { results -> for (item in results) list.add(item) }
        return Observable.just(list)
    }

}