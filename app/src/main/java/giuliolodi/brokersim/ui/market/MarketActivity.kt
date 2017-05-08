package giuliolodi.brokersim.ui.market

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.MenuItem
import android.widget.Toast
import es.dmoral.toasty.Toasty
import giuliolodi.brokersim.R
import giuliolodi.brokersim.ui.base.BaseActivity
import giuliolodi.brokersim.ui.stock.StockActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.market_activity.*
import kotlinx.android.synthetic.main.market_activity_content.*
import kotlinx.android.synthetic.main.money_view.*
import yahoofinance.Stock
import javax.inject.Inject

class MarketActivity : BaseActivity(), MarketContract.View {

    @Inject lateinit var mPresenter: MarketContract.Presenter<MarketContract.View>

    // The following variables are used to handle the infinite scroll
    private var LOADING = false
    private var NO_MORE = false
    private var PAGE = 10
    private lateinit var mSymbols: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.market_activity)

        initLayout()

        getActivityComponent().inject(this)

        mPresenter.onAttach(this)

        mPresenter.subscribe()
    }

    fun initLayout() {
        setSupportActionBar(market_activity_toolbar)
        market_activity_toolbar.addView(layoutInflater.inflate(R.layout.money_view, null), Toolbar.LayoutParams(Gravity.RIGHT))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        title = "Markets"

        // Setup RecyclerView
        val adapter: MarketAdapter = MarketAdapter()
        adapter.setHasStableIds(true)
        val llm = LinearLayoutManager(applicationContext)
        market_activity_content_rv.layoutManager = llm
        market_activity_content_rv.adapter = adapter
        val mScrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (LOADING)
                    return
                val visibleItemCount = llm.childCount
                val totalItemCount = llm.itemCount
                val pastVisibleItems = llm.findFirstVisibleItemPosition()
                if (pastVisibleItems + visibleItemCount >= totalItemCount) {
                    if (!NO_MORE) {
                        LOADING = true
                        (market_activity_content_rv.adapter as MarketAdapter).addLoading()
                        if (mSymbols.lastIndex >= PAGE + 10)
                            mPresenter.getMoreStocks(mSymbols.subList(PAGE, PAGE + 10))
                        else {
                            mPresenter.getMoreStocks(mSymbols.subList(PAGE, mSymbols.lastIndex))
                            NO_MORE = true
                        }
                    }
                }
            }
        }
        market_activity_content_rv.setOnScrollListener(mScrollListener)

        // Setup SwipeToRefreshLayout
        market_activity_content_srl.setColorScheme(R.color.colorAccent)
        market_activity_content_srl.setOnRefreshListener { mPresenter.subscribe() }

        // Setup adapter onClickListener
        adapter.getPositionClicks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { symbol -> startActivity(StockActivity.getIntent(applicationContext).putExtra("symbol", symbol)) }
    }

    override fun showMoreContent(stocks: List<Stock>) {
        (market_activity_content_rv.adapter as MarketAdapter).addMoreStocks(stocks)
        PAGE += 10
        LOADING = false
    }

    override fun showLoading() { market_activity_content_srl.isRefreshing = true }

    override fun hideLoading() { market_activity_content_srl.isRefreshing = false }

    override fun showContent(stocks: List<Stock>) { (market_activity_content_rv.adapter as MarketAdapter).addStocks(stocks) }

    override fun setSymbolList(symbols: List<String>) { mSymbols = symbols }

    override fun updateMoney(money: String) { money_view_text.text = "$$money" }

    override fun showError(error: String) { Toasty.error(applicationContext, error, Toast.LENGTH_LONG).show() }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, MarketActivity::class.java)
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