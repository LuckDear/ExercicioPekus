package com.projeto.calculadorapekus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "calculadora.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "calculos";
    private static final String COL_ID = "ID";
    private static final String COL_VALOR_A = "ValorA";
    private static final String COL_VALOR_B = "ValorB";
    private static final String COL_OPERACAO = "Operacao";
    private static final String COL_RESULTADO = "Resultado";
    private static final String COL_DATA_HORA = "DataHora";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_VALOR_A + " REAL, " +
                COL_VALOR_B + " REAL, " +
                COL_OPERACAO + " TEXT, " +
                COL_RESULTADO + " REAL, " +
                COL_DATA_HORA + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertCalculo(double valorA, double valorB, String operacao, double resultado, String dataHora) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_VALOR_A, valorA);
        contentValues.put(COL_VALOR_B, valorB);
        contentValues.put(COL_OPERACAO, operacao);
        contentValues.put(COL_RESULTADO, resultado);
        contentValues.put(COL_DATA_HORA, dataHora);

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public Cursor getAllCalculos() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
}