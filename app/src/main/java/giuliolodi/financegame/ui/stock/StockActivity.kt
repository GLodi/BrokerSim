package giuliolodi.financegame.ui.stock

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import giuliolodi.financegame.R
import giuliolodi.financegame.models.StockDb
import giuliolodi.financegame.ui.base.BaseActivity
import kotlinx.android.synthetic.main.stock_activity.*
import javax.inject.Inject
import android.view.WindowManager

class StockActivity : BaseActivity(), StockContract.View {

    @Inject lateinit var mPresenter: StockContract.Presenter<StockContract.View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.stock_activity)

        initLayout()

        getActivityComponent().inject(this)

        mPresenter.onAttach(this)

        // Get stockDb from position in array
        mPresenter.getStockDb(intent.getIntExtra("position", 0))
    }

    fun initLayout() {
        setSupportActionBar(stock_activity_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        stock_activity_fab.setOnClickListener {  }
    }

    override fun updateViewWithStockDb(stockDb: StockDb) {
        stock_activity_collapsing_toolbar.title = stockDb.symbol
        stock_activity_collapsing_toolbar.setContentScrimColor(stockDb.iconColor)
        stock_activity_image.setBackgroundColor(stockDb.iconColor)

        // Setup statusBar color if SDK > Lollipop
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.setStatusBarColor(stockDb.iconColorDark)
        }
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