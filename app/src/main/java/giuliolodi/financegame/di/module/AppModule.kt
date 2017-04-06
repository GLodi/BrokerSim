package giuliolodi.financegame.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import giuliolodi.financegame.data.DataManager
import giuliolodi.financegame.data.DataManagerImpl
import giuliolodi.financegame.data.api.ApiHelper
import giuliolodi.financegame.data.api.ApiHelperImpl
import giuliolodi.financegame.data.db.DbHelper
import giuliolodi.financegame.data.db.DbHelperImpl
import giuliolodi.financegame.di.scope.AppContext
import giuliolodi.financegame.models.StockDb
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    @AppContext
    fun provideContext(): Context {
        return application
    }

    @Provides
    fun provideApplication(): Application {
        return application
    }

    @Provides
    @Singleton
    fun provideDataManager(dataManagerImpl: DataManagerImpl): DataManager {
        return dataManagerImpl
    }

    @Provides
    @Singleton
    fun provideApiHelper(apiHelperImpl: ApiHelperImpl): ApiHelper {
        return apiHelperImpl
    }

    @Provides
    @Singleton
    fun provideDbHelper(dbHelperImpl: DbHelperImpl): DbHelper {
        return dbHelperImpl
    }

    @Provides
    fun provideRealm(): Realm {
        return Realm.getDefaultInstance()
    }

}