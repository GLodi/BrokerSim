package giuliolodi.financegame

import android.app.Application
import giuliolodi.financegame.di.component.AppComponent
import giuliolodi.financegame.di.component.DaggerAppComponent
import giuliolodi.financegame.di.module.AppModule
import io.realm.Realm

class App : Application() {

    lateinit var mAppComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

        mAppComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()

        mAppComponent.inject(this)

    }

    fun getAppComponent(): AppComponent {
        return mAppComponent
    }

}