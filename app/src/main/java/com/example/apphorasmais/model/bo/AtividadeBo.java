package com.example.apphorasmais.model.bo;

import android.database.sqlite.SQLiteDatabase;

import com.example.apphorasmais.model.entity.Atividade;

import java.util.List;
import com.example.apphorasmais.repository.AtividadeRepository;

/**
 * @author Thiago Ferreira Assumpção
 */

public class AtividadeBo {

    public int salvar(SQLiteDatabase conexao, Atividade atividade) {
        if(conexao != null){
            AtividadeRepository dao = new AtividadeRepository(conexao);
            return dao.salvar(atividade);
        }
        return 0;
    }

    public String editar(SQLiteDatabase conexao, Atividade atividade) {
        if(conexao != null){
            AtividadeRepository dao = new AtividadeRepository(conexao);
            dao.editar(atividade);
            return "Alterado";
        }
        return null;
    }

    public String excluir(SQLiteDatabase conexao, int id) {
        if(conexao != null){
            AtividadeRepository dao = new AtividadeRepository(conexao);
            dao.excluir(id);
        }
        return null;
    }

    public List<Atividade> listar(SQLiteDatabase conexao) {
        if(conexao != null){
            AtividadeRepository dao = new AtividadeRepository(conexao);
            return dao.listar();
        }
        return null;
    }

    public List<Atividade> pesquisar(SQLiteDatabase conexao, String escopo) {
        if(conexao != null){
            AtividadeRepository dao = new AtividadeRepository(conexao);
            return dao.pesquisar(escopo);
        }
        return null;
    }

    public Atividade consultar(SQLiteDatabase conexao, int id) {
        if(conexao != null){
            AtividadeRepository dao = new AtividadeRepository(conexao);
            return dao.consultar(id);
        }
        return null;
    }

    public List<Atividade> buscarAtividadeAluno(SQLiteDatabase conexao, int horaComplementar_id){
        if(conexao != null){
            AtividadeRepository dao = new AtividadeRepository(conexao);
            return dao.consultarAtividadeAluno(horaComplementar_id);
        }
        return null;
    }

}
