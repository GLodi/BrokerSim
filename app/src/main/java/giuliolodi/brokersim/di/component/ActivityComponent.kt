/*
 * Copyright 2017 GLodi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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