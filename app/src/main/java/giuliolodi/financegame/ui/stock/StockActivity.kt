package giuliolodi.financegame.ui.stock

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import giuliolodi.financegame.R
import giuliolodi.financegame.models.StockDb
import giuliolodi.financegame.ui.base.BaseActivity
import kotlinx.android.synthetic.main.stock_activity.*
import javax.inject.Inject
import android.view.WindowManager
import android.widget.Toast
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration
import es.dmoral.toasty.Toasty
import giuliolodi.financegame.models.StockDbBought
import giuliolodi.financegame.ui.buydialog.BuyDialogFragment
import giuliolodi.financegame.utils.CommonUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.stock_activity_content.*
import yahoofinance.Stock

class StockActivity : BaseActivity(), StockContract.View {

    @Inject lateinit var mPresenter: StockContract.Presenter<StockContract.View>

    lateinit var mLoadingDialog: ProgressDialog
    lateinit var mSymbol: String
    lateinit var mStock: Stock

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.stock_activity)

        initLayout()

        getActivityComponent().inject(this)

        mPresenter.onAttach(this)

        mSymbol = intent.getStringExtra("symbol")

        mPresenter.getStock(mSymbol)
    }

    fun initLayout() {
        setSupportActionBar(stock_activity_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val adapter = StockAdapter()

        stock_activity_content_rv.layoutManager = LinearLayoutManager(applicationContext)
        stock_activity_content_rv.adapter = adapter
        stock_activity_content_rv.isNestedScrollingEnabled = false
        stock_activity_content_rv.addItemDecoration(HorizontalDividerItemDecoration.Builder(this).build())

        stock_activity_fab.setOnClickListener { showBuyFragment() }

        adapter.getPositionClicks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { sellRequest -> mPresenter.sellStock(sellRequest) }
    }

    override fun updateViewWithStockDb(stockDb: StockDb) {
        stock_activity_collapsing_toolbar.title = stockDb.symbol
        stock_activity_content_description.text = stockDb.name
        stock_activity_collapsing_toolbar.setContentScrimColor(stockDb.iconColor)
        stock_activity_image.setBackgroundColor(stockDb.iconColor)
        stock_activity_price.text = "$${stockDb.price}"
        stock_activity_priceavg.text = "$${stockDb.priceAvg50}"
        stock_activity_daylow.text = "$${stockDb.dayLow}"
        stock_activity_dayhigh.text = "$${stockDb.dayHigh}"
        stock_activity_yearlow.text = "$${stockDb.yearLow}"
        stock_activity_yearhigh.text = "$${stockDb.yearHigh}"
        stock_activity_div4.visibility = View.VISIBLE

        // Setup statusBar color if SDK > Lollipop
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.setStatusBarColor(stockDb.iconColorDark)
        }
    }

    override fun updateViewWithStock(stock: Stock) {
        stock_activity_collapsing_toolbar.title = stock.symbol
        stock_activity_content_description.text = stock.name
        stock_activity_price.text = "$${stock.quote.price}"
        stock_activity_priceavg.text = "$${stock.quote.priceAvg50}"
        stock_activity_daylow.text = "$${stock.quote.dayLow}"
        stock_activity_dayhigh.text = "$${stock.quote.dayHigh}"
        stock_activity_yearlow.text = "$${stock.quote.yearLow}"
        stock_activity_yearhigh.text = "$${stock.quote.yearHigh}"
    }

    override fun updateAdapter(stockDbBoughtList: List<StockDbBought>) {
        val adapter = StockAdapter()
        stock_activity_content_rv.adapter = adapter
        (stock_activity_content_rv.adapter as StockAdapter).addStockDbBoughtList(stockDbBoughtList, mStock)
        adapter.getPositionClicks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { sellRequest -> mPresenter.sellStock(sellRequest) }
    }

    override fun showContent(stockDbBoughtList: List<StockDbBought>, stock: Stock) {
        mStock = stock
        (stock_activity_content_rv.adapter as StockAdapter).addStockDbBoughtList(stockDbBoughtList, mStock)
    }

    override fun showSuccess(message: String) {
        Toasty.success(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    override fun showError(error: String) {
        Toasty.error(applicationContext, error, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        mLoadingDialog = CommonUtils.showLoadingDialog(this)
    }

    override fun showBuyFragment() {
        val fragment: BuyDialogFragment = BuyDialogFragment()
        fragment.arguments = Bundle()
        fragment.arguments.putString("symbol", mSymbol)
        fragment.show(supportFragmentManager, "Stock Fragment")
    }

    override fun hideLoading() {
        if (mLoadingDialog.isShowing)
            mLoadingDialog.cancel()
    }

    override fun showFab() {
        stock_activity_fab.show()
    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, StockActivity::class.java)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        mPresenter.onDetach()
        super.onDestroy()
    }

}