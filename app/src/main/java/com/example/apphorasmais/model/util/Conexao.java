package com.example.apphorasmais.model.util;

import android.app.AlertDialog;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

/**
 * @author Thiago Ferreira Assumpção
 */

public class Conexao {

    public static SQLiteDatabase estartaConexao(Context context) {
        SQLiteDatabase conexao;
        EstruturaOpenHelper estruturaOpenHelper;

        try{
            estruturaOpenHelper = new EstruturaOpenHelper(context);

            return conexao = estruturaOpenHelper.getWritableDatabase();
        }catch(SQLException sqlException){
            AlertDialog.Builder adlg = new AlertDialog.Builder(context);
            adlg.setTitle("Erro");
            adlg.setMessage(sqlException.getMessage());
            adlg.setNeutralButton("OK", null);
            adlg.show();
        }
        return null;
    }

}
