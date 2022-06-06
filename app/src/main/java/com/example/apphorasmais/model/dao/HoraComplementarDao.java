package com.example.apphorasmais.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.apphorasmais.repository.HoraComplementar;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class HoraComplementarDao {

    private SQLiteDatabase conexao;
    private ContentValues contentValues;

    public HoraComplementarDao(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    public int salvar(HoraComplementar horas) {
        contentValues = new ContentValues();

        contentValues.put("total", horas.getTotal());
        contentValues.put("totalAtividade", horas.getTotalAtividade());

        return (int) conexao.insert("horascomplementares", null, contentValues);
    }

    public void editar(HoraComplementar horas) {
        contentValues = new ContentValues();

        contentValues.put("total", horas.getTotal());
        contentValues.put("totalAtividade", horas.getTotalAtividade());

        String[] args = new String[1];
        args[0] = String.valueOf(horas.getId());

        conexao.update("horascomplementares", contentValues, "id = ?",args);
    }

    public void excluir(int id) {
        String[] args = new String[1];
        args[0] = String.valueOf(id);

        conexao.delete("horascomplementares", "id = ?", args);
    }

    public List<HoraComplementar> listar() {
        List<HoraComplementar> lista = new ArrayList<HoraComplementar>();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT id, total, totalAtividade FROM horascomplementares");

        Cursor cursor = conexao.rawQuery(sql.toString(), null);
        validaResultadoConsulta(lista, cursor);

        return lista;
    }

    public HoraComplementar consultar(int id) {
        HoraComplementar horas = new HoraComplementar();
        StringBuilder sql = new StringBuilder();
        String[] args = new String[1];

        sql.append("SELECT id, total, totalAtividade FROM horascomplementares WHERE id = ?");
        args[0] = String.valueOf(id);

        Cursor cursor = conexao.rawQuery(sql.toString(), args);

        iteraDadosConsulta(horas, cursor);

        return horas;
    }


    private void validaResultadoConsulta(List<HoraComplementar> lista, Cursor cursor) {
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            iteraDadosConsulta(lista, cursor);
        }
    }

    private void iteraDadosConsulta(List<HoraComplementar> lista, Cursor cursor) {
        do{
            HoraComplementar horas = new HoraComplementar();

            horas.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            horas.setTotal(cursor.getInt(cursor.getColumnIndexOrThrow("total")));
            horas.setTotalAtividade(cursor.getInt(cursor.getColumnIndexOrThrow("totalAtividade")));

            lista.add(horas);
        }while (cursor.moveToNext());
    }

    private void iteraDadosConsulta(HoraComplementar horas, Cursor cursor) {
        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            horas.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            horas.setTotal(cursor.getInt(cursor.getColumnIndexOrThrow("total")));
            horas.setTotalAtividade(cursor.getInt(cursor.getColumnIndexOrThrow("totalAtividade")));
        }
    }
}
