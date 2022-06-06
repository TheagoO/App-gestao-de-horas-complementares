package com.example.apphorasmais;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.apphorasmais.adapter.AtividadeComplementarAdapter;
import com.example.apphorasmais.repository.Atividade;
import com.example.apphorasmais.repository.Aluno;

/**
 * @author Thiago Ferreira Assumpção
 */

public class AtividadesComplementar extends AppCompatActivity {

    private Aluno aluno;
    private Atividade atividade;
    private ImageView voltar;
    private RecyclerView recyclerView;
    private AtividadeComplementarAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividades_complementar);
        recuperaParametros();
        constroiRecyclerView();
        voltar();
    }

    protected void constroiRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.atividadesComplementarRecyclerView);
        defineLayoutRecyclerView();
    }

    protected void defineLayoutRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        preencheDadosRecyclerView();
    }

    protected void preencheDadosRecyclerView() {
        adapter = new AtividadeComplementarAdapter(atividade.getAtividadesComplementares());
        recyclerView.setAdapter(adapter);
    }

    protected void voltar() {
        voltar = (ImageView) findViewById(R.id.atividadesComplementarVoltar);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaMinhasAtividades();
            }
        });
    }

    public void onBackPressed(){
        abrirTelaMinhasAtividades();
    }

    protected void abrirTelaMinhasAtividades() {
        Intent i = new Intent(AtividadesComplementar.this, MinhasAtividades.class);
        i.putExtra("ALUNO", aluno);
        startActivity(i);
        finish();
    }

    protected void recuperaParametros(){
        Bundle bundle = getIntent().getExtras();
        atividade = (Atividade) bundle.get("ATIVIDADE");
        aluno = (Aluno) bundle.get("ALUNO");
    }
}