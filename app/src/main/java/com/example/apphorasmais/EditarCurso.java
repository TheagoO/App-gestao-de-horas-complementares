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

import com.example.apphorasmais.repository.Curso;
import com.example.apphorasmais.repository.Coordenador;
import com.example.apphorasmais.model.facade.Facade;

import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class EditarCurso extends AppCompatActivity {

    private ImageView voltar;
    private Curso curso;
    private TextView titulo;
    private Spinner coordenadores;
    private Facade facade;
    private Button confirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_curso);
        facade = new Facade();
        recuperaParametros();
        carregaComponentes();
        carregaSpinnerCoordenador();
        voltar();
        confirmar();
    }

    protected void confirmar() {
        confirmar = (Button) findViewById(R.id.editarCursoConfirmar);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String retorno = facade.editarCurso(EditarCurso.this, curso, titulo.getText().toString().trim(), coordenadores.getSelectedItem().toString());
                Toast.makeText(EditarCurso.this, retorno, Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void voltar() {
        voltar = (ImageView) findViewById(R.id.editarCursoVoltar);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(EditarCurso.this);
        builder.setTitle("Atenção");
        builder.setMessage("Descartar alterações?");
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                abrirTelaGerenciarCurso();
            }
        });
        builder.setNegativeButton("Cancelar", null);

        alertDialog = builder.create();
        alertDialog.show();
    }

    protected void abrirTelaGerenciarCurso() {
        Intent i = new Intent(this, GerenciarCurso.class);
        startActivity(i);
        finish();
    }

    protected void carregaComponentes() {
        titulo = (TextView) findViewById(R.id.editarCursoCurso);
        titulo.setText(curso.getTitulo());
    }

    protected void carregaSpinnerCoordenador() {
        coordenadores = (Spinner) findViewById(R.id.editarCursoCoordenador);
        List<Coordenador> lista = facade.listarCoordenadores(this);
        String[] nomes = new String[lista.size()+1];

        iteraNomes(lista, nomes);

        ArrayAdapter<String> adapter = getStringArrayAdapter(nomes);
        coordenadores.setAdapter(adapter);
    }

    protected ArrayAdapter<String> getStringArrayAdapter(String[] nomes) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nomes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    protected void iteraNomes(List<Coordenador> lista, String[] nomes) {
        int i=1;
        for(Coordenador c : lista){
            nomes[i] = c.getNome();
            i++;
        }
        nomes[0] = curso.getCoordenador().getNome();
    }

    protected void recuperaParametros(){
        Bundle bundle = getIntent().getExtras();
        curso = (Curso) bundle.get("CURSO");
    }
}