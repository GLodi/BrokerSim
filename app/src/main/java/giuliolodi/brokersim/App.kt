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