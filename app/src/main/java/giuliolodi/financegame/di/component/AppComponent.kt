package giuliolodi.financegame.di.component

import android.app.Application
import android.content.Context
import dagger.Component
import giuliolodi.financegame.App
import giuliolodi.financegame.data.DataManager
import giuliolodi.financegame.di.AppContext
import giuliolodi.financegame.di.module.AppModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    fun inject(app: App)

    @AppContext
    fun context(): Context

    fun application(): Application

    fun dataManager(): DataManager

}