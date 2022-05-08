package com.example.apphorasmais.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.apphorasmais.model.entity.Atividade;
import com.example.apphorasmais.model.entity.AtividadeComplementar;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class AtividadeDao {

    private SQLiteDatabase conexao;
    private ContentValues contentValues;

    public AtividadeDao(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    public int salvar(Atividade atividade) {
        contentValues = new ContentValues();

        contentValues.put("escopo_id", atividade.getEscopo().getId());
        contentValues.put("quantidade", atividade.getQuantidade());
        contentValues.put("horasComplementares_id", atividade.getHorasComplementares().getId());

        return (int) conexao.insert("atividade", null, contentValues);
    }

    public void editar(Atividade atividade) {
        contentValues = new ContentValues();
        String[] args = new String[1];

        contentValues.put("escopo_id", atividade.getEscopo().getId());
        contentValues.put("quantidade", atividade.getQuantidade());
        contentValues.put("horasComplementares_id", atividade.getHorasComplementares().getId());

        args[0] = String.valueOf(atividade.getId());

        conexao.update("atividade", contentValues, "id = ?", args);
    }

    public void excluir(int id) {
        String[] args = new String[1];
        args[0] = String.valueOf(id);

        conexao.delete("atividade", "id = ?", args);
    }

    public List<Atividade> listar() {
        List<Atividade> lista = new ArrayList<Atividade>();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT * FROM atividade a INNER JOIN escopo e ON e.id = a.escopo_id");

        Cursor cursor = conexao.rawQuery(sql.toString(), null);
        validaResultadoConsulta(lista, cursor);

        return lista;
    }

    public List<Atividade> pesquisar(String escopo) {
        List<Atividade> lista = new ArrayList<Atividade>();
        StringBuilder sql = new StringBuilder();
        String[] args = new String[1];

        sql.append("SELECT id, escopo_id, quantidade, horasComplementares_id FROM atividade WHERE escopo LIKE ?%");
        args[0] = escopo;

        Cursor cursor = conexao.rawQuery(sql.toString(), args);
        validaResultadoConsulta(lista, cursor);

        return lista;
    }

    public Atividade consultar(int id) {
        Atividade atividade = new Atividade();
        StringBuilder sql = new StringBuilder();
        String[] args = new String[1];

        sql.append("SELECT id, escopo_id, quantidade, horasComplementares_id FROM atividade WHERE id = ?");
        args[0] = String.valueOf(id);

        Cursor cursor = conexao.rawQuery(sql.toString(), args);
        iteraDadosConsulta(atividade, cursor);

        return atividade;
    }

    public List<Atividade> consultarAtividadeAluno(int horaComplementar_id){
        List<Atividade> lista = new ArrayList<Atividade>();
        StringBuilder sql = new StringBuilder();
        String[] args = new String[1];

        sql.append("SELECT a.id, a.quantidade, a.escopo_id, e.atividade, a.horasComplementares_id FROM atividade a INNER JOIN escopo e ON e.id = a.escopo_id WHERE a.horasComplementares_id = ?");
        args[0] = String.valueOf(horaComplementar_id);

        Cursor cursor = conexao.rawQuery(sql.toString(), args);
        validaResultadoConsulta(lista, cursor);

        return lista;
    }


    private void validaResultadoConsulta(List<Atividade> lista, Cursor cursor) {
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            iteraDadosConsulta(lista, cursor);
        }
    }

    private void iteraDadosConsulta(List<Atividade> lista, Cursor cursor) {
        do{
            Atividade atividade = new Atividade();

            atividade.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            atividade.getEscopo().setId(cursor.getInt(cursor.getColumnIndexOrThrow("escopo_id")));
            atividade.getEscopo().setAtividade(cursor.getString(cursor.getColumnIndexOrThrow("atividade")));
            atividade.setQuantidade(cursor.getInt(cursor.getColumnIndexOrThrow("quantidade")));
            atividade.getHorasComplementares().setId(cursor.getInt(cursor.getColumnIndexOrThrow("horasComplementares_id")));

            lista.add(atividade);
        }while (cursor.moveToNext());
    }

    private void iteraDadosConsulta(Atividade atividade, Cursor cursor) {
        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            atividade.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            atividade.setQuantidade(cursor.getInt(cursor.getColumnIndexOrThrow("quantidade")));

            atividade.getEscopo().setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            atividade.getEscopo().setAtividade(cursor.getString(cursor.getColumnIndexOrThrow("atividade")));

            atividade.getHorasComplementares().setId(cursor.getInt(cursor.getColumnIndexOrThrow("horasComplementares_id")));

        }
    }
}
