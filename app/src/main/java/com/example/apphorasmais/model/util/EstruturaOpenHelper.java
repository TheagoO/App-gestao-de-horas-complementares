package com.example.apphorasmais.model.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Thiago Ferreira Assumpção
 */

public class EstruturaOpenHelper extends SQLiteOpenHelper {

    private static final String NOME = "apphorasmais.db";
    private static final int VERSION = 1;

    public EstruturaOpenHelper(Context context) {
        super(context, NOME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(ScriptDLL.getTableCoordenador());
        sqLiteDatabase.execSQL(ScriptDLL.getTableCurso());
        sqLiteDatabase.execSQL(ScriptDLL.getTableEscopo());
        sqLiteDatabase.execSQL(ScriptDLL.getTablePeriodoLetivo());
        sqLiteDatabase.execSQL(ScriptDLL.getTableHorasComplementares());
        sqLiteDatabase.execSQL(ScriptDLL.getTableTurma());
        sqLiteDatabase.execSQL(ScriptDLL.getTableAluno());
        sqLiteDatabase.execSQL(ScriptDLL.getTableSituacao());
        sqLiteDatabase.execSQL(ScriptDLL.getTableAtividade());
        sqLiteDatabase.execSQL(ScriptDLL.getTableAtividadeComplementar());
        sqLiteDatabase.execSQL(ScriptDLL.getTableRequerimento());
        sqLiteDatabase.execSQL(ScriptDLL.getTableSolicitcao());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int old, int newVersion) {
        sqLiteDatabase.execSQL(ScriptDLL.getDropTableCoordenador());
        sqLiteDatabase.execSQL(ScriptDLL.getDropTableCurso());
        sqLiteDatabase.execSQL(ScriptDLL.getDropTableEscopo());
        sqLiteDatabase.execSQL(ScriptDLL.getDropTablePeriodoLetivo());
        sqLiteDatabase.execSQL(ScriptDLL.getDropTableHorasComplementares());
        sqLiteDatabase.execSQL(ScriptDLL.getDropTableTurma());
        sqLiteDatabase.execSQL(ScriptDLL.getDropTableAluno());
        sqLiteDatabase.execSQL(ScriptDLL.getDropTableSituacao());
        sqLiteDatabase.execSQL(ScriptDLL.getDropTableAtividade());
        sqLiteDatabase.execSQL(ScriptDLL.getDropTableAtividadeComplementar());
        sqLiteDatabase.execSQL(ScriptDLL.getDropTableRequerimento());
        sqLiteDatabase.execSQL(ScriptDLL.getDropTableSolicitcao());
        onCreate(sqLiteDatabase);
    }
}
