package giuliolodi.financegame.ui.market

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import giuliolodi.financegame.R
import giuliolodi.financegame.ui.base.BaseActivity
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
        title = "Markets"
    }

    override fun showContent() {
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