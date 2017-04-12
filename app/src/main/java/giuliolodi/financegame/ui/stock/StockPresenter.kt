package giuliolodi.financegame.ui.stock

import giuliolodi.financegame.data.DataManager
import giuliolodi.financegame.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class StockPresenter<V: StockContract.View> : BasePresenter<V>, StockContract.Presenter<V> {

    @Inject
    constructor(mCompositeDisposable: CompositeDisposable, mDataManager: DataManager): super(mCompositeDisposable, mDataManager)

    override fun getStockDb(position: Int) {
        getDataManager().getStockDbAtPosition(position)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { stockDb -> getView().updateViewWithStockDb(stockDb) }
    }

    override fun getStock(symbol: String) {
        getDataManager().getStock(symbol)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { stock -> getView().updateViewWithStock(stock) }
    }

}
