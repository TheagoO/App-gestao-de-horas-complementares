package com.example.apphorasmais.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.apphorasmais.model.entity.PeriodoLetivo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class PeriodoLetivoRepository {

    private SQLiteDatabase conexao;
    private ContentValues contentValues;

    public PeriodoLetivoRepository(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    public int salvar(PeriodoLetivo periodo) {
        contentValues = new ContentValues();

        contentValues.put("periodo", periodo.getPeriodo().trim());

        return (int) conexao.insert("periodoletivo", null, contentValues);
    }

    public void editar(PeriodoLetivo periodo) {
        contentValues = new ContentValues();
        String[] args = new String[1];

        contentValues.put("periodo", periodo.getPeriodo().trim());
        args[0] = String.valueOf(periodo.getId());

        conexao.update("periodoletivo", contentValues, "id = ?", args);
    }

    public void excluir(int id) {
        String[] args = new String[1];
        args[0] = String.valueOf(id);

        conexao.delete("periodoletivo", "id = ?", args);
    }

    public List<PeriodoLetivo> listar() {
        List<PeriodoLetivo> lista = new ArrayList<PeriodoLetivo>();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT * FROM periodoletivo");

        Cursor cursor = conexao.rawQuery(sql.toString(), null);
        validaResultadoConsulta(lista, cursor);

        return lista;
    }

    public List<PeriodoLetivo> listar(String turma) {
        List<PeriodoLetivo> lista = new ArrayList<PeriodoLetivo>();
        StringBuilder sql = new StringBuilder();
        String[] args = new String[1];

        sql.append("SELECT p.id, p.periodo FROM turma t INNER JOIN periodoletivo p ON p.id = t.periodo_id WHERE t.grupo = ?");
        args[0] = turma;

        Cursor cursor = conexao.rawQuery(sql.toString(), args);
        validaResultadoConsulta(lista, cursor);

        return lista;
    }

    public List<PeriodoLetivo> pesquisar(String periodo) {
        List<PeriodoLetivo> lista = new ArrayList<PeriodoLetivo>();
        StringBuilder sql = new StringBuilder();
        String[] args = new String[1];

        sql.append("SELECT * FROM periodoletivo WHERE periodo LIKE ?");
        args[0] = periodo+"%";

        Cursor cursor = conexao.rawQuery(sql.toString(), args);
        validaResultadoConsulta(lista, cursor);

        return lista;
    }

    public PeriodoLetivo consultar(String p) {
        PeriodoLetivo periodo = new PeriodoLetivo();
        StringBuilder sql = new StringBuilder();
        String[] args = new String[1];

        sql.append("SELECT * FROM periodoletivo WHERE periodo = ?");
        args[0] = p;

        Cursor cursor = conexao.rawQuery(sql.toString(), args);
        iteraDadosConsulta(periodo, cursor);

        return periodo;
    }

    private void validaResultadoConsulta(List<PeriodoLetivo> lista, Cursor cursor) {
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            iteraDadosConsulta(lista, cursor);
        }
    }

    private void iteraDadosConsulta(List<PeriodoLetivo> lista, Cursor cursor) {
        do{
            PeriodoLetivo periodo = new PeriodoLetivo();

            periodo.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            periodo.setPeriodo(cursor.getString(cursor.getColumnIndexOrThrow("periodo")));

            lista.add(periodo);
        }while (cursor.moveToNext());
    }

    private void iteraDadosConsulta(PeriodoLetivo periodo, Cursor cursor) {
        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            periodo.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            periodo.setPeriodo(cursor.getString(cursor.getColumnIndexOrThrow("periodo")));
        }
    }
}
