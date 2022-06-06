package com.example.apphorasmais.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apphorasmais.R;
import com.example.apphorasmais.repository.Solicitacao;
import com.example.apphorasmais.repository.Coordenador;
import com.example.apphorasmais.AprovarRequerimento;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class SolicitacaoAdapter extends RecyclerView.Adapter<SolicitacaoAdapter.ViewHolderRequisicao> {

    private List<Solicitacao> dados;
    private Coordenador coordenador;

    public SolicitacaoAdapter(List<Solicitacao> dados, Coordenador coordenador) {
        this.dados = dados;
        this.coordenador = coordenador;
    }

    @Override
    public SolicitacaoAdapter.ViewHolderRequisicao onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_card_solicitacoes, parent, false);
        ViewHolderRequisicao viewHolderRequisicao = new ViewHolderRequisicao(view, parent.getContext());

        return viewHolderRequisicao;
    }

    @Override
    public void onBindViewHolder(SolicitacaoAdapter.ViewHolderRequisicao holder, int position) {
        if((dados != null) && (dados.size() > 0)){
            holder.protocolo.setText(String.valueOf(dados.get(position).getProtocolo()));
            holder.status.setText(" "+dados.get(position).getSituacao().getStatus()+" ");
            defineCoresStatus(holder);
            holder.escopo.setText(dados.get(position).getRequerimento().getEscopo().getAtividade());
            holder.dataSolicitacao.setText(String.valueOf((dados.get(position).getDataSolicitacao()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        }
    }

    private void defineCoresStatus(@NonNull SolicitacaoAdapter.ViewHolderRequisicao holder) {
        if(holder.status.getText().toString().contains("Aguardando")){
            holder.status.setBackgroundColor(Color.rgb(255, 191, 0));
        }
    }

    @Override
    public int getItemCount() {
        return dados.size();
    }

    public class ViewHolderRequisicao extends RecyclerView.ViewHolder {

        public TextView protocolo;
        public TextView status;
        public TextView escopo;
        public TextView dataSolicitacao;

        public ViewHolderRequisicao(View itemView, final Context context) {
            super(itemView);
            protocolo = (TextView) itemView.findViewById(R.id.cardSolicitacoesProtocolo);
            status = (TextView) itemView.findViewById(R.id.cardSolicitacoesStatus);
            escopo = (TextView) itemView.findViewById(R.id.cardSolicitacoesEscopo);
            dataSolicitacao = (TextView) itemView.findViewById(R.id.cardSolicitacoesDataSolicitacao);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, AprovarRequerimento.class);
                    i.putExtra("SOLICITACAO", dados.get(getLayoutPosition()));
                    i.putExtra("COORDENADOR", coordenador);
                    context.startActivity(i);
                }
            });


        }

    }
}
