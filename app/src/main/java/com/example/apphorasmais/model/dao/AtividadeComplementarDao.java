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

public class AtividadeComplementarDao {

    private SQLiteDatabase conexao;
    private ContentValues contentValues;

    public AtividadeComplementarDao(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    public int salvar(AtividadeComplementar atividadeComplementar) {
        contentValues = new ContentValues();

        contentValues.put("cargaHoraria", atividadeComplementar.getCargaHoraria());
        contentValues.put("horaComplementar", atividadeComplementar.getHoraComplementar());
        contentValues.put("instituicao", atividadeComplementar.getInstituicao());
        contentValues.put("titulo", atividadeComplementar.getTitulo());
        contentValues.put("atividade_id", atividadeComplementar.getAtividade().getId());

        return (int) conexao.insertOrThrow("atividadecomplementar", null, contentValues);
    }

    public void editar(AtividadeComplementar atividadeComplementar) {
        contentValues = new ContentValues();
        String[] args = new String[1];

        contentValues.put("cargaHoraria", atividadeComplementar.getCargaHoraria());
        contentValues.put("horaComplementar", atividadeComplementar.getHoraComplementar());
        contentValues.put("instituicao", atividadeComplementar.getInstituicao());
        contentValues.put("titulo", atividadeComplementar.getTitulo());
        contentValues.put("atividade_id", atividadeComplementar.getAtividade().getId());

        args[0] = String.valueOf(atividadeComplementar.getId());

        conexao.update("atividadecomplementar", contentValues, "id = ?", args);
    }

    public void excluir(int id) {
        String[] args = new String[1];
        args[0] = String.valueOf(id);

        conexao.delete("atividadecomplementar", "id = ?", args);
    }

    public List<AtividadeComplementar> listar() {
        List<AtividadeComplementar> lista = new ArrayList<AtividadeComplementar>();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT id, cargaHoraria, horaComplementar, instituicao, titulo, atividade_id FROM atividadecomplementar");

        Cursor cursor = conexao.rawQuery(sql.toString(), null);
        validaResultadoConsulta(lista, cursor);

        return lista;
    }

    public List<AtividadeComplementar> pesquisar(String titulo) {
        List<AtividadeComplementar> lista = new ArrayList<AtividadeComplementar>();
        StringBuilder sql = new StringBuilder();
        String[] args = new String[1];

        sql.append("SELECT id, cargaHoraria, horaComplementar, instituicao, titulo, atividade_id FROM atividadecomplementar WHERE titulo = ?%");
        args[0] = titulo;

        Cursor cursor = conexao.rawQuery(sql.toString(), args);
        validaResultadoConsulta(lista, cursor);

        return lista;
    }

    public List<AtividadeComplementar> consultar(int atividade_id) {
        List<AtividadeComplementar> lista = new ArrayList<AtividadeComplementar>();
        StringBuilder sql = new StringBuilder();
        String[] args = new String[1];

        sql.append("SELECT * FROM atividadecomplementar a WHERE a.atividade_id = ? ORDER BY a.horaComplementar DESC");
        args[0] = String.valueOf(atividade_id);

        Cursor cursor = conexao.rawQuery(sql.toString(), args);
        validaResultadoConsulta(lista, cursor);

        return lista;
    }

    private void validaResultadoConsulta(List<AtividadeComplementar> lista, Cursor cursor) {
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            iteraDadosConsulta(lista, cursor);
        }
    }

    private void iteraDadosConsulta(List<AtividadeComplementar> lista, Cursor cursor) {
        do{
            AtividadeComplementar atividadeComplementar = new AtividadeComplementar();

            atividadeComplementar.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            atividadeComplementar.setTitulo(cursor.getString(cursor.getColumnIndexOrThrow("titulo")));
            atividadeComplementar.setInstituicao(cursor.getString(cursor.getColumnIndexOrThrow("instituicao")));
            atividadeComplementar.setCargaHoraria(cursor.getInt(cursor.getColumnIndexOrThrow("cargaHoraria")));
            atividadeComplementar.setHoraComplementar(cursor.getInt(cursor.getColumnIndexOrThrow("horaComplementar")));
            atividadeComplementar.getAtividade().setId(cursor.getInt(cursor.getColumnIndexOrThrow("atividade_id")));

            lista.add(atividadeComplementar);
        }while (cursor.moveToNext());
    }

    private void iteraDadosConsulta(AtividadeComplementar atividadeComplementar, Cursor cursor) {
        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            atividadeComplementar.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            atividadeComplementar.setTitulo(cursor.getString(cursor.getColumnIndexOrThrow("titulo")));
            atividadeComplementar.setInstituicao(cursor.getString(cursor.getColumnIndexOrThrow("instituicao")));
            atividadeComplementar.setCargaHoraria(cursor.getInt(cursor.getColumnIndexOrThrow("cargaHoraria")));
            atividadeComplementar.setHoraComplementar(cursor.getInt(cursor.getColumnIndexOrThrow("horaComplementar")));
            atividadeComplementar.getAtividade().setId(cursor.getInt(cursor.getColumnIndexOrThrow("atividade_id")));
        }
    }
}
