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

package giuliolodi.brokersim

import android.app.Application
import giuliolodi.brokersim.di.component.AppComponent
import giuliolodi.brokersim.di.component.DaggerAppComponent
import giuliolodi.brokersim.di.module.AppModule
import giuliolodi.brokersim.models.Assets
import io.realm.Realm
import io.realm.RealmConfiguration

class App : Application() {

    lateinit var mAppComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        mAppComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()

        mAppComponent.inject(this)

        initRealm()
    }

    fun initRealm() {
        Realm.init(this)
        Realm.setDefaultConfiguration(RealmConfiguration.Builder()
                .initialData { realm -> realm.createObject(Assets::class.java, "Assets") }
                .deleteRealmIfMigrationNeeded()
                .build())
    }

    fun getAppComponent(): AppComponent {
        return mAppComponent
    }

}