package giuliolodi.financegame.ui.stock

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import giuliolodi.financegame.R
import giuliolodi.financegame.ui.base.BaseActivity
import kotlinx.android.synthetic.main.stock_activity.*
import javax.inject.Inject

class StockActivity : BaseActivity(), StockContract.View {

    @Inject lateinit var mPresenter: StockContract.Presenter<StockContract.View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.stock_activity)

        initLayout()
    }

    fun initLayout() {
        stock_activity_toolbar.title = "Stock"
        setSupportActionBar(stock_activity_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)



        stock_activity_image.setBackgroundColor(Color.parseColor("#000000"))
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

}