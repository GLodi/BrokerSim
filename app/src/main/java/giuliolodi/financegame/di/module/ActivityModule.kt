package giuliolodi.financegame.di.module

import android.app.Activity
import android.content.Context
import dagger.Module
import dagger.Provides
import giuliolodi.financegame.di.scope.ActivityContext
import giuliolodi.financegame.di.scope.PerActivity
import giuliolodi.financegame.ui.main.MainContract
import giuliolodi.financegame.ui.main.MainPresenter
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
    fun provideMainPresenter(presenter: MainPresenter<MainContract.View>): MainContract.Presenter<MainContract.View> {
        return presenter
    }

}