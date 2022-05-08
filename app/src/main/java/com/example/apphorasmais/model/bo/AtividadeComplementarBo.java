package com.example.apphorasmais.model.bo;

import android.database.sqlite.SQLiteDatabase;

import com.example.apphorasmais.model.entity.AtividadeComplementar;

import java.util.List;
import com.example.apphorasmais.model.dao.AtividadeComplementarDao;

/**
 * @author Thiago Ferreira Assumpção
 */

public class AtividadeComplementarBo {

    public int salvar(SQLiteDatabase conexao, AtividadeComplementar atividadeComplementar) {
        if(conexao != null){
            AtividadeComplementarDao dao = new AtividadeComplementarDao(conexao);
            return dao.salvar(atividadeComplementar);
        }
        return 0;
    }

    public String editar(SQLiteDatabase conexao, AtividadeComplementar atividadeComplementar) {
        if(conexao != null){
            AtividadeComplementarDao dao = new AtividadeComplementarDao(conexao);
            dao.editar(atividadeComplementar);
            return "Alterado";
        }
        return null;
    }

    public String excluir(SQLiteDatabase conexao, int id) {
        if(conexao != null){
            AtividadeComplementarDao dao = new AtividadeComplementarDao(conexao);
            dao.excluir(id);
        }
        return null;
    }

    public List<AtividadeComplementar> listar(SQLiteDatabase conexao) {
        if(conexao != null){
            AtividadeComplementarDao dao = new AtividadeComplementarDao(conexao);
            return dao.listar();
        }
        return null;
    }

    public List<AtividadeComplementar> pesquisar(SQLiteDatabase conexao, String titulo) {
        if(conexao != null){
            AtividadeComplementarDao dao = new AtividadeComplementarDao(conexao);
            return dao.pesquisar(titulo);
        }
        return null;
    }

    public List<AtividadeComplementar> consultar(SQLiteDatabase conexao, int atividade_id) {
        if(conexao != null){
            AtividadeComplementarDao dao = new AtividadeComplementarDao(conexao);
            return dao.consultar(atividade_id);
        }
        return null;
    }

}
