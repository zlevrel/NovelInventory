package zoupyote.novelinventory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {

    private Button validate;
    private TextView novelName;
    private TextView novelWriter;
    private TextView novelComment;
    private Spinner spinnerScore;
    private List scores;
    private boolean add_novel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        add_novel = false;

        novelName = findViewById(R.id.novelName);
        novelWriter = findViewById(R.id.novelWriter);
        novelComment = findViewById(R.id.novelComment);
        spinnerScore = findViewById(R.id.spinnerScore);
        validate = findViewById(R.id.validate);

        scores = new ArrayList();
        scores.add("Mauvais");
        scores.add("Bof");
        scores.add("Pas mal");
        scores.add("Bien");
        scores.add("Très bien");
        scores.add("Coup de cœur");
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, scores);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerScore.setAdapter(adapter);

        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_novel = true;
                finish();
            }
        });
    }

    public void finish(){
        Intent result = new Intent();
        if(add_novel) {
            result.putExtra("Name", "" + novelName.getText());
            result.putExtra("Writer", "" + novelWriter.getText());
            result.putExtra("Score", "" + spinnerScore.getSelectedItem());
            result.putExtra("Comment", "" + novelComment.getText());
            setResult(1, result);
        }else setResult(3, result);
        super.finish();
    }


}
