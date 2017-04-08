package giuliolodi.financegame

import android.app.Application
import android.support.v4.content.res.ResourcesCompat
import giuliolodi.financegame.di.component.AppComponent
import giuliolodi.financegame.di.component.DaggerAppComponent
import giuliolodi.financegame.di.module.AppModule
import giuliolodi.financegame.models.Assets
import giuliolodi.financegame.models.StockDb
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
                        realm.insert(StockDb(item, getRandomColor(), getRandomDarkColor()))
                    }
                    realm.createObject(Assets::class.java, "Assets")
                }
                .deleteRealmIfMigrationNeeded()
                .build())
    }

    var t: Int = 0

    fun getRandomColor(): Int {
        val colorList: IntArray = intArrayOf(
                ResourcesCompat.getColor(resources, R.color.grey, null),
                ResourcesCompat.getColor(resources, R.color.brown, null),
                ResourcesCompat.getColor(resources, R.color.deepOrange, null),
                ResourcesCompat.getColor(resources, R.color.orange, null),
                ResourcesCompat.getColor(resources, R.color.amber, null),
                ResourcesCompat.getColor(resources, R.color.yellow, null),
                ResourcesCompat.getColor(resources, R.color.lime, null),
                ResourcesCompat.getColor(resources, R.color.lightGreen, null),
                ResourcesCompat.getColor(resources, R.color.green, null),
                ResourcesCompat.getColor(resources, R.color.teal, null),
                ResourcesCompat.getColor(resources, R.color.cyan, null),
                ResourcesCompat.getColor(resources, R.color.lightBlue, null),
                ResourcesCompat.getColor(resources, R.color.blue, null),
                ResourcesCompat.getColor(resources, R.color.indigo, null),
                ResourcesCompat.getColor(resources, R.color.deepPurple, null),
                ResourcesCompat.getColor(resources, R.color.purple, null),
                ResourcesCompat.getColor(resources, R.color.pink, null),
                ResourcesCompat.getColor(resources, R.color.red, null))
        t = colorList[(Math.random() * colorList.size).toInt()]
        return t
    }

    fun getRandomDarkColor(): Int {
        when(t) {
            ResourcesCompat.getColor(resources, R.color.grey, null) -> return ResourcesCompat.getColor(resources, R.color.greyDark, null)
            ResourcesCompat.getColor(resources, R.color.brown, null) -> return ResourcesCompat.getColor(resources, R.color.brownDark, null)
            ResourcesCompat.getColor(resources, R.color.deepOrange, null) -> return ResourcesCompat.getColor(resources, R.color.deepOrangeDark, null)
            ResourcesCompat.getColor(resources, R.color.orange, null) -> return ResourcesCompat.getColor(resources, R.color.orangeDark, null)
            ResourcesCompat.getColor(resources, R.color.amber, null) -> return ResourcesCompat.getColor(resources, R.color.amberDark, null)
            ResourcesCompat.getColor(resources, R.color.yellow, null) -> return ResourcesCompat.getColor(resources, R.color.yellowDark, null)
            ResourcesCompat.getColor(resources, R.color.lime, null) -> return ResourcesCompat.getColor(resources, R.color.limeDark, null)
            ResourcesCompat.getColor(resources, R.color.lightGreen, null) -> return ResourcesCompat.getColor(resources, R.color.lightGreenDark, null)
            ResourcesCompat.getColor(resources, R.color.green, null) -> return ResourcesCompat.getColor(resources, R.color.greenDark, null)
            ResourcesCompat.getColor(resources, R.color.teal, null) -> return ResourcesCompat.getColor(resources, R.color.tealDark, null)
            ResourcesCompat.getColor(resources, R.color.cyan, null) -> return ResourcesCompat.getColor(resources, R.color.cyanDark, null)
            ResourcesCompat.getColor(resources, R.color.lightBlue, null) -> return ResourcesCompat.getColor(resources, R.color.lightBlueDark, null)
            ResourcesCompat.getColor(resources, R.color.blue, null) -> return ResourcesCompat.getColor(resources, R.color.blueDark, null)
            ResourcesCompat.getColor(resources, R.color.indigo, null) -> return ResourcesCompat.getColor(resources, R.color.indigoDark, null)
            ResourcesCompat.getColor(resources, R.color.deepPurple, null) -> return ResourcesCompat.getColor(resources, R.color.deepPurpleDark, null)
            ResourcesCompat.getColor(resources, R.color.purple, null) -> return ResourcesCompat.getColor(resources, R.color.purpleDark, null)
            ResourcesCompat.getColor(resources, R.color.pink, null) -> return ResourcesCompat.getColor(resources, R.color.pinkDark, null)
            ResourcesCompat.getColor(resources, R.color.red, null) -> return ResourcesCompat.getColor(resources, R.color.redDark, null)
        }
        return 0
    }

    fun getAppComponent(): AppComponent {
        return mAppComponent
    }

}