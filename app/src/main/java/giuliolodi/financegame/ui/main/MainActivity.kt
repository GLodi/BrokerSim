package giuliolodi.financegame.ui.main

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import giuliolodi.financegame.R
import giuliolodi.financegame.ui.base.BaseActivity
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.main_activity_content.*
import yahoofinance.Stock
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

        main_activity_fab.setOnClickListener { view -> Snackbar.make(view, "Prova", Snackbar.LENGTH_LONG).show() }

        main_activity_content_rv.layoutManager = LinearLayoutManager(applicationContext)
        main_activity_content_rv.adapter = MainAdapter()
    }

    override fun showContent(stocks: List<Stock>) {
        (main_activity_content_rv.adapter as MainAdapter).addStocks(stocks)
    }

    override fun onDestroy() {
        mPresenter.onDetach()
        super.onDestroy()
    }

}
