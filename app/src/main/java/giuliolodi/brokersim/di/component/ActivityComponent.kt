package giuliolodi.brokersim.di.component

import dagger.Component
import giuliolodi.brokersim.di.scope.PerActivity
import giuliolodi.brokersim.di.module.ActivityModule
import giuliolodi.brokersim.ui.assets.AssetsActivity
import giuliolodi.brokersim.ui.buydialog.BuyDialogFragment
import giuliolodi.brokersim.ui.market.MarketActivity
import giuliolodi.brokersim.ui.stock.StockActivity

@PerActivity
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject(assetsActivity: AssetsActivity)

    fun inject(stockActivity: StockActivity)

    fun inject(marketActivity: MarketActivity)

    fun inject(buyDialogFragment: BuyDialogFragment)

}