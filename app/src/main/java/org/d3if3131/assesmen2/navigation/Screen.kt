package org.d3if3131.assesmen2.navigation

sealed class Screen(val route: String) {
    data object Main: Screen("mainScreen")
    data object Cek: Screen("cekScreen")
    data object About: Screen("aboutScreen")
    object FormBaru: Screen("detailScreen")
    object FormUbah : Screen("detailScreen/{id}") {
        fun withId(id: Long) = "detailScreen/$id"
    }
}