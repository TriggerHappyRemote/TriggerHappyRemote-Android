package com.triggerhappy.android.view;

import android.os.Bundle;
import android.widget.TextView;

import com.triggerhappy.android.R;
import com.triggerhappy.android.common.TriggerHappyNavigation;

public class AboutTriggerHappyActvity extends TriggerHappyNavigation{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        ((TextView)findViewById(R.id.info_text)).setText(R.string.about);        
    }
}
