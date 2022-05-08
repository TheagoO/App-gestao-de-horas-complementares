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

import com.example.apphorasmais.model.facade.Facade;
import com.example.apphorasmais.model.entity.Coordenador;
import com.example.apphorasmais.model.entity.Curso;

import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class CadastroCurso extends AppCompatActivity {

    private ImageView voltar;
    private TextView curso;
    private Spinner coordenador;
    private Button cadastrar;
    private Facade facade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_curso);
        facade = new Facade();
        curso = (TextView) findViewById(R.id.cadastroCursoCurso);
        voltar();
        carregaSpinnerCoordenador();
        cadastrarCurso();
    }

    protected void carregaSpinnerCoordenador() {
        coordenador = (Spinner) findViewById(R.id.cadastroCursoCoordenador);
        List<Coordenador> lista = facade.listarCoordenadores(this);
        String[] nomes = new String[lista.size()];

        iteraNomes(lista, nomes);

        ArrayAdapter<String> adapter = getStringArrayAdapter(nomes);
        coordenador.setAdapter(adapter);
    }

    protected void iteraNomes(List<Coordenador> lista, String[] nomes) {
        int i=0;
        for(Coordenador c : lista){
            nomes[i] = c.getNome();
            i++;
        }
    }

    protected ArrayAdapter<String> getStringArrayAdapter(String[] nomes) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, nomes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    protected void cadastrarCurso() {
        cadastrar = (Button) findViewById(R.id.cadastroCursoCadastrar);
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validaCampos()){
                    Curso novoCurso = new Curso(0, curso.getText().toString(), null);
                    String retorno = facade.cadastrarCurso(CadastroCurso.this, novoCurso, coordenador.getSelectedItem().toString());
                    Toast.makeText(CadastroCurso.this, retorno, Toast.LENGTH_SHORT).show();
                    resetaCampos();
                }
            }
        });
    }

    protected void resetaCampos(){
        curso.setText("");
    }

    protected boolean validaCampos(){
        if(curso.getText().toString().isEmpty() || curso.getText().length() < 5){
            curso.setError("Curso muito curto");
            return false;
        }
        if(coordenador.getSelectedItem() == null || coordenador.getSelectedItem().toString().isEmpty()){
            Toast.makeText(CadastroCurso.this, "Selecione um coordenador", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    protected void voltar() {
        voltar = (ImageView) findViewById(R.id.cadastroCursoVoltar);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaGerenciarCurso();
            }
        });
    }

    public void onBackPressed(){
        abrirTelaGerenciarCurso();
    }

    protected void abrirTelaGerenciarCurso() {
        Intent i = new Intent(CadastroCurso.this, GerenciarCurso.class);
        startActivity(i);
        finish();
    }
}