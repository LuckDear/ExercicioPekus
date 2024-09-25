package com.projeto.calculadorapekus;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText editTextValorA, editTextValorB;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextValorA = findViewById(R.id.editTextValorA);
        editTextValorB = findViewById(R.id.editTextValorB);
        Button btnSoma = findViewById(R.id.btnSoma);
        Button btnSubtracao = findViewById(R.id.btnSubtracao);
        Button btnMultiplicacao = findViewById(R.id.btnMultiplicacao);
        Button btnDivisao = findViewById(R.id.btnDivisao);
        Button btnVerHistorico = findViewById(R.id.btnVerHistorico);

        db = new DatabaseHelper(this);

        btnSoma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarOperacao("+");
            }
        });

        btnSubtracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarOperacao("-");
            }
        });

        btnMultiplicacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarOperacao("*");
            }
        });

        btnDivisao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarOperacao("/");
            }
        });

        btnVerHistorico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HistoricActivity.class));
            }
        });
    }

    private void realizarOperacao(String operacao) {
        String valorA = editTextValorA.getText().toString();
        String valorB = editTextValorB.getText().toString();

        if (TextUtils.isEmpty(valorA) || TextUtils.isEmpty(valorB)) {
            Toast.makeText(this, "Por favor, insira ambos os valores.", Toast.LENGTH_SHORT).show();
            return;
        }

        double a = Double.parseDouble(valorA);
        double b = Double.parseDouble(valorB);
        double resultado = 0;

        switch (operacao) {
            case "+":
                resultado = a + b;
                break;
            case "-":
                resultado = a - b;
                break;
            case "*":
                resultado = a * b;
                break;
            case "/":
                if (b == 0) {
                    Toast.makeText(this, "Divisão por zero não é permitida.", Toast.LENGTH_SHORT).show();
                    return;
                }
                resultado = a / b;
                break;
        }

        String dataHora = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
        db.insertCalculo(a, b, operacao, resultado, dataHora);

        Toast.makeText(this, "Cálculo armazenado com sucesso!", Toast.LENGTH_SHORT).show();

        editTextValorA.setText("");
        editTextValorB.setText("");
    }
}