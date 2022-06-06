package com.example.apphorasmais.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.apphorasmais.repository.Turma;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class TurmaDao {

    private SQLiteDatabase conexao;
    private ContentValues contentValues;

    public TurmaDao(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    public int salvar(Turma turma) {
        contentValues = new ContentValues();

        contentValues.put("grupo", turma.getGrupo().trim().toUpperCase());
        contentValues.put("curso_id", turma.getCurso().getId());
        contentValues.put("periodo_id", turma.getPeriodo().getId());

        return (int) conexao.insertOrThrow("turma", null, contentValues);
    }

    public void editar(Turma turma) {
        contentValues = new ContentValues();

        contentValues.put("grupo", turma.getGrupo().trim().toUpperCase());
        contentValues.put("curso_id", turma.getCurso().getId());
        contentValues.put("periodo_id", turma.getPeriodo().getId());

        String[] args = new String[1];
        args[0] = String.valueOf(turma.getId());

        conexao.update("turma", contentValues, "id = ?", args);
    }

    public void excluir(int id) {
        String[] args = new String[1];

        args[0] = String.valueOf(id);

        conexao.delete("turma", "id = ?", args);
    }

    public List<Turma> listar() {
        List<Turma> lista = new ArrayList<Turma>();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT t.id, t.grupo, t.curso_id, t.periodo_id, p.periodo, c.curso FROM turma t INNER JOIN curso c ON c.id = t.curso_id INNER JOIN periodoletivo p ON p.id = t.periodo_id");
        Cursor cursor = conexao.rawQuery(sql.toString(), null);

        validaResultadoConsulta(lista, cursor);

        return lista;
    }

    public List<Turma> listar(String curso) {
        List<Turma> lista = new ArrayList<Turma>();
        StringBuilder sql = new StringBuilder();
        String[] args = new String[1];

        sql.append("SELECT t.id, t.grupo, t.curso_id, t.periodo_id, p.periodo, c.curso FROM turma t INNER JOIN curso c ON c.id = t.curso_id INNER JOIN periodoletivo p ON p.id = t.periodo_id WHERE c.curso = ?");
        args[0] = curso;

        Cursor cursor = conexao.rawQuery(sql.toString(), args);

        validaResultadoConsulta(lista, cursor);

        return lista;
    }

    public List<Turma> pesquisar(String turma) {
        List<Turma> lista = new ArrayList<Turma>();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT * FROM turma t INNER JOIN periodoletivo p ON t.periodo_id = p.id INNER JOIN curso c ON c.id = t.curso_id WHERE t.grupo LIKE ?");

        String[] args = new String[1];
        args[0] = turma+"%";

        Cursor cursor = conexao.rawQuery(sql.toString(), args);

        validaResultadoConsulta(lista, cursor);

        return lista;
    }

    public Turma consultar(String grupo) {
        Turma turma = new Turma();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT * FROM turma WHERE grupo = ?");

        String[] args = new String[1];
        args[0] = String.valueOf(grupo);

        Cursor cursor = conexao.rawQuery(sql.toString(), args);
        iteraDadosConsulta(turma, cursor);

        return turma;
    }

    private void validaResultadoConsulta(List<Turma> lista, Cursor cursor) {
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            iteraDadosConsulta(lista, cursor);
        }
    }

    private void iteraDadosConsulta(List<Turma> lista, Cursor cursor) {
        do{
            Turma turma = new Turma();

            turma.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            turma.setGrupo(cursor.getString(cursor.getColumnIndexOrThrow("grupo")));
            turma.getCurso().setId(cursor.getInt(cursor.getColumnIndexOrThrow("curso_id")));
            turma.getCurso().setTitulo(cursor.getString(cursor.getColumnIndexOrThrow("curso")));
            turma.getPeriodo().setId(cursor.getInt(cursor.getColumnIndexOrThrow("periodo_id")));
            turma.getPeriodo().setPeriodo(cursor.getString(cursor.getColumnIndexOrThrow("periodo")));

            lista.add(turma);
        }while (cursor.moveToNext());
    }

    private void iteraDadosConsulta(Turma turma, Cursor cursor) {
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            turma.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            turma.setGrupo(cursor.getString(cursor.getColumnIndexOrThrow("grupo")));
            turma.getCurso().setId(cursor.getInt(cursor.getColumnIndexOrThrow("curso_id")));
            turma.getPeriodo().setId(cursor.getInt(cursor.getColumnIndexOrThrow("periodo_id")));
        }
    }

}
