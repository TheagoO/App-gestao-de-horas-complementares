package com.example.apphorasmais.model.bo;

import android.database.sqlite.SQLiteDatabase;

import com.example.apphorasmais.model.entity.Requerimento;

import java.util.List;
import com.example.apphorasmais.repository.RequerimentoRepository;

/**
 * @author Thiago Ferreira Assumpção
 */

public class RequerimentoBo {

    public int salvar(SQLiteDatabase conexao, Requerimento requerimento) {
        if(conexao != null){
            if(validaCampos(requerimento)){
                RequerimentoRepository dao = new RequerimentoRepository(conexao);
                return dao.salvar(requerimento);
            }
        }
        return -1;
    }

    private boolean validaCampos(Requerimento requerimento){
        if(requerimento.getCargaHoraria() != 0 && !requerimento.getInstituicao().isEmpty() && !requerimento.getTitulo().isEmpty()){
            return true;
        }
        return false;
    }

    public String editar(SQLiteDatabase conexao, Requerimento requerimento) {
        if(conexao != null){
            RequerimentoRepository dao = new RequerimentoRepository(conexao);
            dao.editar(requerimento);
        }
        return null;
    }

    public String excluir(SQLiteDatabase conexao, int id) {
        if(conexao != null){
            RequerimentoRepository dao = new RequerimentoRepository(conexao);
            dao.excluir(id);
        }
        return null;
    }

    public List<Requerimento> listar(SQLiteDatabase conexao) {
        if(conexao != null){
            RequerimentoRepository dao = new RequerimentoRepository(conexao);
            return dao.listar();
        }
        return null;
    }

    public List<Requerimento> pesquisar(SQLiteDatabase conexao, String titulo) {
        if(conexao != null){
            RequerimentoRepository dao = new RequerimentoRepository(conexao);
            return dao.pesquisar(titulo);
        }
        return null;
    }

    public Requerimento consultar(SQLiteDatabase conexao, int id) {
        if(conexao != null){
            RequerimentoRepository dao = new RequerimentoRepository(conexao);
            return dao.consultar(id);
        }
        return null;
    }

}
