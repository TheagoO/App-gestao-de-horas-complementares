package com.example.apphorasmais;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apphorasmais.model.entity.PeriodoLetivo;
import com.example.apphorasmais.model.facade.Facade;

/**
 * @author Thiago Ferreira Assumpção
 */

public class EditarPeriodo extends AppCompatActivity {

    private ImageView voltar;
    private TextView periodo;
    private Button confirmar;
    private PeriodoLetivo periodoLetivo;
    private Facade facade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_periodo);
        facade = new Facade();
        voltar();
        recuperaParametros();
        carregaComponentes();
        confirmar();
    }

    protected void confirmar() {
        confirmar = (Button) findViewById(R.id.editarPeriodoConfirmar);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String retorno = facade.editarPeriodo(EditarPeriodo.this, periodoLetivo, periodo.getText().toString().trim());
                Toast.makeText(EditarPeriodo.this, retorno, Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void carregaComponentes() {
        periodo = (TextView) findViewById(R.id.editarPeriodoPeriodo);
        periodo.setText(periodoLetivo.getPeriodo());
    }

    protected void voltar() {
        voltar = (ImageView) findViewById(R.id.editarPeriodoVoltar);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(EditarPeriodo.this);
        builder.setTitle("Atenção");
        builder.setMessage("Descartar alterações?");
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                abrirTelaGerenciarPeriodo();
            }
        });
        builder.setNegativeButton("Cancelar", null);

        alertDialog = builder.create();
        alertDialog.show();
    }

    protected void abrirTelaGerenciarPeriodo() {
        Intent i = new Intent(this, GerenciarPeriodo.class);
        startActivity(i);
        finish();
    }

    protected void recuperaParametros(){
        Bundle bundle = getIntent().getExtras();
        periodoLetivo = (PeriodoLetivo) bundle.get("PERIODO");
    }
}