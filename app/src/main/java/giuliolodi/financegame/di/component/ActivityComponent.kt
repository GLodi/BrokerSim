package giuliolodi.financegame.di.component

import dagger.Component
import giuliolodi.financegame.di.scope.PerActivity
import giuliolodi.financegame.di.module.ActivityModule
import giuliolodi.financegame.ui.assets.AssetsActivity
import giuliolodi.financegame.ui.buydialog.BuyDialogFragment
import giuliolodi.financegame.ui.market.MarketActivity
import giuliolodi.financegame.ui.stock.StockActivity

@PerActivity
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject(assetsActivity: AssetsActivity)

    fun inject(stockActivity: StockActivity)

    fun inject(marketActivity: MarketActivity)

    fun inject(buyDialogFragment: BuyDialogFragment)

}