package com.santog.wizards.data.cache.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.santog.wizards.data.cache.entities.CharacterEntity


@Database(entities = [CharacterEntity::class], version = 1, exportSchema = false) // entities = [Character::class]
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDAO

    /*companion object {
        private lateinit var instance: AppDatabase

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                if (instance == null) {
                    instance = Room
                        .databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "wizards"
                        ).fallbackToDestructiveMigration()
                        .build()
                }

            }
            return instance
        }
    }
*/

    /*companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room
                        .databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "wizards"
                        ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }

        }

    }*/
}

