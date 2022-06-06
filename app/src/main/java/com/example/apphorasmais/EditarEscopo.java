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

import com.example.apphorasmais.repository.Escopo;
import com.example.apphorasmais.model.facade.Facade;

/**
 * @author Thiago Ferreira Assumpção
 */

public class EditarEscopo extends AppCompatActivity {

    private Escopo escopo;
    private ImageView voltar;
    private TextView motivo;
    private Button confirmar;
    private Facade facade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_escopo);
        facade = new Facade();
        recuperaParametros();
        voltar();
        carregaComponentes();
        confirmar();
    }

    protected void confirmar() {
        confirmar = (Button) findViewById(R.id.editarEscopoConfirmar);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String retorno = facade.editarEscopo(EditarEscopo.this, escopo, motivo.getText().toString().trim());
                Toast.makeText(EditarEscopo.this, retorno, Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void carregaComponentes() {
        motivo = (TextView) findViewById(R.id.editarEscopoEscopo);
        motivo.setText(escopo.getAtividade());
    }

    protected void voltar() {
        voltar = (ImageView) findViewById(R.id.editarEscopoVoltar);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(EditarEscopo.this);
        builder.setTitle("Atenção");
        builder.setMessage("Descartar alterações?");
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                abrirTelaGerenciarEscopo();
            }
        });
        builder.setNegativeButton("Cancelar", null);

        alertDialog = builder.create();
        alertDialog.show();
    }

    protected void abrirTelaGerenciarEscopo() {
        Intent i = new Intent(this, GerenciarEscopo.class);
        startActivity(i);
        finish();
    }

    protected void recuperaParametros(){
        Bundle bundle = getIntent().getExtras();
        escopo = (Escopo) bundle.get("ESCOPO");
    }
}