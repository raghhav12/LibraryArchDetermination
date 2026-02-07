package com.example.libraryarchitecturedetermination;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView outputText;
    private Button btn;
    private ActivityResultLauncher<Intent> folderPickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        outputText = findViewById(R.id.text_view1);
        btn = findViewById(R.id.btn1);
        folderPickerLauncher =
                registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        result -> {
                            if (result.getResultCode() == RESULT_OK
                                    && result.getData() != null) {

                                Uri uri = result.getData().getData();
                                getContentResolver().takePersistableUriPermission(
                                        uri,
                                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                                );

                                scanFolder(uri);
                            }
                        });
        btn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
            folderPickerLauncher.launch(intent);
        });
    }

    private void scanFolder(Uri uri) {
        DocumentFile folder =
                DocumentFile.fromTreeUri(this, uri);

        if (folder == null || !folder.isDirectory()) {
            outputText.setText("Invalid folder selected.");
            return;
        }
        List<LibraryScanner.LibInfo> libs =
                LibraryScanner.scanDocument(this, folder);
        StringBuilder result = new StringBuilder();
        result.append("Total number of libraries: ")
                .append(libs.size())
                .append("\n\n");
        for (LibraryScanner.LibInfo lib : libs) {
            result.append("File name -> ")
                    .append(lib.name)
                    .append(" || Arch type -> ")
                    .append(lib.arch)
                    .append("\n");
        }
        outputText.setText(result.toString());
    }
}