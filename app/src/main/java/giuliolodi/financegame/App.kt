package giuliolodi.financegame

import android.app.Application
import giuliolodi.financegame.di.component.AppComponent
import giuliolodi.financegame.di.component.DaggerAppComponent
import giuliolodi.financegame.di.module.AppModule
import giuliolodi.financegame.models.Assets
import giuliolodi.financegame.models.StockDb
import giuliolodi.financegame.utils.ColorUtils
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