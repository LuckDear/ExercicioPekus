package com.projeto.calculadorapekus;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class HistoricActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private ListView listViewHist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historic);

        listViewHist = findViewById(R.id.listViewHistory);
        db = new DatabaseHelper(this);

        Button btnVoltar = findViewById(R.id.btnVoltar);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HistoricActivity.this, MainActivity.class));
            }
        });

        loadHistory();
    }

    private void loadHistory() {
        Cursor cursor = db.getAllCalculos();
        ArrayList<String> calculos = new ArrayList<>();

        if (cursor != null && cursor.getCount() > 0){
            while (cursor.moveToNext()){
                int id = cursor.getInt(0);
                double valorA = cursor.getDouble(1);
                double valorB = cursor.getDouble(2);
                String operacao = cursor.getString(3);
                double resultado = cursor.getDouble(4);
                String dataHora = cursor.getString(5);

                String registro = "ID: " + id +
                "\nValor A: " + valorA +
                "\nValor B: " + valorB +
                "\nOperação: " + operacao +
                "\nResultado: " + resultado +
                "\nData/Hora: " + dataHora;
                calculos.add(registro);
            }
        }

        if (cursor != null){
            cursor.close();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,android.R.layout.simple_list_item_1,calculos
        );

        listViewHist.setAdapter(adapter);
    }
}