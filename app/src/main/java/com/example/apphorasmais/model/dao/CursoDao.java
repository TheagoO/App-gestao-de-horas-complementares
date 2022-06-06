package com.example.apphorasmais.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.apphorasmais.repository.Curso;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class CursoDao {

    private SQLiteDatabase conexao;
    private ContentValues contentValues;

    public CursoDao(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    public int salvar(Curso curso) {
        contentValues = new ContentValues();

        contentValues.put("curso", curso.getTitulo());
        contentValues.put("coordenador_id", curso.getCoordenador().getId());

        return (int) conexao.insert("curso", null, contentValues);
    }

    public void editar(Curso curso) {
        contentValues = new ContentValues();
        String[] args = new String[1];

        contentValues.put("curso", curso.getTitulo());
        contentValues.put("coordenador_id", curso.getCoordenador().getId());
        args[0] = String.valueOf(curso.getId());

        conexao.update("curso", contentValues, "id = ?", args);
    }

    public void excluir(int id) {
        String[] args = new String[1];
        args[0] = String.valueOf(id);

        conexao.delete("curso", "id = ?", args);
    }

    public List<Curso> listar() {
        List<Curso> lista = new ArrayList<Curso>();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT c.id, c.curso, c.coordenador_id, coord.nome FROM curso c INNER JOIN coordenador coord ON coord.id = c.coordenador_id");


        Cursor cursor = conexao.rawQuery(sql.toString(), null);
        validaResultadoConsulta(lista, cursor);

        return lista;
    }

    public List<Curso> pesquisar(String titulo) {
        List<Curso> lista = new ArrayList<Curso>();
        StringBuilder sql = new StringBuilder();
        String[] args = new String[1];

        sql.append("SELECT * FROM curso INNER JOIN coordenador ON coordenador.id = curso.coordenador_id WHERE curso.curso LIKE ?");
        args[0] = titulo+"%";

        Cursor cursor = conexao.rawQuery(sql.toString(), args);

        validaResultadoConsulta(lista, cursor);

        return lista;
    }

    public Curso consultar(String titulo) {
        Curso curso = new Curso();
        StringBuilder sql = new StringBuilder();
        String[] args = new String[1];

        sql.append("SELECT * FROM curso c INNER JOIN coordenador coord ON c.coordenador_id = coord.id WHERE c.curso = ?");
        args[0] = titulo;

        Cursor cursor = conexao.rawQuery(sql.toString(), args);
        iteraDadosConsulta(curso, cursor);

        return curso;
    }

    private void validaResultadoConsulta(List<Curso> lista, Cursor cursor) {
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            iteraDadosConsulta(lista, cursor);
        }
    }

    private void iteraDadosConsulta(List<Curso> lista, Cursor cursor) {
        do{
            Curso curso = new Curso();

            curso.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            curso.setTitulo(cursor.getString(cursor.getColumnIndexOrThrow("curso")));
            curso.getCoordenador().setId(cursor.getInt(cursor.getColumnIndexOrThrow("coordenador_id")));
            curso.getCoordenador().setNome(cursor.getString(cursor.getColumnIndexOrThrow("nome")));

            lista.add(curso);
        }while (cursor.moveToNext());
    }

    private void iteraDadosConsulta(Curso curso, Cursor cursor) {
        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            curso.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            curso.setTitulo(cursor.getString(cursor.getColumnIndexOrThrow("curso")));
            curso.getCoordenador().setId(cursor.getInt(cursor.getColumnIndexOrThrow("coordenador_id")));
            curso.getCoordenador().setNome(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
        }
    }
}
