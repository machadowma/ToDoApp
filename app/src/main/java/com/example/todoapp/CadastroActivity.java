package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CadastroActivity extends AppCompatActivity {
    EditText txtTitulo, txtDescricao;
    Button btnCadastro;
    SQLiteDatabase bancoDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        txtTitulo = (EditText) findViewById(R.id.txtTitulo);
        txtDescricao = (EditText) findViewById(R.id.txtDescricao);
        btnCadastro = (Button) findViewById(R.id.btnAlterar);

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrar();
            }
        });

    }

    public void cadastrar(){
        if( !TextUtils.isEmpty(txtTitulo.getText().toString()) && !TextUtils.isEmpty(txtDescricao.getText().toString()) ){
            try{
                bancoDados = openOrCreateDatabase("todoapp", MODE_PRIVATE, null);
                String sql = "INSERT INTO tarefa (titulo,descricao) VALUES (?,?)";
                SQLiteStatement stmt = bancoDados.compileStatement(sql);
                stmt.bindString(1,txtTitulo.getText().toString());
                stmt.bindString(2,txtDescricao.getText().toString());
                stmt.executeInsert();
                bancoDados.close();
                finish();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}