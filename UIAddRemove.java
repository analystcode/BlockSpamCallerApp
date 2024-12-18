import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class BlockNumberActivity extends AppCompatActivity {

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_number);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();
    }

    public void addNumber(View view) {
        EditText editText = findViewById(R.id.phoneNumberInput);
        String number = editText.getText().toString();

        BlockedNumber blockedNumber = new BlockedNumber();
        blockedNumber.phoneNumber = number;

        new Thread(() -> db.blockedNumberDao().insert(blockedNumber)).start();
    }
}
