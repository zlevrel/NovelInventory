package zoupyote.novelinventory;

import android.Manifest;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lvNovel;
    private List<Novel> novels;
    private NovelAdapter adapter;
    private FloatingActionButton add;
    private Spinner spinner;
    private List spinner_items;
    private final int PERMISSIONS_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Donner la permission en écriture et lecture
        getPermissions();


        // Initialisation / Récupération de la liste de romans
        if(savedInstanceState==null){
            novels = new ArrayList<>();
            ImportDataFile();
        } else {
            novels = (ArrayList<Novel>) savedInstanceState.get("Novels");
        }


        // Binding des éléments graphiques
        adapter = new NovelAdapter(this,novels);
        lvNovel = findViewById(R.id.lvNovel);
        lvNovel.setAdapter(adapter);
        add = findViewById(R.id.add);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddActivity.class);
                startActivityForResult(intent,0);
            }
        });

        lvNovel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent data = new Intent(view.getContext(), DetailActivity.class);
                data.putExtra("Name",novels.get(position).getName());
                data.putExtra("Writer",novels.get(position).getWriter());
                data.putExtra("Score",novels.get(position).getScoreString());
                data.putExtra("Comment",novels.get(position).getComment());
                data.putExtra("Position",String.valueOf(position));
                startActivityForResult(data,0);
            }
        });



        // Gestion du spinner filtre
        spinner_items = new ArrayList();
        spinner_items.add("Tri par le titre");
        spinner_items.add("Tri par l'auteur");
        spinner_items.add("Tri par le score");
        ArrayAdapter adapter_spinner = new ArrayAdapter(this, android.R.layout.simple_spinner_item, spinner_items);
        adapter_spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = findViewById(R.id.spinner);
        spinner.setAdapter(adapter_spinner);

        // Tri des données selon les titres (par défaut)
        DataSort();

        // Changer le tri des romans
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Log.i("Main","Appel DataSort");
                DataSort();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        // A décommenter pour importer une liste de base
//        try {
//            fillList();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }




    // Gestion des permissions (écriture/lecture)
    public void getPermissions(){

        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != getPackageManager().PERMISSION_GRANTED) {
            //Log.i("Main","Passage dans le if");
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST) {
            if (grantResults.length == 1 &&
                    grantResults[0] == getPackageManager().PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                // showRationale = false if user clicks Never Ask Again, otherwise true
                boolean showRationale = shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS);

                if (showRationale) {
                    // do something here to handle degraded mode
                } else {
                    Toast.makeText(this, "Read Contacts permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }




    // Base de romans initale
    private void fillList() throws IOException {
        InputStream input = getResources().openRawResource(R.raw.novels2);
        BufferedReader br = new BufferedReader(new InputStreamReader(input));
        while(br.ready()) {
            String[] line = br.readLine().split(";");
            if (line.length == 4 ) {
                novels.add(new Novel(line[0],line[1],line[2],line[3]));
                DataSort();
            } else if (line.length == 3) {
                novels.add(new Novel(line[0],line[1],line[2],""));
                DataSort();
            } else Log.i("Main","Error with a line");

            System.out.println(novels);
        }
        br.close();
    }



    // Surcharges de fonctions pour la persistence des données
    public void onSaveInstanceState(Bundle bundle){
        bundle.putParcelableArrayList("Novels",(ArrayList<Novel>)novels);
        super.onSaveInstanceState(bundle);
    }


    // Importer les romans stockés en mémoire
    public void ImportDataFile(){
        //Log.i("Main","ImportDataFile");
        File dataFile = new File(this.getFilesDir().getAbsolutePath() + File.separator + "AppNovels.txt");
        // VERSION INITIALE File dataFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "dataFile.txt");
        //File dataFile = new File(Environment.getExternalStorageDirectory(), "dataFile.txt");
        if (dataFile != null) {
            // Reads the data from the file
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(dataFile));
                String line = reader.readLine();
                Type listType = new TypeToken<ArrayList<Novel>>(){}.getType();
                novels = new Gson().fromJson(line,listType);
                //Log.i("Main",line);
                reader.close();
            } catch (Exception e) {
                Log.i("Main", "ERROR : " + e.getMessage());
            }
        }

        super.onResume();
    }



    // Trier la liste de romans
    public void DataSort(){
        String sort_type = "" + spinner.getSelectedItem();
        if(sort_type.equals("Tri par le titre")){
            novels.sort(new NovelComparatorByName());
            adapter.notifyDataSetChanged();
        }else if(sort_type.equals("Tri par l'auteur")){
            novels.sort(new NovelComparatorByWriter());
            adapter.notifyDataSetChanged();
        }else {
            novels.sort(new NovelComparatorByScore());
            adapter.notifyDataSetChanged();
        }
    }




    public void onStop(){
        //Log.i("Main","ON STOP");
        //Log.e("Permissions", checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE));

        /*
         *
         * https://developer.android.com/training/data-storage/files#java
         * https://developer.android.com/reference/android/content/Context.html#openFileInput(java.lang.String)
         * https://developer.android.com/reference/java/io/FileInputStream.html#read()
         *
         * */

        String json = new Gson().toJson(novels);
        //Log.i("Main", json);


        try {
            // If the file does not exists, it is created.
            //File dataFile = new File("/storage/dataFile.txt");
            File dataFile = new File(this.getFilesDir(), "AppNovels.txt");

            if (!dataFile.exists()) {
                dataFile.createNewFile();
            }else {
                dataFile.delete();
                dataFile.createNewFile();
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile, true /*append*/));
            writer.write(json);
            writer.flush();
            writer.close();

            //Permet de lire les premiers caractères du fichier (pour vérifier que le premier nom n'est plus A silent voice)
            //FileInputStream fis = new FileInputStream(dataFile);
//            for(int i =0; i<15; i++){
//                //Log.i("FILE", Character.toString((char)fis.read()) );
//            }

        } catch (IOException e) {
            Log.e("Main", "ERROR : " + e.getMessage());
        }


        super.onStop();
    }





    // Retour d'activités
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        if(resultCode == 1){
            Novel novel = new Novel(data.getStringExtra("Name"),data.getStringExtra("Writer"),data.getStringExtra("Score"),data.getStringExtra("Comment"));
            novels.add(novel);
            DataSort();
        }else if(resultCode == 2){
            novels.remove(Integer.parseInt(data.getStringExtra("Position")));
            adapter.notifyDataSetChanged();
        }else if(resultCode == 4) {
            novels.get(Integer.parseInt(data.getStringExtra("Position"))).setName(data.getStringExtra("Name"));
            novels.get(Integer.parseInt(data.getStringExtra("Position"))).setWriter(data.getStringExtra("Writer"));
            novels.get(Integer.parseInt(data.getStringExtra("Position"))).setComment(data.getStringExtra("Comment"));
            novels.get(Integer.parseInt(data.getStringExtra("Position"))).setScore(data.getStringExtra("Score"));
            adapter.notifyDataSetChanged();
            DataSort();
        }
    }

}
