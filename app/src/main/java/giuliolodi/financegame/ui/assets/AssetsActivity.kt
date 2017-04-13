package giuliolodi.financegame.ui.assets

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import giuliolodi.financegame.R
import giuliolodi.financegame.models.StockDb
import giuliolodi.financegame.ui.base.BaseActivity
import giuliolodi.financegame.ui.market.MarketActivity
import giuliolodi.financegame.ui.stock.StockActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.main_activity_content.*
import javax.inject.Inject

class AssetsActivity : BaseActivity(), AssetsContract.View {

    @Inject lateinit var mPresenter: AssetsContract.Presenter<AssetsContract.View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        initLayout()

        getActivityComponent().inject(this)

        mPresenter.onAttach(this)

        mPresenter.subscribe()
    }

    private fun initLayout() {
        setSupportActionBar(main_activity_toolbar)
        title = "Assets"

        val adapter: AssetsAdapter = AssetsAdapter()
        adapter.setHasStableIds(true)
        val llm = LinearLayoutManager(applicationContext)
        llm.initialPrefetchItemCount = 4

        main_activity_content_rv.layoutManager = llm
        main_activity_content_rv.adapter = adapter
        main_activity_content_rv.setHasFixedSize(true)

        main_activity_content_srl.setColorScheme(R.color.colorAccent)
        main_activity_content_srl.setOnRefreshListener { mPresenter.subscribe() }

        main_activity_fab.setOnClickListener { startActivity(MarketActivity.getIntent(applicationContext)) }

        // Click listener on rv. Calls Presenter passing stockDb position in array.
        adapter.getPositionClicks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { position -> startActivity(StockActivity.getIntent(applicationContext).putExtra("position", position)) }
    }

    override fun showLoading() {
        main_activity_content_srl.isRefreshing = true
    }

    override fun hideLoading() {
        main_activity_content_srl.isRefreshing = false
    }

    override fun showContent(stocks: List<StockDb>) {
        (main_activity_content_rv.adapter as AssetsAdapter).addStocks(stocks)
    }

    override fun showError(error: String) {
        Snackbar.make(currentFocus, error, Snackbar.LENGTH_LONG).show()
    }

    override fun updateMoney(money: String) {
        main_activity_toolbar.title = money
    }

    override fun onDestroy() {
        mPresenter.onDetach()
        super.onDestroy()
    }

}
