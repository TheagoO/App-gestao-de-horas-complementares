package com.example.apphorasmais;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.widget.SearchView;

import com.example.apphorasmais.adapter.EscopoAdapter;
import com.example.apphorasmais.model.facade.Facade;
import com.example.apphorasmais.model.entity.Escopo;

import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class GerenciarEscopo extends AppCompatActivity {

    private ImageView voltar;
    private SearchView pesquisar;
    private ImageView novoEscopo;
    private RecyclerView recyclerView;
    private EscopoAdapter escopoAdapter;
    private Facade facade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciar_escopo);
        facade = new Facade();
        voltar();
        abrirTelaCadastroEscopo();
        constroiRecyclerView();
        pesquisarEscopo();
    }

    protected void pesquisarEscopo() {
        pesquisar = (SearchView) findViewById(R.id.gerenciarEscopoPesquisar);
        pesquisar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Escopo> lista = facade.pesquisarEscopo(GerenciarEscopo.this, newText);
                carregaRecyclerView(lista);
                if(newText.isEmpty() || newText == null){
                    carregaListaDados();
                }
                return false;
            }
        });
    }

    protected void constroiRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewEscopo);

        defineLayoutRecyclerView();
    }

    protected void defineLayoutRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        carregaListaDados();
    }

    protected void carregaListaDados() {
        List<Escopo> lista = facade.listarEscopos(this);

        carregaRecyclerView(lista);
    }

    protected void carregaRecyclerView(List<Escopo> lista) {
        escopoAdapter = new EscopoAdapter(lista);
        recyclerView.setAdapter(escopoAdapter);
    }

    protected void abrirTelaCadastroEscopo() {
        novoEscopo = (ImageView) findViewById(R.id.gerenciarEscopoNovo);
        novoEscopo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GerenciarEscopo.this, CadastroEscopo.class);
                startActivity(i);
                finish();
            }
        });
    }

    protected void voltar() {
        voltar = (ImageView) findViewById(R.id.gerenciarEscopoVoltar);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaAdministrador();
            }
        });
    }

    public void onBackPressed(){
        abrirTelaAdministrador();
    }

    protected void abrirTelaAdministrador() {
        Intent i = new Intent(GerenciarEscopo.this, Administrador.class);
        startActivity(i);
        finish();
    }
}