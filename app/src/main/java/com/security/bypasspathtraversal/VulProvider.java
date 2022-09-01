package com.security.bypasspathtraversal;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class VulProvider extends ContentProvider {

    @Nullable
    @Override
    public ParcelFileDescriptor openFile(@NonNull Uri uri, @NonNull String mode) throws FileNotFoundException {
        File root = getContext().getExternalFilesDir("sandbox");
        String tag = uri.getQueryParameter("tag");
        String path = uri.getQueryParameter("path");

        switch (tag){
            case "task1":
                return ParcelFileDescriptor.open(new File(root, path), ParcelFileDescriptor.MODE_READ_ONLY);

            case "task2":
                return ParcelFileDescriptor.open(new File(root, uri.getLastPathSegment()), ParcelFileDescriptor.MODE_READ_ONLY);
            // The next task needs to set `setenforce 0`
            case "task3":
                File file3 = new File(path);
                File internalDir = getContext().getFilesDir();
                try {
//                    if (path.contains("..") || file3.getCanonicalPath().startsWith(internalDir.getCanonicalPath())) {
                    if (path.contains("..") || path.startsWith(internalDir.getCanonicalPath())) {
                        throw new IllegalArgumentException();
                    }
                } catch (IOException e){
                    throw new IllegalArgumentException();
                }
                return ParcelFileDescriptor.open(file3, ParcelFileDescriptor.MODE_READ_ONLY);
            case "task4":
                // Excerpt from: https://support.google.com/faqs/answer/7496913
                File file4 = new File(getContext().getExternalFilesDir("sandbox"), uri.getLastPathSegment());
                try {
                    if (!file4.getCanonicalPath().startsWith(root.getCanonicalPath())) {
                        throw new IllegalArgumentException();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return ParcelFileDescriptor.open(file4, ParcelFileDescriptor.MODE_READ_ONLY);
            case "task5":
                // This is the correct example
                File file5 = new File(getContext().getExternalFilesDir("sandbox"), uri.getLastPathSegment());
                try {
                    file5 = file5.getCanonicalFile();
                    if (!file5.getPath().startsWith(root.getCanonicalPath())) {
                        throw new IllegalArgumentException();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return ParcelFileDescriptor.open(file5, ParcelFileDescriptor.MODE_READ_ONLY);
        }
        return null;
    }



    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
