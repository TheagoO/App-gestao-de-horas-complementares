package com.example.apphorasmais;

import androidx.appcompat.app.AppCompatActivity;

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

public class DefinirSenha extends AppCompatActivity {

    private ImageView voltar;
    private TextView senha;
    private TextView confirmarSenha;
    private Button entrar;
    private Aluno aluno;
    private Coordenador coordenador;
    private Facade facade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definir_senha);
        facade = new Facade();
        recuperaParametros();
        instanciaComponentes();
        voltar();
        entrar();
    }

    protected void entrar() {
        entrar = (Button) findViewById(R.id.definirSenhaEntrar);
        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validaCampos()){
                    if((verificaTipoUsuario()).contains("Aluno")){
                        redefineSenhaAluno();
                    }else{
                        redefineSenhaCoordenador();
                    }
                }
            }
        });
    }

    protected boolean validaCampos(){
        if(senha.getText().toString().isEmpty() || senha.getText().length() < 3){
            senha.setError("Senha muito curta");
            return false;
        }
        if(senha.getText().equals(confirmarSenha.getText())){
            confirmarSenha.setError("Senhas divergentes");
            return false;
        }
        return true;
    }

    protected void redefineSenhaCoordenador() {
        String retorno;
        retorno = facade.redefinirSenha(getApplicationContext(), verificaTipoUsuario(), coordenador.getId(), senha.getText().toString(), confirmarSenha.getText().toString());
        if(retorno.contains("Senha definida")){
            abrirTelaCoordenador(coordenador);
        }else{
            Toast.makeText(getApplicationContext(), retorno, Toast.LENGTH_SHORT).show();
        }
    }

    protected void redefineSenhaAluno() {
        String retorno;
        retorno = facade.redefinirSenha(getApplicationContext(), verificaTipoUsuario(), aluno.getId(), senha.getText().toString(), confirmarSenha.getText().toString());
        if(retorno.contains("Senha definida")){
            abrirTelaAluno(aluno);
        }else{
            Toast.makeText(getApplicationContext(), retorno, Toast.LENGTH_SHORT).show();
        }
    }

    protected void abrirTelaCoordenador(Coordenador coordenador) {
        Intent i = new Intent(getApplicationContext(), com.example.apphorasmais.Coordenador.class);
        i.putExtra("COORDENADOR", coordenador);
        startActivity(i);
        finish();
        Toast.makeText(this, "Olá, "+ coordenador.getNome()+", seja bem-vindo!", Toast.LENGTH_SHORT).show();
    }

    protected void abrirTelaAluno(Aluno aluno) {
        Intent i = new Intent(getApplicationContext(), com.example.apphorasmais.Aluno.class);
        i.putExtra("ALUNO", aluno);
        startActivity(i);
        finish();
        Toast.makeText(this, "Olá, "+ aluno.getNome()+", seja bem-vindo!", Toast.LENGTH_SHORT).show();
    }

    protected void instanciaComponentes() {
        senha = (TextView) findViewById(R.id.definirSenhaSenha);
        confirmarSenha = (TextView) findViewById(R.id.definirSenhaConfirmarSenha);
    }

    protected String verificaTipoUsuario(){
        if(aluno!=null){
            return "Aluno";
        }else{
            return "Coordenador";
        }
    }

    protected void voltar() {
        voltar = (ImageView) findViewById(R.id.definirSenhaVoltar);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaLogin();
            }
        });
    }

    public void onBackPressed(){
        abrirTelaLogin();
    }

    protected void abrirTelaLogin(){
        Intent i = new Intent(getApplicationContext(), Login.class);
        startActivity(i);
        finish();
    }

    protected void recuperaParametros(){
        Bundle bundle = getIntent().getExtras();
        aluno = (Aluno) bundle.get("ALUNO");
        coordenador = (Coordenador) bundle.get("COORDENADOR");
    }
}