package com.example.apphorasmais.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.apphorasmais.repository.Aluno;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class AlunoDao {

    private SQLiteDatabase conexao;
    private ContentValues contentValues;

    public AlunoDao(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    public void salvar(Aluno aluno) {
        contentValues = new ContentValues();

        contentValues.put("nome", aluno.getNome().trim());
        contentValues.put("usuario", aluno.getUsuario().trim());
        contentValues.put("senha", aluno.getSenha().trim());
        contentValues.put("horasComplementares_id", aluno.getHorasComplementares().getId());
        contentValues.put("turma_id", aluno.getTurma().getId());

        conexao.insertOrThrow("aluno", null, contentValues);
    }

    public void editar(Aluno aluno) {
        contentValues = new ContentValues();
        String[] args = new String[1];

        contentValues.put("nome", aluno.getNome());
        contentValues.put("usuario", aluno.getUsuario());
        
        args[0] = String.valueOf(aluno.getId());

        conexao.update("aluno", contentValues, "id = ?", args);
    }

    public void resetarSenha(int id){
        contentValues = new ContentValues();
        String[] args = new String[1];

        contentValues.put("senha", "20220320");

        args[0] = String.valueOf(id);

        conexao.update("aluno", contentValues, "id = ?", args);
    }

    public void definirSenha(int id, String senha){
        contentValues = new ContentValues();
        String[] args = new String[1];

        contentValues.put("senha", senha);

        args[0] = String.valueOf(id);

        conexao.update("aluno", contentValues, "id = ?", args);
    }

    public void excluir(int id) {
        String[] args = new String[1];
        args[0] = String.valueOf(id);

        conexao.delete("aluno", "id = ?", args);
    }

    public List<Aluno> listar() {
        List<Aluno> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT * FROM aluno");

        Cursor cursor = conexao.rawQuery(sql.toString(), null);
        validaResultadoConsulta(lista, cursor);

        return lista;
    }

    public List<Aluno> pesquisar(String nome) {
        List<Aluno> lista = new ArrayList<Aluno>();
        StringBuilder sql = new StringBuilder();
        String[] args = new String[1];

        sql.append("SELECT * FROM aluno WHERE nome LIKE ?");
        args[0] = nome+"%";

        Cursor cursor = conexao.rawQuery(sql.toString(), args);
        validaResultadoConsulta(lista, cursor);

        return lista;
    }

    public Aluno consultar(String usuario) {
        Aluno aluno = new Aluno();
        StringBuilder sql = new StringBuilder();
        String[] args = new String[1];

        sql.append("SELECT * FROM aluno a INNER JOIN horascomplementares hc ON hc.id = a.horasComplementares_id WHERE a.usuario = ?");
        args[0] = usuario;

        Cursor cursor = conexao.rawQuery(sql.toString(), args);

        iteraDadosConsultarPorUsuario(aluno, cursor);

        return aluno;
    }

    private void iteraDadosConsultarPorUsuario(Aluno aluno, Cursor cursor) {
        if(cursor.moveToFirst()){
            aluno.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            aluno.setNome(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
            aluno.setUsuario(cursor.getString(cursor.getColumnIndexOrThrow("usuario")));
            aluno.setSenha(cursor.getString(cursor.getColumnIndexOrThrow("senha")));

            aluno.getHorasComplementares().setId(cursor.getInt(cursor.getColumnIndexOrThrow("horasComplementares_id")));

            aluno.getHorasComplementares().setTotal(cursor.getInt(cursor.getColumnIndexOrThrow("total")));
            aluno.getHorasComplementares().setTotalAtividade(cursor.getInt(cursor.getColumnIndexOrThrow("totalAtividade")));

            aluno.getTurma().setId(cursor.getInt(cursor.getColumnIndexOrThrow("turma_id")));
        }
    }

    public Aluno consultar(int id) {
        Aluno aluno = new Aluno();
        StringBuilder sql = new StringBuilder();
        String[] args = new String[1];

        sql.append("SELECT a.id, a.usuario, a.horasComplementares_id FROM aluno a INNER JOIN horascomplementares hc ON hc.id = a.horasComplementares_id WHERE a.id = ?");
        args[0] = String.valueOf(id);

        Cursor cursor = conexao.rawQuery(sql.toString(), args);

        iteraDadosConsultarPorId(aluno, cursor);

        return aluno;
    }

    private void iteraDadosConsultarPorId(Aluno aluno, Cursor cursor) {
        if(cursor.moveToFirst()){
            aluno.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            aluno.setUsuario(cursor.getString(cursor.getColumnIndexOrThrow("usuario")));
            aluno.getHorasComplementares().setId(cursor.getInt(cursor.getColumnIndexOrThrow("horasComplementares_id")));

        }
    }

    private void validaResultadoConsulta(List<Aluno> lista, Cursor cursor) {
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            iteraDadosConsulta(lista, cursor);
        }
    }

    private void iteraDadosConsulta(List<Aluno> lista, Cursor cursor) {
        do{
            Aluno aluno = new Aluno();

            aluno.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            aluno.setNome(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
            aluno.setUsuario(cursor.getString(cursor.getColumnIndexOrThrow("usuario")));
            aluno.setSenha(cursor.getString(cursor.getColumnIndexOrThrow("senha")));
            aluno.getHorasComplementares().setId(cursor.getInt(cursor.getColumnIndexOrThrow("horasComplementares_id")));
            aluno.getTurma().setId(cursor.getInt(cursor.getColumnIndexOrThrow("turma_id")));

            lista.add(aluno);
        }while (cursor.moveToNext());
    }

}
