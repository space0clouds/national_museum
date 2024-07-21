package clouds.space.museum.national.ui.navigation

sealed class NationalMuseumDestination(val route: String) {
    data object CollectionsScreen : NationalMuseumDestination("collections")
    data object CollectionDetailScreen : NationalMuseumDestination("collection/{id}")
}