/*
 * Copyright 2017 GLodi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package giuliolodi.brokersim.ui.buydialog

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import es.dmoral.toasty.Toasty
import giuliolodi.brokersim.R
import giuliolodi.brokersim.ui.base.BaseFragment
import giuliolodi.brokersim.ui.stock.StockActivity
import giuliolodi.brokersim.utils.CommonUtils
import kotlinx.android.synthetic.main.buy_fragment.*
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

    override fun updateSeekbar(money: Double, price: Double) {
        var amount = 0
        buy_fragment_max.text = (money/price).toInt().toString()
        buy_fragment_seekbar.max = (money/price).toInt()
        buy_fragment_progress.text = "0"
        buy_fragment_price.text = "Price: $${String.format("%.2f", price)}"
        buy_fragment_cash.text = "Cash: $${String.format("%.2f", money)}"
        buy_fragment_seekbar.progress = 0
        buy_fragment_seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                buy_fragment_progress.text = p1.toString()
                buy_fragment_total.text = "Total: $${String.format("%.2f", (p1 * price))}"
                amount = p1
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
        buy_fragment_button.setOnClickListener {
            if (amount > 0) {
                (activity as StockActivity).mPresenter.buyStock(mSymbol, amount)
                hideDialog()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mPresenter.subscribe(mSymbol)
    }

    override fun showLoading() { mProgressDialog = CommonUtils.showLoadingDialog(context) }

    override fun hideLoading() {
        if (mProgressDialog.isShowing)
            mProgressDialog.cancel()
    }

    override fun hideDialog() { dialog.dismiss() }

    override fun showError(error: String) { Toasty.error(context, error, Toast.LENGTH_LONG).show() }

}