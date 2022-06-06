package com.example.apphorasmais;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.example.apphorasmais.model.facade.Facade;
import com.example.apphorasmais.repository.Coordenador;
import com.example.apphorasmais.repository.Aluno;
import com.example.apphorasmais.adapter.CoordenadorAdapter;
import com.example.apphorasmais.adapter.AlunoAdapter;

import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class GerenciarUsuarios extends AppCompatActivity {

    private ImageView voltar;
    private RadioButton coordenador;
    private RadioButton aluno;
    private SearchView pesquisar;
    private ImageView novoUsuario;
    private RecyclerView recyclerView;
    private CoordenadorAdapter coordenadorAdapter;
    private AlunoAdapter alunoAdapter;
    private Facade facade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciar_usuarios);
        facade = new Facade();
        coordenador = (RadioButton) findViewById(R.id.gerenciarUsuariosCoordenador);
        aluno = (RadioButton) findViewById(R.id.gerenciarUsuariosAluno);
        defineLayoutRecyclerView();
        selecionaCoordenadores();
        selecionaAlunos();
        voltar();
        cadastrarNovoUsuario();
        pesquisarUsuario();
    }

    protected void selecionaAlunos() {
        aluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carregaListaDadosAluno();
            }
        });
    }

    protected void selecionaCoordenadores() {
        coordenador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carregaListaDadosCoordenador();
            }
        });
    }

    protected void defineLayoutRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerGerenciarUsuarios);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        carregaDadosNaAdapter();
    }

    protected void carregaDadosNaAdapter() {
        if (coordenador.isChecked()) {
            carregaListaDadosCoordenador();
        } else {
            carregaListaDadosAluno();
        }
    }

    protected void carregaListaDadosAluno() {
        List<Aluno> lista = facade.listarAlunos(getApplicationContext());
        carregaDadosAlunoRecyclerView(lista);
    }

    protected void carregaDadosAlunoRecyclerView(List<Aluno> lista) {
        alunoAdapter = new AlunoAdapter(lista);
        recyclerView.setAdapter(alunoAdapter);
    }

    protected void carregaListaDadosCoordenador() {
        List<Coordenador> lista = facade.listarCoordenadores(getApplicationContext());
        carregaDadosCoordenadorRecyclerView(lista);
    }

    protected void carregaDadosCoordenadorRecyclerView(List<Coordenador> lista) {
        coordenadorAdapter = new CoordenadorAdapter(lista);
        recyclerView.setAdapter(coordenadorAdapter);
    }

    protected void voltar() {
        voltar = (ImageView) findViewById(R.id.gerenciarUsuarioVoltar);
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
        Intent i = new Intent(GerenciarUsuarios.this, Administrador.class);
        startActivity(i);
        finish();
    }

    protected void cadastrarNovoUsuario() {
        novoUsuario = (ImageView) findViewById(R.id.gerenciarUsuarioNovo);
        novoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GerenciarUsuarios.this, CadastroUsuario.class);
                startActivity(i);
                finish();
            }
        });
    }

    protected void pesquisarUsuario() {
        pesquisar = (SearchView) findViewById(R.id.gerenciarUsuariosPesquisar);
        pesquisar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (aluno.isChecked()) {
                    List<Aluno> lista = facade.pesquisarAluno(GerenciarUsuarios.this, newText);
                    carregaDadosAlunoRecyclerView(lista);
                    if (newText == null || newText.isEmpty()) {
                        carregaListaDadosAluno();
                    }
                } else {
                    List<Coordenador> lista = facade.pesquisarCoordenador(GerenciarUsuarios.this, newText);
                    carregaDadosCoordenadorRecyclerView(lista);
                    if (newText == null || newText.isEmpty()) {
                        carregaListaDadosCoordenador();
                    }
                }
                return false;
            }
        });
    }

}