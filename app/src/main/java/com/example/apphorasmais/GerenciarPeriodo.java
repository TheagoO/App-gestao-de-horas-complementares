package com.example.apphorasmais;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.apphorasmais.repository.PeriodoLetivo;
import com.example.apphorasmais.model.facade.Facade;
import com.example.apphorasmais.adapter.PeriodoAdapter;

import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class GerenciarPeriodo extends AppCompatActivity {

    private ImageView voltar;
    private SearchView pesquisar;
    private ImageView novoPeriodo;
    private RecyclerView recyclerViewPeriodos;
    private PeriodoAdapter periodoAdapter;
    private Facade facade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciar_periodo);
        facade = new Facade();
        voltar();
        pesquisarPeriodo();
        abrirTelaCadastroPeriodo();
        constroiRecyclerView();
    }

    protected void constroiRecyclerView() {
        recyclerViewPeriodos = findViewById(R.id.recyclerGerenciarPeriodo);

        defineLayoutRecyclerView();
    }

    protected void defineLayoutRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewPeriodos.setLayoutManager(linearLayoutManager);

        carregaListaDados();
    }

    protected void carregaListaDados() {
        List<PeriodoLetivo> lista = facade.listarPeriodos(this);

        carregaRecyclerView(lista);
    }

    protected void carregaRecyclerView(List<PeriodoLetivo> lista) {
        periodoAdapter = new PeriodoAdapter(lista);
        recyclerViewPeriodos.setAdapter(periodoAdapter);
    }

    protected void abrirTelaCadastroPeriodo() {
        novoPeriodo = (ImageView) findViewById(R.id.gerenciarPeriodoNovo);
        novoPeriodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GerenciarPeriodo.this, CadastroPeriodo.class);
                startActivity(i);
                finish();
            }
        });
    }

    protected void pesquisarPeriodo() {
        pesquisar = (SearchView) findViewById(R.id.gerenciarPeriodoPesquisar);
        pesquisar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<PeriodoLetivo> lista = facade.pesquisarPeriodo(GerenciarPeriodo.this, newText);
                carregaRecyclerView(lista);
                if (newText.isEmpty() || newText == null) {
                    carregaListaDados();
                }

                return false;
            }
        });
    }

    protected void voltar() {
        voltar = (ImageView) findViewById(R.id.gerenciarPeriodoVoltar);
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
        Intent i = new Intent(GerenciarPeriodo.this, Administrador.class);
        startActivity(i);
        finish();
    }
}