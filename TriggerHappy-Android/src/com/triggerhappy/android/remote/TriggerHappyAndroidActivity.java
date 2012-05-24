package com.triggerhappy.android.remote;

import android.app.TabActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class TriggerHappyAndroidActivity extends TabActivity {
	

    private TabHost mTabHost;

    private void setupTabHost() {
            mTabHost = (TabHost) findViewById(android.R.id.tabhost);
            mTabHost.setup();
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // construct the tabhost
            setContentView(R.layout.main);

            setupTabHost();
            mTabHost.getTabWidget().setDividerDrawable(R.drawable.tab_divider);

            setupTab(new TextView(this), mTabHost.getContext().getString(R.string.tab_1));
            setupTab(new TextView(this), mTabHost.getContext().getString(R.string.tab_2));
            setupTab(new TextView(this), mTabHost.getContext().getString(R.string.tab_3));
    }

    private void setupTab(final View view, final String tag) {
            View tabview = createTabView(mTabHost.getContext(), tag);

            TabSpec setContent = mTabHost.newTabSpec(tag).setIndicator(tabview).setContent(new TabContentFactory() {
                    public View createTabContent(String tag) {return view;}
            });
            mTabHost.addTab(setContent);

    }

    private static View createTabView(final Context context, final String text) {
            View view = LayoutInflater.from(context).inflate(R.layout.tabs_bg, null);
            TextView tv = (TextView) view.findViewById(R.id.tabsText);
            tv.setText(text);
            return view;
    }
}