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

import com.example.apphorasmais.repository.Situacao;
import com.example.apphorasmais.model.facade.Facade;

/**
 * @author Thiago Ferreira Assumpção
 */

public class EditarSituacao extends AppCompatActivity {

    private ImageView voltar;
    private TextView status;
    private Button confirmar;
    private Situacao situacao;
    private Facade facade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_situacao);
        facade = new Facade();
        recuperaParametros();
        voltar();
        carregaComponentes();
        confirmar();
    }

    protected void confirmar() {
        confirmar = (Button) findViewById(R.id.editarSituacaoConfirmar);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String retorno = facade.editarStatus(EditarSituacao.this, situacao, status.getText().toString().trim());
                Toast.makeText(EditarSituacao.this, retorno, Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void voltar() {
        voltar = (ImageView) findViewById(R.id.editarSituacaoVoltar);
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

    protected void carregaComponentes() {
        status = (TextView) findViewById(R.id.editarSituacaoSituacao);
        status.setText(situacao.getStatus());
    }

    protected void alertaDialogo() {
        AlertDialog alertDialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(EditarSituacao.this);
        builder.setTitle("Atenção");
        builder.setMessage("Descartar alterações?");
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                abrirTelaGerenciarSituacao();
            }
        });
        builder.setNegativeButton("Cancelar", null);

        alertDialog = builder.create();
        alertDialog.show();
    }

    protected void abrirTelaGerenciarSituacao() {
        Intent i = new Intent(this, GerenciarSituacao.class);
        startActivity(i);
        finish();
    }

    protected void recuperaParametros(){
        Bundle bundle = getIntent().getExtras();
        situacao = (Situacao) bundle.get("SITUACAO");
    }
}