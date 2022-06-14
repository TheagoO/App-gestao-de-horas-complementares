package com.example.apphorasmais;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.apphorasmais.adapter.SituacaoAdapter;
import com.example.apphorasmais.model.entity.Situacao;
import com.example.apphorasmais.model.facade.*;

import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class GerenciarSituacao extends AppCompatActivity {

    private ImageView voltar;
    private SearchView pesquisar;
    private ImageView novaSituacao;
    private SituacaoAdapter situacaoAdapter;
    private RecyclerView recyclerView;
    private Facade facade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciar_situacao);
        facade = new Facade();
        voltar();
        pesquisarSituacao();
        abrirTelaCadastroSituacao();
        constroiRecyclerView();
    }

    protected void constroiRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerGerenciarSituacao);

        defineLayoutRecyclerView();
    }

    protected void defineLayoutRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        carregaListaDados();
    }

    protected void carregaListaDados() {
        List<Situacao> lista = facade.listarStatus(this);
        carregaDadosRecyclerView(lista);
    }

    protected void carregaDadosRecyclerView(List<Situacao> lista) {
        situacaoAdapter = new SituacaoAdapter(lista);

        recyclerView.setAdapter(situacaoAdapter);
    }

    protected void abrirTelaCadastroSituacao() {
        novaSituacao = (ImageView) findViewById(R.id.gerenciarStatusNovo);
        novaSituacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GerenciarSituacao.this, CadastroSituacao.class);
                startActivity(i);
                finish();
            }
        });
    }

    protected void pesquisarSituacao() {
        pesquisar = (SearchView) findViewById(R.id.gerenciarStatusPesquisar);
        pesquisar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Situacao> lista = facade.pesquisarStatus(GerenciarSituacao.this, newText);
                carregaDadosRecyclerView(lista);
                if(newText.isEmpty() || newText == null){
                    carregaListaDados();
                }

                return false;
            }
        });
    }

    protected void voltar() {
        voltar = (ImageView) findViewById(R.id.gerenciarStatusVoltar);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaAdministrador();
            }
        });
    }

    public void onBackPressed() {
        abrirTelaAdministrador();
    }

    protected void abrirTelaAdministrador() {
        Intent i = new Intent(GerenciarSituacao.this, Administrador.class);
        startActivity(i);
        finish();
    }
}