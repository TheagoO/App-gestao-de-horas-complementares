package com.example.apphorasmais;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apphorasmais.model.facade.Facade;
import com.example.apphorasmais.repository.Coordenador;
import com.example.apphorasmais.repository.Aluno;
import com.example.apphorasmais.repository.Turma;
import com.example.apphorasmais.repository.Curso;
import com.example.apphorasmais.repository.PeriodoLetivo;

import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class CadastroUsuario extends AppCompatActivity {

    private ImageView voltar;
    private RadioButton coordenador;
    private RadioButton aluno;
    private TextView nome;
    private TextView usuario;
    private Spinner curso;
    private Spinner turma;
    private Spinner periodo;
    private Button cadastrar;
    private Facade facade;
    private TextView tituloCurso;
    private TextView tituloTurma;
    private TextView tituloPeriodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);
        facade = new Facade();
        instanciaComponentes();
        voltar();
        validaRadioButtonSelecionado();
        escondeCampos(coordenador, View.INVISIBLE);
        exibeCampos(aluno, View.VISIBLE);
        carregaSpinnerCurso();
        listenerSpinnerCurso();
        listenerSpinnerTurma();
        cadastrar();
    }

    protected void listenerSpinnerTurma() {
        turma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                carregaSpinnerPeriodo();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    protected void listenerSpinnerCurso() {
        curso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                carregaSpinnerTurma();
                carregaSpinnerPeriodo();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    protected void instanciaComponentes() {
        tituloTurma = (TextView) findViewById(R.id.textUsuarioTurma);
        tituloCurso = (TextView) findViewById(R.id.textUsuarioCurso);
        tituloPeriodo = (TextView) findViewById(R.id.textUsuarioPeriodo);
        coordenador = (RadioButton) findViewById(R.id.cadastroUsuarioCoordenador);
        aluno = (RadioButton) findViewById(R.id.cadastroUsuarioAluno);
        nome = (TextView) findViewById(R.id.cadastroUsuarioNome);
        usuario = (TextView) findViewById(R.id.cadastroUsuarioUsuario);
        turma = (Spinner) findViewById(R.id.cadastroUsuarioTurma);
        curso = (Spinner) findViewById(R.id.cadastroUsuarioCurso);
        periodo = (Spinner) findViewById(R.id.cadastroUsuarioPeriodo);
    }

    protected void carregaSpinnerTurma() {
        List<Turma> lista = facade.listarTurmas(this, curso.getSelectedItem().toString());
        String[] turmas = new String[lista.size()];

        iteraTurmas(lista, turmas);

        ArrayAdapter<String> adapter = getStringArrayAdapter(turmas);
        turma.setAdapter(adapter);
    }

    protected void iteraTurmas(List<Turma> lista, String[] turmas) {
        int i = 0;
        for (Turma t : lista) {
            turmas[i] = t.getGrupo();
            i++;
        }
    }

    protected void carregaSpinnerCurso() {
        List<Curso> lista = facade.listarCursos(this);
        String[] cursos = new String[lista.size()];

        iteraCursos(lista, cursos);

        ArrayAdapter<String> adapter = getStringArrayAdapter(cursos);
        curso.setAdapter(adapter);
    }

    protected void iteraCursos(List<Curso> lista, String[] cursos) {
        int i = 0;
        for (Curso c : lista) {
            cursos[i] = c.getTitulo();
            i++;
        }
    }

    protected void carregaSpinnerPeriodo() {
        List<PeriodoLetivo> lista = facade.listarPeriodos(this, turma.getSelectedItem().toString());
        String[] periodos = new String[lista.size()];

        iteraPeriodos(lista, periodos);

        ArrayAdapter<String> adapter = getStringArrayAdapter(periodos);
        periodo.setAdapter(adapter);
    }

    protected ArrayAdapter<String> getStringArrayAdapter(String[] array) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    protected void iteraPeriodos(List<PeriodoLetivo> lista, String[] periodos) {
        int i = 0;
        for (PeriodoLetivo p : lista) {
            periodos[i] = p.getPeriodo();
            i++;
        }
    }

    protected void cadastrar() {
        cadastrar = (Button) findViewById(R.id.cadastroUsuarioCadastrar);
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validaCampos()) {
                    varificaTipoDeCadastro();
                }
            }

            private void varificaTipoDeCadastro() {
                if (coordenador.isChecked()) {
                    cadastraCoordenador();
                } else {
                    cadastraAluno();
                }
            }
        });
    }

    protected void resetaCampos() {
        nome.setText("");
        usuario.setText("");
    }

    protected boolean validaCampos() {
        if (nome.getText().toString().isEmpty() || nome.getText().length() < 5) {
            nome.setError("Nome muito curto");
            return false;
        }
        if (usuario.getText().toString().isEmpty() || usuario.getText().length() < 5) {
            usuario.setError("Usuário muito curto");
            return false;
        }
        if(curso.getSelectedItem() == null || curso.getSelectedItem().toString().isEmpty()){
            menssagem("Selecione um curso");
            return false;
        }
        if(turma.getSelectedItem() == null || turma.getSelectedItem().toString().isEmpty()){
            menssagem("Selecione uma turma");
            return false;
        }
        if(periodo.getSelectedItem() == null || periodo.getSelectedItem().toString().isEmpty()){
            menssagem("Selecione um período");
            return false;
        }
        return true;
    }

    protected void menssagem(String msg) {
        Toast.makeText(CadastroUsuario.this, msg, Toast.LENGTH_LONG).show();
    }


    protected void cadastraAluno() {
        String retorno;
        Aluno novoAluno = new Aluno(nome.getText().toString().trim(), usuario.getText().toString().trim());
        retorno = facade.cadastrarAluno(CadastroUsuario.this, novoAluno, curso.getSelectedItem().toString(), turma.getSelectedItem().toString(), periodo.getSelectedItem().toString());
        menssagem(retorno);
        resetaCampos();
    }

    protected void cadastraCoordenador() {
        String retorno;
        Coordenador novoCoordenador = new Coordenador(nome.getText().toString().trim(), usuario.getText().toString().trim());
        retorno = facade.cadastrarCoordenador(CadastroUsuario.this, novoCoordenador);
        menssagem(retorno);
        resetaCampos();
    }

    protected void exibeCampos(RadioButton aluno, int visible) {
        aluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tituloCurso.setVisibility(visible);
                curso.setVisibility(visible);
                tituloTurma.setVisibility(visible);
                turma.setVisibility(visible);
                tituloPeriodo.setVisibility(visible);
                periodo.setVisibility(visible);
            }
        });
    }

    protected void escondeCampos(RadioButton coordenador, int invisible) {
        coordenador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tituloCurso.setVisibility(invisible);
                curso.setVisibility(invisible);
                tituloTurma.setVisibility(invisible);
                turma.setVisibility(invisible);
                tituloPeriodo.setVisibility(invisible);
                periodo.setVisibility(invisible);
            }
        });
    }

    protected void validaRadioButtonSelecionado() {
        if (coordenador.isChecked()) {
            tituloCurso.setVisibility(View.INVISIBLE);
            curso.setVisibility(View.INVISIBLE);
            tituloTurma.setVisibility(View.INVISIBLE);
            turma.setVisibility(View.INVISIBLE);
            tituloPeriodo.setVisibility(View.INVISIBLE);
            periodo.setVisibility(View.INVISIBLE);
        }
    }

    protected void voltar() {
        voltar = (ImageView) findViewById(R.id.cadastroUsuarioVoltar);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaGerenciarUsuarios();
            }
        });
    }

    public void onBackPressed(){
        abrirTelaGerenciarUsuarios();
    }

    protected void abrirTelaGerenciarUsuarios() {
        Intent i = new Intent(CadastroUsuario.this, GerenciarUsuarios.class);
        startActivity(i);
        finish();
    }
}