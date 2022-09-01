package com.security.bypasspathtraversal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File externalFile = new File(getExternalFilesDir("sandbox"), "file1");
        File internalFile = new File(getFilesDir(), "file2");

        try {
            IOUtils.write("I'm in external\n", new FileOutputStream(externalFile));
            IOUtils.write("I'm in internal\n", new FileOutputStream(internalFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}