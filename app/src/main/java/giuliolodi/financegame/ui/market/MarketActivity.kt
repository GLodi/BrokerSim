package giuliolodi.financegame.ui.market

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import giuliolodi.financegame.R
import giuliolodi.financegame.ui.base.BaseActivity
import kotlinx.android.synthetic.main.market_activity.*
import kotlinx.android.synthetic.main.market_activity_content.*
import yahoofinance.Stock
import javax.inject.Inject

class MarketActivity : BaseActivity(), MarketContract.View {

    @Inject lateinit var mPresenter: MarketContract.Presenter<MarketContract.View>

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
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        title = "Markets"

        val adapter: MarketAdapter = MarketAdapter()
        adapter.setHasStableIds(true)
        val llm = LinearLayoutManager(applicationContext)
        llm.initialPrefetchItemCount = 4

        market_activity_content_rv.layoutManager = llm
        market_activity_content_rv.adapter = adapter
        market_activity_content_rv.setHasFixedSize(true)

        market_activity_content_srl.setColorScheme(R.color.colorAccent)
        market_activity_content_srl.setOnRefreshListener { mPresenter.subscribe() }
    }

    override fun showLoading() {
        market_activity_content_srl.isRefreshing = true
    }

    override fun hideLoading() {
        market_activity_content_srl.isRefreshing = false
    }

    override fun showContent(stocks: List<Stock>) {
        (market_activity_content_rv.adapter as MarketAdapter).addStocks(stocks)
    }

    override fun showError(error: String) {
        Snackbar.make(currentFocus, error, Snackbar.LENGTH_LONG).show()
    }

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