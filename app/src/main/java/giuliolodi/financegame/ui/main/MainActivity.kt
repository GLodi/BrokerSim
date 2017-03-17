package giuliolodi.financegame.ui.main

import android.os.Bundle
import giuliolodi.financegame.R
import giuliolodi.financegame.ui.base.BaseActivity
import javax.inject.Inject

class MainActivity : BaseActivity(), MainContract.View {

    @Inject lateinit var mPresenter: MainContract.Presenter<MainContract.View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        getActivityComponent().inject(this)

        initLayout()

        mPresenter.onAttach(this)

        mPresenter.subscribe()

    }

    private fun initLayout() {

    }

    override fun showContent() {
    }

    override fun onDestroy() {
        mPresenter.onDetach()
        super.onDestroy()
    }

}
