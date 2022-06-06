package com.example.apphorasmais;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apphorasmais.repository.Coordenador;
import com.example.apphorasmais.repository.Solicitacao;
import com.example.apphorasmais.model.facade.Facade;

/**
 * @author Thiago Ferreira Assumpção
 */

public class RegistrarHoras extends AppCompatActivity {

    private ImageView voltar;
    private TextView nome;
    private TextView curso;
    private TextView turma;
    private TextView titulo;
    private TextView carga;
    private TextView instituicao;
    private TextView escopo;
    private TextView horasComplementares;
    private Button registrar;
    private Facade facade;
    private Solicitacao solicitacao;
    private Coordenador coordenador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_horas);
        facade = new Facade();
        voltar();
        recuperaParametros();
        instanciaComponentes();
        preencheComponentes();
        registrarHoras();
    }

    protected void registrarHoras() {
        registrar = (Button) findViewById(R.id.registrarHorasRegistrar);
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validaCampos()){
                    String retorno = facade.registarHorasComplementares(RegistrarHoras.this, solicitacao, Integer.valueOf(horasComplementares.getText().toString()));
                    if(retorno.contains("Sucesso")){
                        Toast.makeText(RegistrarHoras.this, "Horas complementares registradas", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(RegistrarHoras.this, ListaSolicitacoes.class);
                        i.putExtra("COORDENADOR", coordenador);
                        startActivity(i);
                        finish();
                    }
                }
            }
        });
    }

    protected boolean validaCampos(){
        if(horasComplementares.getText().toString().isEmpty()){
            horasComplementares.setError("Campo vazio");
            return false;
        }
        return true;
    }

    protected void recuperaParametros(){
        Bundle bundle = getIntent().getExtras();
        solicitacao = (Solicitacao) bundle.get("SOLICITACAO");
        coordenador = (Coordenador) bundle.get("COORDENADOR");
    }


    protected void voltar() {
        voltar = (ImageView) findViewById(R.id.registrarHorasVoltar);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaAprovarRequerimento();
            }
        });
    }

    public void onBackPressed() {
        abrirTelaAprovarRequerimento();
    }

    protected void abrirTelaAprovarRequerimento() {
        Intent i = new Intent(RegistrarHoras.this, AprovarRequerimento.class);
        i.putExtra("COORDENADOR", coordenador);
        i.putExtra("SOLICITACAO", solicitacao);
        startActivity(i);
        finish();
    }

    protected void instanciaComponentes() {
        nome = (TextView) findViewById(R.id.registrarHorasNome);
        curso = (TextView) findViewById(R.id.registrarHorasCurso);
        turma = (TextView) findViewById(R.id.registrarHorasTurma);
        titulo = (TextView) findViewById(R.id.registrarHorasTitulo);
        carga = (TextView) findViewById(R.id.registrarHorasCargaHoraria);
        instituicao = (TextView) findViewById(R.id.registrarHorasInstituicao);
        escopo = (TextView) findViewById(R.id.registrarHorasEscopo);
        horasComplementares = (TextView) findViewById(R.id.registrarHorasHorasComplementares);
    }

    protected void preencheComponentes() {
        nome.setText(solicitacao.getAluno().getNome());
        curso.setText(solicitacao.getAluno().getTurma().getCurso().getTitulo());
        turma.setText(solicitacao.getAluno().getTurma().getPeriodo().getPeriodo()+"º "+solicitacao.getAluno().getTurma().getGrupo());
        titulo.setText(solicitacao.getRequerimento().getTitulo());
        carga.setText(String.valueOf(solicitacao.getRequerimento().getCargaHoraria())+" Horas");
        instituicao.setText(solicitacao.getRequerimento().getInstituicao());
        escopo.setText(solicitacao.getRequerimento().getEscopo().getAtividade());
    }
}