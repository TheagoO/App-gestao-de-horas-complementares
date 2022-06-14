package com.example.apphorasmais;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import com.example.apphorasmais.model.entity.Requerimento;
import com.example.apphorasmais.model.entity.Escopo;
import com.example.apphorasmais.model.facade.Facade;

/**
 * @author Thiago Ferreira Assumpção
 */

public class NovaRequisicao extends AppCompatActivity {

    private com.example.apphorasmais.model.entity.Aluno aluno;
    private ImageView voltar;
    private TextView titulo;
    private TextView instituicao;
    private Spinner escopo;
    private TextView cargaHoraria;
    private EditText dataInicio;
    private EditText dataTermino;
    private Button enviar;
    private Facade facade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        facade = new Facade();
        setContentView(R.layout.activity_nova_requisicao);
        recuperaParametros();
        voltar();
        instanciaComponentes();
        datePickerInicio();
        datePickerTermino();
        constroiSpinnerEscopo();
        enviarRequisicao();
    }

    protected void enviarRequisicao() {
        enviar = (Button) findViewById(R.id.novaRequisicaoEnviar);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validaCampos()){
                    Requerimento requerimento = new Requerimento(0, titulo.getText().toString(), Integer.parseInt(cargaHoraria.getText().toString()), dataInicio.getText().toString(), dataTermino.getText().toString(), instituicao.getText().toString(), null);
                    String retorno = facade.registrarRequisicao(NovaRequisicao.this, requerimento, escopo.getSelectedItem().toString(), aluno.getId());
                    Toast.makeText(NovaRequisicao.this, retorno, Toast.LENGTH_SHORT).show();
                    validaEnvio(retorno);
                }
            }

            private void validaEnvio(String retorno) {
                if(retorno.contains("enviada")){
                    abrirTelaAluno();
                }
            }
        });
    }

    protected boolean validaCampos(){
        if(titulo.getText().toString().isEmpty() || titulo.getText().length() < 5){
            titulo.setError("Título muito curto");
            return false;
        }
        if(instituicao.getText().toString().isEmpty() || instituicao.getText().length() < 5){
            instituicao.setError("Instituição muito curta");
            return false;
        }
        if(cargaHoraria.getText().toString().isEmpty()){
            cargaHoraria.setError("Campo vazio");
            return false;
        }
        if(dataInicio.getText().toString().isEmpty()){
            Toast.makeText(NovaRequisicao.this, "Selecione uma data", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(dataTermino.getText().toString().isEmpty()){
            Toast.makeText(NovaRequisicao.this, "Selecione uma data", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    protected void constroiSpinnerEscopo() {
        escopo = (Spinner) findViewById(R.id.novaRequisicaoEscopo);
        iteraDadosLista();
    }

    protected void iteraDadosLista() {
        List<Escopo> lista = facade.listarEscopos(this);
        String[] escopos = new String[lista.size()];
        int i=0;
        for(Escopo e : lista){
            escopos[i] = e.getAtividade();
            i++;
        }

        defineAdapterSpinner(escopos);
    }

    protected void defineAdapterSpinner(String[] escopos) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, escopos);
        escopo.setAdapter(adapter);
    }


    protected void instanciaComponentes() {
        titulo = (TextView) findViewById(R.id.novaRequisicaoTitulo);
        instituicao = (TextView) findViewById(R.id.novaRequisicaoInstituicao);
        cargaHoraria = (TextView) findViewById(R.id.novaRequisicaoCargaHoraria);
        dataInicio = findViewById(R.id.novaRequisicaoDataInicio);
        dataTermino = findViewById(R.id.novaRequisicaoDataTermino);
    }

    protected void voltar() {
        voltar = (ImageView) findViewById(R.id.novaRequisicaoVoltar);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaAluno();
            }
        });
    }

    public void onBackPressed() {
        abrirTelaAluno();
    }

    protected void abrirTelaAluno() {
        Intent i = new Intent(NovaRequisicao.this, Aluno.class);
        i.putExtra("ALUNO", aluno);
        startActivity(i);
        finish();
    }

    protected void recuperaParametros() {
        Bundle bundle = getIntent().getExtras();
        aluno = (com.example.apphorasmais.model.entity.Aluno) bundle.get("ALUNO");
    }

    protected void datePickerInicio(){
        Calendar calendar = Calendar.getInstance();
        final int dia = calendar.get(Calendar.DAY_OF_MONTH);
        final int mes = calendar.get(Calendar.MONTH);
        final int ano = calendar.get(Calendar.YEAR);

        dataInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(NovaRequisicao.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
                        mes = mes+1;
                        String data = formataData(dia, mes, ano);
                        dataInicio.setText(data);
                    }
                }, ano, mes, dia);
                datePickerDialog.show();
            }
        });
    }

    protected void datePickerTermino(){
        Calendar calendar = Calendar.getInstance();
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int mes = calendar.get(Calendar.MONTH);
        int ano = calendar.get(Calendar.YEAR);

        dataTermino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(NovaRequisicao.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
                        mes = mes+1;
                        String data = formataData(dia, mes, ano);
                        dataTermino.setText(data);
                    }
                }, ano, mes, dia);
                datePickerDialog.show();
            }
        });
    }

    protected String formataData(int dia, int mes, int ano) {
        return dia +"/"+ "0"+mes +"/"+ ano;
    }

}