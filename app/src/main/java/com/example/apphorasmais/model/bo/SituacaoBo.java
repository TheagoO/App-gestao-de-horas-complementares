package com.example.apphorasmais.model.bo;

import android.database.sqlite.SQLiteDatabase;

import com.example.apphorasmais.model.dao.SituacaoDao;
import com.example.apphorasmais.model.entity.Curso;
import com.example.apphorasmais.model.entity.Situacao;

import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class SituacaoBo {

    public String salvar(SQLiteDatabase conexao, Situacao situacao) {
        if(conexao != null){
            SituacaoDao dao = new SituacaoDao(conexao);
            dao.salvar(situacao);
        }
        return null;
    }

    public String editar(SQLiteDatabase conexao, Situacao situacao, String status) {
        if(conexao != null){
            if(validarDados(situacao, status)){
                SituacaoDao dao = new SituacaoDao(conexao);
                dao.editar(situacao);
                return "Status editado";
            }
            return "Altere/Preencha os campos";
        }
        return "Sem conexão";
    }

    private boolean validarDados(Situacao situacao, String status) {
        if(!situacao.getStatus().equals(status) && !status.isEmpty()){
            situacao.setStatus(status);
            return true;
        }
        return false;
    }

    public String excluir(SQLiteDatabase conexao, int id) {
        if(conexao != null){
            SituacaoDao dao = new SituacaoDao(conexao);
            dao.excluir(id);
        }
        return null;
    }

    public List<Situacao> listar(SQLiteDatabase conexao) {
        if(conexao != null){
            SituacaoDao dao = new SituacaoDao(conexao);
            return dao.listar();
        }
        return null;
    }

    public List<Situacao> pesquisar(SQLiteDatabase conexao, String situacao) {
        if(conexao != null){
            SituacaoDao dao = new SituacaoDao(conexao);
            return dao.pesquisar(situacao);
        }
        return null;
    }

    public Situacao consultar(SQLiteDatabase conexao, String status) {
        if(conexao != null){
            SituacaoDao dao = new SituacaoDao(conexao);
            return dao.consultar(status);
        }
        return null;
    }

}
