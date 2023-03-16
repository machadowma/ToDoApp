package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AlterarActivity extends AppCompatActivity {
    SQLiteDatabase bancoDados;
    EditText txtTitulo, txtDescricao;
    Button btnAlterar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar);

        txtTitulo = (EditText) findViewById(R.id.txtTitulo);
        txtDescricao = (EditText) findViewById(R.id.txtDescricao);
        btnAlterar = (Button) findViewById(R.id.btnAlterar);

        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alterar();
            }
        });


        carregarDados();
    }

    public void carregarDados(){
        Intent intent = getIntent();
        Integer id = intent.getIntExtra("id",0);
        try {
            bancoDados = openOrCreateDatabase("todoapp", MODE_PRIVATE, null);
            Cursor cursor = bancoDados.rawQuery("SELECT id, titulo, descricao FROM tarefa WHERE id = " + id.toString(), null);
            cursor.moveToFirst();

            txtTitulo.setText(cursor.getString(1));
            txtDescricao.setText(cursor.getString(2));

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void alterar(){
        Intent intent = getIntent();
        Integer id = intent.getIntExtra("id",0);

        String titulo = txtTitulo.getText().toString();
        String descricao = txtDescricao.getText().toString();

        try{
            bancoDados = openOrCreateDatabase("todoapp", MODE_PRIVATE, null);
            String sql = "UPDATE tarefa SET titulo=?,descricao=? WHERE id=?";
            SQLiteStatement stmt = bancoDados.compileStatement(sql);
            stmt.bindString(1,titulo);
            stmt.bindString(2,descricao);
            stmt.bindLong(3,id);
            stmt.executeUpdateDelete();
            bancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finish();
    }
}