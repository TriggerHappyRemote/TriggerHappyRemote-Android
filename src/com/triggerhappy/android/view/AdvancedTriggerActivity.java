package com.triggerhappy.android.view;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.triggerhappy.android.R;
import com.triggerhappy.android.common.TriggerHappyNavigation;

public class AdvancedTriggerActivity extends TriggerHappyNavigation{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bramp);
        
        
		Spinner spin = (Spinner) findViewById(R.id.spinner1);
		
        ArrayAdapter<CharSequence> list = ArrayAdapter.createFromResource(getBaseContext(), R.array.Progression, spin.getId());
        list.setDropDownViewResource(spin.getId());

        this.initNavigation(1);
    }
}
