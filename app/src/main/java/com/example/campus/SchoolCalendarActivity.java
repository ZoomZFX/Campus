package com.example.campus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

/**
 * 校历界面
 */

public class SchoolCalendarActivity extends AppCompatActivity {

    public ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_calendar);

        init();
    }

    public void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("校历");
        setSupportActionBar(toolbar);

        setSupportActionBar(toolbar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    finish();
                }
            });
        }
        imageView = (ImageView) findViewById(R.id.piv_calan);
        imageView.setImageResource(R.drawable.xl1);
    }

    /**
     * 三点菜单
     *
     * @param menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.xl_1) {
            imageView.setImageResource(R.drawable.xl1);
            return true;
        }else if(id == R.id.xl_2){
            imageView.setImageResource(R.drawable.xl2);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
