package com.example.apphorasmais.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.apphorasmais.repository.Coordenador;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class CoordenadorDao {

    private SQLiteDatabase conexao;
    private ContentValues contentValues;

    public CoordenadorDao(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    public void salvar(Coordenador coordenador) {
        contentValues = new ContentValues();

        contentValues.put("nome", coordenador.getNome());
        contentValues.put("usuario", coordenador.getUsuario());
        contentValues.put("senha", coordenador.getSenha());

        conexao.insertOrThrow("coordenador", null, contentValues);
    }

    public void editar(Coordenador coordenador) {
        contentValues = new ContentValues();
        String[] args = new String[1];

        contentValues.put("nome", coordenador.getNome());
        contentValues.put("usuario", coordenador.getUsuario());
        contentValues.put("senha", coordenador.getSenha());
        args[0] = String.valueOf(coordenador.getId());

        conexao.update("coordenador", contentValues, "id = ?", args);
    }

    public void resetarSenha(int id){
        contentValues = new ContentValues();
        String[] args = new String[1];

        contentValues.put("senha", "20220320");

        args[0] = String.valueOf(id);

        conexao.update("coordenador", contentValues, "id = ?", args);
    }

    public void definirSenha(int id, String senha){
        contentValues = new ContentValues();
        String[] args = new String[1];

        contentValues.put("senha", senha);

        args[0] = String.valueOf(id);

        conexao.update("coordenador", contentValues, "id = ?", args);
    }

    public void excluir(int id) {
        String[] args = new String[1];
        args[0] = String.valueOf(id);

        conexao.delete("coordenador", "id = ?", args);
    }

    public List<Coordenador> listar() {
        List<Coordenador> lista = new ArrayList<Coordenador>();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT * FROM coordenador");
        Cursor cursor = conexao.rawQuery(sql.toString(), null);
        validaResultadoConsulta(lista, cursor);

        return lista;
    }

    public List<Coordenador> pesquisar(String nome) {
        List<Coordenador> lista = new ArrayList<Coordenador>();
        StringBuilder sql = new StringBuilder();
        String[] args = new String[1];

        sql.append("SELECT * FROM coordenador WHERE nome LIKE ?");
        args[0] = nome+"%";

        Cursor cursor = conexao.rawQuery(sql.toString(), args);

        validaResultadoConsulta(lista, cursor);

        return lista;
    }

    public Coordenador consultar(String nome) {
        Coordenador coordenador = new Coordenador();
        StringBuilder sql = new StringBuilder();
        String[] args = new String[1];

        sql.append("SELECT * FROM coordenador WHERE nome = ?");
        args[0] = nome;

        Cursor cursor = conexao.rawQuery(sql.toString(), args);
        iteraDadosConsulta(coordenador, cursor);

        return coordenador;
    }

    public Coordenador consultarParaLogin(String usuario) {
        Coordenador coordenador = new Coordenador();
        StringBuilder sql = new StringBuilder();
        String[] args = new String[1];

        sql.append("SELECT * FROM coordenador WHERE usuario = ?");
        args[0] = usuario;

        Cursor cursor = conexao.rawQuery(sql.toString(), args);
        iteraDadosConsulta(coordenador, cursor);

        return coordenador;
    }

    private void validaResultadoConsulta(List<Coordenador> lista, Cursor cursor) {
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            iteraDadosConsulta(lista, cursor);
        }
    }

    private void iteraDadosConsulta(List<Coordenador> lista, Cursor cursor) {
        do{
            Coordenador coordenador = new Coordenador();

            coordenador.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            coordenador.setNome(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
            coordenador.setUsuario(cursor.getString(cursor.getColumnIndexOrThrow("usuario")));
            coordenador.setSenha(cursor.getString(cursor.getColumnIndexOrThrow("senha")));

            lista.add(coordenador);
        }while (cursor.moveToNext());
    }

    private void iteraDadosConsulta(Coordenador coordenador, Cursor cursor) {
        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            coordenador.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            coordenador.setNome(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
            coordenador.setUsuario(cursor.getString(cursor.getColumnIndexOrThrow("usuario")));
            coordenador.setSenha(cursor.getString(cursor.getColumnIndexOrThrow("senha")));
        }
    }

}
