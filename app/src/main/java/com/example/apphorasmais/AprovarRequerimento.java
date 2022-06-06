package com.example.apphorasmais;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apphorasmais.model.facade.Facade;
import com.example.apphorasmais.repository.Solicitacao;
import com.example.apphorasmais.repository.Coordenador;

import java.time.format.DateTimeFormatter;

/**
 * @author Thiago Ferreira Assumpção
 */

public class AprovarRequerimento extends AppCompatActivity {

    private ImageView voltar;
    private TextView nome;
    private TextView curso;
    private TextView turma;
    private TextView protocolo;
    private TextView dataEmissao;
    private TextView status;
    private TextView titulo;
    private TextView carga;
    private TextView instituicao;
    private TextView escopo;
    private TextView dataInicio;
    private TextView dataTermino;
    private Button recusar;
    private Button aprovar;
    private Facade facade;
    private Solicitacao solicitacao;
    private Coordenador coordenador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aprovar_requerimento);
        facade = new Facade();
        voltar();
        recuperaParametros();
        instanciaComponentes();
        preencheComponentes();
        recusarRequerimento();
        aprovarRequerimento();

    }

    protected void voltar() {
        voltar = (ImageView) findViewById(R.id.aprovarRequerimentoVoltar);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaListaSolicitacoes();
            }
        });
    }

    public void onBackPressed(){
        abrirTelaListaSolicitacoes();
    }

    protected void abrirTelaListaSolicitacoes() {
        Intent i = new Intent(AprovarRequerimento.this, ListaSolicitacoes.class);
        i.putExtra("COORDENADOR", coordenador);
        startActivity(i);
        finish();
    }

    protected void aprovarRequerimento() {
        aprovar = (Button) findViewById(R.id.aprovarRequerimentoAprovar);
        aprovar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AprovarRequerimento.this, RegistrarHoras.class);
                i.putExtra("COORDENADOR", coordenador);
                i.putExtra("SOLICITACAO", solicitacao);
                startActivity(i);
                finish();
            }
        });
    }

    protected void recusarRequerimento() {
        recusar = (Button) findViewById(R.id.aprovarRequerimentoRecusar);
        recusar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String retorno = facade.recusarSolicitacao(AprovarRequerimento.this, solicitacao);
                validaRetorno(retorno);
            }

            private void validaRetorno(String retorno) {
                if(retorno.contains("Sucesso")){
                    Toast.makeText(AprovarRequerimento.this, "Requerimento negado", Toast.LENGTH_SHORT).show();
                    abrirTelaListaSolicitacoes();
                }
            }
        });
    }

    protected void preencheComponentes() {
        nome.setText(solicitacao.getAluno().getNome());
        curso.setText(solicitacao.getAluno().getTurma().getCurso().getTitulo());
        turma.setText(solicitacao.getAluno().getTurma().getPeriodo().getPeriodo()+"º "+solicitacao.getAluno().getTurma().getGrupo());
        protocolo.setText(String.valueOf(solicitacao.getProtocolo()));
        dataEmissao.setText(String.valueOf((solicitacao.getDataSolicitacao()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        status.setText(solicitacao.getSituacao().getStatus());
        status.setBackgroundColor(Color.rgb(255, 191, 0));
        titulo.setText(solicitacao.getRequerimento().getTitulo());
        carga.setText(String.valueOf(solicitacao.getRequerimento().getCargaHoraria())+" Horas");
        instituicao.setText(solicitacao.getRequerimento().getInstituicao());
        escopo.setText(solicitacao.getRequerimento().getEscopo().getAtividade());
        dataInicio.setText(solicitacao.getRequerimento().getDataInicio());
        dataTermino.setText(solicitacao.getRequerimento().getDataTermino());
    }

    protected void instanciaComponentes() {
        nome = (TextView) findViewById(R.id.aprovarRequerimentoNome);
        curso = (TextView) findViewById(R.id.aprovarRequerimentoCurso);
        turma = (TextView) findViewById(R.id.aprovarRequerimentoTurma);
        protocolo = (TextView) findViewById(R.id.aprovarRequerimentoProtocolo);
        dataEmissao = (TextView) findViewById(R.id.aprovarRequerimentoDataEmissao);
        status = (TextView) findViewById(R.id.aprovarRequerimentoStatus);
        titulo = (TextView) findViewById(R.id.aprovarRequerimentoTitulo);
        carga = (TextView) findViewById(R.id.aprovarRequerimentoCargaHoraria);
        instituicao = (TextView) findViewById(R.id.aprovarRequerimentoInstituicao);
        escopo = (TextView) findViewById(R.id.aprovarRequerimentoEscopo);
        dataInicio = (TextView) findViewById(R.id.aprovarRequerimentoDataInicio);
        dataTermino = (TextView) findViewById(R.id.aprovarRequerimentoDataTermino);
    }

    protected void recuperaParametros(){
        Bundle bundle = getIntent().getExtras();
        solicitacao = (Solicitacao) bundle.get("SOLICITACAO");
        coordenador = (Coordenador) bundle.get("COORDENADOR");
    }
}