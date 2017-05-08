package giuliolodi.brokersim.di.module

import android.app.Activity
import android.content.Context
import dagger.Module
import dagger.Provides
import giuliolodi.brokersim.di.scope.ActivityContext
import giuliolodi.brokersim.di.scope.PerActivity
import giuliolodi.brokersim.ui.assets.AssetsContract
import giuliolodi.brokersim.ui.assets.AssetsPresenter
import giuliolodi.brokersim.ui.buydialog.BuyDialogContract
import giuliolodi.brokersim.ui.buydialog.BuyDialogPresenter
import giuliolodi.brokersim.ui.market.MarketContract
import giuliolodi.brokersim.ui.market.MarketPresenter
import giuliolodi.brokersim.ui.stock.StockContract
import giuliolodi.brokersim.ui.stock.StockPresenter
import io.reactivex.disposables.CompositeDisposable

@Module
class ActivityModule(val activity: Activity) {

    @Provides
    @ActivityContext
    fun provideContext(): Context {
        return activity
    }

    @Provides
    fun provideActivity(): Activity {
        return activity
    }

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Provides
    @PerActivity
    fun provideMainPresenter(presenter: AssetsPresenter<AssetsContract.View>): AssetsContract.Presenter<AssetsContract.View> {
        return presenter
    }

    @Provides
    fun provideStockPresenter(presenter: StockPresenter<StockContract.View>): StockContract.Presenter<StockContract.View> {
        return presenter
    }

    @Provides
    fun provideMarketPresenter(presenter: MarketPresenter<MarketContract.View>): MarketContract.Presenter<MarketContract.View> {
        return presenter
    }

    @Provides
    fun provideBuyDialogPresenter(presenter: BuyDialogPresenter<BuyDialogContract.View>): BuyDialogContract.Presenter<BuyDialogContract.View> {
        return presenter
    }

}