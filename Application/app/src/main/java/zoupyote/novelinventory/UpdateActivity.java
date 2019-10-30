package zoupyote.novelinventory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class UpdateActivity extends AppCompatActivity {

    Intent intent;
    Boolean modify=false;
    EditText novelName,novelWriter,novelComment;
    Spinner novelScore;
    Button validate;
    List<String> scoreList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        intent = getIntent();

        novelName = findViewById(R.id.novelName);
        novelWriter = findViewById(R.id.novelWriter);
        novelComment = findViewById(R.id.novelComment);

        novelScore = findViewById(R.id.spinnerScore);
        scoreList = new ArrayList<>();
        scoreList.add("Mauvais");
        scoreList.add("Bof");
        scoreList.add("Pas mal");
        scoreList.add("Bien");
        scoreList.add("Très bien");
        scoreList.add("Coup de cœur");

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, scoreList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        novelScore.setAdapter(adapter);




        validate = findViewById(R.id.validate);
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modify = true;
                finish();
            }
        });


        novelName.setText(intent.getStringExtra("Name"));
        novelWriter.setText(intent.getStringExtra("Writer"));
        novelComment.setText(intent.getStringExtra("Comment"));
        switch(intent.getStringExtra("Score")){
            case "Mauvais" : novelScore.setSelection(0); break;
            case "Bof" : novelScore.setSelection(1); break;
            case "Pas mal" : novelScore.setSelection(2); break;
            case "Bien" : novelScore.setSelection(3); break;
            case "Très bien" : novelScore.setSelection(4); break;
            case "Coup de cœur" : novelScore.setSelection(5); break;
        }

        validate.setText("Mettre à jour");


    }



    public void finish(){
        Intent data = new Intent();
        if(modify) {
            data.putExtra("Name", "" + novelName.getText());
            data.putExtra("Writer", "" + novelWriter.getText());
            data.putExtra("Comment", "" + novelComment.getText());
            data.putExtra("Score", "" + novelScore.getSelectedItem());
            data.putExtra("Position",intent.getStringExtra("Position"));
            setResult(4, data);
        }else {
            setResult(5, data);
        }

        super.finish();
    }
}