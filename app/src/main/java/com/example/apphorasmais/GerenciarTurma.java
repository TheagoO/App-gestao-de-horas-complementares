package com.example.apphorasmais;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.apphorasmais.adapter.TurmaAdapter;
import com.example.apphorasmais.model.facade.Facade;
import com.example.apphorasmais.repository.Turma;

import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class GerenciarTurma extends AppCompatActivity {

    private ImageView voltar;
    private SearchView pesquisar;
    private ImageView novaTurma;
    private RecyclerView recyclerViewTurma;
    private TurmaAdapter turmaAdapter;
    private Facade facade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciar_turma);
        facade = new Facade();
        voltar();
        pesquisarTurma();
        abrirTelaCadastroTurma();
        constroiRecyclerView();

    }

    protected void constroiRecyclerView() {
        recyclerViewTurma = (RecyclerView) findViewById(R.id.recyclerGerenciarTurma);

        defineLayoutRecyclerView();
    }

    protected void defineLayoutRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewTurma.setLayoutManager(linearLayoutManager);

        carregaListaDados();
    }

    protected void carregaListaDados() {
        List<Turma> lista = facade.listarTurmas(this);

        carregaDadosRecyclerView(lista);
    }

    protected void carregaDadosRecyclerView(List<Turma> lista) {
        turmaAdapter = new TurmaAdapter(lista);
        recyclerViewTurma.setAdapter(turmaAdapter);
    }

    protected void abrirTelaCadastroTurma() {
        novaTurma = (ImageView) findViewById(R.id.gerenciarTurmaNovo);
        novaTurma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GerenciarTurma.this, CadastroTurma.class);
                startActivity(i);
                finish();
            }
        });
    }

    protected void pesquisarTurma() {
        pesquisar = (SearchView) findViewById(R.id.gerenciarTurmaPesquisar);
        pesquisar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Turma> lista = facade.pesquisarTurmas(GerenciarTurma.this, newText);
                carregaDadosRecyclerView(lista);
                if(newText.isEmpty() || newText == null){
                    carregaListaDados();
                }
                return false;
            }
        });
    }

    protected void voltar() {
        voltar = (ImageView) findViewById(R.id.gerenciarTurmaVoltar);
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
        Intent i = new Intent(GerenciarTurma.this, Administrador.class);
        startActivity(i);
        finish();
    }
}