package com.example.apphorasmais.model.util;

/**
 * @author Thiago Ferreira Assumpção
 */

public class ScriptDLL {

    public static String getTableCoordenador(){
        StringBuilder sqlQuery = new StringBuilder();

        sqlQuery.append("  CREATE TABLE IF NOT EXISTS coordenador (  ");
        sqlQuery.append("  id INTEGER NOT NULL,                      ");
        sqlQuery.append("  nome varchar(60) DEFAULT NULL,            ");
        sqlQuery.append("  senha varchar(50) DEFAULT NULL,           ");
        sqlQuery.append("  PRIMARY KEY (id AUTOINCREMENT) )          ");

        return sqlQuery.toString();
    }

    public static String getTableCurso(){
        StringBuilder sqlQuery = new StringBuilder();

        sqlQuery.append("  CREATE TABLE IF NOT EXISTS curso (                          ");
        sqlQuery.append("  id INTEGER NOT NULL,                                        ");
        sqlQuery.append("  curso varchar(100) DEFAULT NULL,                           ");
        sqlQuery.append("  coordenador_id INTEGER DEFAULT NULL,                        ");
        sqlQuery.append("  PRIMARY KEY (id AUTOINCREMENT),                             ");
        sqlQuery.append("  FOREIGN KEY (coordenador_id) REFERENCES coordenador (id) )  ");

        return sqlQuery.toString();
    }

    public static String getTableEscopo(){
        StringBuilder sqlQuery = new StringBuilder();

        sqlQuery.append("  CREATE TABLE IF NOT EXISTS escopo (  ");
        sqlQuery.append("  id INTEGER NOT NULL,                 ");
        sqlQuery.append("  atividade varchar(50) DEFAULT NULL,  ");
        sqlQuery.append("  PRIMARY KEY (id AUTOINCREMENT) )     ");

        return sqlQuery.toString();
    }

    public static String getTablePeriodoLetivo(){
        StringBuilder sqlQuery = new StringBuilder();

        sqlQuery.append("  CREATE TABLE IF NOT EXISTS periodoletivo (  ");
        sqlQuery.append("  id INTEGER NOT NULL,                        ");
        sqlQuery.append("  periodo varchar(5) DEFAULT NULL,            ");
        sqlQuery.append("  PRIMARY KEY (id AUTOINCREMENT) )            ");

        return sqlQuery.toString();
    }

    public static String getTableHorasComplementares(){
        StringBuilder sqlQuery = new StringBuilder();

        sqlQuery.append("  CREATE TABLE IF NOT EXISTS horascomplementares (  ");
        sqlQuery.append("  id INTEGER NOT NULL,                              ");
        sqlQuery.append("  total INTEGER DEFAULT 0,                          ");
        sqlQuery.append("  totalAtividade INTEGER DEFAULT 0,                 ");
        sqlQuery.append("  PRIMARY KEY (id AUTOINCREMENT) )                  ");

        return sqlQuery.toString();
    }

    public static String getTableTurma(){
        StringBuilder sqlQuery = new StringBuilder();

        sqlQuery.append("  CREATE TABLE IF NOT EXISTS turma (                      ");
        sqlQuery.append("  id INTEGER NOT NULL,                                    ");
        sqlQuery.append("  grupo VARCHAR(2) DEFAULT NULL,                          ");
        sqlQuery.append("  curso_id INTEGER DEFAULT NULL,                          ");
        sqlQuery.append("  periodo_id INTEGER DEFAULT NULL,                        ");
        sqlQuery.append("  PRIMARY KEY (id AUTOINCREMENT),                         ");
        sqlQuery.append("  FOREIGN KEY(periodo_id) REFERENCES periodoletivo (id),  ");
        sqlQuery.append("  FOREIGN KEY(curso_id) REFERENCES curso (id) )           ");

        return sqlQuery.toString();
    }

