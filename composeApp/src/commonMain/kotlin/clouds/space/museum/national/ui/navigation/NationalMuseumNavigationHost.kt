package clouds.space.museum.national.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import clouds.space.museum.national.ui.collections.CollectionsRoute
import clouds.space.museum.national.ui.detail.CollectionDetailRoute
import clouds.space.museum.national.ui.navigation.NationalMuseumDestination.CollectionDetailScreen
import clouds.space.museum.national.ui.navigation.NationalMuseumDestination.CollectionsScreen

@Composable
internal fun NationalMuseumNavigationHost(
    modifier: Modifier = Modifier,
    startDestination: NationalMuseumDestination = CollectionsScreen,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(navController, startDestination = startDestination.route, modifier) {
        composable(CollectionsScreen.route) {
            CollectionsRoute(
                onCollectionClick = {
                    navController.navigate(CollectionDetailScreen.route.replace("{id}", it))
                },
            )
        }

        composable(CollectionDetailScreen.route) { entry ->
            val id = entry.arguments
                ?.getString("id")
                ?: return@composable

            CollectionDetailRoute(id, onBackClick = { navController.popBackStack() })
        }
    }
}