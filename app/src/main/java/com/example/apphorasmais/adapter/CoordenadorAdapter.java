package com.example.apphorasmais.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apphorasmais.model.facade.Facade;
import com.example.apphorasmais.R;

import androidx.recyclerview.widget.RecyclerView;
import com.example.apphorasmais.model.entity.Coordenador;
import com.example.apphorasmais.EditarUsuario;

import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class CoordenadorAdapter extends RecyclerView.Adapter<CoordenadorAdapter.ViewHolderCoordenador> {

    private List<Coordenador> dados;

    public CoordenadorAdapter(List<Coordenador> dados) {
        this.dados = dados;
    }

    @Override
    public ViewHolderCoordenador onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_card_usuario, parent, false);

        ViewHolderCoordenador viewHolderCoordenador = new ViewHolderCoordenador(view, parent.getContext());

        return viewHolderCoordenador;
    }

    @Override
    public void onBindViewHolder(ViewHolderCoordenador holder, int position) {
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

    public class ViewHolderCoordenador extends RecyclerView.ViewHolder {

        private TextView nome;
        private TextView usuario;
        private ImageView editar;
        private ImageView excluir;
        private Facade facade;

        public ViewHolderCoordenador(View itemView, final Context context) {
            super(itemView);
            facade = new Facade();
            nome = (TextView) itemView.findViewById(R.id.cardUsuarioNome);
            usuario = (TextView) itemView.findViewById(R.id.cardUsuarioUsuario);
            editarCoordenador(itemView, context);
            excluirCoordenador(context);
        }

        private void editarCoordenador(View itemView, Context context) {
            editar = (ImageView) itemView.findViewById(R.id.cardUsuarioEditar);
            editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, EditarUsuario.class);
                    i.putExtra("COORDENADOR", dados.get(getLayoutPosition()));
                    context.startActivity(i);
                }
            });
        }

        private void excluirCoordenador(Context context) {
            excluir = (ImageView) itemView.findViewById(R.id.cardUsuarioExcluir);
            excluir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    facade.excluirCoordenador(context, getId());
                    atualizarLista(context);
                    Toast.makeText(context, "Coordenador "+nome.getText()+" deletado", Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void atualizarLista(Context context){
            dados = facade.listarCoordenadores(context);
            notifyDataSetChanged();
        }

        private int getId(){
            return dados.get(getLayoutPosition()).getId();
        }
    }
}