    public static String getTableAluno(){
        StringBuilder sqlQuery = new StringBuilder();

        sqlQuery.append("  CREATE TABLE IF NOT EXISTS aluno (                                          ");
        sqlQuery.append("  id INTEGER NOT NULL,                                                        ");
        sqlQuery.append("  nome varchar(60) DEFAULT NULL,                                              ");
        sqlQuery.append("  senha varchar(60) DEFAULT NULL,                                             ");
        sqlQuery.append("  usuario varchar(30) DEFAULT NULL,                                           ");
        sqlQuery.append("  horasComplementares_id INTEGER DEFAULT NULL,                                ");
        sqlQuery.append("  turma_id INTEGER DEFAULT NULL,                                              ");
        sqlQuery.append("  PRIMARY KEY (id AUTOINCREMENT),                                             ");
        sqlQuery.append("  FOREIGN KEY (turma_id) REFERENCES turma (id),                               ");
        sqlQuery.append("  FOREIGN KEY (horasComplementares_id) REFERENCES horascomplementares (id) )  ");

        return sqlQuery.toString();
    }

    public static String getTableSituacao(){
        StringBuilder sqlQuery = new StringBuilder();

        sqlQuery.append("  CREATE TABLE IF NOT EXISTS situacao (  ");
        sqlQuery.append("  id INTEGER NOT NULL,                   ");
        sqlQuery.append("  status varchar(20) DEFAULT NULL,       ");
        sqlQuery.append("  PRIMARY KEY (id AUTOINCREMENT) )       ");

        return sqlQuery.toString();
    }

    public static String getTableAtividade(){
        StringBuilder sqlQuery = new StringBuilder();

        sqlQuery.append("  CREATE TABLE IF NOT EXISTS atividade (                                      ");
        sqlQuery.append("  id INTEGER NOT NULL,                                                        ");
        sqlQuery.append("  escopo varchar(30) DEFAULT NULL,                                            ");
        sqlQuery.append("  quantidade INTEGER DEFAULT 0,                                               ");
        sqlQuery.append("  horasComplementares_id INTEGER DEFAULT NULL,                                ");
        sqlQuery.append("  PRIMARY KEY (id AUTOINCREMENT),                                             ");
        sqlQuery.append("  FOREIGN KEY (horasComplementares_id) REFERENCES horascomplementares (id) )  ");

        return sqlQuery.toString();
    }

    public static String getTableAtividadeComplementar(){
        StringBuilder sqlQuery = new StringBuilder();

        sqlQuery.append("  CREATE TABLE IF NOT EXISTS atividadecomplementar (      ");
        sqlQuery.append("  id INTEGER NOT NULL,                                    ");
        sqlQuery.append("  cargaHoraria INTEGER DEFAULT 0,                         ");
        sqlQuery.append("  horaComplementar INTEGER DEFAULT 0,                     ");
        sqlQuery.append("  instituicao varchar(50) DEFAULT NULL,                   ");
        sqlQuery.append("  titulo varchar(50) DEFAULT NULL,                        ");
        sqlQuery.append("  atividade_id INTEGER DEFAULT NULL,                      ");
        sqlQuery.append("  PRIMARY KEY (id AUTOINCREMENT),                         ");
        sqlQuery.append("  FOREIGN KEY (atividade_id) REFERENCES atividade (id) )  ");

        return sqlQuery.toString();
    }

    public static String getTableRequerimento(){
        StringBuilder sqlQuery = new StringBuilder();

        sqlQuery.append("  CREATE TABLE IF NOT EXISTS requerimento (         ");
        sqlQuery.append("  id INTEGER NOT NULL,                              ");
        sqlQuery.append("  cargaHoraria INTEGER DEFAULT 0,                   ");
        sqlQuery.append("  dataInicio varchar(20) DEFAULT NULL,              ");
        sqlQuery.append("  dataTermino varchar(20) DEFAULT NULL,             ");
        sqlQuery.append("  instituicao varchar(50) DEFAULT NULL,             ");
        sqlQuery.append("  titulo varchar(50) DEFAULT NULL,                  ");
        sqlQuery.append("  escopo_id INTEGER DEFAULT NULL,                   ");
        sqlQuery.append("  PRIMARY KEY (id AUTOINCREMENT),                   ");
        sqlQuery.append("  FOREIGN KEY (escopo_id) REFERENCES escopo (id) )  ");

        return sqlQuery.toString();
    }

