package giuliolodi.brokersim.ui.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import giuliolodi.brokersim.App
import giuliolodi.brokersim.di.component.ActivityComponent
import giuliolodi.brokersim.di.component.DaggerActivityComponent
import giuliolodi.brokersim.di.module.ActivityModule
import giuliolodi.brokersim.utils.NetworkUtils

open class BaseActivity : AppCompatActivity(), BaseContract.View {

    private lateinit var mActivityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app: App = application as App

        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .appComponent(app.getAppComponent())
                .build()
    }

    fun getActivityComponent(): ActivityComponent {
        return mActivityComponent
    }

    override fun isNetworkAvailable(): Boolean {
        return NetworkUtils.isNetworkAvailable(applicationContext)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}