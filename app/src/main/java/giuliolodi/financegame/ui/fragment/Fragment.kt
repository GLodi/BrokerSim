package giuliolodi.financegame.ui.fragment

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import giuliolodi.financegame.R
import giuliolodi.financegame.ui.base.BaseFragment
import giuliolodi.financegame.utils.CommonUtils
import kotlinx.android.synthetic.main.stock_fragment.*
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
        val view: View = inflater.inflate(R.layout.stock_fragment, container, false)

        dialog.setCanceledOnTouchOutside(false)

        getActivityComponent().inject(this)

        mPresenter.onAttach(this)

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
    }

    fun initLayout() {
        stock_fragment_button.setOnClickListener { hideDialog() }
    }

    override fun onStart() {
        super.onStart()
        mPresenter.subscribe()
    }

    override fun showLoading() {
        mProgressDialog = CommonUtils.showLoadingDialog(context)
    }

    override fun hideLoading() {
        if (mProgressDialog.isShowing)
            mProgressDialog.cancel()
    }

    override fun hideDialog() {
        dialog.dismiss()
    }

}