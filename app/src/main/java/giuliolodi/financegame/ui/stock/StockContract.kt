package giuliolodi.financegame.ui.stock

import giuliolodi.financegame.di.scope.PerActivity
import giuliolodi.financegame.ui.base.BaseContract

interface StockContract {

    interface View : BaseContract.View {



    }

    @PerActivity
    interface Presenter<V: StockContract.View> : BaseContract.Presenter<V> {



    }

}