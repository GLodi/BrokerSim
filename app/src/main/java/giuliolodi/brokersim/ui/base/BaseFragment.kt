package giuliolodi.brokersim.ui.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import giuliolodi.brokersim.di.component.ActivityComponent

open class BaseFragment : DialogFragment(), BaseContract.View {

    private lateinit var mActivity: BaseActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseActivity)
            mActivity = context
    }

    override fun isNetworkAvailable(): Boolean {
        return mActivity.isNetworkAvailable()
    }

    fun getActivityComponent(): ActivityComponent {
        return mActivity.getActivityComponent()
    }

}