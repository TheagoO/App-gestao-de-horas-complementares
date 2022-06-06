package com.example.apphorasmais;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apphorasmais.repository.Escopo;
import com.example.apphorasmais.model.facade.*;

/**
 * @author Thiago Ferreira Assumpção
 */

public class CadastroEscopo extends AppCompatActivity {

    private ImageView voltar;
    private TextView escopo;
    private Button cadastrar;
    private Facade facade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_escopo);
        facade = new Facade();
        escopo = (TextView) findViewById(R.id.cadastroEscopoEscopo);
        voltar();
        cadastrarEscopo();
    }

    protected void cadastrarEscopo() {
        cadastrar = (Button) findViewById(R.id.cadastroEscopoCadastrar);
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validaCampos()){
                    Escopo atividade = new Escopo(0, escopo.getText().toString(), null);
                    String retorno = facade.cadastrarEscopo(CadastroEscopo.this, atividade);
                    Toast.makeText(CadastroEscopo.this, retorno, Toast.LENGTH_SHORT).show();
                    resetaCampos();
                }
            }
        });
    }

    protected void resetaCampos(){
        escopo.setText("");
    }

    protected boolean validaCampos(){
        if(escopo.getText().toString().isEmpty() || escopo.getText().length() < 5){
            escopo.setError("Motivo muito curto");
            return false;
        }
        return true;
    }

    protected void voltar() {
        voltar = (ImageView) findViewById(R.id.cadastroEscopoVoltar);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaGerenciarEscopo();
            }
        });
    }

    public void onBackPressed(){
        abrirTelaGerenciarEscopo();
    }

    protected void abrirTelaGerenciarEscopo() {
        Intent i = new Intent(CadastroEscopo.this, GerenciarEscopo.class);
        startActivity(i);
        finish();
    }
}