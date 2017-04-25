package giuliolodi.financegame.ui.buydialog

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import es.dmoral.toasty.Toasty
import giuliolodi.financegame.R
import giuliolodi.financegame.ui.base.BaseFragment
import giuliolodi.financegame.utils.CommonUtils
import kotlinx.android.synthetic.main.buy_fragment.*
import kotlinx.android.synthetic.main.item_stock_activity.view.*
import javax.inject.Inject

class BuyDialogFragment : BaseFragment(), BuyDialogContract.View {

    @Inject lateinit var mPresenter: BuyDialogContract.Presenter<BuyDialogContract.View>

    private lateinit var mProgressDialog: ProgressDialog
    private lateinit var mSymbol: String

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
        mSymbol = arguments.getString("symbol")

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

    override fun updateSeekbar(money: Double, price: Double) {
        buy_fragment_max.text = (money/price).toInt().toString()
        buy_fragment_seekbar.max = (money/price).toInt()
        buy_fragment_progress.text = "0"
        buy_fragment_seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) { buy_fragment_progress.text = p1.toString() }
            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }

    override fun onStart() {
        super.onStart()
        mPresenter.subscribe(mSymbol)
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

    override fun showError(error: String) {
        Toasty.error(context, error, Toast.LENGTH_LONG).show()
    }

}