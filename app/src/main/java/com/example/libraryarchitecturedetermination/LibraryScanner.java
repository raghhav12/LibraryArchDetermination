package com.example.libraryarchitecturedetermination;

import android.content.Context;

import androidx.documentfile.provider.DocumentFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LibraryScanner {

    public static class LibInfo {
        public String name;
        public String arch;

        public LibInfo(String name, String arch) {
            this.name = name;
            this.arch = arch;
        }
    }

    public static List<LibInfo> scanDocument(Context context, DocumentFile dir) {
        List<LibInfo> result = new ArrayList<>();
        scanRecursive(context, dir, result);
        return result;
    }

    private static void scanRecursive(Context context, DocumentFile dir, List<LibInfo> result) {
        if (dir == null || !dir.isDirectory()) return;

        for (DocumentFile f : dir.listFiles()) {

            if (f.isDirectory()) {
                scanRecursive(context, f, result);

            } else if (f.getName() != null &&
                    f.getName().endsWith(".so")) {

                String arch =
                        ElfParser.detectArchFromUri(context, f.getUri());

                if (arch != null && !arch.equals("unknown")) {
                    result.add(new LibInfo(f.getName(), arch));
                }
            }
        }
    }
}
