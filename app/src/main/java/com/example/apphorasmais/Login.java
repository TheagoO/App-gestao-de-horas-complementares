package com.example.apphorasmais;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apphorasmais.model.facade.Facade;
import com.example.apphorasmais.model.entity.Aluno;
import com.example.apphorasmais.model.entity.Coordenador;
import com.example.apphorasmais.model.util.Conexao;

/**
 * @author Thiago Ferreira Assumpção
 */

public class Login extends AppCompatActivity {

    private TextView usuario;
    private TextView senha;
    private Button entrar;
    private Facade facade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        facade = new Facade();
        Conexao.estartaConexao(getApplicationContext());
        instanciaComponentes();
        entrar();
    }

    protected void entrar() {
        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validaLogin();
            }
        });
    }

    protected void validaLogin() {
        if(usuario.getText().toString().equals("admin") && senha.getText().toString().equals("admin")){
            abrirTelaAdministrador();
        }else if(pegaPrimeirosDigitos(usuario.getText().toString()).contains("2022")){
            buscaAluno();
        }else if(pegaPrimeirosDigitos(usuario.getText().toString()).contains("2220")){
            buscarCoordenador();
        }else{
            Toast.makeText(this, "Usuário/senha inválidos", Toast.LENGTH_SHORT).show();
        }
    }

    protected String pegaPrimeirosDigitos(String usuario){
        return usuario.substring(0, 4);
    }

    protected void buscarCoordenador() {
        Coordenador coordenador = facade.buscarCoordenador(this, usuario.getText().toString(), senha.getText().toString());
        if(coordenador != null && coordenador.getId() != 0){
            if(coordenador.getSenha().contains("20220320")){
                abrirTelaDefinirSenha(null, coordenador);
            }else{
                abrirTelaCoordenador(coordenador);
            }
        }else{
            Toast.makeText(this, "Usuário/senha inválidos", Toast.LENGTH_SHORT).show();
        }
    }

    protected void abrirTelaCoordenador(Coordenador coordenador) {
        Intent i = new Intent(getApplicationContext(), com.example.apphorasmais.Coordenador.class);
        i.putExtra("COORDENADOR", coordenador);
        startActivity(i);
        finish();
        Toast.makeText(this, "Olá, "+ coordenador.getNome()+", seja bem-vindo!", Toast.LENGTH_SHORT).show();
    }

    protected void buscaAluno() {
        Aluno aluno = facade.buscarAluno(this, usuario.getText().toString(), senha.getText().toString());
        if(aluno != null && aluno.getId() != 0){
            if(aluno.getSenha().contains("20220320")){
                abrirTelaDefinirSenha(aluno, null);
            }else{
                abrirTelaAluno(aluno);
            }
        }else{
            Toast.makeText(this, "Usuário/senha inválidos", Toast.LENGTH_SHORT).show();
        }
    }

    protected void abrirTelaDefinirSenha(Aluno aluno, Coordenador coordenador) {
        Intent i = new Intent(getApplicationContext(), DefinirSenha.class);
        if(aluno!=null){
            i.putExtra("ALUNO", aluno);
        }else{
            i.putExtra("COORDENADOR", coordenador);
        }
        startActivity(i);
        finish();
    }

    protected void abrirTelaAluno(Aluno aluno) {
        Intent i = new Intent(getApplicationContext(), com.example.apphorasmais.Aluno.class);
        i.putExtra("ALUNO", aluno);
        startActivity(i);
        finish();
        Toast.makeText(this, "Olá, "+ aluno.getNome()+", seja bem-vindo!", Toast.LENGTH_SHORT).show();
    }

    protected void abrirTelaAdministrador() {
        Intent i = new Intent(Login.this, Administrador.class);
        startActivity(i);
        finish();
    }

    public void onBackPressed(){
        finishAffinity();
    }

    protected void instanciaComponentes() {
        usuario = findViewById(R.id.loginUsuario);
        senha = findViewById(R.id.loginSenha);
        entrar = findViewById(R.id.botaoLoginEntrar);
    }
}