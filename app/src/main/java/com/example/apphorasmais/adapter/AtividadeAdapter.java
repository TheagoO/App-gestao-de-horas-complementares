package com.example.apphorasmais.adapter;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apphorasmais.repository.Atividade;
import com.example.apphorasmais.AtividadesComplementar;
import com.example.apphorasmais.R;
import com.example.apphorasmais.repository.Aluno;

import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class AtividadeAdapter extends RecyclerView.Adapter<AtividadeAdapter.ViewHolderAtividade> {

    private List<Atividade> dados;
    private Aluno aluno;

    public AtividadeAdapter(List<Atividade> dados, Aluno aluno) {
        this.dados = dados;
        this.aluno = aluno;
    }

    @NonNull
    @Override
    public ViewHolderAtividade onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_card_minhas_atividades, parent, false);
        ViewHolderAtividade viewHolderAtividade = new ViewHolderAtividade(view, parent.getContext());

        return viewHolderAtividade;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAtividade holder, int position) {
        holder.escopo.setText(dados.get(position).getEscopo().getAtividade());
        holder.quantidade.setText(String.valueOf(dados.get(position).getQuantidade()));
    }

    @Override
    public int getItemCount() {
        if(dados != null){
            return dados.size();
        }
        return 0;
    }

    public class ViewHolderAtividade extends RecyclerView.ViewHolder {

        private TextView escopo;
        private TextView quantidade;

        public ViewHolderAtividade(@NonNull View itemView, final Context context) {
            super(itemView);
            escopo = (TextView) itemView.findViewById(R.id.cardMinhasAtividadesEscopo);
            quantidade = (TextView) itemView.findViewById(R.id.cardMinhasAtividadesQuantidade);
            abreTelaComAtividades(itemView, context);
        }

        private void abreTelaComAtividades(@NonNull View itemView, Context context) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    abrirTelaAtividadesComplementares();
                }

                private void abrirTelaAtividadesComplementares() {
                    Intent i = new Intent(context, AtividadesComplementar.class);
                    i.putExtra("ATIVIDADE", dados.get(getLayoutPosition()));
                    i.putExtra("ALUNO", aluno);
                    context.startActivity(i);
                }
            });
        }
    }
}
