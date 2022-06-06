package com.example.apphorasmais;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.apphorasmais.repository.Atividade;
import com.example.apphorasmais.adapter.AtividadeAdapter;

import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class MinhasAtividades extends AppCompatActivity {

    private com.example.apphorasmais.repository.Aluno aluno;
    private ImageView voltar;
    private RecyclerView recyclerView;
    private AtividadeAdapter atividadeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minhas_atividades);
        recuperaParametros();
        voltar();
        constroiRecyclerView();
    }

    protected void recuperaParametros() {
        Bundle bundle = getIntent().getExtras();
        aluno = (com.example.apphorasmais.repository.Aluno) bundle.get("ALUNO");
    }

    protected void constroiRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.minhasAtividadesRecyclerView);

        defineLayoutRecyclerView();
    }

    protected void defineLayoutRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        carregaDadosLista();
    }

    protected void carregaDadosLista() {
        List<Atividade> lista = aluno.getHorasComplementares().getAtividades();
        carregaDadosRecyclerView(lista);
    }

    protected void carregaDadosRecyclerView(List<Atividade> lista) {
        atividadeAdapter = new AtividadeAdapter(lista, aluno);
        recyclerView.setAdapter(atividadeAdapter);
    }

    protected void voltar() {
        voltar = (ImageView) findViewById(R.id.minhasAtividadesVoltar);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaAluno();
            }
        });
    }

    public void onBackPressed() {
        abrirTelaAluno();
    }

    protected void abrirTelaAluno() {
        Intent i = new Intent(MinhasAtividades.this, Aluno.class);
        i.putExtra("ALUNO", aluno);
        startActivity(i);
        finish();
    }
}