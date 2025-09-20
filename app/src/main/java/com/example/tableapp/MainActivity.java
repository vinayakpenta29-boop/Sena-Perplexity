package com.example.tableapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TableLayout tableLayout;
    int rowCount = 0;
    int columnCount = 4; // Initial columns

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tableLayout = findViewById(R.id.tableLayout);

        Button addRowBtn = findViewById(R.id.addRowBtn);
        Button addColumnBtn = findViewById(R.id.addColumnBtn);

        // Header row with Edit buttons
        renderHeaderRow();

        addRowBtn.setOnClickListener(v -> showAddDialog(true));
        addColumnBtn.setOnClickListener(v -> showAddDialog(false));
    }

    // Header with Edit buttons for columns
    private void renderHeaderRow() {
        TableRow headerRow = new TableRow(this);
        for (int i = 0; i < columnCount; i++) {
            LinearLayout headerCell = new LinearLayout(this);
            headerCell.setOrientation(LinearLayout.VERTICAL);
            headerCell.setGravity(Gravity.CENTER);

            Button editBtn = new Button(this);
            editBtn.setText("Edit");
            int finalI = i;
            editBtn.setOnClickListener(v -> showColumnEditDialog(finalI));
            editBtn.setPadding(4,4,4,4);

            TextView colLabel = new TextView(this);
            colLabel.setText("Col " + (i+1));
            colLabel.setGravity(Gravity.CENTER);

            headerCell.addView(editBtn);
            headerCell.addView(colLabel);

            headerRow.addView(headerCell);
        }
        // Remove current header if present (always keep at row 0)
        if (tableLayout.getChildCount() > 0)
            tableLayout.removeViewAt(0);
        tableLayout.addView(headerRow, 0);
    }

    // Show dialog: asks for number of rows/columns
    private void showAddDialog(final boolean addRow) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(addRow ? "Number of Rows" : "Number of Columns");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            int num = 1;
            try { num = Integer.parseInt(input.getText().toString()); } catch (Exception ignored) {}
            if (addRow) {
                for (int i = 0; i < num; i++) addRow();
            } else {
                for (int i = 0; i < num; i++) addColumn();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    // Add one row below header
    private void addRow() {
        TableRow row = new TableRow(this);
        for(int i = 0; i < columnCount; i++) {
            EditText editText = new EditText(this);
            editText.setBackgroundResource(R.drawable.cell_border);
            editText.setHint("Col " + (i+1));
            editText.setLayoutParams(new TableRow.LayoutParams(220, TableRow.LayoutParams.WRAP_CONTENT));
            row.addView(editText);
        }
        tableLayout.addView(row);
        rowCount++;
    }

    // Add new column to all rows
    private void addColumn() {
        columnCount++;
        // Update header row
        renderHeaderRow();

        // Add EditText to each TableRow except header
        for (int i = 1; i < tableLayout.getChildCount(); i++) {
            TableRow row = (TableRow) tableLayout.getChildAt(i);
            EditText editText = new EditText(this);
            editText.setBackgroundResource(R.drawable.cell_border);
            editText.setHint("Col " + columnCount);
            editText.setLayoutParams(new TableRow.LayoutParams(220, TableRow.LayoutParams.WRAP_CONTENT));
            row.addView(editText);
        }
    }

    // Edit column width/height for all rows in column [colIndex]
    private void showColumnEditDialog(final int colIndex) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Column " + (colIndex+1) + " Size");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText widthInput = new EditText(this);
        widthInput.setHint("Width (dp)");

        final EditText heightInput = new EditText(this);
        heightInput.setHint("Height (dp)");

        layout.addView(widthInput);
        layout.addView(heightInput);

        builder.setView(layout);

        builder.setPositiveButton("OK", (dialog, which) -> {
            int w = 220;
            int h = TableRow.LayoutParams.WRAP_CONTENT;
            try { w = Integer.parseInt(widthInput.getText().toString()); } catch (Exception ignored) {}
            try { h = Integer.parseInt(heightInput.getText().toString()); } catch (Exception ignored) {}

            // Update all rows in column
            for (int i = 1; i < tableLayout.getChildCount(); i++) {
                TableRow row = (TableRow) tableLayout.getChildAt(i);
                if (row.getChildCount() > colIndex) {
                    EditText cell = (EditText) row.getChildAt(colIndex);
                    cell.setLayoutParams(new TableRow.LayoutParams(w, h));
                }
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }
}
