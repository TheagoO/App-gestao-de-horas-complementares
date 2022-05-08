package com.example.apphorasmais.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apphorasmais.model.entity.Turma;
import com.example.apphorasmais.model.facade.Facade;
import com.example.apphorasmais.EditarTurma;
import com.example.apphorasmais.R;

import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class TurmaAdapter extends RecyclerView.Adapter<TurmaAdapter.ViewHolderTurma> {

    private List<Turma> dados;

    public TurmaAdapter(List<Turma> dados) {
        this.dados = dados;
    }

    @NonNull
    @Override
    public ViewHolderTurma onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_card_turma, parent, false);
        ViewHolderTurma viewHolderTurma = new ViewHolderTurma(view, parent.getContext());

        return viewHolderTurma;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTurma holder, int position) {
        holder.turma.setText(dados.get(position).getGrupo());
        holder.periodo.setText(dados.get(position).getPeriodo().getPeriodo()+"º");
        holder.curso.setText(dados.get(position).getCurso().getTitulo());
    }

    @Override
    public int getItemCount() {
        if(dados != null){
            return dados.size();
        }
        return 0;
    }

    public class ViewHolderTurma extends RecyclerView.ViewHolder {

        private TextView turma;
        private TextView curso;
        private TextView periodo;
        private ImageView editar;
        private ImageView excluir;
        private Facade facade;

        public ViewHolderTurma(@NonNull View itemView, final Context context) {
            super(itemView);
            facade = new Facade();
            turma = (TextView) itemView.findViewById(R.id.cardTurmaTurma);
            curso = (TextView) itemView.findViewById(R.id.cardTurmaCurso);
            periodo = (TextView) itemView.findViewById(R.id.cardTurmaPeriodo);
            editarTurma(itemView, context);
            excluirTurma(context);
        }

        private void editarTurma(@NonNull View itemView, Context context) {
            editar = (ImageView) itemView.findViewById(R.id.cardTurmaEditar);
            editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, EditarTurma.class);
                    i.putExtra("TURMA", dados.get(getLayoutPosition()));
                    context.startActivity(i);
                }
            });
        }

        private void excluirTurma(Context context) {
            excluir = (ImageView) itemView.findViewById(R.id.cardTuramExcluir);
            excluir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String retorno = facade.excluirTurma(context, getId());
                    atualizarLista(context);
                    Toast.makeText(context, retorno, Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void atualizarLista(Context context) {
            dados = facade.listarTurmas(context);
            notifyDataSetChanged();
        }

        private int getId(){
            return dados.get(getLayoutPosition()).getId();
        }
    }
}
