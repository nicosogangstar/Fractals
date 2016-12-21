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

    private int fractalType, colorType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_viewer);

        fractalView = new MyGLSurfaceView(this, 0, 0);

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
                fractalDialog();
                break;
            case R.id.nav_brush:
                colorDialog();
                break;
            case R.id.nav_palette:
                break;
        }

        setTitle(menuItem.getTitle());
        drawerLayout.closeDrawers();
    }

    public void fractalDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick a fractal")
                .setItems(R.array.fractals, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        fractalType = which;
                        updateFractal();
                    }
                });
        builder.create().show();
    }

    public void colorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick a coloring algorithm")
                .setItems(R.array.colortypes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        colorType = which;
                        updateFractal();
                    }
                });
        builder.create().show();
    }

    public void updateFractal() {
        linearLayout.removeAllViews();
        fractalView = new MyGLSurfaceView(getApplicationContext(), fractalType, colorType);
        linearLayout.addView(fractalView);
    }
}