    public static String getTableSolicitcao(){
        StringBuilder sqlQuery = new StringBuilder();

        sqlQuery.append("  CREATE TABLE IF NOT EXISTS solicitacao (                     ");
        sqlQuery.append("  id INTEGER NOT NULL,                                         ");
        sqlQuery.append("  dataSolicitacao DATE DEFAULT NULL,                           ");
        sqlQuery.append("  protocolo INTEGER DEFAULT NULL,                              ");
        sqlQuery.append("  aluno_id INTEGER DEFAULT NULL,                               ");
        sqlQuery.append("  requerimento_id INTEGER DEFAULT NULL,                        ");
        sqlQuery.append("  situacao_id INTEGER DEFAULT NULL,                            ");
        sqlQuery.append("  PRIMARY KEY (id AUTOINCREMENT),                              ");
        sqlQuery.append("  FOREIGN KEY (requerimento_id) REFERENCES requerimento (id),  ");
        sqlQuery.append("  FOREIGN KEY (aluno_id) REFERENCES aluno (id),                ");
        sqlQuery.append("  FOREIGN KEY (situacao_id) REFERENCES situacao (id) )         ");

        return sqlQuery.toString();
    }

    public static String getDropTableCoordenador(){
        StringBuilder sqlQuery = new StringBuilder();

        sqlQuery.append(" DROP TABLE IF EXISTS coordenador ");

        return sqlQuery.toString();
    }

    public static String getDropTableCurso(){
        StringBuilder sqlQuery = new StringBuilder();

        sqlQuery.append(" DROP TABLE IF EXISTS curso ");

        return sqlQuery.toString();
    }

    public static String getDropTableEscopo(){
        StringBuilder sqlQuery = new StringBuilder();

        sqlQuery.append(" DROP TABLE IF EXISTS escopo ");

        return sqlQuery.toString();
    }

    public static String getDropTablePeriodoLetivo(){
        StringBuilder sqlQuery = new StringBuilder();

        sqlQuery.append(" DROP TABLE IF EXISTS periodoletivo ");

        return sqlQuery.toString();
    }

    public static String getDropTableHorasComplementares(){
        StringBuilder sqlQuery = new StringBuilder();

        sqlQuery.append(" DROP TABLE IF EXISTS horascomplementares ");

        return sqlQuery.toString();
    }

    public static String getDropTableTurma(){
        StringBuilder sqlQuery = new StringBuilder();

        sqlQuery.append(" DROP TABLE IF EXISTS turma ");

        return sqlQuery.toString();
    }

    public static String getDropTableAluno(){
        StringBuilder sqlQuery = new StringBuilder();

        sqlQuery.append(" DROP TABLE IF EXISTS aluno ");

        return sqlQuery.toString();
    }

    public static String getDropTableSituacao(){
        StringBuilder sqlQuery = new StringBuilder();

        sqlQuery.append(" DROP TABLE IF EXISTS situacao ");

        return sqlQuery.toString();
    }

    public static String getDropTableAtividade(){
        StringBuilder sqlQuery = new StringBuilder();

        sqlQuery.append(" DROP TABLE IF EXISTS atividade ");

        return sqlQuery.toString();
    }

    public static String getDropTableAtividadeComplementar(){
        StringBuilder sqlQuery = new StringBuilder();

        sqlQuery.append(" DROP TABLE IF EXISTS atividadecomplementar ");

        return sqlQuery.toString();
    }

    public static String getDropTableRequerimento(){
        StringBuilder sqlQuery = new StringBuilder();

        sqlQuery.append(" DROP TABLE IF EXISTS requerimento ");

        return sqlQuery.toString();
    }

    public static String getDropTableSolicitcao(){
        StringBuilder sqlQuery = new StringBuilder();

        sqlQuery.append(" DROP TABLE IF EXISTS solicitacao ");

        return sqlQuery.toString();
    }

}
