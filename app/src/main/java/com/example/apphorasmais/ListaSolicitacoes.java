package com.example.apphorasmais;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apphorasmais.model.facade.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.apphorasmais.adapter.SolicitacaoAdapter;
import com.example.apphorasmais.repository.Solicitacao;
import com.example.apphorasmais.repository.Coordenador;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class ListaSolicitacoes extends AppCompatActivity {

    private Coordenador coordenador;
    private ImageView voltar;
    private SolicitacaoAdapter solicitacaoAdapter;
    private RecyclerView recyclerView;
    private Facade facade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_solicitacoes);
        facade = new Facade();
        recuperarParametros();
        voltar();
        constroiRecyclerView();
    }

    protected void recuperarParametros(){
        Bundle bundle = getIntent().getExtras();
        coordenador = (Coordenador) bundle.get("COORDENADOR");
    }

    protected void voltar() {
        voltar = (ImageView) findViewById(R.id.listaSolicitacoesVoltar);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaCoordenador();
            }
        });
    }

    public void onBackPressed() {
        abrirTelaCoordenador();
    }

    protected void abrirTelaCoordenador() {
        Intent i = new Intent(ListaSolicitacoes.this, com.example.apphorasmais.Coordenador.class);
        i.putExtra("COORDENADOR", coordenador);
        startActivity(i);
        finish();
    }

    protected void constroiRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.listaSolicitacoesRecyclerView);
        defineLayoutRecyclerView(recyclerView);
    }

    protected void defineLayoutRecyclerView(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        carregaListaDados(recyclerView);
    }

    protected void carregaListaDados(RecyclerView recyclerView) {
        List<Solicitacao> lista = facade.listarSolicitacoesPorCurso(this, coordenador.getId());
        List<Solicitacao> dados = new ArrayList<>();
        iteraSituacoesPendentes(lista, dados);

        solicitacaoAdapter = new SolicitacaoAdapter(dados, coordenador);
        recyclerView.setAdapter(solicitacaoAdapter);
    }

    protected void iteraSituacoesPendentes(List<Solicitacao> lista, List<Solicitacao> dados) {
        for(Solicitacao s : lista){
            if(s.getSituacao().getStatus().contains("Aguardando")){
                dados.add(s);
            }
        }
    }

}
