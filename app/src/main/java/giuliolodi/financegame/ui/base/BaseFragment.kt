package giuliolodi.financegame.ui.base

import android.app.Fragment
import android.content.Context
import android.os.Bundle

open class BaseFragment : Fragment(), BaseContract.View {

    private lateinit var mActivity: BaseActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseActivity) {
            val activity = context
            this.mActivity = activity
            activity.onFragmentAttached()
        }
    }

    override fun showLoading() {
        mActivity.showLoading()
    }

    override fun hideLoading() {
        mActivity.hideLoading()
    }

    override fun isNetworkAvailable(): Boolean {
        return mActivity.isNetworkAvailable()
    }

    interface Callback {

        fun onFragmentAttached()

        fun onFragmentDetached(tag: String)

    }

}