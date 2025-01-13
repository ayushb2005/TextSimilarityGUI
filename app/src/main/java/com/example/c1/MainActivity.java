package com.example.c1;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.io.InputStream;
import java.util.ArrayList;
import android.widget.ScrollView;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final int PICK_FILES_REQUEST = 1;

    private RecyclerView fileRecyclerView;
    private FileAdapter fileAdapter;
    private ArrayList<String> fileNames;
    private ArrayList<Passage> passages;
    private TextView similaritiesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fileNames = new ArrayList<>();
        passages = new ArrayList<>();
        fileRecyclerView = findViewById(R.id.fileRecyclerView);
        fileRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        fileAdapter = new FileAdapter(fileNames);
        fileRecyclerView.setAdapter(fileAdapter);

        similaritiesTextView = findViewById(R.id.similaritiesTextView);

        Button selectFileButton = findViewById(R.id.selectFilesButton);
        selectFileButton.setOnClickListener(view -> openFilePicker());

        Button findSimilaritiesButton = findViewById(R.id.findSimilaritiesButton);
        findSimilaritiesButton.setOnClickListener(view -> findSimilarities());

        ScrollView scrollView = findViewById(R.id.scrollView);
        scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
    }

    private void findSimilarities() {
        if (passages.size() < 2) {
            similaritiesTextView.setText("Please select at least two files to compare.");
            return;
        }

        String percentageStr = ((EditText) findViewById(R.id.percentageEditText)).getText().toString();
        double similarityThreshold;

        try {
            similarityThreshold = Double.parseDouble(percentageStr) / 100.0;
        } catch (NumberFormatException e) {
            similaritiesTextView.setText("Invalid percentage value.");
            return;
        }

        TextAnalyzer textAnalyzer = new TextAnalyzer();
        String result = textAnalyzer.analyzeText(similarityThreshold, passages);

        similaritiesTextView.setText(result);
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Select Files"), PICK_FILES_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILES_REQUEST && resultCode == RESULT_OK && data != null) {
            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri fileUri = data.getClipData().getItemAt(i).getUri();
                    handleFileSelection(fileUri);
                }
            } else if (data.getData() != null) {
                Uri fileUri = data.getData();
                handleFileSelection(fileUri);
            }
        }
    }

    private void handleFileSelection(Uri fileUri) {
        String fileName = getFileName(fileUri);
        int chop = fileName.indexOf(".");
        fileName = fileName.substring(0, chop);
        fileNames.add(fileName);
        if (fileName.equals("StopWords.txt")) {
            Log.d("MainActivity", "Skipping StopWords.txt");
            return;
        }
        fileAdapter.notifyDataSetChanged();

        try {
            InputStream inputStream = getContentResolver().openInputStream(fileUri);
            if (inputStream != null) {
                Passage passage = new Passage(this, fileName, fileUri);
                passages.add(passage);
                Log.d("MainActivity", "File selected: " + fileName);
            }
        } catch (Exception e) {
            Log.e("MainActivity", "Error reading file: " + fileName, e);
        }
    }

    private String getFileName(Uri uri) {
        String fileName = "Unknown File";
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            if (cursor.moveToFirst()) {
                fileName = cursor.getString(nameIndex);
            }
            cursor.close();
        }
        return fileName;
    }
}
