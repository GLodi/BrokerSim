package giuliolodi.financegame.ui.main

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import giuliolodi.financegame.R
import giuliolodi.financegame.model.StockDb
import giuliolodi.financegame.ui.base.BaseActivity
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.main_activity_content.*
import javax.inject.Inject

class MainActivity : BaseActivity(), MainContract.View {

    @Inject lateinit var mPresenter: MainContract.Presenter<MainContract.View>

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

        main_activity_fab.setOnClickListener { mPresenter.addStock() }

        main_activity_content_rv.layoutManager = LinearLayoutManager(applicationContext)
        main_activity_content_rv.adapter = MainAdapter()

        main_activity_content_srl.setColorScheme(R.color.colorAccent)
        main_activity_content_srl.setOnRefreshListener { mPresenter.subscribe() }
    }

    override fun showContent(stocks: List<StockDb>) {
        (main_activity_content_rv.adapter as MainAdapter).addStocks(stocks)
    }

    override fun showLoading() {
        main_activity_content_srl.isRefreshing = true
    }

    override fun hideLoading() {
        main_activity_content_srl.isRefreshing = false
    }

    override fun onDestroy() {
        mPresenter.onDetach()
        super.onDestroy()
    }

}
