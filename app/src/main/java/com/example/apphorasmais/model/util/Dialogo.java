package com.example.apphorasmais.model.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class Dialogo {

    public boolean alertaDialogo(Context context, String tipo) {
        AlertDialog alertDialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Atenção");
        builder.setMessage("Deseja mesmo excluir"+ tipo + "selecionado");
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.setNegativeButton("Cancelar", null);
        alertDialog = builder.create();
        alertDialog.show();
        return false;
    }

}
