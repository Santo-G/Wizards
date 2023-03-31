package com.santog.wizards.data.cache.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.santog.wizards.data.cache.entities.CharacterEntity


@Database(entities = [CharacterEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDAO
}

