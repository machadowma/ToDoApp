package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase bancoDados;
    public ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.lista);

        criarBancoDados();
        //inserirDadosTemp();
        listarDados();
    }


    public void criarBancoDados(){
        try {
            bancoDados = openOrCreateDatabase("todoapp", MODE_PRIVATE, null);
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS tarefa(" +
                    " id INTEGER PRIMARY KEY AUTOINCREMENT" +
                    " , titulo VARCHAR" +
                    " , descricao VARCHAR)");
            //bancoDados.execSQL("DELETE FROM animal");
            bancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void inserirDadosTemp(){
        try{
            bancoDados = openOrCreateDatabase("todoapp", MODE_PRIVATE, null);
            String sql = "INSERT INTO tarefa (titulo,descricao) VALUES (?,?)";
            SQLiteStatement stmt = bancoDados.compileStatement(sql);

            stmt.bindString(1,"Arrumar cama");
            stmt.bindString(2,"Todo dia quando acordo, lembrar de arrumar a cama.");
            stmt.executeInsert();

            stmt.bindString(1,"Tomar café");
            stmt.bindString(2,"Lembrar de comer pra não morrer de inanição");
            stmt.executeInsert();

            stmt.bindString(1,"Assistir Dory");
            stmt.bindString(2,"Saudades da vida da Bel qdo era menor");
            stmt.executeInsert();



            bancoDados.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void listarDados(){
        try {

            bancoDados = openOrCreateDatabase("todoapp", MODE_PRIVATE, null);
            Cursor meuCursor = bancoDados.rawQuery("SELECT id, titulo, descricao FROM tarefa", null);
            ArrayList<String> linhas = new ArrayList<String>();
            ArrayAdapter meuAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    linhas
            );
            listView.setAdapter(meuAdapter);
            //arrayIds = new ArrayList<>();
            meuCursor.moveToFirst();
            do {
                linhas.add(meuCursor.getString(0) + " - " + meuCursor.getString(1));
                //arrayIds.add(meuCursor.getInt(0));
            } while(meuCursor.moveToNext());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}