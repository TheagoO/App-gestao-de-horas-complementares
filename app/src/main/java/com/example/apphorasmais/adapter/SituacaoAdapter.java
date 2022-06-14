package com.example.apphorasmais.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apphorasmais.R;
import com.example.apphorasmais.model.entity.Situacao;

import java.util.List;

import com.example.apphorasmais.EditarSituacao;
import com.example.apphorasmais.model.facade.Facade;

/**
 * @author Thiago Ferreira Assumpção
 */

public class SituacaoAdapter extends RecyclerView.Adapter<SituacaoAdapter.ViewHolderSituacao> {

    private List<Situacao> dados;

    public SituacaoAdapter(List<Situacao> dados) {
        this.dados = dados;
    }

    @Override
    public SituacaoAdapter.ViewHolderSituacao onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_card_situacao, parent, false);

        ViewHolderSituacao viewHolderSituacao = new ViewHolderSituacao(view, parent.getContext());

        return viewHolderSituacao;
    }

    @Override
    public void onBindViewHolder(@NonNull SituacaoAdapter.ViewHolderSituacao holder, int position) {
        if (dados != null && dados.size() > 0) {
            holder.status.setText(dados.get(position).getStatus());
        }
    }

    @Override
    public int getItemCount() {
        if (dados != null) {
            return dados.size();
        }
        return 0;
    }

    public class ViewHolderSituacao extends RecyclerView.ViewHolder {

        private TextView status;
        private ImageView editar;
        private ImageView excluir;
        private Facade facade;

        public ViewHolderSituacao(View itemView, final Context context) {
            super(itemView);
            facade = new Facade();
            status = (TextView) itemView.findViewById(R.id.cardStatusStatus);
            editar = (ImageView) itemView.findViewById(R.id.cardStatusEditar);
            excluir = (ImageView) itemView.findViewById(R.id.cardStatusExcluir);

            editarSituacao(context);

            excluirSituacao(context);
        }

        private void excluirSituacao(Context context) {
            excluir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertaDialogo(view.getContext());
                }
            });
        }

        private void atualizarLista(Context context) {
            dados = facade.listarStatus(context);
            notifyDataSetChanged();
        }

        protected void alertaDialogo(final Context context) {
            AlertDialog alertDialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Atenção");
            builder.setMessage("Deseja mesmo excluir este status?");
            builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    facade.excluirStatus(context, getId());
                    atualizarLista(context);
                    Toast.makeText(context, "Status deletado", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Cancelar", null);
            alertDialog = builder.create();
            alertDialog.show();
        }

        private void editarSituacao(Context context) {
            editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (dados.size() > 0) {
                        Intent i = new Intent(context, EditarSituacao.class);
                        i.putExtra("SITUACAO", dados.get(getLayoutPosition()));
                        context.startActivity(i);
                    }
                }
            });
        }

        private int getId() {
            return dados.get(getLayoutPosition()).getId();
        }

    }
}
