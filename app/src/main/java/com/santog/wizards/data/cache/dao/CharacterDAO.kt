package com.santog.wizards.data.cache.dao

import androidx.room.*
import com.santog.wizards.data.cache.entities.CharacterEntity

@Dao
interface CharacterDAO {
    @Query("SELECT * FROM character")
    suspend fun getAll(): List<CharacterEntity>

    @Query("SELECT * FROM character WHERE name LIKE :first")
    suspend fun findByName(first: String): CharacterEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: CharacterEntity)

    @Delete
    suspend fun delete(character: CharacterEntity)

    @Query("DELETE FROM character")
    suspend fun clearTable()
}
