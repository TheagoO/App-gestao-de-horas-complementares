package com.example.apphorasmais;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apphorasmais.model.facade.Facade;
import com.example.apphorasmais.repository.Curso;
import com.example.apphorasmais.repository.Turma;
import com.example.apphorasmais.repository.PeriodoLetivo;

import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class EditarTurma extends AppCompatActivity {

    private Turma turma;
    private ImageView voltar;
    private TextView grupo;
    private Spinner curso;
    private Spinner periodo;
    private Button confirmar;
    private Facade facade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_turma);
        facade = new Facade();
        recuperaParametros();
        carregaComponentes();
        carregaSpinnerCurso();
        carregaSpinnerPeriodo();
        voltar();
        confirmar();
    }

    protected void carregaComponentes() {
        grupo = (TextView) findViewById(R.id.editarTurmaTurma);
        grupo.setText(turma.getGrupo());
    }

    protected void confirmar() {
        confirmar = (Button) findViewById(R.id.editarTurmaConfirmar);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String retorno = facade.editarTurma(getApplicationContext(), turma, grupo.getText().toString(), curso.getSelectedItem().toString(), periodo.getSelectedItem().toString());
                Toast.makeText(getApplicationContext(), retorno, Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void voltar() {
        voltar = (ImageView) findViewById(R.id.editarTurmaVoltar);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertaDialogo();
            }
        });
    }

    public void onBackPressed(){
        alertaDialogo();
    }

    protected void alertaDialogo() {
        AlertDialog alertDialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(EditarTurma.this);
        builder.setTitle("Atenção");
        builder.setMessage("Descartar alterações?");
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                abrirTelaGerenciarTurma();
            }
        });
        builder.setNegativeButton("Cancelar", null);

        alertDialog = builder.create();
        alertDialog.show();
    }

    protected void recuperaParametros(){
        Bundle bundle = getIntent().getExtras();
        turma = (Turma) bundle.get("TURMA");
    }

    protected void abrirTelaGerenciarTurma(){
        Intent i = new Intent(getApplicationContext(), GerenciarTurma.class);
        startActivity(i);
        finish();
    }

    protected void carregaSpinnerPeriodo() {
        periodo = (Spinner) findViewById(R.id.editarTurmaPeriodo);
        List<PeriodoLetivo> lista = facade.listarPeriodos(getApplicationContext());
        String[] turmas = new String[lista.size()+1];

        iteraPeriodos(lista, turmas);

        ArrayAdapter<String> adapter = getStringArrayAdapter(turmas);
        periodo.setAdapter(adapter);
    }

    protected void iteraPeriodos(List<PeriodoLetivo> lista, String[] turmas) {
        int i=1;
        for(PeriodoLetivo p : lista){
            turmas[i] = p.getPeriodo();
            i++;
        }
        turmas[0] = turma.getPeriodo().getPeriodo();
    }

    protected void carregaSpinnerCurso() {
        curso = (Spinner) findViewById(R.id.editarTurmaCurso);
        List<Curso> lista = facade.listarCursos(getApplicationContext());
        String[] cursos = new String[lista.size()+1];

        iteraCursos(lista, cursos);

        ArrayAdapter<String> adapter = getStringArrayAdapter(cursos);
        curso.setAdapter(adapter);
    }

    protected void iteraCursos(List<Curso> lista, String[] cursos) {
        int i=1;
        for(Curso c : lista){
            cursos[i] = c.getTitulo();
            i++;
        }
        cursos[0] = turma.getCurso().getTitulo();
    }

    protected ArrayAdapter<String> getStringArrayAdapter(String[] array) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }
}