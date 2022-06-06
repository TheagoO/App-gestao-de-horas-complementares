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

import com.example.apphorasmais.repository.PeriodoLetivo;
import com.example.apphorasmais.R;
import com.example.apphorasmais.EditarPeriodo;
import com.example.apphorasmais.model.facade.Facade;

import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class PeriodoAdapter extends RecyclerView.Adapter<PeriodoAdapter.ViewHolderPeriodo> {

    private List<PeriodoLetivo> dados;

    public PeriodoAdapter(List<PeriodoLetivo> dados) {
        this.dados = dados;
    }

    @NonNull
    @Override
    public ViewHolderPeriodo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_card_periodo, parent, false);
        ViewHolderPeriodo viewHolderPeriodo = new ViewHolderPeriodo(view, parent.getContext());

        return viewHolderPeriodo;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPeriodo holder, int position) {
        holder.periodo.setText(dados.get(position).getPeriodo());
    }

    @Override
    public int getItemCount() {
        if(dados != null){
            return dados.size();
        }
        return 0;
    }

    public class ViewHolderPeriodo extends RecyclerView.ViewHolder {

        private TextView periodo;
        private ImageView editar;
        private ImageView excluir;
        private Facade facade;

        public ViewHolderPeriodo(@NonNull View itemView, final Context context) {
            super(itemView);
            facade = new Facade();
            periodo = itemView.findViewById(R.id.cardPeriodoPeriodo);
            editarPeriodo(itemView, context);
            excluirPeriodo(context);
        }

        private void editarPeriodo(@NonNull View itemView, Context context) {
            editar = itemView.findViewById(R.id.cardPeriodoEditar);
            editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, EditarPeriodo.class);
                    i.putExtra("PERIODO", dados.get(getLayoutPosition()));
                    context.startActivity(i);
                }
            });
        }

        private void excluirPeriodo(Context context) {
            excluir = itemView.findViewById(R.id.cardPeriodoExcluir);
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
            builder.setMessage("Deseja mesmo excluir este período?");
            builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String retorno = facade.excluirPeriodo(context, getId());
                    atualizarLista(context);
                    Toast.makeText(context, retorno, Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Cancelar", null);
            alertDialog = builder.create();
            alertDialog.show();
        }

        private void atualizarLista(Context context) {
            dados = facade.listarPeriodos(context);
            notifyDataSetChanged();
        }

        private int getId() {
            return dados.get(getLayoutPosition()).getId();
        }
    }
}
