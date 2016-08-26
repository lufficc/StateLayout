package com.lcc.demo.statelayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.lufficc.stateLayout.FadeScaleViewAnimProvider;
import com.lufficc.stateLayout.FadeViewAnimProvider;
import com.lufficc.stateLayout.StateLayout;


public class MainActivity extends AppCompatActivity {

    StateLayout stateLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stateLayout = (StateLayout) findViewById(R.id.stateLayout);
        stateLayout.setViewSwitchAnimProvider(new FadeViewAnimProvider());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.error:
                stateLayout.showErrorView();
                break;
            case R.id.empty:
                stateLayout.showEmptyView();
                break;
            case R.id.progress:
                stateLayout.showProgressView();
                break;
            case R.id.content:
                stateLayout.showContentView();
                break;

            case R.id.fade:
                stateLayout.setViewSwitchAnimProvider(new FadeViewAnimProvider());
                break;
            case R.id.fade_scale:
                stateLayout.setViewSwitchAnimProvider(new FadeScaleViewAnimProvider());
                break;
        }
        return true;
    }
}
