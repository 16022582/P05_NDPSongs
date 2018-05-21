package rp.p05_song;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editTitle, editSinger, editYear;
    Button insert, showList;
    RadioGroup groupStars ;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTitle = findViewById(R.id.editTitle);
        editSinger = findViewById(R.id.editSinger);
        editYear = findViewById(R.id.editYear);
        insert = findViewById(R.id.insert);
        showList = findViewById(R.id.showList);
        groupStars = findViewById(R.id.groupStars);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTitle.getText().toString();
                String singers = editSinger.getText().toString();
                String year = editYear.getText().toString();
                int selectedButtonId = groupStars.getCheckedRadioButtonId();
                radioButton = findViewById(selectedButtonId);

                //If they are not empty
                if (!title.equals("") || !singers.equals("") || !year.equals("")){

                    DBHelper database = new DBHelper(MainActivity.this);
                    database.getWritableDatabase();
                    database.insertSong(title, singers
                            , Integer.parseInt(year)
                            , Integer.parseInt(radioButton.getText().toString()));

                    Toast.makeText(MainActivity.this, "Song inserted", Toast.LENGTH_LONG).show();
                    editTitle.setText("");
                    editSinger.setText("");
                    editYear.setText("");


                }
                //If they are empty
                else {
                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_LONG).show();
                }
            }
        });

        showList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }
}
