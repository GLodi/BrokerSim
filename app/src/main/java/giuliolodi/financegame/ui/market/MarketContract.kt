package giuliolodi.financegame.ui.market

import giuliolodi.financegame.di.scope.PerActivity
import giuliolodi.financegame.ui.base.BaseContract

interface MarketContract {

    interface View : BaseContract.View {

        fun showContent()

    }

    @PerActivity
    interface Presenter<V: MarketContract.View> : BaseContract.Presenter<V> {

        fun subscribe()

    }

}