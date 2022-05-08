package com.example.apphorasmais.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apphorasmais.model.entity.Curso;
import androidx.recyclerview.widget.RecyclerView;
import com.example.apphorasmais.R;
import com.example.apphorasmais.EditarCurso;
import com.example.apphorasmais.model.facade.*;

import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class CursoAdapter extends RecyclerView.Adapter<CursoAdapter.ViewHolderCurso> {

    private List<Curso> dados;

    public CursoAdapter(List<Curso> dados) {
        this.dados = dados;
    }

    @Override
    public ViewHolderCurso onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_card_curso, parent, false);

        ViewHolderCurso viewHolderCurso = new ViewHolderCurso(view, parent.getContext());

        return viewHolderCurso;
    }

    @Override
    public void onBindViewHolder(ViewHolderCurso holder, int position) {
        holder.curso.setText(dados.get(position).getTitulo());
    }

    @Override
    public int getItemCount() {
        if(dados != null){
            return dados.size();
        }
        return 0;
    }

    public class ViewHolderCurso extends RecyclerView.ViewHolder {

        private TextView curso;
        private ImageView editar;
        private ImageView excluir;
        private Facade facade;

        public ViewHolderCurso(View itemView, final Context context) {
            super(itemView);
            facade = new Facade();
            curso = (TextView) itemView.findViewById(R.id.cardCursoCurso);
            editarCurso(itemView, context);
            excluirCurso(context);
        }

        private void editarCurso(View itemView, Context context) {
            editar = (ImageView) itemView.findViewById(R.id.cardCursoEditar);
            editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, EditarCurso.class);
                    i.putExtra("CURSO", dados.get(getLayoutPosition()));
                    context.startActivity(i);
                }
            });
        }

        private void excluirCurso(Context context) {
            excluir = (ImageView) itemView.findViewById(R.id.cardCursoExcluir);
            excluir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String retorno = facade.excluirCurso(context, getId());
                    atualizarLista(context);
                    Toast.makeText(context, retorno, Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void atualizarLista(Context context) {
            dados = facade.listarCursos(context);
            notifyDataSetChanged();
        }

        private int getId(){
            return dados.get(getLayoutPosition()).getId();
        }
    }
}
