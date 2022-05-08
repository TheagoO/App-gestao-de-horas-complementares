package com.example.apphorasmais.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.apphorasmais.model.entity.Requerimento;
import com.example.apphorasmais.model.entity.Turma;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class RequerimentoDao {

    private SQLiteDatabase conexao;
    private ContentValues contentValues;

    public RequerimentoDao(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    public int salvar(Requerimento requerimento) {
        contentValues = new ContentValues();

        contentValues.put("cargaHoraria", requerimento.getCargaHoraria());
        contentValues.put("dataInicio", requerimento.getDataInicio());
        contentValues.put("dataTermino", requerimento.getDataTermino());
        contentValues.put("instituicao", requerimento.getInstituicao().trim());
        contentValues.put("titulo", requerimento.getTitulo().trim());
        contentValues.put("escopo_id", requerimento.getEscopo().getId());

        return (int) conexao.insert("requerimento", null, contentValues);
    }

    public void editar(Requerimento requerimento) {
        contentValues = new ContentValues();

        contentValues.put("cargaHoraria", requerimento.getCargaHoraria());
        contentValues.put("dataInicio", requerimento.getDataInicio());
        contentValues.put("dataTermino", requerimento.getDataTermino());
        contentValues.put("instituicao", requerimento.getInstituicao().trim());
        contentValues.put("titulo", requerimento.getTitulo().trim());
        contentValues.put("escopo_id", requerimento.getEscopo().getId());

        String[] args = new String[1];
        args[0] = String.valueOf(requerimento.getId());

        conexao.update("requerimento", contentValues, "id = ?", args);
    }

    public void excluir(int id) {
        String[] args = new String[1];

        args[0] = String.valueOf(id);

        conexao.delete("requerimento", "id = ?", args);
    }

    public List<Requerimento> listar() {
        List<Requerimento> lista = new ArrayList<Requerimento>();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT * FROM requerimento");

        Cursor cursor = conexao.rawQuery(sql.toString(), null);
        validaResultadoConsulta(lista, cursor);

        return lista;
    }

    public List<Requerimento> pesquisar(String titulo) {
        List<Requerimento> lista = new ArrayList<Requerimento>();
        StringBuilder sql = new StringBuilder();
        String[] args = new String[1];

        args[0] = titulo;
        sql.append("SELECT * FROM requerimento WHERE curso LIKE ?%");

        Cursor cursor = conexao.rawQuery(sql.toString(), args);
        validaResultadoConsulta(lista, cursor);

        return lista;
    }

    public Requerimento consultar(int id) {
        Requerimento requerimento = new Requerimento();
        StringBuilder sql = new StringBuilder();
        String[] args = new String[1];

        args[0] = String.valueOf(id);
        sql.append("SELECT * FROM requerimento WHERE id = ?");

        Cursor cursor = conexao.rawQuery(sql.toString(), args);
        iteraDadosConsulta(requerimento, cursor);

        return requerimento;
    }

    private void validaResultadoConsulta(List<Requerimento> lista, Cursor cursor) {
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            iteraDadosConsulta(lista, cursor);
        }
    }

    private void iteraDadosConsulta(List<Requerimento> lista, Cursor cursor) {
        do{
            Requerimento requerimento = new Requerimento();

            requerimento.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            requerimento.setCargaHoraria(cursor.getInt(cursor.getColumnIndexOrThrow("cargaHoraria")));
            requerimento.setDataInicio(cursor.getString(cursor.getColumnIndexOrThrow("dataInicio")));
            requerimento.setDataTermino(cursor.getString(cursor.getColumnIndexOrThrow("dataTermino")));
            requerimento.setInstituicao(cursor.getString(cursor.getColumnIndexOrThrow("instituicao")));
            requerimento.setTitulo(cursor.getString(cursor.getColumnIndexOrThrow("titulo")));
            requerimento.getEscopo().setId(cursor.getInt(cursor.getColumnIndexOrThrow("escopo_id")));

            lista.add(requerimento);
        }while (cursor.moveToNext());
    }

    private void iteraDadosConsulta(Requerimento requerimento, Cursor cursor) {
        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            requerimento.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            requerimento.setCargaHoraria(cursor.getInt(cursor.getColumnIndexOrThrow("cargaHoraria")));
            requerimento.setDataInicio(cursor.getString(cursor.getColumnIndexOrThrow("dataInicio")));
            requerimento.setDataTermino(cursor.getString(cursor.getColumnIndexOrThrow("dataTermino")));
            requerimento.setInstituicao(cursor.getString(cursor.getColumnIndexOrThrow("instituicao")));
            requerimento.setTitulo(cursor.getString(cursor.getColumnIndexOrThrow("titulo")));
            requerimento.getEscopo().setId(cursor.getInt(cursor.getColumnIndexOrThrow("escopo_id")));

        }
    }

}
