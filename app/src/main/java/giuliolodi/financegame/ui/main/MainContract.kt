package giuliolodi.financegame.ui.main

import giuliolodi.financegame.di.PerActivity
import giuliolodi.financegame.ui.base.BaseContract

interface MainContract {

    interface View : BaseContract.View {

        fun showLoading()

        fun hideLoading()

        fun showContent()

    }

    @PerActivity
    interface Presenter<V: MainContract.View> : BaseContract.Presenter<V> {

        fun subscribe()

    }
}