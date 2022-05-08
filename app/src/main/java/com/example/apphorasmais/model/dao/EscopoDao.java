package com.example.apphorasmais.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.apphorasmais.model.entity.Escopo;
import com.example.apphorasmais.model.entity.HoraComplementar;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class EscopoDao {

    private SQLiteDatabase conexao;
    private ContentValues contentValues;

    public EscopoDao(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    public void salvar(Escopo escopo) {
        contentValues = new ContentValues();

        contentValues.put("atividade", escopo.getAtividade());

        conexao.insertOrThrow("escopo", null, contentValues);
    }

    public void editar(Escopo escopo) {
        contentValues = new ContentValues();

        contentValues.put("atividade", escopo.getAtividade());

        String[] args = new String[1];
        args[0] = String.valueOf(escopo.getId());

        conexao.update("escopo", contentValues, "id = ?", args);
    }

    public void excluir(int id) {
        String[] args = new String[1];
        args[0] = String.valueOf(id);

        conexao.delete("escopo", "id = ?", args);
    }

    public List<Escopo> listar() {
        List<Escopo> lista = new ArrayList<Escopo>();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT id, atividade FROM escopo");
        Cursor cursor = conexao.rawQuery(sql.toString(), null);

        validaResultadoConsulta(lista, cursor);

        return lista;
    }

    public List<Escopo> pesquisar(String escopo) {
        List<Escopo> lista = new ArrayList<Escopo>();
        StringBuilder sql = new StringBuilder();
        String[] args = new String[1];

        sql.append("SELECT * FROM escopo WHERE atividade LIKE ?");
        args[0] = escopo+"%";

        Cursor cursor = conexao.rawQuery(sql.toString(), args);

        validaResultadoConsulta(lista, cursor);

        return lista;
    }

    public Escopo consultar(String motivo) {
        Escopo escopo = new Escopo();
        StringBuilder sql = new StringBuilder();
        String[] args = new String[1];

        sql.append("SELECT * FROM escopo WHERE atividade = ?");
        args[0] = motivo;

        Cursor cursor = conexao.rawQuery(sql.toString(), args);

        iteraDadosConsulta(escopo, cursor);

        return escopo;
    }

    private void validaResultadoConsulta(List<Escopo> lista, Cursor cursor) {
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            iteraDadosConsulta(lista, cursor);
        }
    }

    private void iteraDadosConsulta(List<Escopo> lista, Cursor cursor) {
        do{
            Escopo escopo = new Escopo();

            escopo.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            escopo.setAtividade(cursor.getString(cursor.getColumnIndexOrThrow("atividade")));

            lista.add(escopo);
        }while (cursor.moveToNext());
    }

    private void iteraDadosConsulta(Escopo escopo, Cursor cursor) {
        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            escopo.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            escopo.setAtividade(cursor.getString(cursor.getColumnIndexOrThrow("atividade")));
        }
    }
}
