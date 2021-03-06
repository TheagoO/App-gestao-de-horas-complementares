package com.example.apphorasmais.model.bo;

import android.database.sqlite.SQLiteDatabase;

import com.example.apphorasmais.model.entity.Escopo;

import java.util.List;

import com.example.apphorasmais.repository.EscopoRepository;

/**
 * @author Thiago Ferreira Assumpção
 */

public class EscopoBo {

    public String salvar(SQLiteDatabase conexao, Escopo escopo) {
        if(conexao != null){
            EscopoRepository dao = new EscopoRepository(conexao);
            dao.salvar(escopo);
        }
        return null;
    }

    public String editar(SQLiteDatabase conexao, Escopo escopo, String motivo) {
        if(conexao != null){
            if(validarDados(escopo, motivo)){
                EscopoRepository dao = new EscopoRepository(conexao);
                dao.editar(escopo);
                return "Motivo alterado";
            }
            return "Altere/Preencha os campos";
        }
        return "Sem conexão";
    }

    private boolean validarDados(Escopo escopo, String motivo){
        if(!escopo.getAtividade().equals(motivo) && !motivo.isEmpty()){
            escopo.setAtividade(motivo.trim());
            return true;
        }
        return false;
    }

    public String excluir(SQLiteDatabase conexao, int id) {
        if(conexao != null){
            EscopoRepository dao = new EscopoRepository(conexao);
            dao.excluir(id);
        }
        return null;
    }

    public List<Escopo> listar(SQLiteDatabase conexao) {
        if(conexao != null){
            EscopoRepository dao = new EscopoRepository(conexao);
            return dao.listar();
        }
        return null;
    }

    public List<Escopo> pesquisar(SQLiteDatabase conexao, String escopo) {
        if(conexao != null){
            EscopoRepository dao = new EscopoRepository(conexao);
            return dao.pesquisar(escopo);
        }
        return null;
    }

    public Escopo consultar(SQLiteDatabase conexao, String atividade) {
        if(conexao != null){
            EscopoRepository dao = new EscopoRepository(conexao);
            return dao.consultar(atividade);
        }
        return null;
    }

}
