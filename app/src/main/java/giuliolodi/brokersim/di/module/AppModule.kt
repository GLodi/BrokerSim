package giuliolodi.brokersim.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import giuliolodi.brokersim.data.DataManager
import giuliolodi.brokersim.data.DataManagerImpl
import giuliolodi.brokersim.data.api.ApiHelper
import giuliolodi.brokersim.data.api.ApiHelperImpl
import giuliolodi.brokersim.data.db.DbHelper
import giuliolodi.brokersim.data.db.DbHelperImpl
import giuliolodi.brokersim.di.scope.AppContext
import io.realm.Realm
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