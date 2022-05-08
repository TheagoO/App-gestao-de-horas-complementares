package com.example.apphorasmais.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apphorasmais.R;
import com.example.apphorasmais.model.entity.AtividadeComplementar;

import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class AtividadeComplementarAdapter extends RecyclerView.Adapter<AtividadeComplementarAdapter.ViewHolderAtividadeComplementar> {

    private List<AtividadeComplementar> dados;

    public AtividadeComplementarAdapter(List<AtividadeComplementar> dados) {
        this.dados = dados;
    }

    @NonNull
    @Override
    public ViewHolderAtividadeComplementar onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_card_atividade_complementar, parent, false);
        ViewHolderAtividadeComplementar viewHolderAtividadeComplementar = new ViewHolderAtividadeComplementar(view, parent.getContext());

        return viewHolderAtividadeComplementar;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAtividadeComplementar holder, int position) {
        holder.titulo.setText(dados.get(position).getTitulo());
        holder.instituicao.setText(dados.get(position).getInstituicao());
        holder.carga.setText(String.valueOf(dados.get(position).getCargaHoraria()));
        holder.horas.setText(String.valueOf(dados.get(position).getHoraComplementar()));
    }

    @Override
    public int getItemCount() {
        if(dados != null){
            return dados.size();
        }
        return 0;
    }

    public class ViewHolderAtividadeComplementar extends RecyclerView.ViewHolder {

        private TextView titulo;
        private TextView instituicao;
        private TextView carga;
        private TextView horas;

        public ViewHolderAtividadeComplementar(@NonNull View itemView, final Context context) {
            super(itemView);
            titulo = (TextView) itemView.findViewById(R.id.cardAtividadeComplementarTitulo);
            instituicao = (TextView) itemView.findViewById(R.id.cardAtividadeComplementarInstituicao);
            carga = (TextView) itemView.findViewById(R.id.cardAtividadeComplementarCargaHoraria);
            horas = (TextView) itemView.findViewById(R.id.cardAtividadeComplementarHorasComplementares);

        }
    }
}
