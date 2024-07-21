import androidx.compose.ui.window.ComposeUIViewController
import clouds.space.museum.national.NationalMuseumApp
import clouds.space.museum.national.di.appModules
import org.koin.compose.KoinApplication

fun MainViewController() = ComposeUIViewController {
    KoinApplication(
        application = {
            modules(appModules)
        },
        content = {
            NationalMuseumApp()
        }
    )
}