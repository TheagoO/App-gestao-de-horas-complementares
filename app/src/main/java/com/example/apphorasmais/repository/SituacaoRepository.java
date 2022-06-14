package com.example.apphorasmais.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.apphorasmais.model.entity.Situacao;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class SituacaoRepository {

    private SQLiteDatabase conexao;
    private ContentValues contentValues;

    public SituacaoRepository(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    public void salvar(Situacao situacao) {
        contentValues = new ContentValues();

        contentValues.put("status", situacao.getStatus());
        conexao.insertOrThrow("situacao", null, contentValues);

    }

    public void editar(Situacao situacao) {
        contentValues = new ContentValues();

        contentValues.put("status", situacao.getStatus());

        String[] args = new String[1];
        args[0] = String.valueOf(situacao.getId());

        conexao.update("situacao", contentValues, "id = ?", args);

    }

    public void excluir(int id) {
        String[] args = new String[1];
        args[0] = String.valueOf(id);

        conexao.delete("situacao", "id = ?", args);
    }

    public List<Situacao> listar() {
        List<Situacao> lista = new ArrayList<Situacao>();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT id, status FROM situacao");

        Cursor cursor = conexao.rawQuery(sql.toString(), null);

        validaResultadoConsulta(lista, cursor);

        return lista;
    }

    public List<Situacao> pesquisar(String status) {
        List<Situacao> lista = new ArrayList<Situacao>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT id, status FROM situacao WHERE status LIKE ?");
        String[] args = new String[1];

        args[0] = status+"%";
        Cursor cursor = conexao.rawQuery(sql.toString(), args);

        validaResultadoConsulta(lista, cursor);

        return lista;
    }

    public Situacao consultar(String status) {
        Situacao situacao = new Situacao();
        StringBuilder sql = new StringBuilder();
        String[] args = new String[1];

        args[0] = status;
        sql.append("SELECT * FROM situacao WHERE status LIKE ?");

        Cursor cursor = conexao.rawQuery(sql.toString(), args);

        iteraDadosConsulta(situacao, cursor);

        return situacao;
    }

    private void validaResultadoConsulta(List<Situacao> lista, Cursor resultado) {
        if(resultado.getCount() > 0){
            resultado.moveToFirst();
            iteraDadosConsulta(lista, resultado);
        }
    }

    private void iteraDadosConsulta(List<Situacao> lista, Cursor cursor) {
        do{
            Situacao status = new Situacao();

            status.setId(cursor.getInt( cursor.getColumnIndexOrThrow("id")));
            status.setStatus(cursor.getString( cursor.getColumnIndexOrThrow("status")));
            lista.add(status);
        }while(cursor.moveToNext());
    }

    private void iteraDadosConsulta(Situacao situacao, Cursor cursor) {
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            situacao.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            situacao.setStatus(cursor.getString(cursor.getColumnIndexOrThrow("status")));
        }
    }

}
