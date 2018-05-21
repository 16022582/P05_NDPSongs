package rp.p05_song;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class EditActivity extends AppCompatActivity {

    EditText editEditText, editEditTitle, editEditSingers, editEditYears;
    Button update, delete, cancel;
    RadioGroup radioGroup;
    RadioButton one, two, three, four, five;
    RadioButton button;
    Song data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editEditText = findViewById(R.id.editEditID);
        editEditTitle = findViewById(R.id.editEditTitle);
        editEditSingers = findViewById(R.id.editEditSingers);
        editEditYears = findViewById(R.id.editEditYear);
        radioGroup = findViewById(R.id.editGroupStars);
        one = findViewById(R.id.radio1);
        two = findViewById(R.id.radio2);
        three = findViewById(R.id.radio3);
        four = findViewById(R.id.radio4);
        five = findViewById(R.id.radio5);
        update = findViewById(R.id.update);
        cancel = findViewById(R.id.cancel);
        delete = findViewById(R.id.delete);

        Intent i = getIntent();
        data = (Song) i.getSerializableExtra("data");

        editEditText.setText(String.valueOf(data.getId()) );
        editEditTitle.setText(data.getTitle());
        editEditSingers.setText(data.getSingers());
        editEditYears.setText(String.valueOf(data.getYear()));

        if(data.getStars() == 5) {
            five.setChecked(true);
        } else if (data.getStars() == 4) {
            four.setChecked(true);
        } else if (data.getStars() == 3) {
            three.setChecked(true);
        } else if (data.getStars() == 2) {
            two.setChecked(true);
        } else {
            one.setChecked(true);
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(EditActivity.this);

                int selectedButtonId = radioGroup.getCheckedRadioButtonId();
                button = findViewById(selectedButtonId);

                data.setTitle(editEditTitle.getText().toString());
                data.setSingers(editEditSingers.getText().toString());
                data.setYear(Integer.parseInt(editEditYears.getText().toString()));
                data.setStars(Integer.parseInt(button.getText().toString()));
                db.updateSong(data);
                db.close();

                setResult(RESULT_OK);
                finish();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(EditActivity.this);
                db.deleteSong(data.getId());
                db.close();
                setResult(RESULT_OK);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

