package com.example.apphorasmais.model.bo;

import android.database.sqlite.SQLiteDatabase;

import com.example.apphorasmais.repository.Aluno;

import java.util.List;
import com.example.apphorasmais.model.dao.AlunoDao;

/**
 * @author Thiago Ferreira Assumpção
 */

public class AlunoBo {

    public String salvar(SQLiteDatabase conexao, Aluno aluno) {
        if(conexao != null){
            AlunoDao dao = new AlunoDao(conexao);
            if(aplicarRegrasNegocio(aluno)){
                dao.salvar(aluno);
                return "Aluno cadastrado! Senha: "+aluno.getSenha();
            }else{
                return "Usuário inválido. Iniciar com 2022...";
            }
        }
        return null;
    }

    private boolean aplicarRegrasNegocio(Aluno aluno) {
        aluno.setId(0);
        aluno.setSenha("20220320");
        String user = aluno.getUsuario().substring(0, 4);
        if(!user.equals("2022")){
            return false;
        }
        return true;
    }

    public String editar(SQLiteDatabase conexao, Aluno aluno, String nome, String usuario) {
        if(conexao != null){
            String retorno = validaDados(aluno, nome, usuario);
            if (retorno.contains("Usuário alterado")){
                AlunoDao dao = new AlunoDao(conexao);
                dao.editar(aluno);
            }
            return retorno;
        }
        return "Sem conexão";
    }

    private String validaDados(Aluno aluno, String nome, String usuario) {
        if((!aluno.getNome().equals(nome) && !nome.isEmpty()) || (!aluno.getUsuario().equals(usuario) && !usuario.isEmpty())){
            aluno.setNome(nome.trim());
            aluno.setUsuario(usuario.trim());
            return "Usuário alterado";
        }
        return "Altere/Preencha os campos";
    }

    public String resetarSenha(SQLiteDatabase conexao, int id){
        if(conexao != null){
            AlunoDao dao = new AlunoDao(conexao);
            dao.resetarSenha(id);
            return "Senha redefinida para 20220320";
        }
        return "Sem conexão";
    }

    public String definirSenha(SQLiteDatabase conexao, int id, String senha, String confirmarSenha){
        if(conexao != null){
            if(validarSenha(senha, confirmarSenha)){
                AlunoDao dao = new AlunoDao(conexao);
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

    public String excluir(SQLiteDatabase conexao, int id) {
        if(conexao != null){
            AlunoDao dao = new AlunoDao(conexao);
            dao.excluir(id);
        }
        return null;
    }

    public List<Aluno> listar(SQLiteDatabase conexao) {
        if(conexao != null){
            AlunoDao dao = new AlunoDao(conexao);
            return dao.listar();
        }
        return null;
    }

    public List<Aluno> pesquisar(SQLiteDatabase conexao, String nome) {
        if(conexao != null){
            AlunoDao dao = new AlunoDao(conexao);
            return dao.pesquisar(nome);
        }
        return null;
    }

    public Aluno consultar(SQLiteDatabase conexao, String usuario, String senha) {
        if(conexao != null){
            AlunoDao dao = new AlunoDao(conexao);
            Aluno aluno = dao.consultar(usuario);
            if(aluno != null && aluno.getId() != 0 && senha != null){
                if(aluno.getSenha().equals(senha)){
                    return aluno;
                }
            }else{
                return aluno;
            }
        }
        return null;
    }

    public Aluno consultar(SQLiteDatabase conexao, int id) {
        if(conexao != null){
            AlunoDao dao = new AlunoDao(conexao);
            return dao.consultar(id);
        }
        return null;
    }

}
