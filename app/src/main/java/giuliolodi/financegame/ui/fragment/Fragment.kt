package giuliolodi.financegame.ui.fragment

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import giuliolodi.financegame.R
import giuliolodi.financegame.ui.base.BaseFragment
import giuliolodi.financegame.utils.CommonUtils
import javax.inject.Inject

class Fragment: BaseFragment(), FragmentContract.View {

    val TAG = "Fragment"

    @Inject lateinit var mPresenter: FragmentContract.Presenter<FragmentContract.View>

    private lateinit var mProgressDialog: ProgressDialog

    companion object {
        fun newInstance(): Fragment {
            val args: Bundle = Bundle()
            val fragment: Fragment = Fragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.fragment_sample_dialog, container, false)

        getActivityComponent().inject(this)

        mPresenter.onAttach(this)

        return view
    }

    override fun showLoading() {
        mProgressDialog = CommonUtils.showLoadingDialog(context)
    }

    override fun hideLoading() {
        if (mProgressDialog.isShowing)
            mProgressDialog.cancel()
    }
}