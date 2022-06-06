package com.example.apphorasmais.model.facade;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.apphorasmais.model.bo.*;
import com.example.apphorasmais.model.util.Conexao;
import com.example.apphorasmais.repository.Aluno;
import com.example.apphorasmais.repository.Atividade;
import com.example.apphorasmais.repository.AtividadeComplementar;
import com.example.apphorasmais.repository.Coordenador;
import com.example.apphorasmais.repository.Curso;
import com.example.apphorasmais.repository.Escopo;
import com.example.apphorasmais.repository.HoraComplementar;
import com.example.apphorasmais.repository.PeriodoLetivo;
import com.example.apphorasmais.repository.Requerimento;
import com.example.apphorasmais.repository.Situacao;
import com.example.apphorasmais.repository.Solicitacao;
import com.example.apphorasmais.repository.Turma;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class Facade {

    private SQLiteDatabase conexao;
    private AlunoBo alunoBo;
    private AtividadeBo atividadeBo;
    private AtividadeComplementarBo atividadeComplementarBo;
    private CoordenadorBo coordenadorBo;
    private CursoBo cursoBo;
    private EscopoBo escopoBo;
    private HoraComplementarBo horaComplementarBo;
    private PeriodoLetivoBo periodoLetivoBo;
    private RequerimentoBo requerimentoBo;
    private SituacaoBo situacaoBo;
    private SolicitacaoBo solicitacaoBo;
    private TurmaBo turmaBo;

    public Facade() {
        alunoBo = new AlunoBo();
        atividadeBo = new AtividadeBo();
        atividadeComplementarBo = new AtividadeComplementarBo();
        coordenadorBo = new CoordenadorBo();
        cursoBo = new CursoBo();
        escopoBo = new EscopoBo();
        horaComplementarBo = new HoraComplementarBo();
        periodoLetivoBo = new PeriodoLetivoBo();
        requerimentoBo = new RequerimentoBo();
        situacaoBo = new SituacaoBo();
        solicitacaoBo = new SolicitacaoBo();
        turmaBo = new TurmaBo();
    }


    /**
     * Método para cadastrar um curso no sistema. É feito a busca do Coordenador no
     * banco de dados e varificado se é válido, em seguida será encapsulado no objeto
     * curso e salvo.
     *
     * @param context - Contexto da classe que esta chamando o método
     * @param curso   - Objeto curso
     * @param nome    - Nome do coordenador
     * @return - String com feedback da ação do método
     */
    public String cadastrarCurso(Context context, Curso curso, String nome) {
        try {
            conexao = Conexao.estartaConexao(context);
            Coordenador coordenador = coordenadorBo.consultar(conexao, nome);
            if (coordenador.getId() != 0) {
                curso.setCoordenador(coordenador);
                cursoBo.salvar(conexao, curso);
            } else {
                return "Error";
            }
        } catch (SQLException e) {
            return "Erro: " + e.getMessage();
        }
        return "Curso cadastrado";
    }

    /**
     * Método para listar os cursos.
     *
     * @param context - Contexto da classe que está chamando o método
     * @return - Lista de objetos do tipo Curso
     */
    public List<Curso> listarCursos(Context context) {
        try{
            conexao = Conexao.estartaConexao(context);
            return cursoBo.listar(conexao);
        }catch (SQLException e){
            e.getMessage();
            return null;
        }
    }

    /**
     * Método para pesquisar os cursos.
     *
     * @param context - Contexto da classe que está chamando o método
     * @param curso   - Nome do curso que está sendo pesquisado
     * @return - Lista de cursos
     */
    public List<Curso> pesquisarCurso(Context context, String curso) {
        try{
            conexao = Conexao.estartaConexao(context);
            return cursoBo.pesquisar(conexao, curso);
        }catch (SQLException e){
            e.getMessage();
            return null;
        }
    }

    /**
     * Método para excluir um curso.
     *
     * @param context - Contexto da classe que está chamando o método
     * @param id      - ID do curso a ser deletado
     * @return - String com feedback da ação do método
     */
    public String excluirCurso(Context context, int id) {
        try {
            conexao = Conexao.estartaConexao(context);
            cursoBo.excluir(conexao, id);
        } catch (SQLException e) {
            return "Erro: " + e.getMessage();
        }
        return "Curso deletado";
    }

    /**
     * Método para editar o curso. É validado se houve alteração do coordenador
     * do curso e, se houver alterações será recuperado o novo coordenador e
     * encapsulado no curso e em seguida chamado o metodo editar da cursoBo.
     *
     * @param context     - Contexto da classe que está chamando o método
     * @param curso       - Objeto curso com seus dados antigos
     * @param titulo      - Novo nome para o curso
     * @param coordenador - Nome do coordenador selecionado no spinner
     * @return - String com o feedback da ação do método
     */
    public String editarCurso(Context context, Curso curso, String titulo, String coordenador) {
        boolean editado = false;
        try {
            conexao = Conexao.estartaConexao(context);

            if (!curso.getCoordenador().getNome().equals(coordenador)) {
                curso.setCoordenador(coordenadorBo.consultar(conexao, coordenador));
                editado = true;
            }

            return cursoBo.editar(conexao, curso, editado, titulo);
        } catch (SQLException e) {
            return "Erro: " + e.getMessage();
        }

    }

    /**
     * Método para editar a turma. É validado se houve alguma alteração
     * no curso e período e, se houver alterações serão consultados no banco
     * e encapsulados no objeto turma e chamado o metodo para editar.
     *
     * @param context - Contexto da classe que está chamando o método
     * @param turma   - Objeto turma com seus dados antigos
     * @param grupo   - Novo grupo para turma
     * @param curso   - Nome do curso selecionado no spinner
     * @param periodo - Período da turma selecionado no spinner
     * @return - String com o feedback da ação do metodo
     */
    public String editarTurma(Context context, Turma turma, String grupo, String curso, String periodo) {
        Boolean editado = false;
        try {
            conexao = Conexao.estartaConexao(context);
            if (!turma.getCurso().getTitulo().equals(curso)) {
                Curso novoCurso = cursoBo.consultar(conexao, curso);
                turma.setCurso(novoCurso);
                editado = true;
            }
            if (!turma.getPeriodo().getPeriodo().equals(periodo)) {
                PeriodoLetivo periodoLetivo = periodoLetivoBo.consultar(conexao, periodo);
                turma.setPeriodo(periodoLetivo);
                editado = true;
            }
            return turmaBo.editar(conexao, turma, grupo, editado);
        } catch (SQLException e) {
            return "Erro: " + e.getMessage();
        }
    }

    /**
     * Método para cadastrar uma turma. Possuí dois métodos que vão ancapsular
     * os objetos Curso e PeriodoLetivo para que seja possível salvar a turma.
     *
     * @param context - Contexto da classe que está chamando o método
     * @param turma   - Objeto turma com seus dados
     * @param titulo  - Nome do curso selecionado no spinner
     * @param periodo - Período selecionado no spinner
     * @return - String com o feedback da ação do método
     */
    public String cadastrarTurma(Context context, Turma turma, String titulo, String periodo) {
        try {
            conexao = Conexao.estartaConexao(context);
            associaCursoAturma(turma, titulo);
            associaPeriodoAturma(turma, periodo);
            return turmaBo.salvar(conexao, turma);
        } catch (SQLException e) {
            return "Erro: " + e.getMessage();
        }
    }

    /**
     * Método para recuperar o PeriodoLetivo do banco de dados para
     * encapsular no objeto turma.
     *
     * @param turma   - Objeto da classe Turma
     * @param periodo - Período selecionado no spinner
     */
    private void associaPeriodoAturma(Turma turma, String periodo) {
        try{
            PeriodoLetivo periodoLetivo = periodoLetivoBo.consultar(conexao, periodo);
            turma.setPeriodo(periodoLetivo);
        }catch (SQLException e){
            e.getMessage();
        }
    }

    /**
     * Método para recuperar o objeto Curso do banco de dados para
     * encapsular no objeto turma.
     *
     * @param turma  - Objeto da classe turma
     * @param titulo - Nome do curso selecionado no spinner
     */
    private void associaCursoAturma(Turma turma, String titulo) {
        try{
            Curso curso = cursoBo.consultar(conexao, titulo);
            turma.setCurso(curso);
        }catch (SQLException e){
            e.getMessage();
        }
    }

    /**
     * Método para listar as turmas.
     *
     * @param context - Contexto da classe que está chamando o método
     * @return - Lista de Turmas
     */
    public List<Turma> listarTurmas(Context context) {
        try{
            conexao = Conexao.estartaConexao(context);
            return turmaBo.listar(conexao);
        }catch (SQLException e){
            e.getMessage();
            return null;
        }
    }

    /**
     * Método para listar as turmas que pertencem a um curso específico.
     *
     * @param context - Contexto da classe que está chamando o método
     * @param curso   - Nome do curso
     * @return - Lista de Turmas que pertencem a um curso
     */
    public List<Turma> listarTurmas(Context context, String curso) {
        try{
            conexao = Conexao.estartaConexao(context);
            return turmaBo.listar(conexao, curso);
        }catch (SQLException e){
            e.getMessage();
            return null;
        }
    }

    /**
     * Método para excluir uma turma.
     *
     * @param context - Contexto da classe que está chamando o método
     * @param id      - ID da turma a ser excluida
     * @return - String com o feedback da ação do método
     */
    public String excluirTurma(Context context, int id) {
        try {
            conexao = Conexao.estartaConexao(context);
            turmaBo.excluir(conexao, id);
        } catch (SQLException e) {
            return "Erro: " + e.getMessage();
        }
        return "Turma deletada";
    }

    /**
     * Método para pesquisar as turmas.
     *
     * @param context - Contexto da classe que está chamando o método
     * @param turma   - Grupo da turma a ser pesquisada (turma A, B, C)
     * @return - Lista de Turmas
     */
    public List<Turma> pesquisarTurmas(Context context, String turma) {
        try{
            conexao = Conexao.estartaConexao(context);
            return turmaBo.pesquisar(conexao, turma);
        }catch (SQLException e){
            e.getMessage();
            return null;
        }
    }

    /**
     * Método para listar os períodos.
     *
     * @param context - Contexto da classe que está chamando o método
     * @return - Lista de PeriodoLetivo
     */
    public List<PeriodoLetivo> listarPeriodos(Context context) {
        try{
            conexao = Conexao.estartaConexao(context);
            return periodoLetivoBo.listar(conexao);
        }catch (SQLException e){
            e.getMessage();
            return null;
        }
    }

    /**
     * Método para listar os períodos associados a uma turma.
     *
     * @param context - Contexto da classe que está chamando o método
     * @param turma   - Grupo da turma a ser pesquisada (turma A, B, C)
     * @return - Lista de PeriodoLetivo
     */
    public List<PeriodoLetivo> listarPeriodos(Context context, String turma) {
        try{
            conexao = Conexao.estartaConexao(context);
            return periodoLetivoBo.listar(conexao, turma);
        }catch (SQLException e){
            e.getMessage();
            return null;
        }
    }

    /**
     * Método para cadastrar um período.
     *
     * @param context - Contexto da classe que está chamando o método
     * @param periodo - Objeto da classe PeríodoLetivo com seus dados
     * @return - String com o feedback da ação do método
     */
    public String cadastrarPeriodo(Context context, PeriodoLetivo periodo) {
        try {
            conexao = Conexao.estartaConexao(context);
            periodoLetivoBo.salvar(conexao, periodo);
        } catch (SQLException e) {
            return "Erro: " + e.getMessage();
        }
        return "Período letivo cadastrado";
    }

    /**
     * Método para pesquisar um período.
     *
     * @param context - Contexto da classe que está chamando o método
     * @param periodo - Período a ser pesquisado
     * @return - Lista de PeriodoLetivo
     */
    public List<PeriodoLetivo> pesquisarPeriodo(Context context, String periodo) {
        try{
            conexao = Conexao.estartaConexao(context);
            return periodoLetivoBo.pesquisar(conexao, periodo);
        }catch (SQLException e){
            e.getMessage();
            return null;
        }
    }

    /**
     * Método para excluír um periodo.
     *
     * @param context - Contexto da classe que está chamando o método
     * @param id      - ID do período a ser excluído
     * @return - String com o feedback da ação do método
     */
    public String excluirPeriodo(Context context, int id) {
        try {
            conexao = Conexao.estartaConexao(context);
            periodoLetivoBo.excluir(conexao, id);
        } catch (SQLException e) {
            return "Erro: " + e.getMessage();
        }
        return "Período letivo deletado";
    }

    /**
     * Método para editar um Período.
     *
     * @param context       - Contexto da classe que está chamando o método
     * @param periodoLetivo - Objeto da classe PeriodoLetivo com seus dados antigos
     * @param periodo       - Nome do periodo selecionado no spinner
     * @return - String com o feedback da ação do método
     */
    public String editarPeriodo(Context context, PeriodoLetivo periodoLetivo, String periodo) {
        try {
            conexao = Conexao.estartaConexao(context);
            return periodoLetivoBo.editar(conexao, periodoLetivo, periodo);
        } catch (SQLException e) {
            return "Erro: " + e.getMessage();
        }
    }

    /**
     * Método para listar os Coordenadores.
     *
     * @param context - Contexto da classe que está chamando o método
     * @return - Lista Coordenadores
     */
    public List<Coordenador> listarCoordenadores(Context context) {
        try{
            conexao = Conexao.estartaConexao(context);
            return coordenadorBo.listar(conexao);
        }catch (SQLException e){
            e.getMessage();
            return null;
        }
    }

    /**
     * Método para cadastrar um Coordenador.
     *
     * @param context     - Contexto da classe que está chamando o método
     * @param coordenador - Objeto da classe Coordenador com seus dados
     * @return - String com o feedback da ação do método
     */
    public String cadastrarCoordenador(Context context, Coordenador coordenador) {
        try {
            conexao = Conexao.estartaConexao(context);
            return coordenadorBo.salvar(conexao, coordenador);
        } catch (SQLException e) {
            return "Erro: " + e.getMessage();
        }
    }

    /**
     * Método para pesquisar um Coordenador.
     *
     * @param context - Contexto da classe que está chamando o método
     * @param nome    - Nome do coordenador a ser pesquisado
     * @return - Lista Coordenadores
     */
    public List<Coordenador> pesquisarCoordenador(Context context, String nome) {
        try{
            conexao = Conexao.estartaConexao(context);
            return coordenadorBo.pesquisar(conexao, nome);
        }catch (SQLException e){
            e.getMessage();
            return null;
        }
    }

    /**
     * Método para excluir um Coordenador.
     *
     * @param context - Contexto da classe que está chamando o método
     * @param id      - ID do Coordenador a ser excluído
     * @return - String com o feedback da ação do método
     */
    public String excluirCoordenador(Context context, int id) {
        try {
            conexao = Conexao.estartaConexao(context);
            coordenadorBo.excluir(conexao, id);
        } catch (SQLException e) {
            return "Erro: " + e.getMessage();
        }
        return "Coordenador deletado";
    }

    /**
     * Método para buscar um Coordenador específico. É feita a consulta e válidado
     * se foi encontrado um Coordenador.
     *
     * @param context - Contexto da classe que está chamando o método
     * @param usuario - Usuário de login do Coordenador
     * @param senha   - Senha de login do Coordenador
     * @return - Coordenador ou nulo
     */
    public Coordenador buscarCoordenador(Context context, String usuario, String senha) {
        try {
            conexao = Conexao.estartaConexao(context);
            Coordenador coordenador = coordenadorBo.consultar(conexao, usuario, senha);
            return coordenador;
        } catch (SQLException e) {
            e.getMessage();
            return null;
        }
    }

    /**
     * Método para editar um Coordenador.
     *
     * @param context     - Contexto da classe que está chamando o método
     * @param coordenador - Objeto da classe Coordenador com seus dados antigos
     * @param nome        - Novo nome para o Coordenador
     * @param usuario     - Novo usuário para o Coordenador
     * @return - String com o feedback da ação do método
     */
    public String editarCoordenador(Context context, Coordenador coordenador, String nome, String usuario) {
        try {
            conexao = Conexao.estartaConexao(context);
            return coordenadorBo.editar(conexao, coordenador, nome, usuario);
        } catch (SQLException e) {
            return "Erro: " + e.getMessage();
        }
    }

    /**
     * Método para resetar a senha dos usuários. É válidado o tipo de usuário que será resetado a senha
     * e chamado a Bo referente ao seu tipo de usuário.
     *
     * @param context     - Contexto da classe que está chamando o método
     * @param tipoUsuario - Tipo de usuário (Coordenador/Aluno)
     * @param id          - ID do usuário
     * @return - String com o feedback da ação do método
     */
    public String resetarSenha(Context context, String tipoUsuario, int id) {
        try {
            conexao = Conexao.estartaConexao(context);
            if (tipoUsuario.contains("Aluno")) {
                return alunoBo.resetarSenha(conexao, id);
            } else {
                return coordenadorBo.resetarSenha(conexao, id);
            }
        } catch (SQLException e) {
            return "Erro: " + e.getMessage();
        }
    }

    /**
     * Método para redefinir a senha do usuário. É validado o tipo de usuário e chamado
     * a Bo referente ao tipo do usuário.
     *
     * @param context        - Contexto da classe que está chamando o método
     * @param tipoUsuario    - Tipo de usuário (Coordenador/Aluno)
     * @param id             - ID do usuário
     * @param senha          - Nova senha
     * @param confirmarSenha - Confirmação da nova senha
     * @return - String com o feedback da ação do método
     */
    public String redefinirSenha(Context context, String tipoUsuario, int id, String senha, String confirmarSenha) {
        try {
            conexao = Conexao.estartaConexao(context);
            if (tipoUsuario.contains("Aluno")) {
                return alunoBo.definirSenha(conexao, id, senha, confirmarSenha);
            } else {
                return coordenadorBo.definirSenha(conexao, id, senha, confirmarSenha);
            }
        } catch (SQLException e) {
            return "Erro: " + e.getMessage();
        }
    }

    /**
     * Método para cadastrar um aluno. É feito uma validação se já existe um aluno cadastrado com
     * o usuário inserido no objeto. Em seguida é chamado o método para obter a referencia do ID da Turma que
     * o Aluno pertencerá e o método para obter a referencia do ID das HorasComplementares. Por fim chamado o
     * método salvar da AlunoBo.
     *
     * @param context - Contexto da classe que está chamando o método
     * @param aluno   - Objeto da classe Aluno com seus dados
     * @param curso   - Nome do curso selecionado no spinner
     * @param grupo   - Grupo da turma selecionado no spinner
     * @param periodo - Período letivo selecionado no spinner
     * @return - String com o feedback da ação do método
     */
    public String cadastrarAluno(Context context, Aluno aluno, String curso, String grupo, String periodo) {
        try {
            conexao = Conexao.estartaConexao(context);
            Aluno valida = alunoBo.consultar(conexao, aluno.getUsuario(), null);
            if (valida != null && valida.getId() != 0) {
                return "Usuário já cadastrado";
            }

            associaTurmaAoAluno(conexao, aluno, grupo, curso, periodo);
            associaHoraComplementarAoAluno(aluno);
            return alunoBo.salvar(conexao, aluno);
        } catch (SQLException e) {
            return "Erro: " + e.getMessage();
        }

    }

    /**
     * Método para encapsular uma Turma no objeto Aluno para referenciar seus ID. É consultado o curso e
     * período letivo no banco e encapsulado na turma e em seguida a turma é encapsulada ao aluno.
     *
     * @param conexao - Contexto da classe que está chamando o método
     * @param aluno   - Objeto da classe Aluno com seus dados
     * @param grupo   - Grupo da Turma (Turma A, B, C) selecionado no spinner
     * @param titulo  - Nome do curso selecionado no spinner
     * @param periodo - Nome do período selecionado no spinner
     */
    private void associaTurmaAoAluno(SQLiteDatabase conexao, Aluno aluno, String grupo, String titulo, String periodo) {
        Curso curso = cursoBo.consultar(conexao, titulo);
        PeriodoLetivo periodoLetivo = periodoLetivoBo.consultar(conexao, periodo);
        Turma turma = turmaBo.consultar(conexao, grupo);
        turma.setPeriodo(periodoLetivo);
        turma.setCurso(curso);
        aluno.setTurma(turma);
    }

    /**
     * Método para gerar a tabela HorasComplementares no banco e encapsular seu o ID
     * no objeto Aluno. É salvo um novo objeto da classe HorasComplementares e encapsulado
     * o id no objeto Aluno.
     *
     * @param aluno - Objeto da classe Aluno
     */
    private void associaHoraComplementarAoAluno(Aluno aluno) {
        int id = getNovoIdHorasComplementares();
        if (id != -1 && id != 0) {
            aluno.getHorasComplementares().setId(id);
        }
    }

    /**
     * Método para pesquisar um aluno.
     *
     * @param context - Contexto da classe que está chamando o método
     * @param nome    - Nome do aluno a ser pesquisado
     * @return - Lista de objetos do tipo Aluno
     */
    public List<Aluno> pesquisarAluno(Context context, String nome) {
        try {
            conexao = Conexao.estartaConexao(context);
            return alunoBo.pesquisar(conexao, nome);
        } catch (SQLException e) {
            e.getMessage();
            return null;
        }
    }

    /**
     * Método para buscar um Aluno específico. É feito a consulta no banco com usuário
     * e senha do Aluno em seguida validado se foi encontrado um Aluno e utilizado o método
     * ListaAtividadeAluno para buscar a Lista de atividades do Aluno. Em seguida encapsulado
     * a lista no objeto Aluno.
     *
     * @param context - Contexto da classe que está chamando o método
     * @param usuario - Usuário de login do Aluno
     * @param senha   - Senha de login do Aluno
     * @return - Objeto da classe Aluno
     */
    public Aluno buscarAluno(Context context, String usuario, String senha) {

        conexao = Conexao.estartaConexao(context);
        Aluno aluno = alunoBo.consultar(conexao, usuario, senha);

        if (aluno != null && aluno.getId() != 0) {
            List<Atividade> atividades = listaAtividadesAluno(aluno.getHorasComplementares().getId());

            aluno.getHorasComplementares().setAtividades(atividades);
            return aluno;
        }
        return null;
    }

    /**
     * Método para buscar as atividades e atividades complementares de um Aluno. É feito a busca
     * das atividades com o ID das Horas Complementares do Aluno. Em seguida iterado a lista de
     * atividades e a cada iteração é consultado uma AtividadeComplementar associada ao ID da Atividade
     * iterada. A AtividadeComplementar encontrada é adicionada a lista de atividades.
     *
     * @param horaComplementar_id - ID do objeto HorasComplementares encapsulado no objeto Aluno
     * @return - Lista de objetos do tipo Atividade
     */
    private List<Atividade> listaAtividadesAluno(int horaComplementar_id) {
        List<Atividade> lista = atividadeBo.buscarAtividadeAluno(conexao, horaComplementar_id);
        List<Atividade> atividades = new ArrayList<Atividade>();

        if (lista != null) {
            List<AtividadeComplementar> atividadeComplementar;
            for (Atividade a : lista) {
                atividadeComplementar = atividadeComplementarBo.consultar(conexao, a.getId());
                if (atividadeComplementar != null) {
                    a.setAtividadesComplementares(atividadeComplementar);
                    atividades.add(a);
                }
            }
        }
        return atividades;
    }

    /**
     * Método para editar um Aluno.
     *
     * @param aluno   - Objeto da classe Aluno com seus dados antigos
     * @param nome    - Novo nome para o Aluno
     * @param usuario - Novo usuário para o Aluno
     * @return - String com o feedback da ação do método
     */
    public String editarAluno(Context context, Aluno aluno, String nome, String usuario) {
        try {
            conexao = Conexao.estartaConexao(context);
            return alunoBo.editar(conexao, aluno, nome, usuario);
        } catch (SQLException e) {
            return "Erro: " + e.getMessage();
        }
    }

    /**
     * Método para listar os Alunos
     *
     * @param context - Contexto da classe que está chamando o método
     * @return - Lista de objetos do tipo Aluno
     */
    public List<Aluno> listarAlunos(Context context) {
        try {
            conexao = Conexao.estartaConexao(context);
            return alunoBo.listar(conexao);
        } catch (SQLException e) {
            e.getMessage();
            return null;
        }
    }

    /**
     * Método para excluír um Aluno
     *
     * @param context - Contexto da classe que está chamando o método
     * @param id      = ID do Aluno a ser excluído
     * @return - String com o feedback da ação do método
     */
    public String excluirAluno(Context context, int id) {
        try {
            conexao = Conexao.estartaConexao(context);
            alunoBo.excluir(conexao, id);
            return "Aluno deletado";
        } catch (SQLException e) {
            return "Erro: " + e.getMessage();
        }
    }

    /*
    public Aluno buscarAluno(Context context, int id) {
        try{
            conexao = Conexao.estartaConexao(context);
            return alunoBo.consultar(conexao, id);
        }catch (SQLException e){
            return null;
        }
    }
     */


    /**
     * Método para excluir uma Situação
     *
     * @param context - Contexto da classe que está chamando o método
     * @param id      - ID da Situação
     * @return - String com o feedback da ação do método
     */
    public String excluirStatus(Context context, int id) {
        try {
            conexao = Conexao.estartaConexao(context);
            situacaoBo.excluir(conexao, id);
            return "Status deletado";
        } catch (SQLException e) {
            return "Erro: " + e.getMessage();
        }
    }

    /**
     * Método para cadastrar uma Situação
     *
     * @param context  - Contexto da classe que está chamando o método
     * @param situacao - Objeto da classe Situação com seus dados
     * @return - String com o feedback da ação do método
     */
    public String cadastrarStatus(Context context, Situacao situacao) {
        try {
            conexao = Conexao.estartaConexao(context);
            situacaoBo.salvar(conexao, situacao);
            return "Status cadastrado";
        } catch (SQLException e) {
            return "Erro: " + e.getMessage();
        }
    }

    /**
     * Método para listar as Situações
     *
     * @param context - Contexto da classe que está chamando o método
     * @return - Lista de objetos do tipo Situação
     */
    public List<Situacao> listarStatus(Context context) {
        try {
            conexao = Conexao.estartaConexao(context);
            return situacaoBo.listar(conexao);
        } catch (SQLException e) {
            e.getMessage();
            return null;
        }
    }

    /**
     * Método para pesquisar uma Situação
     *
     * @param context - Contexto da classe que está chamando o método
     * @param status  - Nome do status
     * @return - Lista de objetos do tipo Situação
     */
    public List<Situacao> pesquisarStatus(Context context, String status) {
        try {
            conexao = Conexao.estartaConexao(context);
            return situacaoBo.pesquisar(conexao, status);
        } catch (SQLException e) {
            e.getMessage();
            return null;
        }
    }

    /**
     * Método para consultar uma Situação
     *
     * @param context - Contexto da classe que está chamando o método
     * @param status  - Nome do status
     * @return - Objeto do tipo Situação
     */
    public Situacao consultarStatus(Context context, String status) {
        try {
            conexao = Conexao.estartaConexao(context);
            return situacaoBo.consultar(conexao, status);
        } catch (SQLException e) {
            e.getMessage();
            return null;
        }
    }

    /**
     * Método para editar uma Situação
     *
     * @param context  - Contexto da classe que está chamando o método
     * @param situacao - Objeto da classe situação com seus dados antigos
     * @param status   - Novo status
     * @return - String com o feedback da ação do método
     */
    public String editarStatus(Context context, Situacao situacao, String status) {
        try {
            conexao = Conexao.estartaConexao(context);
            return situacaoBo.editar(conexao, situacao, status);
        } catch (SQLException e) {
            return "Erro: " + e.getMessage();
        }
    }

    /**
     * Método para salvar um novo objeto do tipo HorasComplementares no banco de dados
     *
     * @return - ID gerado ao salvar o objeto
     */
    public int getNovoIdHorasComplementares() {
        try {
            HoraComplementar horaComplementar = new HoraComplementar();
            return horaComplementarBo.salvar(conexao, horaComplementar);
        } catch (SQLException e) {
            e.getMessage();
            return 0;
        }
    }

    /**
     * Método para salvar uma nova atividade. É encapsulado o ID de HorasComplementares
     * e o Escopo do requerimento no objeto atividade e salvo no banco. Em seguida, após recuperar o ID
     * da atividade salva, é chamado o método para salvar a atividade complementar referente a nova atividade.
     *
     * @param horasComplementares_id - ID do objeto HorasComplementares
     * @param requerimento           - Objeto com os dados do requerimento
     * @param horas                  - Quantidade de horas complementares
     * @return - True Ocorreu tudo certo/False ocorreu algum erro
     */
    private boolean salvaAtividade(int horasComplementares_id, Requerimento requerimento, int horas) {
        Atividade atividade = new Atividade();

        atividade.getHorasComplementares().setId(horasComplementares_id);
        atividade.setEscopo(requerimento.getEscopo());

        int atividade_id = atividadeBo.salvar(conexao, atividade);

        if (atividade_id != -1 && atividade_id != 0) {
            return salvaAtividadeComplementar(atividade_id, requerimento, horas, horasComplementares_id);
        }
        return false;
    }

    /**
     * Método para salvar a Atividade Complementar em um grupo de atividade específico. É listado todas as atividades
     * que o aluno possui para validar o limite de horas que o mesmo possui e posteriormente é encapsulado no objeto
     * do tipo AtividadeComplementar as informações necessárias do requerimento e salvo a atividade com plementar.
     *
     * @param atividade_id           - ID da Atividade para referenciar na AtividadeComplementar
     * @param requerimento           - Objeto com os dados do requerimento
     * @param horas                  - Quantidade de horas complementares
     * @param horasComplementares_id - ID para buscar as Atividades do aluno
     * @return - Feedback da ação do método
     */
    private boolean salvaAtividadeComplementar(int atividade_id, Requerimento requerimento, int horas, int horasComplementares_id) {
        AtividadeComplementar atividadeComplementar = new AtividadeComplementar();
        Aluno aluno = new Aluno();
        aluno.getHorasComplementares().setAtividades(listaAtividadesAluno(horasComplementares_id));

        horas = validaLimiteHoras(horas, aluno);

        atividadeComplementar.getAtividade().setId(atividade_id);
        atividadeComplementar.setTitulo(requerimento.getTitulo());
        atividadeComplementar.setInstituicao(requerimento.getInstituicao());
        atividadeComplementar.setCargaHoraria(requerimento.getCargaHoraria());
        atividadeComplementar.setHoraComplementar(horas);

        int i = atividadeComplementarBo.salvar(conexao, atividadeComplementar);
        if (i != 0 && i != -1) {
            return true;
        }
        return false;
    }

    /**
     * Método para não permitir que seja acrescentado mais que o limite de 150 horas.
     * Caso o valor de horas a ser acrescentado ultrapasse o limite será subtraido o
     * total de horas do aluno de 150 e atribuído em 'horas'.
     *
     * @param horas - Quantidade de horas complementares
     * @param aluno - Objeto com seus dados
     * @return - Qunatidade de horas complementares
     */
    private int validaLimiteHoras(int horas, Aluno aluno) {
        if ((aluno.getHorasComplementares().getTotal() + horas) > 150) {
            horas = 150 - aluno.getHorasComplementares().getTotal();
        }
        return horas;
    }

    /**
     * Método para listar as Solicitações dos alunos dos cursos referente a
     * um coordenador específico.
     *
     * @param context        - Contexto da classe que está chamando o método
     * @param id_coordenador - ID do Coordenador
     * @return - Lista de Solicitações dos alunos do curso
     */
    public List<Solicitacao> listarSolicitacoesPorCurso(Context context, int id_coordenador) {
        try {
            conexao = Conexao.estartaConexao(context);
            return solicitacaoBo.listarPorCurso(conexao, id_coordenador);
        } catch (SQLException e) {
            e.getMessage();
            return null;
        }
    }

    /**
     * Método para listar as Solicitações de um aluno específico. Irá buscar as solicitações
     * com o ID do aluno
     *
     * @param context  - Contexto da classe que está chamando o método
     * @param id_aluno - ID do aluno
     * @return - Lista de Solicitações do aluno
     */
    public List<Solicitacao> listarSolicitacoesPorAluno(Context context, int id_aluno) {
        try {
            conexao = Conexao.estartaConexao(context);
            return solicitacaoBo.listarPorAluno(conexao, id_aluno);
        } catch (SQLException e) {
            e.getMessage();
            return null;
        }
    }

    /*
    public Solicitacao buscarSolicitacao(Context context, int protocolo) {
        conexao = Conexao.estartaConexao(context);
        return solicitacaoBo.consultar(conexao, protocolo);
    }
    */

    /**
     * Método para negar uma Solicitação. Irá alterar o status da solicitação
     * e atualizar no banco de dados
     *
     * @param context     - Contexto da classe que está chamando o método
     * @param solicitacao - Objeto da Solicitação com seus dados
     * @return - String com o feedback da ação do método
     */
    public String recusarSolicitacao(Context context, Solicitacao solicitacao) {
        try {
            conexao = Conexao.estartaConexao(context);
            Situacao status = consultarStatus(context, "Indeferido");
            if (status != null) {
                return alteraStatusSolicitacao(solicitacao, status);
            }
            return "Erro ao negar requerimento";
        } catch (SQLException e) {
            return "Erro: " + e.getMessage();
        }
    }

    /**
     * Método para registrar as horas complementares de um aluno.
     *
     * @param context     - Contexto da classe que está chamando o método
     * @param solicitacao - Objeto da solicitação
     * @param horas       - Quantidade de horas complementares
     * @return - String com o feedback da ação do método
     */
    public String registarHorasComplementares(Context context, Solicitacao solicitacao, int horas) {
        boolean valido = false;
        try {
            conexao = Conexao.estartaConexao(context);
            Aluno aluno = alunoBo.consultar(conexao, solicitacao.getAluno().getId());
            solicitacao.getRequerimento().setEscopo(escopoBo.consultar(conexao, solicitacao.getRequerimento().getEscopo().getAtividade()));

            valido = validaGrupoAtividadeExistente(solicitacao, horas, valido, aluno);
            if (valido) {
                Situacao status = consultarStatus(context, "Deferido");
                if (status != null) {
                    return alteraStatusSolicitacao(solicitacao, status);
                }
            }
            return "Erro";
        } catch (SQLException e) {
            return "Erro: " + e.getMessage();
        }
    }

    /**
     * Método para válidar se o aluno já possuí uma atividade registrada. Caso ele não tenha, será chamado o método para
     * salvar uma nova atividade. Se houver algum tipo de atividade, será verificado se o escopo do requerimento (Atividade)
     * é a mesma que o aluno já possuí. Se não encontar será salvo uma nova atividade.
     *
     * @param solicitacao - Objeto da solicitação
     * @param horas       - Quantidade de horas complementares
     * @param valido      - Feedback se foi salvo ou se encontrou alguma atividade
     * @param aluno       - Objeto aluno
     * @return - Feedback
     */
    private boolean validaGrupoAtividadeExistente(Solicitacao solicitacao, int horas, boolean valido, Aluno aluno) {
        if ((atividadeBo.buscarAtividadeAluno(conexao, aluno.getHorasComplementares().getId())) == null) {
            valido = salvaAtividade(aluno.getHorasComplementares().getId(), solicitacao.getRequerimento(), horas);
        } else {
            valido = buscaGrupoAtividade(solicitacao, horas, valido, aluno);
            if (!valido) {
                valido = salvaAtividade(aluno.getHorasComplementares().getId(), solicitacao.getRequerimento(), horas);
            }
        }
        return valido;
    }

    /**
     * Método para verificar se o requerimento do aluno pertence a algum grupo de atividade que já foi registrada e
     * se encontrar será chamado o método para salvar esta atividade complementar.
     *
     * @param solicitacao - Objeto da solicitação
     * @param horas       - Quantidade de horas complementares
     * @param valido      - Feedback se foi salvo ou se encontrou alguma atividade
     * @param aluno       - Objeto do aluno
     * @return - Feedback
     */
    private boolean buscaGrupoAtividade(Solicitacao solicitacao, int horas, boolean valido, Aluno aluno) {
        List<Atividade> lista = atividadeBo.buscarAtividadeAluno(conexao, aluno.getHorasComplementares().getId());
        for (Atividade a : lista) {
            if (a.getEscopo().getId() == solicitacao.getRequerimento().getEscopo().getId()) {
                valido = salvaAtividadeComplementar(a.getId(), solicitacao.getRequerimento(), horas, aluno.getHorasComplementares().getId());
                break;
            }
        }
        return valido;
    }

    /**
     * Método para alterar o status da solicitação
     *
     * @param solicitacao - Solicitação de horas complementares
     * @param status      - Status
     * @return - String com o feedback da ação do método
     */
    private String alteraStatusSolicitacao(Solicitacao solicitacao, Situacao status) {
        solicitacao = solicitacaoBo.consultar(conexao, solicitacao.getProtocolo());
        solicitacao.setSituacao(status);
        return solicitacaoBo.editar(conexao, solicitacao);
    }

    /*
    private void preencheAtividadeComplementar(Solicitacao solicitacao, int horas, AtividadeComplementar atividadeComplementar) {
        atividadeComplementar.setTitulo(solicitacao.getRequerimento().getTitulo());
        atividadeComplementar.setInstituicao(solicitacao.getRequerimento().getInstituicao());
        atividadeComplementar.setHoraComplementar(horas);
        atividadeComplementar.setCargaHoraria(solicitacao.getRequerimento().getCargaHoraria());
    }
    */

    /**
     * Método para cadastrar um escopo.
     *
     * @param context - Contexto da classe que está chamando o método
     * @param escopo  - Escopo
     * @return - String com o feedback da ação do método
     */
    public String cadastrarEscopo(Context context, Escopo escopo) {
        try {
            conexao = Conexao.estartaConexao(context);
            escopoBo.salvar(conexao, escopo);
            return "Motivo cadastrado";
        } catch (SQLException e) {
            return "Erro: " + e.getMessage();
        }
    }

    /**
     * Método para excluir um escopo.
     *
     * @param context - Contexto da classe que está chamando o método
     * @param id      - ID do escopo
     * @return - String com o feedback da ação do método
     */
    public String excluirEscopo(Context context, int id) {
        try {
            conexao = Conexao.estartaConexao(context);
            escopoBo.excluir(conexao, id);
            return "Motivo deletado";
        } catch (SQLException e) {
            return "Erro: " + e.getMessage();
        }
    }

    /**
     * Método para listar os escopos.
     *
     * @param context - Contexto da classe que está chamando o método
     * @return - Lista de escopos
     */
    public List<Escopo> listarEscopos(Context context) {
        try {
            conexao = Conexao.estartaConexao(context);
            return escopoBo.listar(conexao);
        } catch (SQLException e) {
            e.getMessage();
            return null;
        }
    }

    /**
     * Método para pesquisar um escopo
     *
     * @param context - Contexto da classe que está chamando o método
     * @param escopo  - Nome do escopo a ser pesquisado
     * @return - Lista de escopos
     */
    public List<Escopo> pesquisarEscopo(Context context, String escopo) {
        try {
            conexao = Conexao.estartaConexao(context);
            return escopoBo.pesquisar(conexao, escopo);
        } catch (SQLException e) {
            e.getMessage();
            return null;
        }
    }

    /**
     * Método para buscar um escopo específico.
     *
     * @param context - Contexto da classe que está chamando o método
     * @param escopo  - Nome do escopo
     * @return - Escopo
     */
    public Escopo buscarEscopo(Context context, String escopo) {
        try {
            conexao = Conexao.estartaConexao(context);
            return escopoBo.consultar(conexao, escopo);
        } catch (SQLException e) {
            e.getMessage();
            return null;
        }
    }

    /**
     * Método para editaar um escopo.
     *
     * @param context - Contexto da classe que está chamando o método
     * @param escopo  - Escopo com seus dados antigos
     * @param motivo  - Nome do novo escopo
     * @return - String com o feedback da ação do método
     */
    public String editarEscopo(Context context, Escopo escopo, String motivo) {
        try {
            conexao = Conexao.estartaConexao(context);
            return escopoBo.editar(conexao, escopo, motivo);
        } catch (SQLException e) {
            return "Erro: " + e.getMessage();
        }
    }

    /*
    public List<Atividade> listarAtividades(Context context, Aluno aluno) {
        conexao = Conexao.estartaConexao(context);
        return atividadeBo.listar(conexao);
    }
    */

    /**
     * Método para registrar uma resquisição de horas complementares.
     *
     * @param context      - Contexto da classe que está chamando o método
     * @param requerimento - Requerimento de horas
     * @param atividade    - Grupo da atividade selecionada (Escopo)
     * @param alunoId      - ID do aluno que enviou o requerimento
     * @return - String com o feedback da ação do método
     */
    public String registrarRequisicao(Context context, Requerimento requerimento, String atividade, int alunoId) {
        Escopo escopo = buscarEscopo(context, atividade);
        requerimento.setEscopo(escopo);
        try {
            conexao = Conexao.estartaConexao(context);
            int requerimento_id = requerimentoBo.salvar(conexao, requerimento);
            if (requerimento_id != -1) {
                Solicitacao solicitacao = getSolicitacao(alunoId, requerimento_id);
                solicitacaoBo.salvar(conexao, solicitacao);
            } else {
                return "Preencha os campos";
            }
        } catch (SQLException e) {
            return "Erro: " + e.getMessage();
        }
        return "Requisição enviada";
    }

    /**
     * Método para gerar uma solicitação referente ao requerimento registrado.
     *
     * @param alunoId         - ID do aluno que enviou o requerimento
     * @param requerimento_id - ID do requerimento salvo
     * @return - Nova Solicitação
     */
    private Solicitacao getSolicitacao(int alunoId, int requerimento_id) {
        Solicitacao solicitacao = new Solicitacao();
        solicitacao.getAluno().setId(alunoId);
        solicitacao.getRequerimento().setId(requerimento_id);
        solicitacao.getSituacao().setId(1);
        return solicitacao;
    }


}
