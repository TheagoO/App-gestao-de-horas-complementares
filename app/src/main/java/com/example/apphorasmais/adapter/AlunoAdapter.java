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

import com.example.apphorasmais.model.entity.Aluno;
import androidx.recyclerview.widget.RecyclerView;
import com.example.apphorasmais.R;
import com.example.apphorasmais.EditarUsuario;
import com.example.apphorasmais.model.facade.Facade;

import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class AlunoAdapter extends RecyclerView.Adapter<AlunoAdapter.ViewHolderAluno> {

    private List<Aluno> dados;

    public AlunoAdapter(List<Aluno> dados) {
        this.dados = dados;
    }

    @Override
    public AlunoAdapter.ViewHolderAluno onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_card_usuario, parent, false);

        ViewHolderAluno viewHolderAluno = new ViewHolderAluno(view, parent.getContext());

        return viewHolderAluno;
    }

    @Override
    public void onBindViewHolder(AlunoAdapter.ViewHolderAluno holder, int position) {
        if(dados != null && dados.size() > 0){
            holder.nome.setText(dados.get(position).getNome());
            holder.usuario.setText(dados.get(position).getUsuario());
        }
    }

    @Override
    public int getItemCount() {
        if(dados != null){
            return dados.size();
        }
        return 0;
    }

    public class ViewHolderAluno extends RecyclerView.ViewHolder {

        private TextView nome;
        private TextView usuario;
        private ImageView editar;
        private ImageView excluir;
        private Facade facade;

        public ViewHolderAluno(View itemView, final Context context) {
            super(itemView);
            facade = new Facade();
            nome = (TextView) itemView.findViewById(R.id.cardUsuarioNome);
            usuario = (TextView) itemView.findViewById(R.id.cardUsuarioUsuario);
            editarAluno(itemView, context);
            excluirAluno(context);
        }

        private void editarAluno(View itemView, Context context) {
            editar = (ImageView) itemView.findViewById(R.id.cardUsuarioEditar);
            editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, EditarUsuario.class);
                    i.putExtra("ALUNO", dados.get(getLayoutPosition()));
                    context.startActivity(i);
                }
            });
        }

        private void excluirAluno(Context context) {
            excluir = (ImageView) itemView.findViewById(R.id.cardUsuarioExcluir);
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
            builder.setMessage("Deseja mesmo excluir este aluno?");
            builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String retorno = facade.excluirAluno(context, getId());
                    atualizarLista(context);
                    Toast.makeText(context, retorno, Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Cancelar", null);
            alertDialog = builder.create();
            alertDialog.show();
        }

        private void atualizarLista(Context context) {
            dados = facade.listarAlunos(context);
            notifyDataSetChanged();
        }

        private int getId(){
            return dados.get(getLayoutPosition()).getId();
        }
    }
}
