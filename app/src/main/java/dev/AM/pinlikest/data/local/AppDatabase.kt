package dev.AM.pinlikest.data.local

import android.content.Context
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [
        Pin::class,
        Mensagem::class
    ],
    version = 1
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun pinsDAO(): PinsDAO
    abstract fun mensagensDAO(): MensagensDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            } else {
                synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_database"
                    )
                        .fallbackToDestructiveMigration(false)
                        .build()
                    INSTANCE = instance
                    return instance
                }
            }
        }
    }
}
fun botaoAlerta(context: Context, mensagem: String) {
    ContextCompat.getMainExecutor(context).execute {
        Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show()
    }
}