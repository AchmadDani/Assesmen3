package org.d3if3131.assesmen2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tanaman")
data class Plants(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nama: String,
    val tanggal: String,
    val catatan: String,
    val kondisi: String,
    var selectedOption: String = ""
)