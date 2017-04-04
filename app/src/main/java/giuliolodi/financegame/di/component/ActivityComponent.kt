package giuliolodi.financegame.di.component

import dagger.Component
import giuliolodi.financegame.di.scope.PerActivity
import giuliolodi.financegame.di.module.ActivityModule
import giuliolodi.financegame.ui.fragment.Fragment
import giuliolodi.financegame.ui.main.MainActivity

@PerActivity
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(fragment: Fragment)

}