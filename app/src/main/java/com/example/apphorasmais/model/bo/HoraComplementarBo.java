package com.example.apphorasmais.model.bo;

import android.database.sqlite.SQLiteDatabase;

import com.example.apphorasmais.model.entity.HoraComplementar;

import java.util.List;
import com.example.apphorasmais.model.dao.HoraComplementarDao;

/**
 * @author Thiago Ferreira Assumpção
 */

public class HoraComplementarBo {

    public int salvar(SQLiteDatabase conexao, HoraComplementar horas) {
        if(conexao != null){
            HoraComplementarDao dao = new HoraComplementarDao(conexao);
            return dao.salvar(horas);
        }
        return 0;
    }

    public String editar(SQLiteDatabase conexao, HoraComplementar horas) {
        if(conexao != null){
            HoraComplementarDao dao = new HoraComplementarDao(conexao);
            dao.editar(horas);
        }
        return null;
    }

    public String excluir(SQLiteDatabase conexao, int id) {
        if(conexao != null){
            HoraComplementarDao dao = new HoraComplementarDao(conexao);
            dao.excluir(id);
        }
        return null;
    }

    public List<HoraComplementar> listar(SQLiteDatabase conexao) {
        if(conexao != null){
            HoraComplementarDao dao = new HoraComplementarDao(conexao);
            return dao.listar();
        }
        return null;
    }

    public HoraComplementar consultar(SQLiteDatabase conexao, int id) {
        if(conexao != null){
            HoraComplementarDao dao = new HoraComplementarDao(conexao);
            return dao.consultar(id);
        }
        return null;
    }

}
