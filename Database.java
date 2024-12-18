import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {BlockedNumber.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract BlockedNumberDao blockedNumberDao();
}
