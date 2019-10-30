package zoupyote.novelinventory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private Intent intent;
    private TextView novelName;
    private TextView novelWriter;
    private TextView novelComment;
    private TextView novelScore;
    private Button erase;
    private Button update;
    private boolean erase_novel;
    private boolean update_novel;
    private String position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        intent = getIntent();

        erase_novel = false;
        update_novel = false;
        position = intent.getStringExtra("Position");

        erase = findViewById(R.id.erase);
        update = findViewById(R.id.update);
        novelName = findViewById(R.id.novelName);
        novelWriter = findViewById(R.id.novelWriter);
        novelComment = findViewById(R.id.novelComment);
        novelScore = findViewById(R.id.novelScore);

        novelName.setText(intent.getStringExtra("Name"));
        novelScore.setText(intent.getStringExtra("Score"));
        novelWriter.setText(intent.getStringExtra("Writer"));
        novelComment.setText(intent.getStringExtra("Comment"));

        erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                erase_novel = true;
                finish();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_novel = true;
                Intent data = new Intent(v.getContext(), UpdateActivity.class);
                data.putExtra("Name",intent.getStringExtra("Name"));
                data.putExtra("Writer",intent.getStringExtra("Writer"));
                data.putExtra("Score",intent.getStringExtra("Score"));
                data.putExtra("Comment",intent.getStringExtra("Comment"));
                data.putExtra("Position",position);
                startActivityForResult(data,0);
            }
        });
    }

    public void finish(){
        Intent result = new Intent();
        if(erase_novel){
            result.putExtra("Position","" + position);
            setResult(2,result);
        } else if (update_novel){
            result.putExtra("Name", "" + novelName.getText());
            result.putExtra("Writer", "" + novelWriter.getText());
            result.putExtra("Comment", "" + novelComment.getText());
            result.putExtra("Score", "" + novelScore.getText());
            result.putExtra("Position", "" + position);
            setResult(4,result);
        } else {
            setResult(3);
        }

        super.finish();
    }


    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        if(resultCode==4){
            novelName.setText(data.getStringExtra("Name"));
            novelScore.setText(data.getStringExtra("Score"));
            novelWriter.setText(data.getStringExtra("Writer"));
            novelComment.setText(data.getStringExtra("Comment"));
        }
    }

}
