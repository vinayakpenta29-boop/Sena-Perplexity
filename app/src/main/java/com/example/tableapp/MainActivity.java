package com.example.tableapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tableLayout = findViewById(R.id.tableLayout);

        Button addRowBtn = findViewById(R.id.addRowBtn);
        addRowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRow();
            }
        });
    }

    private void addRow() {
        TableRow row = new TableRow(this);
        for(int i = 0; i < 4; i++) {
            EditText editText = new EditText(this);
            editText.setHint("Col " + (i+1));
            row.addView(editText);
        }
        tableLayout.addView(row);
    }
}
