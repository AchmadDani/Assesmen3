package org.d3if3131.assesmen2.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3131.assesmen2.database.TanamanDao
import org.d3if3131.assesmen2.model.Plants

class DetailViewModel(private val dao: TanamanDao) : ViewModel() {

    fun insert(nama: String, tanggal: String, catatan: String, kondisi: String) {
        val plants = Plants(
            nama = nama,
            tanggal = tanggal,
            catatan = catatan,
            kondisi = kondisi
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(plants)
        }
    }

    suspend fun getTanaman(id: Long): Plants? {
        return dao.getTanamanById(id)
    }

    fun update(id: Long, nama: String, tanggal: String, catatan: String, kondisi: String) {
        val plants = Plants(
            id = id,
            nama = nama,
            tanggal = tanggal,
            catatan = catatan,
            kondisi = kondisi
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.update(plants)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }
}