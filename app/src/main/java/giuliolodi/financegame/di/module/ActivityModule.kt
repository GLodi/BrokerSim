package giuliolodi.financegame.di.module

import android.app.Activity
import android.content.Context
import dagger.Module
import dagger.Provides
import giuliolodi.financegame.di.scope.ActivityContext
import giuliolodi.financegame.di.scope.PerActivity
import giuliolodi.financegame.ui.assets.AssetsContract
import giuliolodi.financegame.ui.assets.AssetsPresenter
import giuliolodi.financegame.ui.market.MarketContract
import giuliolodi.financegame.ui.market.MarketPresenter
import giuliolodi.financegame.ui.stock.StockContract
import giuliolodi.financegame.ui.stock.StockPresenter
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

}