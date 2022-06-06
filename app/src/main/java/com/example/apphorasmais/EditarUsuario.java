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

import com.example.apphorasmais.repository.Aluno;
import com.example.apphorasmais.repository.Coordenador;
import com.example.apphorasmais.model.facade.Facade;

/**
 * @author Thiago Ferreira Assumpção
 */

public class EditarUsuario extends AppCompatActivity {

    private Aluno aluno;
    private Coordenador coordenador;
    private ImageView voltar;
    private TextView nome;
    private TextView usuario;
    private Button resetarSenha;
    private Button confirmar;
    private Facade facade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_usuario);
        facade = new Facade();
        instanciaComponentes();
        voltar();
        recuperaParametros();
        resetarSenha();
        alterarUsuario();
    }

    protected void resetarSenha() {
        resetarSenha = (Button) findViewById(R.id.editarUsuarioResetarSenha);
        resetarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String retorno;
                if((verificaTipoUsuario()).contains("Aluno")){
                    retorno = facade.resetarSenha(getApplicationContext(), "Aluno", aluno.getId());
                    menssagem(retorno, Toast.LENGTH_LONG);
                }else{
                    retorno = facade.resetarSenha(getApplicationContext(), "Coordenador", coordenador.getId());
                    menssagem(retorno, Toast.LENGTH_LONG);
                }
            }
        });
    }

    protected void alterarUsuario() {
        confirmar = (Button) findViewById(R.id.editarUsuarioConfirmar);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String retorno;
                if ((verificaTipoUsuario()).contains("Aluno")) {
                    retorno = facade.editarAluno(getApplicationContext(), aluno, nome.getText().toString(), usuario.getText().toString());
                    menssagem(retorno, Toast.LENGTH_SHORT);
                } else {
                    retorno = facade.editarCoordenador(getApplicationContext(), coordenador, nome.getText().toString(), usuario.getText().toString());
                    menssagem(retorno, Toast.LENGTH_SHORT);
                }
            }
        });
    }

    protected void menssagem(String retorno, int duracao) {
        Toast.makeText(getApplicationContext(), retorno, duracao).show();
    }

    protected void instanciaComponentes() {
        nome = (TextView) findViewById(R.id.editarUsuarioNome);
        usuario = (TextView) findViewById(R.id.editarUsuarioUsuario);
    }

    protected String verificaTipoUsuario() {
        if (aluno != null) {
            return "Aluno";
        } else {
            return "Coordenador";
        }
    }

    protected void carregaComponentesCoordenador() {
        nome.setText(coordenador.getNome());
        usuario.setText(coordenador.getUsuario());
    }

    protected void carregaComponentesAluno() {
        nome.setText(aluno.getNome());
        usuario.setText(aluno.getUsuario());
    }

    protected void voltar() {
        voltar = (ImageView) findViewById(R.id.editarUsuarioVoltar);
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

    protected void abrirTelaGerenciarUsuario() {
        Intent intent = new Intent(EditarUsuario.this, GerenciarUsuarios.class);
        startActivity(intent);
        finish();
    }

    protected void alertaDialogo() {
        AlertDialog alertDialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(EditarUsuario.this);
        builder.setTitle("Atenção");
        builder.setMessage("Descartar alterações?");
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                abrirTelaGerenciarUsuario();
            }
        });
        builder.setNegativeButton("Cancelar", null);

        alertDialog = builder.create();
        alertDialog.show();
    }

    protected void recuperaParametros() {
        Bundle bundle = getIntent().getExtras();
        aluno = (Aluno) bundle.get("ALUNO");
        coordenador = (Coordenador) bundle.get("COORDENADOR");
        if((verificaTipoUsuario()).contains("Aluno")){
            carregaComponentesAluno();
        }else{
            carregaComponentesCoordenador();
        }
    }


}