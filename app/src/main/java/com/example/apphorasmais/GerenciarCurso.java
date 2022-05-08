package com.example.apphorasmais;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.apphorasmais.adapter.CursoAdapter;
import android.widget.ImageView;

import com.example.apphorasmais.model.facade.*;
import com.example.apphorasmais.model.entity.Curso;

import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class GerenciarCurso extends AppCompatActivity {

    private ImageView voltar;
    private SearchView pesquisar;
    private ImageView novoCurso;
    private CursoAdapter cursoAdapter;
    private RecyclerView recyclerViewCursos;
    private Facade facade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciar_curso);
        facade = new Facade();
        voltar();
        constroiRecyclerView();
        pesquisarCurso();
        abrirTelaCadastroCurso();
    }

    protected void constroiRecyclerView() {
        recyclerViewCursos = (RecyclerView) findViewById(R.id.recyclerGerenciarCurso);

        defineLayoutRecyclerView();
    }

    protected void defineLayoutRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewCursos.setLayoutManager(linearLayoutManager);

        carregaListaDados();
    }

    protected void carregaListaDados() {
        List<Curso> lista = facade.listarCursos(GerenciarCurso.this);

        carregaRecyclerView(lista);
    }

    protected void carregaRecyclerView(List<Curso> lista) {
        cursoAdapter = new CursoAdapter(lista);
        recyclerViewCursos.setAdapter(cursoAdapter);
    }

    protected void abrirTelaCadastroCurso() {
        novoCurso = (ImageView) findViewById(R.id.gerenciarCursoNovo);
        novoCurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GerenciarCurso.this, CadastroCurso.class);
                startActivity(i);
                finish();
            }
        });
    }

    protected void pesquisarCurso() {
        pesquisar = (SearchView) findViewById(R.id.gerenciarCursoPesquisar);
        pesquisar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Curso> lista = facade.pesquisarCurso(GerenciarCurso.this, newText);
                carregaRecyclerView(lista);
                if(newText.isEmpty() || newText == null){
                    carregaListaDados();
                }

                return false;
            }
        });
    }

    protected void voltar() {
        voltar = (ImageView) findViewById(R.id.gerenciarCursoVoltar);
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
        Intent i = new Intent(GerenciarCurso.this, Administrador.class);
        startActivity(i);
        finish();
    }
}