package com.example.schoolrun.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolrun.R;
import com.google.android.material.snackbar.Snackbar;

public class TestMeAc extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.me);

        button=findViewById(R.id.button3);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(button, "测试我的功能部件是否绑定成功", Snackbar.LENGTH_LONG).show();
            }
        });

    }
}
