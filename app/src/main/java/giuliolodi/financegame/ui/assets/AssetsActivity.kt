package giuliolodi.financegame.ui.assets

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import giuliolodi.financegame.R
import giuliolodi.financegame.models.StockDb
import giuliolodi.financegame.ui.base.BaseActivity
import giuliolodi.financegame.ui.market.MarketActivity
import giuliolodi.financegame.ui.stock.StockActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.assets_activity.*
import kotlinx.android.synthetic.main.assets_activity_content.*
import javax.inject.Inject

class AssetsActivity : BaseActivity(), AssetsContract.View {

    @Inject lateinit var mPresenter: AssetsContract.Presenter<AssetsContract.View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.assets_activity)

        initLayout()

        getActivityComponent().inject(this)

        mPresenter.onAttach(this)

        mPresenter.subscribe()
    }

    private fun initLayout() {
        setSupportActionBar(assets_activity_toolbar)
        title = "Assets"

        val adapter: AssetsAdapter = AssetsAdapter()
        adapter.setHasStableIds(true)
        val llm = LinearLayoutManager(applicationContext)
        llm.initialPrefetchItemCount = 4

        assets_activity_content_rv.layoutManager = llm
        assets_activity_content_rv.adapter = adapter
        assets_activity_content_rv.setHasFixedSize(true)

        assets_activity_content_srl.setColorScheme(R.color.colorAccent)
        assets_activity_content_srl.setOnRefreshListener { mPresenter.subscribe() }

        assets_activity_fab.setOnClickListener { startActivity(MarketActivity.getIntent(applicationContext)) }

        // Click listener on rv. Calls Presenter passing stockDb position in array.
        adapter.getPositionClicks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { symbol -> startActivity(StockActivity.getIntent(applicationContext).putExtra("symbol", symbol)) }
    }

    override fun showContent(stocks: List<StockDb>) {
        (assets_activity_content_rv.adapter as AssetsAdapter).addStocks(stocks)
    }

    override fun showLoading() {
        assets_activity_content_srl.isRefreshing = true
    }

    override fun hideLoading() {
        assets_activity_content_srl.isRefreshing = false
    }

    override fun showNoStocksMessage() {
        assets_activity_content_nostocks.visibility = View.VISIBLE
    }

    override fun hideNoStocksMessage() {
        assets_activity_content_nostocks.visibility = View.GONE
    }

    override fun showMessage(message: String) {
        Snackbar.make(window.decorView.rootView, message, Snackbar.LENGTH_LONG).show()
    }

    override fun updateMoney(money: String) {
        assets_activity_toolbar.title = money
    }

    override fun onDestroy() {
        mPresenter.onDetach()
        super.onDestroy()
    }

}
