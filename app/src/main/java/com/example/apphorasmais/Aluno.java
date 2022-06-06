package com.example.apphorasmais;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Thiago Ferreira Assumpção
 */

public class Aluno extends AppCompatActivity {

    private com.example.apphorasmais.repository.Aluno aluno;
    private TextView nome;
    private ImageView sair;
    private TextView totalAtividades;
    private TextView totalHoras;
    private Button novaRequisicao;
    private Button minhasAtividades;
    private Button minhasRequisicoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aluno);
        recuperaParametros();
        sair();
        preencheComponentes();
        abrirTelaNovaRequisicao();
        abrirTelaMinhasAtividades();
        abrirTelaMinhasRequisicoes();
    }

    protected void recuperaParametros() {
        Bundle bundle = getIntent().getExtras();
        aluno = (com.example.apphorasmais.repository.Aluno) bundle.get("ALUNO");
    }

    protected void sair() {
        sair = (ImageView) findViewById(R.id.alunoSair);
        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaLogin();
            }
        });
    }

    protected void abrirTelaLogin() {
        Intent i = new Intent(Aluno.this, Login.class);
        startActivity(i);
        finish();
    }

    protected void preencheComponentes() {
        nome = (TextView) findViewById(R.id.alunoNome);
        nome.setText(aluno.getNome());

        totalAtividades = (TextView) findViewById(R.id.alunoTotalAtividades);
        totalAtividades.setText(String.valueOf(aluno.getHorasComplementares().getTotalAtividade()));

        totalHoras = (TextView) findViewById(R.id.alunoTotalHoras);
        totalHoras.setText(String.valueOf(aluno.getHorasComplementares().getTotal())+"/150");
    }

    protected void abrirTelaNovaRequisicao() {
        novaRequisicao = (Button) findViewById(R.id.alunoNovaRequisicao);
        novaRequisicao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validaLimiteHoras()){
                    Toast.makeText(Aluno.this, "Limite de horas atingido", Toast.LENGTH_SHORT).show();
                }else{
                    Intent i = new Intent(Aluno.this, NovaRequisicao.class);
                    i.putExtra("ALUNO", aluno);
                    startActivity(i);
                    finish();
                }
            }
        });
    }

    protected boolean validaLimiteHoras() {
        if(aluno.getHorasComplementares().getTotal() == 150){
            return false;
        }
        return true;
    }

    protected void abrirTelaMinhasAtividades() {
        minhasAtividades = (Button) findViewById(R.id.alunoMinhasAtividades);
        minhasAtividades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Aluno.this, MinhasAtividades.class);
                i.putExtra("ALUNO", aluno);
                startActivity(i);
                finish();
            }
        });
    }

    protected void abrirTelaMinhasRequisicoes() {
        minhasRequisicoes = (Button) findViewById(R.id.alunoMinhasRequisicoes);
        minhasRequisicoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Aluno.this, MinhasRequisicoes.class);
                i.putExtra("ALUNO", aluno);
                startActivity(i);
                finish();
            }
        });
    }

    public void onBackPressed(){
        abrirTelaLogin();
    }

}