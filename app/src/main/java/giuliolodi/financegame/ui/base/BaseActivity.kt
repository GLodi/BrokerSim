package giuliolodi.financegame.ui.base

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import giuliolodi.financegame.App
import giuliolodi.financegame.di.component.ActivityComponent
import giuliolodi.financegame.di.component.DaggerActivityComponent
import giuliolodi.financegame.di.module.ActivityModule
import giuliolodi.financegame.utils.CommonUtils
import giuliolodi.financegame.utils.NetworkUtils

open class BaseActivity : AppCompatActivity(), BaseContract.View {

    private lateinit var mActivityComponent: ActivityComponent
    private lateinit var mProgressDialog: ProgressDialog

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

    override fun showLoading() {
        mProgressDialog = CommonUtils.showLoadingDialog(this)
    }

    override fun hideLoading() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.cancel()
        }
    }

    override fun isNetworkAvailable(): Boolean {
        return NetworkUtils.isNetworkAvailable(applicationContext)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}