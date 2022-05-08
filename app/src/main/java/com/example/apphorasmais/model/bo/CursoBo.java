package com.example.apphorasmais.model.bo;

import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.example.apphorasmais.model.entity.Curso;

import java.util.List;
import com.example.apphorasmais.model.dao.CursoDao;

/**
 * @author Thiago Ferreira Assumpção
 */

public class CursoBo {

    public int salvar(SQLiteDatabase conexao, Curso curso) {
        if(conexao != null){
            CursoDao dao = new CursoDao(conexao);
            return dao.salvar(curso);
        }
        return 0;
    }

    public String editar(SQLiteDatabase conexao, Curso curso, boolean editado, String titulo) {
        if(conexao != null){
            if(validarDados(curso, editado, titulo)){
                CursoDao dao = new CursoDao(conexao);
                dao.editar(curso);
                return "Curso alterado";
            }
        }
        return "Altere/Preencha os campos";
    }

    private boolean validarDados(Curso curso, boolean editado, String titulo) {
        if((!curso.getTitulo().equals(titulo) && !titulo.isEmpty()) || editado){
            curso.setTitulo(titulo.trim());
            return true;
        }
        return false;
    }

    public String excluir(SQLiteDatabase conexao, int id) {
        if(conexao != null){
            CursoDao dao = new CursoDao(conexao);
            dao.excluir(id);
        }
        return null;
    }

    public List<Curso> listar(SQLiteDatabase conexao) {
        if(conexao != null){
            CursoDao dao = new CursoDao(conexao);
            return dao.listar();
        }
        return null;
    }

    public List<Curso> pesquisar(SQLiteDatabase conexao, String titulo) {
        if(conexao != null){
            CursoDao dao = new CursoDao(conexao);
            return dao.pesquisar(titulo);
        }
        return null;
    }

    public Curso consultar(SQLiteDatabase conexao, String titulo) {
        if(conexao != null){
            CursoDao dao = new CursoDao(conexao);
            return dao.consultar(titulo);
        }
        return null;
    }

}
