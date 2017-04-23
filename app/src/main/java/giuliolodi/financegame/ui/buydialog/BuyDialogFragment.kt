package giuliolodi.financegame.ui.buydialog

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import giuliolodi.financegame.R
import giuliolodi.financegame.ui.base.BaseFragment
import giuliolodi.financegame.utils.CommonUtils
import kotlinx.android.synthetic.main.buy_fragment.*
import javax.inject.Inject

class BuyDialogFragment : BaseFragment(), BuyDialogContract.View {

    @Inject lateinit var mPresenter: BuyDialogContract.Presenter<BuyDialogContract.View>

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
        val view: View = inflater.inflate(R.layout.buy_fragment, container, false)

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
        buy_fragment_button.setOnClickListener { hideDialog() }
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