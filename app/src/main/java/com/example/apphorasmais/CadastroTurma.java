package com.example.apphorasmais;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apphorasmais.model.entity.PeriodoLetivo;
import com.example.apphorasmais.model.entity.Curso;
import com.example.apphorasmais.model.entity.Turma;
import com.example.apphorasmais.model.facade.Facade;

import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class CadastroTurma extends AppCompatActivity {

    private ImageView voltar;
    private TextView turma;
    private Spinner periodo;
    private Spinner curso;
    private Button cadastrar;
    private Facade facade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_turma);
        facade = new Facade();
        instanciaComponentes();
        voltar();
        carregaSpinnerCurso();
        carregaSpinnerPeriodo();
        cadastrarTurma();
    }

    protected void instanciaComponentes() {
        turma = (TextView) findViewById(R.id.cadastroTurmaTurma);
        curso = (Spinner) findViewById(R.id.cadastroTurmaCurso);
        periodo = (Spinner) findViewById(R.id.cadastroTurmaPeriodo);
    }

    protected void carregaSpinnerPeriodo() {
        List<PeriodoLetivo> lista = facade.listarPeriodos(this);
        String[] periodos = new String[lista.size()];

        iteraPeriodos(lista, periodos);

        ArrayAdapter<String> adapter = getStringArrayAdapter(periodos);
        periodo.setAdapter(adapter);
    }

    protected void iteraPeriodos(List<PeriodoLetivo> lista, String[] periodos) {
        int i=0;
        for (PeriodoLetivo p : lista) {
            periodos[i] = p.getPeriodo();
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
        int i=0;
        for (Curso c : lista){
            cursos[i] = c.getTitulo();
            i++;
        }
    }

    protected void cadastrarTurma() {
        cadastrar = (Button) findViewById(R.id.cadastroTurmaCadastrar);
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validaCampos()){
                    Turma novaTurma = new Turma(0, turma.getText().toString());
                    String retorno = facade.cadastrarTurma(CadastroTurma.this, novaTurma, curso.getSelectedItem().toString(), periodo.getSelectedItem().toString());
                    menssagem(retorno, Toast.LENGTH_SHORT);
                    resetaCampos();
                }
            }
        });
    }

    protected ArrayAdapter<String> getStringArrayAdapter(String[] array) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    protected void resetaCampos(){
        turma.setText("");
    }

    protected boolean validaCampos(){
        if(turma.getText().toString().isEmpty()){
            turma.setError("Campo vazio");
            return false;
        }
        if(curso.getSelectedItem() == null || curso.getSelectedItem().toString().isEmpty()){
            menssagem("Selecione um curso", Toast.LENGTH_LONG);
            return false;
        }
        if(periodo.getSelectedItem() == null || periodo.getSelectedItem().toString().isEmpty()){
            menssagem("Selecione um período", Toast.LENGTH_LONG);
            return false;
        }
        return true;
    }

    protected void menssagem(String msg, int duracao) {
        Toast.makeText(CadastroTurma.this, msg, duracao).show();
    }

    protected void voltar() {
        voltar = (ImageView) findViewById(R.id.cadastroTurmaVoltar);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaGerenciarTurma();
            }
        });
    }

    public void onBackPressed(){
        abrirTelaGerenciarTurma();
    }

    protected void abrirTelaGerenciarTurma() {
        Intent i = new Intent(CadastroTurma.this, GerenciarTurma.class);
        startActivity(i);
        finish();
    }

}