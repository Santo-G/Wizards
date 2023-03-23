package com.santog.wizards.data.cache

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.santog.wizards.domain.model.Character

@Dao
interface CharacterDAO {
    @Query("SELECT * FROM character")
    suspend fun getAll(): List<CharacterEntity>

    @Query("SELECT * FROM character WHERE uid IN (:characterIds)")
    suspend fun loadAllByIds(characterIds: IntArray): List<CharacterEntity>

    @Query("SELECT * FROM character WHERE name LIKE :first")
    suspend fun findByName(first: String): CharacterEntity

    @Insert
    suspend fun insertAll(vararg characters: Character)

    @Delete
    suspend fun delete(character: Character)
}
