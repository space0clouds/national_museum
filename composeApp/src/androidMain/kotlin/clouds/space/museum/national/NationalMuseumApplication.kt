package clouds.space.museum.national

import android.app.Application
import clouds.space.museum.national.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NationalMuseumApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()

            androidContext(this@NationalMuseumApplication)

            modules(appModules)
        }
    }
}