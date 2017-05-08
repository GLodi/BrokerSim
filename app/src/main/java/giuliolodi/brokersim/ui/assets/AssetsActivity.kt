package giuliolodi.brokersim.ui.assets

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import giuliolodi.brokersim.R
import giuliolodi.brokersim.models.StockDb
import giuliolodi.brokersim.ui.base.BaseActivity
import giuliolodi.brokersim.ui.market.MarketActivity
import giuliolodi.brokersim.ui.stock.StockActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.assets_activity.*
import kotlinx.android.synthetic.main.assets_activity_content.*
import javax.inject.Inject
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.widget.Toast
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.money_view.*

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
        assets_activity_toolbar.addView(layoutInflater.inflate(R.layout.money_view, null), Toolbar.LayoutParams(Gravity.RIGHT))
        title = "Assets"

        val adapter: AssetsAdapter = AssetsAdapter()
        adapter.setHasStableIds(true)

        assets_activity_content_rv.layoutManager = LinearLayoutManager(applicationContext)
        assets_activity_content_rv.adapter = adapter
        assets_activity_content_rv.isNestedScrollingEnabled = false
        assets_activity_content_rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (dy > 0)
                    assets_activity_fab.hide()
                else if (dy < 0)
                    assets_activity_fab.show()
            }
        })

        assets_activity_content_srl.setColorScheme(R.color.colorAccent)
        assets_activity_content_srl.setOnRefreshListener { mPresenter.subscribe() }

        assets_activity_fab.setOnClickListener { startActivity(MarketActivity.getIntent(applicationContext)) }

        // Click listener on rv. Calls Presenter passing stockDb position in array.
        adapter.getPositionClicks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { symbol -> startActivity(StockActivity.getIntent(applicationContext).putExtra("symbol", symbol)) }
    }

    override fun showContent(stocks: List<StockDb>) { (assets_activity_content_rv.adapter as AssetsAdapter).addStocks(stocks) }

    override fun clearAdapter() { (assets_activity_content_rv.adapter as AssetsAdapter).clearAdapter() }

    override fun showLoading() { assets_activity_content_srl.isRefreshing = true }

    override fun hideLoading() { assets_activity_content_srl.isRefreshing = false }

    override fun showNoStocksMessage() { assets_activity_content_nostocks.visibility = View.VISIBLE }

    override fun hideNoStocksMessage() { assets_activity_content_nostocks.visibility = View.GONE }

    override fun showError(error: String) { Toasty.error(applicationContext, error, Toast.LENGTH_LONG).show() }

    override fun updateMoney(money: String) { money_view_text.text = "$$money" }

    override fun onDestroy() {
        mPresenter.onDetach()
        super.onDestroy()
    }

}
