package org.d3if3131.assesmen2.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.d3if3131.assesmen2.database.TanamanDao
import org.d3if3131.assesmen2.model.Plants

class MainViewModel(dao: TanamanDao) : ViewModel() {

    val data: StateFlow<List<Plants>> = dao.getMahasiswa().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
}