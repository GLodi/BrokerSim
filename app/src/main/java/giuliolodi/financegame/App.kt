package giuliolodi.financegame

import android.app.Application
import giuliolodi.financegame.di.component.AppComponent
import giuliolodi.financegame.di.component.DaggerAppComponent
import giuliolodi.financegame.di.module.AppModule
import giuliolodi.financegame.models.Assets
import giuliolodi.financegame.models.DateRemainder
import giuliolodi.financegame.models.StockDbBought
import giuliolodi.financegame.utils.ColorUtils
import giuliolodi.financegame.utils.CommonUtils
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
        val defaultList: List<String> = listOf("GOOG", "GOOGL", "INTC", "AMZN", "AAPL", "YHOO")
        Realm.setDefaultConfiguration(RealmConfiguration.Builder()
                .initialData { realm ->
                    for (item in defaultList) {
                        val colorUtil: ColorUtils = ColorUtils(applicationContext)
                        realm.insert(StockDbBought(item, colorUtil.getRandomColor(), colorUtil.getRandomDarkColor(), CommonUtils.getDate()))
                    }
                    realm.createObject(Assets::class.java, "Assets")
                    realm.createObject(DateRemainder::class.java, CommonUtils.getDate())
                }
                .deleteRealmIfMigrationNeeded()
                .build())
    }

    fun getAppComponent(): AppComponent {
        return mAppComponent
    }

}