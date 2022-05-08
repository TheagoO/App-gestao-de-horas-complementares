package com.example.apphorasmais;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * @author Thiago Ferreira Assumpção
 */

public class Administrador extends AppCompatActivity {

    private ImageView sair;
    private Button gerenciarUsuario;
    private Button gerenciarCurso;
    private Button gerenciarTurma;
    private Button gerenciarPeriodo;
    private Button gerenciarEscopo;
    private Button botao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);

        abrirTelaGerenciarStatus();

        sair();
        abrirTelaGerenciarUsuario();
        abrirTelaGerenciarCursos();
        abrirTelaGerenciarTurmas();
        abrirTelaGerenciarPeriodo();
        abrirTelaGerenciarEscopo();
    }

    protected void sair() {
        sair = (ImageView) findViewById(R.id.adminSair);
        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaLogin();
            }
        });
    }

    protected void abrirTelaGerenciarUsuario() {
        gerenciarUsuario = (Button) findViewById(R.id.adminGerenciarUsuarios);
        gerenciarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Administrador.this, GerenciarUsuarios.class);
                startActivity(i);
                finish();
            }
        });
    }

    protected void abrirTelaGerenciarCursos() {
        gerenciarCurso = (Button) findViewById(R.id.adminGerenciarCurso);
        gerenciarCurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Administrador.this, GerenciarCurso.class);
                startActivity(i);
                finish();
            }
        });
    }

    protected void abrirTelaGerenciarTurmas() {
        gerenciarTurma = (Button) findViewById(R.id.adminGerenciarTurma);
        gerenciarTurma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Administrador.this, GerenciarTurma.class);
                startActivity(i);
                finish();
            }
        });
    }

    protected void abrirTelaGerenciarPeriodo() {
        gerenciarPeriodo = (Button) findViewById(R.id.adminGerenciarPeriodo);
        gerenciarPeriodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Administrador.this, GerenciarPeriodo.class);
                startActivity(i);
                finish();
            }
        });
    }

    protected void abrirTelaGerenciarEscopo() {
        gerenciarEscopo = (Button) findViewById(R.id.adminGerenciarEscopo);
        gerenciarEscopo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Administrador.this, GerenciarEscopo.class);
                startActivity(i);
                finish();
            }
        });
    }

    protected void abrirTelaGerenciarStatus() {
        botao = (Button) findViewById(R.id.botaoparastatus);
        botao.setVisibility(View.INVISIBLE);
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), GerenciarSituacao.class);
                startActivity(i);
                finish();
            }
        });
    }

    public void onBackPressed(){
        abrirTelaLogin();
    }

    protected void abrirTelaLogin() {
        Intent i = new Intent(Administrador.this, Login.class);
        startActivity(i);
        finish();
    }
}