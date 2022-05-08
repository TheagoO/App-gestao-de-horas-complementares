package com.example.apphorasmais;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apphorasmais.model.facade.*;
import com.example.apphorasmais.model.entity.Situacao;

/**
 * @author Thiago Ferreira Assumpção
 */

public class CadastroSituacao extends AppCompatActivity {

    private ImageView voltar;
    private TextView situacao;
    private Button cadastrar;
    private Facade facade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_situacao);
        facade = new Facade();
        situacao = (TextView) findViewById(R.id.cadastroSituacaoSituacao);
        voltar();
        cadastrarSituacao();
    }

    protected void cadastrarSituacao() {
        cadastrar = (Button) findViewById(R.id.cadastroSituacaoCadastrar);
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validaCampos()){
                    Situacao status = new Situacao(0, situacao.getText().toString(), null);
                    String retorno = facade.cadastrarStatus(CadastroSituacao.this, status);
                    Toast.makeText(CadastroSituacao.this, retorno, Toast.LENGTH_SHORT).show();
                    resetaCampos();
                }
            }
        });
    }

    protected void resetaCampos(){
        situacao.setText("");
    }

    protected boolean validaCampos(){
        if(situacao.getText().toString().isEmpty() || situacao.getText().length() < 5){
            situacao.setError("Status muito curto");
            return false;
        }
        return true;
    }

    protected void voltar() {
        voltar = (ImageView) findViewById(R.id.cadastroSituacaoVoltar);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaGerenciarSituacao();
            }
        });
    }

    public void onBackPressed(){
        abrirTelaGerenciarSituacao();
    }

    protected void abrirTelaGerenciarSituacao() {
        Intent i = new Intent(CadastroSituacao.this, GerenciarSituacao.class);
        startActivity(i);
        finish();
    }
}