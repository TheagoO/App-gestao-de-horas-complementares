package com.example.apphorasmais;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Thiago Ferreira Assumpção
 */

public class Coordenador extends AppCompatActivity {

    private com.example.apphorasmais.model.entity.Coordenador coordenador;
    private ImageView sair;
    private TextView nome;
    private Button requisicoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordenador);
        recuperarParametros();
        sair();
        defineNomeUsuarioLogado();
        abrirTelaSolicitacoes();
    }

    protected void defineNomeUsuarioLogado() {
        nome = (TextView) findViewById(R.id.coordenadorNome);
        nome.setText(coordenador.getNome());
    }

    protected void abrirTelaSolicitacoes() {
        requisicoes = (Button) findViewById(R.id.coordenadorRequisicoes);
        requisicoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Coordenador.this, ListaSolicitacoes.class);
                i.putExtra("COORDENADOR", coordenador);
                startActivity(i);
                finish();
            }
        });
    }

    protected void sair() {
        sair = (ImageView) findViewById(R.id.coordenadorSair);
        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaLogin();
            }
        });
    }

    public void onBackPressed(){
        abrirTelaLogin();
    }

    protected void abrirTelaLogin() {
        Intent i = new Intent(Coordenador.this, Login.class);
        startActivity(i);
        finish();
    }

    protected void recuperarParametros(){
        Bundle bundle = getIntent().getExtras();
        coordenador = (com.example.apphorasmais.model.entity.Coordenador) bundle.get("COORDENADOR");
    }
}