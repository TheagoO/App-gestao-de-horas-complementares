package com.example.apphorasmais.model.bo;

import android.database.sqlite.SQLiteDatabase;

import com.example.apphorasmais.model.entity.Turma;

import java.util.List;
import com.example.apphorasmais.repository.TurmaRepository;

/**
 * @author Thiago Ferreira Assumpção
 */

public class TurmaBo {

    public String salvar(SQLiteDatabase conexao, Turma turma) {
        if(conexao != null){
            TurmaRepository dao = new TurmaRepository(conexao);
            if(dao.salvar(turma) != -1){
                return "Turma cadastrada";
            }
        }
        return "Erro ao cadastrar turma";
    }

    public String editar(SQLiteDatabase conexao, Turma turma, String grupo, Boolean editado) {
        if(conexao != null){
            if(validarDados(turma, grupo) || editado.booleanValue()){
                TurmaRepository dao = new TurmaRepository(conexao);
                turma.setGrupo(grupo);
                dao.editar(turma);
                return "Turma alterada";
            }else{
                return "Altere/Preencha os campos";
            }
        }
        return "Sem conexão";
    }

    private Boolean validarDados(Turma turma, String grupo) {
        if(!turma.getGrupo().equals(grupo) && !grupo.isEmpty()){
            return true;
        }else{
            return false;
        }
    }


    public String excluir(SQLiteDatabase conexao, int id) {
        if(conexao != null){
            TurmaRepository dao = new TurmaRepository(conexao);
            dao.excluir(id);
        }
        return null;
    }

    public List<Turma> listar(SQLiteDatabase conexao) {
        if(conexao != null){
            TurmaRepository dao = new TurmaRepository(conexao);
            return dao.listar();
        }
        return null;
    }

    public List<Turma> listar(SQLiteDatabase conexao, String curso) {
        if(conexao != null){
            TurmaRepository dao = new TurmaRepository(conexao);
            return dao.listar(curso);
        }
        return null;
    }

    public List<Turma> pesquisar(SQLiteDatabase conexao, String turma) {
        if(conexao != null){
            TurmaRepository dao = new TurmaRepository(conexao);
            return dao.pesquisar(turma);
        }
        return null;
    }

    public Turma consultar(SQLiteDatabase conexao, String grupo) {
        if(conexao != null){
            TurmaRepository dao = new TurmaRepository(conexao);
            return dao.consultar(grupo);
        }
        return null;
    }

}
