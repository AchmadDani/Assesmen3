package org.d3if3131.assesmen2.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.d3if3131.assesmen2.model.Tanaman

@Dao
interface TanamanDao {

    @Insert
    suspend fun insert(tanaman: Tanaman)

    @Update
    suspend fun update(tanaman: Tanaman)

    @Query("SELECT * FROM tanaman ORDER BY nama ASC")
    fun getMahasiswa(): Flow<List<Tanaman>>

    @Query("SELECT * FROM tanaman WHERE id = :id")
    suspend fun getTanamanById(id: Long): Tanaman?
    @Query("DELETE FROM tanaman WHERE id = :id")
    suspend fun deleteById(id: Long)
}