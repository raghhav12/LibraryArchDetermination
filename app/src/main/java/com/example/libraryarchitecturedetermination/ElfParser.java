package com.example.libraryarchitecturedetermination;

import android.content.Context;
import android.net.Uri;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ElfParser {

    public static final int EM_ARM = 40;
    public static final int EM_AARCH64 = 183;
    public static final int EM_386 = 3;
    public static final int EM_X86_64 = 62;
    public static final int EM_MIPS = 8;

    public static String detectArchFromUri(Context context, Uri uri) {

        try (InputStream fis =
                     context.getContentResolver().openInputStream(uri)) {

            if (fis == null) return null;

            byte[] header = new byte[20];
            if (fis.read(header) < 20) return null;

            if (header[0] != 0x7F || header[1] != 'E' ||
                    header[2] != 'L' || header[3] != 'F') {
                return null;
            }

            boolean littleEndian = header[5] == 1;
            int eMachine;

            if (littleEndian) {
                eMachine = ((header[19] & 0xFF) << 8) | (header[18] & 0xFF);
            } else {
                eMachine = ((header[18] & 0xFF) << 8) | (header[19] & 0xFF);
            }

            switch (eMachine) {
                case EM_ARM:
                    return "armeabi-v7a";
                case EM_AARCH64:
                    return "arm64-v8a";
                case EM_386:
                    return "x86";
                case EM_X86_64:
                    return "x86-64";
                case EM_MIPS:
                    return "mips";
                default:
                    return "unknown";
            }

        } catch (Exception e) {
            return null;
        }
    }
}
