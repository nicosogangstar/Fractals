package com.nico.gonzo.fractals;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;

public class ViewerActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    LinearLayout linearLayout;

    MyGLSurfaceView fractalView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_viewer);

        fractalView = new MyGLSurfaceView(this, 0);

        linearLayout = (LinearLayout) findViewById(R.id.fractalView);
        linearLayout.addView(fractalView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        setupDrawerContent((NavigationView) findViewById(R.id.nvView));
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    selectDrawerItem(menuItem);
                    return true;
                }
            }
        );
    }

    public void selectDrawerItem(MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.nav_functions:
                createDialog();
                break;
            case R.id.nav_brush:
                break;
            case R.id.nav_palette:
                break;
        }

        setTitle(menuItem.getTitle());
        drawerLayout.closeDrawers();
    }

    public void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick a fractal")
                .setItems(R.array.fractals, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        linearLayout.removeAllViews();
                        fractalView = new MyGLSurfaceView(getApplicationContext(), which);
                        linearLayout.addView(fractalView);
                    }
                });
        builder.create().show();
    }
}