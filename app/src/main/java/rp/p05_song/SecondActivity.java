package rp.p05_song;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    ListView songView;
    ArrayList<Song> al;
    Button fiveStarButton;
    ArrayAdapter aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        fiveStarButton = findViewById(R.id.show5);
        songView = findViewById(R.id.songView);
        al = new ArrayList<Song>();

        DBHelper db = new DBHelper(SecondActivity.this);
        al.clear();
        al.addAll(db.getAllSongs());
        db.close();

        aa = new ArrayAdapter(SecondActivity.this, R.layout.list_view, al);
        songView.setAdapter(aa);

        songView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent moveToEditActivity = new Intent(SecondActivity.this, EditActivity.class);

                Song data = al.get(position);

                moveToEditActivity.putExtra("data", data);
                startActivityForResult(moveToEditActivity, 9);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 9){
            songView = (ListView)findViewById(R.id.songView);
            al = new ArrayList<Song>();
            DBHelper dbh = new DBHelper(SecondActivity.this);
            al.clear();
            al.addAll(dbh.getAllSongs());
            dbh.close();
            aa = new ArrayAdapter(this, R.layout.list_view, al);

            songView.setAdapter(aa);
        }
    }
}
