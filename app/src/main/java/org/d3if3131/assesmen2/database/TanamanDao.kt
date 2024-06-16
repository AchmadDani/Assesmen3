package org.d3if3131.assesmen2.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.d3if3131.assesmen2.model.Plants

@Dao
interface TanamanDao {

    @Insert
    suspend fun insert(plants: Plants)

    @Update
    suspend fun update(plants: Plants)

    @Query("SELECT * FROM tanaman ORDER BY nama ASC")
    fun getMahasiswa(): Flow<List<Plants>>

    @Query("SELECT * FROM tanaman WHERE id = :id")
    suspend fun getTanamanById(id: Long): Plants?
    @Query("DELETE FROM tanaman WHERE id = :id")
    suspend fun deleteById(id: Long)
}