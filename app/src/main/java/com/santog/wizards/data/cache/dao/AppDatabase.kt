package com.santog.wizards.data.cache.dao

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Character::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDAO
}

