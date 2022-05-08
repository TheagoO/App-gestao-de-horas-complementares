package com.example.apphorasmais;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.apphorasmais.model.entity.Solicitacao;
import com.example.apphorasmais.model.facade.Facade;
import com.example.apphorasmais.adapter.RequisicaoAdapter;

import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class MinhasRequisicoes extends AppCompatActivity {

    private com.example.apphorasmais.model.entity.Aluno aluno;
    private ImageView voltar;
    private RecyclerView recyclerView;
    private RequisicaoAdapter requisicaoAdapter;
    private Facade facade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minhas_requisicoes);
        facade = new Facade();
        recuperaParametros();
        voltar();
        constroiRecyclerView();

    }

    protected void recuperaParametros() {
        Bundle bundle = getIntent().getExtras();
        aluno = (com.example.apphorasmais.model.entity.Aluno) bundle.get("ALUNO");
    }

    protected void constroiRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.minhasRequisicoesRecyclerView);

        defineLayoutRecyclerView();
    }

    protected void defineLayoutRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        carregaDadosLista();
    }

    protected void carregaDadosLista() {
        List<Solicitacao> lista = facade.listarSolicitacoesPorAluno(this, aluno.getId());
        carregaDadosRecyclerView(lista);
    }

    protected void carregaDadosRecyclerView(List<Solicitacao> lista) {
        requisicaoAdapter = new RequisicaoAdapter(lista);
        recyclerView.setAdapter(requisicaoAdapter);
    }

    protected void voltar() {
        voltar = (ImageView) findViewById(R.id.minhasRequisicoesVoltar);
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
        Intent i = new Intent(MinhasRequisicoes.this, Aluno.class);
        i.putExtra("ALUNO", aluno);
        startActivity(i);
        finish();
    }
}