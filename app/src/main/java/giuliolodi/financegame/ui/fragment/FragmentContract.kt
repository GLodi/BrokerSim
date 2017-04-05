package giuliolodi.financegame.ui.fragment

import giuliolodi.financegame.di.scope.PerActivity
import giuliolodi.financegame.ui.base.BaseContract

interface FragmentContract {

    interface View : BaseContract.View {

        fun showLoading()

        fun hideLoading()

        fun hideDialog()

    }

    @PerActivity
    interface Presenter<V: FragmentContract.View> : BaseContract.Presenter<V> {

        fun subscribe()

    }

}