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

import com.example.apphorasmais.repository.Escopo;
import com.example.apphorasmais.R;
import com.example.apphorasmais.EditarEscopo;
import com.example.apphorasmais.model.facade.Facade;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class EscopoAdapter extends RecyclerView.Adapter<EscopoAdapter.ViewHolderEscopo> {

    private List<Escopo> dados;

    public EscopoAdapter(List<Escopo> dados) {
        this.dados = dados;
    }

    @NonNull
    @Override
    public ViewHolderEscopo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_card_escopo, parent, false);
        ViewHolderEscopo viewHolderEscopo = new ViewHolderEscopo(view, parent.getContext());

        return viewHolderEscopo;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderEscopo holder, int position) {
        holder.escopo.setText(dados.get(position).getAtividade());
    }

    @Override
    public int getItemCount() {
        if(dados != null){
            return dados.size();
        }
        return 0;
    }

    public class ViewHolderEscopo extends RecyclerView.ViewHolder {

        private TextView escopo;
        private ImageView editar;
        private ImageView excluir;
        private Facade facade;

        public ViewHolderEscopo(View itemView, final Context context) {
            super(itemView);
            facade = new Facade();
            escopo = itemView.findViewById(R.id.cardEscopoEscopo);
            editarEscopo(itemView, context);
            excluirEscopo(context);
        }

        private void editarEscopo(View itemView, Context context) {
            editar = itemView.findViewById(R.id.cardEscopoEditar);
            editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, EditarEscopo.class);
                    i.putExtra("ESCOPO", dados.get(getLayoutPosition()));
                    context.startActivity(i);
                }
            });
        }

        private void excluirEscopo(Context context) {
            excluir = itemView.findViewById(R.id.cardEscopoExcluir);
            excluir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertaDialogo(view.getContext());
                }
            });
        }

        protected void alertaDialogo(final Context context) {
            AlertDialog alertDialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Atenção");
            builder.setMessage("Deseja mesmo excluir este motivo?");
            builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String retorno = facade.excluirEscopo(context, getId());
                    atualizarLista(context);
                    Toast.makeText(context, retorno, Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Cancelar", null);
            alertDialog = builder.create();
            alertDialog.show();
        }

        private void atualizarLista(Context context) {
            dados = facade.listarEscopos(context);
            notifyDataSetChanged();
        }

        private int getId(){
            return dados.get(getLayoutPosition()).getId();
        }
    }
}
