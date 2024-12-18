import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class BlockedNumber {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String phoneNumber;
}
