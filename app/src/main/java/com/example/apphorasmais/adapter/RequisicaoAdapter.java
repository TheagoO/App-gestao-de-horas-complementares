package com.example.apphorasmais.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apphorasmais.R;
import com.example.apphorasmais.repository.Solicitacao;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class RequisicaoAdapter extends RecyclerView.Adapter<RequisicaoAdapter.ViewHolderRequisicao> {

    private List<Solicitacao> dados;

    public RequisicaoAdapter(List<Solicitacao> dados) {
        this.dados = dados;
    }

    @NonNull
    @Override
    public ViewHolderRequisicao onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_card_minhas_requisicoes, parent, false);

        ViewHolderRequisicao viewHolderRequisicao = new ViewHolderRequisicao(view, parent.getContext());

        return viewHolderRequisicao;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderRequisicao holder, int position) {
        if((dados != null) && (dados.size() > 0)){
            holder.protocolo.setText(String.valueOf(dados.get(position).getProtocolo()));
            holder.status.setText(" "+dados.get(position).getSituacao().getStatus()+" ");
            defineCoresStatus(holder);
            holder.escopo.setText(dados.get(position).getRequerimento().getEscopo().getAtividade());
            holder.dataSolicitacao.setText(String.valueOf((dados.get(position).getDataSolicitacao()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        }
    }

    private void defineCoresStatus(@NonNull ViewHolderRequisicao holder) {
        if(holder.status.getText().toString().contains("Aguardando")){
            holder.status.setBackgroundColor(Color.rgb(255, 191, 0));
        }else if(holder.status.getText().toString().contains("Indeferido")){
            holder.status.setBackgroundColor(Color.rgb(205, 1, 1));
        }else if(holder.status.getText().toString().contains("Deferido")){
            holder.status.setBackgroundColor(Color.rgb(2, 142, 16));
        }
    }

    @Override
    public int getItemCount() {
        if(dados != null){
            return dados.size();
        }
        return 0;
    }

    public class ViewHolderRequisicao extends RecyclerView.ViewHolder {

        private TextView protocolo;
        private TextView status;
        private TextView escopo;
        private TextView dataSolicitacao;

        public ViewHolderRequisicao(@NonNull View itemView, final Context context) {
            super(itemView);
            protocolo = (TextView) itemView.findViewById(R.id.cardMinhasRequisicoesProtocolo);
            status = (TextView) itemView.findViewById(R.id.cardMinhasRequisicoesStatus);
            escopo = (TextView) itemView.findViewById(R.id.cardMinhasRequisicoesEscopo);
            dataSolicitacao = (TextView) itemView.findViewById(R.id.cardMinhasRequisicoesDataSolicitacao);
        }
    }
}
