package com.example.apphorasmais.model.bo;

import android.database.sqlite.SQLiteDatabase;

import com.example.apphorasmais.model.dao.AlunoDao;
import com.example.apphorasmais.model.entity.Aluno;
import com.example.apphorasmais.model.entity.Coordenador;

import java.util.List;
import com.example.apphorasmais.model.dao.CoordenadorDao;

/**
 * @author Thiago Ferreira Assumpção
 */

public class CoordenadorBo {

    public String salvar(SQLiteDatabase conexao, Coordenador coordenador) {
        if(conexao != null){
            CoordenadorDao dao = new CoordenadorDao(conexao);
            if(aplicarRegrasNegocio(coordenador)){
                dao.salvar(coordenador);
                return "Coordenador cadastrado! Senha: "+coordenador.getSenha();
            }else{
                return "Usuário inválido. Iniciar com 2220...";
            }
        }
        return null;
    }

    private boolean aplicarRegrasNegocio(Coordenador coordenador) {
        coordenador.setId(0);
        coordenador.setSenha("20220320");
        String user = coordenador.getUsuario().substring(0, 4);
        if(!user.equals("2220")){
            return false;
        }
        return true;
    }

    public String editar(SQLiteDatabase conexao, Coordenador coordenador, String nome, String usuario) {
        if(conexao != null){
            String retorno = validaDados(coordenador, nome, usuario);
            if(retorno.contains("alterado")){
                CoordenadorDao dao = new CoordenadorDao(conexao);
                dao.editar(coordenador);
            }
            return retorno;
        }
        return "Sem conexão";
    }

    public String resetarSenha(SQLiteDatabase conexao, int id){
        if(conexao != null){
            CoordenadorDao dao = new CoordenadorDao(conexao);
            dao.resetarSenha(id);
            return "Senha redefinida para 20220320";
        }
        return "Sem conexão";
    }

    public String definirSenha(SQLiteDatabase conexao, int id, String senha, String confirmarSenha){
        if(conexao != null){
            if(validarSenha(senha, confirmarSenha)){
                CoordenadorDao dao = new CoordenadorDao(conexao);
                dao.definirSenha(id, senha);
                return "Senha definida";
            }else{
                return "Senhas divergentes";
            }
        }
        return "Sem conexão";
    }

    private Boolean validarSenha(String senha, String confirmarSenha) {
        if(senha.equals(confirmarSenha)){
            return true;
        }else{
            return false;
        }
    }

    private String validaDados(Coordenador coordenador, String nome, String usuario) {
        if((!coordenador.getNome().equals(nome) && !nome.isEmpty()) || (!coordenador.getUsuario().equals(usuario) && !usuario.isEmpty())){
            coordenador.setNome(nome.trim());
            coordenador.setUsuario(usuario.trim());
            return "Usuário alterado";
        }
        return "Altere/Preencha os campos";
    }

    public String excluir(SQLiteDatabase conexao, int id) {
        if(conexao != null){
            CoordenadorDao dao = new CoordenadorDao(conexao);
            dao.excluir(id);
        }
        return null;
    }

    public List<Coordenador> listar(SQLiteDatabase conexao) {
        if(conexao != null){
            CoordenadorDao dao = new CoordenadorDao(conexao);
            return dao.listar();
        }
        return null;
    }

    public List<Coordenador> pesquisar(SQLiteDatabase conexao, String nome) {
        if(conexao != null){
            CoordenadorDao dao = new CoordenadorDao(conexao);
            return dao.pesquisar(nome);
        }
        return null;
    }

    public Coordenador consultar(SQLiteDatabase conexao, String nome) {
        if(conexao != null){
            CoordenadorDao dao = new CoordenadorDao(conexao);
            return dao.consultar(nome);
        }
        return null;
    }

    public Coordenador consultar(SQLiteDatabase conexao, String usuario, String senha) {
        if(conexao != null){
            CoordenadorDao dao = new CoordenadorDao(conexao);
            Coordenador coordenador = dao.consultarParaLogin(usuario);
            if(coordenador.getId() != 0){
                if(coordenador.getSenha().equals(senha)){
                    return coordenador;
                }
            }
        }
        return null;
    }

}
