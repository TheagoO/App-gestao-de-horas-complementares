package com.example.apphorasmais.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.apphorasmais.model.entity.Solicitacao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class SolicitacaoRepository {

    private SQLiteDatabase conexao;
    private ContentValues contentValues;

    public SolicitacaoRepository(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    public void salvar(Solicitacao solicitacao) {
        contentValues = new ContentValues();

        contentValues.put("protocolo", solicitacao.getProtocolo());
        contentValues.put("dataSolicitacao", String.valueOf(solicitacao.getDataSolicitacao()));
        contentValues.put("aluno_id", solicitacao.getAluno().getId());
        contentValues.put("requerimento_id", solicitacao.getRequerimento().getId());
        contentValues.put("situacao_id", solicitacao.getSituacao().getId());

        conexao.insertOrThrow("solicitacao", null, contentValues);
    }

    public void editar(Solicitacao solicitacao) {
        contentValues = new ContentValues();
        String[] args = new String[1];

        contentValues.put("situacao_id", solicitacao.getSituacao().getId());

        args[0] = String.valueOf(solicitacao.getId());

        conexao.update("solicitacao", contentValues, "id = ?", args);
    }

    public void excluir(int id) {
        String[] args = new String[1];
        args[0] = String.valueOf(id);

        conexao.delete("solicitacao", "id = ?", args);
    }

    public List<Solicitacao> listar() {
        List<Solicitacao> lista = new ArrayList<Solicitacao>();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT id, protocolo, dataSolicitacao, aluno_id, requerimento_id, situacao_id FROM solicitacao");

        Cursor cursor = conexao.rawQuery(sql.toString(), null);
        validaResultadoConsulta(lista, cursor);

        return lista;
    }

    public List<Solicitacao> listarPorCurso(int id_coordenador) {
        List<Solicitacao> lista = new ArrayList<Solicitacao>();
        StringBuilder sql = new StringBuilder();
        String[] args = new String[1];

        sql.append("SELECT * FROM solicitacao s INNER JOIN situacao st ON st.id = s.situacao_id INNER JOIN requerimento r ON r.id = s.requerimento_id INNER JOIN escopo e ON e.id = r.escopo_id INNER JOIN aluno a ON a.id = s.aluno_id INNER JOIN turma t ON t.id = a.turma_id INNER JOIN periodoletivo p ON p.id = t.periodo_id INNER JOIN curso c ON c.id = t.curso_id INNER JOIN horascomplementares hc ON hc.id = a.horasComplementares_id WHERE c.coordenador_id = ? ORDER BY s.dataSolicitacao DESC");
        args[0] = String.valueOf(id_coordenador);

        Cursor cursor = conexao.rawQuery(sql.toString(), args);
            iteraDadosConsultaPorCurso(lista, cursor);

        return lista;
    }

    private void iteraDadosConsultaPorCurso(List<Solicitacao> lista, Cursor cursor) {
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                Solicitacao solicitacao = new Solicitacao();

                solicitacao.setDataSolicitacao(LocalDate.parse(cursor.getString(cursor.getColumnIndexOrThrow("dataSolicitacao"))));
                solicitacao.setProtocolo(cursor.getInt(cursor.getColumnIndexOrThrow("protocolo")));


                solicitacao.getAluno().setId(cursor.getInt(cursor.getColumnIndexOrThrow("aluno_id")));
                solicitacao.getAluno().setNome(cursor.getString(cursor.getColumnIndexOrThrow("nome")));

                solicitacao.getAluno().getTurma().setId(cursor.getInt(cursor.getColumnIndexOrThrow("turma_id")));
                solicitacao.getAluno().getTurma().setGrupo(cursor.getString(cursor.getColumnIndexOrThrow("grupo")));
                solicitacao.getAluno().getTurma().getPeriodo().setId(cursor.getInt(cursor.getColumnIndexOrThrow("periodo_id")));
                solicitacao.getAluno().getTurma().getPeriodo().setPeriodo(cursor.getString(cursor.getColumnIndexOrThrow("periodo")));

                solicitacao.getAluno().getTurma().getCurso().setId(cursor.getInt(cursor.getColumnIndexOrThrow("curso_id")));
                solicitacao.getAluno().getTurma().getCurso().setTitulo(cursor.getString(cursor.getColumnIndexOrThrow("curso")));

                solicitacao.getRequerimento().setId(cursor.getInt(cursor.getColumnIndexOrThrow("requerimento_id")));
                solicitacao.getRequerimento().setTitulo(cursor.getString(cursor.getColumnIndexOrThrow("titulo")));
                solicitacao.getRequerimento().setInstituicao(cursor.getString(cursor.getColumnIndexOrThrow("instituicao")));
                solicitacao.getRequerimento().setDataInicio(cursor.getString(cursor.getColumnIndexOrThrow("dataInicio")));
                solicitacao.getRequerimento().setDataTermino(cursor.getString(cursor.getColumnIndexOrThrow("dataTermino")));
                solicitacao.getRequerimento().setCargaHoraria(cursor.getInt(cursor.getColumnIndexOrThrow("cargaHoraria")));

                solicitacao.getRequerimento().getEscopo().setId(cursor.getInt(cursor.getColumnIndexOrThrow("escopo_id")));
                solicitacao.getRequerimento().getEscopo().setAtividade(cursor.getString(cursor.getColumnIndexOrThrow("atividade")));

                solicitacao.getSituacao().setId(cursor.getInt(cursor.getColumnIndexOrThrow("situacao_id")));
                solicitacao.getSituacao().setStatus(cursor.getString(cursor.getColumnIndexOrThrow("status")));

                lista.add(solicitacao);
            } while (cursor.moveToNext());
        }
    }

    public List<Solicitacao> listarPorAluno(int id_aluno) {
        List<Solicitacao> lista = new ArrayList<Solicitacao>();
        StringBuilder sql = new StringBuilder();
        String[] args = new String[1];

        sql.append("select s.id, s.protocolo, s.dataSolicitacao, e.atividade, st.status from solicitacao s INNER JOIN requerimento r ON s.requerimento_id = r.id INNER JOIN escopo e ON e.id = r.escopo_id INNER JOIN situacao st ON st.id = s.situacao_id WHERE s.aluno_id = ? ORDER BY s.dataSolicitacao DESC");
        args[0] = String.valueOf(id_aluno);

        Cursor cursor = conexao.rawQuery(sql.toString(), args);
        iteraDadosConsultaPorAluno(lista, cursor);

        return lista;
    }

    private void iteraDadosConsultaPorAluno(List<Solicitacao> lista, Cursor cursor) {
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                Solicitacao solicitacao = new Solicitacao();

                solicitacao.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                solicitacao.setDataSolicitacao(LocalDate.parse(cursor.getString(cursor.getColumnIndexOrThrow("dataSolicitacao"))));
                solicitacao.setProtocolo(cursor.getInt(cursor.getColumnIndexOrThrow("protocolo")));

                solicitacao.getRequerimento().getEscopo().setAtividade(cursor.getString(cursor.getColumnIndexOrThrow("atividade")));

                solicitacao.getSituacao().setStatus(cursor.getString(cursor.getColumnIndexOrThrow("status")));

                lista.add(solicitacao);
            } while (cursor.moveToNext());
        }
    }

    public List<Solicitacao> pesquisar(int protocolo) {
        List<Solicitacao> lista = new ArrayList<Solicitacao>();
        StringBuilder sql = new StringBuilder();
        String[] args = new String[1];

        sql.append("SELECT id, protocolo, dataSolicitacao, aluno_id, requerimento_id, situacao_id FROM solicitacao WHERE protocolo LIKE '?%'");
        args[0] = String.valueOf(protocolo);

        Cursor cursor = conexao.rawQuery(sql.toString(), args);
        validaResultadoConsulta(lista, cursor);

        return lista;
    }

    public Solicitacao consultar(int protocolo) {
        Solicitacao solicitacao = new Solicitacao();
        StringBuilder sql = new StringBuilder();
        String[] args = new String[1];

        sql.append("SELECT * FROM solicitacao s WHERE s.protocolo = ?");
        args[0] = String.valueOf(protocolo);

        Cursor cursor = conexao.rawQuery(sql.toString(), args);
        iteraDadosConsulta(solicitacao, cursor);

        return solicitacao;
    }

    private void validaResultadoConsulta(List<Solicitacao> lista, Cursor cursor) {
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            iteraDadosConsulta(lista, cursor);
        }
    }

    private void iteraDadosConsulta(List<Solicitacao> lista, Cursor cursor) {
        do {
            Solicitacao solicitacao = new Solicitacao();

            solicitacao.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            solicitacao.setDataSolicitacao(LocalDate.parse(cursor.getString(cursor.getColumnIndexOrThrow("dataSolicitacao"))));
            solicitacao.setProtocolo(cursor.getInt(cursor.getColumnIndexOrThrow("protocolo")));
            solicitacao.getAluno().setId(cursor.getInt(cursor.getColumnIndexOrThrow("aluno_id")));
            solicitacao.getRequerimento().setId(cursor.getInt(cursor.getColumnIndexOrThrow("requerimento_id")));
            solicitacao.getSituacao().setId(cursor.getInt(cursor.getColumnIndexOrThrow("situacao_id")));

            lista.add(solicitacao);
        } while (cursor.moveToNext());
    }

    private void iteraDadosConsulta(Solicitacao solicitacao, Cursor cursor) {
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            solicitacao.setId(cursor.getInt(cursor.getColumnIndexOrThrow("s.id")));
            solicitacao.setDataSolicitacao(LocalDate.parse(cursor.getString(cursor.getColumnIndexOrThrow("s.dataSolicitacao"))));
            solicitacao.setProtocolo(cursor.getInt(cursor.getColumnIndexOrThrow("s.protocolo")));

        }
    }

}
