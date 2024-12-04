package com.dripemporium.weather.data

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object DatabaseBuilder {
    private var INSTANCE: WeatherDatabase? = null

    fun getDatabase(context: Context): WeatherDatabase {
        if (INSTANCE == null) {
            synchronized(WeatherDatabase::class) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherDatabase::class.java,
                    "weather_database"
                )
                    //.addMigrations(MIGRATION_1_2)
                    .build()
            }
        }
        return INSTANCE!!
    }
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE weather_data ADD COLUMN date REAL NULL")
        database.execSQL("ALTER TABLE weather_data ADD COLUMN temperature REAL NULL")
        database.execSQL("ALTER TABLE weather_data ADD COLUMN humidity REAL NULL")
        database.execSQL("ALTER TABLE weather_data ADD COLUMN temperature REAL NULL")
        database.execSQL("ALTER TABLE weather_data ADD COLUMN condition REAL NULL")
    }
}