package com.gta.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.gta.model.room.dao.BrowseHistoryDao
import com.gta.model.room.dao.ProjectClassifyDao
import com.gta.model.room.entity.Article
import com.gta.model.room.entity.ProjectClassify


@Database(
    entities = [ProjectClassify::class, Article::class],
    version = 2
)
abstract class PlayDatabase : RoomDatabase() {

    abstract fun projectClassifyDao(): ProjectClassifyDao

    abstract fun browseHistoryDao(): BrowseHistoryDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: PlayDatabase? = null

        fun getDatabase(context: Context): PlayDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlayDatabase::class.java,
                    "play_database"
                ).addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}

val MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        //创建新表connect_prod并添加对应的字段
        //PRIMARY KEY(id)将id设置为主键，NOT NULL设置对应的键不能为空
        database.execSQL(
            "CREATE TABLE IF NOT EXISTS `banner_bean` (`uid` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`desc` TEXT NOT NULL, `id` INTEGER NOT NULL, `imagePath` TEXT NOT NULL, `isVisible` INTEGER NOT NULL, " +
                    "`order` INTEGER NOT NULL, `title` TEXT NOT NULL, `type` INTEGER NOT NULL, `url` TEXT NOT NULL, `file_path` TEXT NOT NULL)"
        )
    }
}