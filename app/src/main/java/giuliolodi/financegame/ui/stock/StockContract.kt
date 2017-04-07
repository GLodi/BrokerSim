package giuliolodi.financegame.ui.stock

import giuliolodi.financegame.di.scope.PerActivity
import giuliolodi.financegame.models.StockDb
import giuliolodi.financegame.ui.base.BaseContract

interface StockContract {

    interface View : BaseContract.View {

        fun updateViewWithStockDb(stockDb: StockDb)

    }

    @PerActivity
    interface Presenter<V: StockContract.View> : BaseContract.Presenter<V> {

        fun getStockDb(position: Int)

    }

}