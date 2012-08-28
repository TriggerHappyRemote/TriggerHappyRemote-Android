package com.triggerhappy.android.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;

import com.triggerhappy.android.R;
import com.triggerhappy.android.common.TriggerHappyNavigation;

public class AboutTriggerHappyActvity extends TriggerHappyNavigation{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        ((TextView)findViewById(R.id.info_text)).setText(R.string.about);
        
        setTitle("What is Trigger Happy?");
        
        Button btn = (Button)findViewById(R.id.about_BuyTriggerHappy);
        btn.setOnTouchListener(OnTouchListener());
        
        btn = (Button)findViewById(R.id.about_LearnMore);
        btn.setOnTouchListener(OnTouchListener());
        
        btn = (Button)findViewById(R.id.about_CloseWindow);
        btn.setOnTouchListener(OnTouchListener());
        
    }

	private OnTouchListener OnTouchListener() {
		return new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				
				if(event.getAction() == 1)
				{
					switch(v.getId()){
						case R.id.about_LearnMore:
						{
							Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.triggerhappyremote.com"));
							startActivity(intent);
							break;
						}
						
						case R.id.about_BuyTriggerHappy:
						{
							Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.triggerhappyremote.com/Pages/TriggerHappy-Canon-DSLR-Remote/22724945_x8gMDh"));
							startActivity(intent);
							break;
						}
						
						case R.id.about_CloseWindow:
						{
							finish();
							break;
						}
					}
				}
				return false;
			}
		};
	};
}
