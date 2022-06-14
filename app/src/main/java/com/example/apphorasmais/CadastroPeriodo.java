package com.example.apphorasmais;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.apphorasmais.model.facade.*;
import com.example.apphorasmais.model.entity.PeriodoLetivo;

/**
 * @author Thiago Ferreira Assumpção
 */

public class CadastroPeriodo extends AppCompatActivity {

    private ImageView voltar;
    private EditText periodo;
    private Button cadastrar;
    private Facade facade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_periodo);
        facade = new Facade();
        periodo = (EditText) findViewById(R.id.cadastroPeriodoPeriodo);
        voltar();
        cadastrarPeriodoLetivo();

    }

    protected void cadastrarPeriodoLetivo() {
        cadastrar = (Button) findViewById(R.id.cadastroPeriodoCadastrar);
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validaCampos()){
                    PeriodoLetivo periodoLetivo = new PeriodoLetivo(0, periodo.getText().toString(), null);
                    String retorno = facade.cadastrarPeriodo(CadastroPeriodo.this, periodoLetivo);
                    Toast.makeText(CadastroPeriodo.this, retorno, Toast.LENGTH_SHORT).show();
                    resetaCampos();
                }
            }
        });
    }

    protected void resetaCampos(){
        periodo.setText("");
    }

    protected boolean validaCampos(){
        if(periodo.getText().toString().isEmpty() || periodo.getText().toString().equals("0")){
            periodo.setError("Período muito curto");
            return false;
        }
        return true;
    }

    protected void voltar() {
        voltar = (ImageView) findViewById(R.id.cadastroPeriodoVoltar);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaGerenciarPeriodo();
            }
        });
    }

    public void onBackPressed(){
        abrirTelaGerenciarPeriodo();
    }

    protected void abrirTelaGerenciarPeriodo() {
        Intent i = new Intent(CadastroPeriodo.this, GerenciarPeriodo.class);
        startActivity(i);
        finish();
    }
}