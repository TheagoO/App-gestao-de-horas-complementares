package com.example.apphorasmais.model.bo;

import android.database.sqlite.SQLiteDatabase;

import com.example.apphorasmais.model.entity.Solicitacao;

import java.util.List;
import java.util.Random;

import com.example.apphorasmais.repository.SolicitacaoRepository;

/**
 * @author Thiago Ferreira Assumpção
 */

public class SolicitacaoBo {

    public String salvar(SQLiteDatabase conexao, Solicitacao solicitacao) {
        if(conexao != null){
            SolicitacaoRepository dao = new SolicitacaoRepository(conexao);
            solicitacao.setProtocolo(getProtocolo());
            dao.salvar(solicitacao);
        }
        return null;
    }

    protected int getProtocolo(){
        Random random = new Random();
        int protocolo;
        do{
            protocolo = random.nextInt(999999);
        }while ((String.valueOf(protocolo)).length() < 6);
        return protocolo;
    }

    public String editar(SQLiteDatabase conexao, Solicitacao solicitacao) {
        if(conexao != null){
            SolicitacaoRepository dao = new SolicitacaoRepository(conexao);
            dao.editar(solicitacao);
            return "Sucesso";
        }
        return "Erro ao negar requerimento";
    }

    public String excluir(SQLiteDatabase conexao, int id) {
        if(conexao != null){
            SolicitacaoRepository dao = new SolicitacaoRepository(conexao);
            dao.excluir(id);
        }
        return null;
    }

    public List<Solicitacao> listar(SQLiteDatabase conexao) {
        if(conexao != null){
            SolicitacaoRepository dao = new SolicitacaoRepository(conexao);
            return dao.listar();
        }
        return null;
    }

    public List<Solicitacao> listarPorCurso(SQLiteDatabase conexao, int id_coordenador) {
        if(conexao != null){
            SolicitacaoRepository dao = new SolicitacaoRepository(conexao);
            return dao.listarPorCurso(id_coordenador);
        }
        return null;
    }

    public List<Solicitacao> listarPorAluno(SQLiteDatabase conexao, int id_aluno) {
        if(conexao != null){
            SolicitacaoRepository dao = new SolicitacaoRepository(conexao);
            return dao.listarPorAluno(id_aluno);
        }
        return null;
    }

    public List<Solicitacao> pesquisar(SQLiteDatabase conexao, int protocolo) {
        if(conexao != null){
            SolicitacaoRepository dao = new SolicitacaoRepository(conexao);
            return dao.pesquisar(protocolo);
        }
        return null;
    }

    public Solicitacao consultar(SQLiteDatabase conexao, int protocolo) {
        if(conexao != null){
            SolicitacaoRepository dao = new SolicitacaoRepository(conexao);
            return dao.consultar(protocolo);
        }
        return null;
    }

}
