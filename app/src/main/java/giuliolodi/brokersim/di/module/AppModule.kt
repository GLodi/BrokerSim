/*
 * Copyright 2017 GLodi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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