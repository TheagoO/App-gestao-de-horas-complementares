package com.example.apphorasmais.model.bo;

import android.database.sqlite.SQLiteDatabase;

import com.example.apphorasmais.model.entity.PeriodoLetivo;

import java.util.List;
import com.example.apphorasmais.repository.PeriodoLetivoRepository;

/**
 * @author Thiago Ferreira Assumpção
 */

public class PeriodoLetivoBo {

    public int salvar(SQLiteDatabase conexao, PeriodoLetivo periodo) {
        if(conexao != null){
            PeriodoLetivoRepository dao = new PeriodoLetivoRepository(conexao);
            return dao.salvar(periodo);
        }
        return 0;
    }

    public String editar(SQLiteDatabase conexao, PeriodoLetivo periodoLetivo, String periodo) {
        if(conexao != null){
            if(validarDados(periodoLetivo, periodo)){
                PeriodoLetivoRepository dao = new PeriodoLetivoRepository(conexao);
                dao.editar(periodoLetivo);
                return "Período alterado";
            }
            return "Altere/Preencha os campos";
        }
        return "Sem conexão";
    }

    private boolean validarDados(PeriodoLetivo periodoLetivo, String periodo){
        if(!periodoLetivo.getPeriodo().equals(periodo) && !periodo.isEmpty()){
            periodoLetivo.setPeriodo(periodo);
            return true;
        }
        return false;
    }

    public String excluir(SQLiteDatabase conexao, int id) {
        if(conexao != null){
            PeriodoLetivoRepository dao = new PeriodoLetivoRepository(conexao);
            dao.excluir(id);
        }
        return null;
    }

    public List<PeriodoLetivo> listar(SQLiteDatabase conexao) {
        if(conexao != null){
            PeriodoLetivoRepository dao = new PeriodoLetivoRepository(conexao);
            return dao.listar();
        }
        return null;
    }

    public List<PeriodoLetivo> listar(SQLiteDatabase conexao, String turma) {
        if(conexao != null){
            PeriodoLetivoRepository dao = new PeriodoLetivoRepository(conexao);
            return dao.listar(turma);
        }
        return null;
    }

    public List<PeriodoLetivo> pesquisar(SQLiteDatabase conexao, String periodo) {
        if(conexao != null){
            PeriodoLetivoRepository dao = new PeriodoLetivoRepository(conexao);
            return dao.pesquisar(periodo);
        }
        return null;
    }

    public PeriodoLetivo consultar(SQLiteDatabase conexao, String periodo) {
        if(conexao != null){
            PeriodoLetivoRepository dao = new PeriodoLetivoRepository(conexao);
            return dao.consultar(periodo);
        }
        return null;
    }

}
