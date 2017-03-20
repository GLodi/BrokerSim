package giuliolodi.financegame.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import giuliolodi.financegame.data.DataManager
import giuliolodi.financegame.data.DataManagerImpl
import giuliolodi.financegame.data.api.ApiHelper
import giuliolodi.financegame.data.api.ApiHelperImpl
import giuliolodi.financegame.di.AppContext
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
    fun provideRealm(): Realm {
        return Realm.getDefaultInstance()
    }

}