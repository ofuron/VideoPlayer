package com.mroli.android.androidbootstrap;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.mroli.android.androidbootstrap.di.components.AppComponent;
import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

  @Inject Application mApplication;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }
}
