package com.triggerhappy.android.remote;

import android.app.Activity;
import android.os.Bundle;
import com.triggerhappy.android.R;

public class BasicTriggerActivity extends Activity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic);
    }
}