package giuliolodi.brokersim.di.component

import android.app.Application
import android.content.Context
import dagger.Component
import giuliolodi.brokersim.App
import giuliolodi.brokersim.data.DataManager
import giuliolodi.brokersim.di.scope.AppContext
import giuliolodi.brokersim.di.module.AppModule
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