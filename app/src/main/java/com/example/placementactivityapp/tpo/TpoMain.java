package com.example.placementactivityapp.tpo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.example.placementactivityapp.MainActivity;
import com.example.placementactivityapp.POSO.Tpo;
import com.example.placementactivityapp.R;
import com.example.placementactivityapp.admin.AdminMain;

public class TpoMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpo_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tpo_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            SharedPreferences sharedPreferences = getSharedPreferences("mypref1",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("email");
            editor.remove("adkey");
            editor.commit();
            startActivity(new Intent(TpoMain.this, MainActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.notification) {
            startActivity(new Intent(TpoMain.this,SaveNotification.class));

        } else if (id == R.id.company) {
            startActivity(new Intent(TpoMain.this,AddCompany.class));

        } else if (id == R.id.showstudent) {
            TShowStudent f2=new TShowStudent(); //User defined fragment class
            FragmentTransaction ft2=getSupportFragmentManager().beginTransaction();
            ft2.replace(R.id.fragment_container,f2);
            ft2.addToBackStack(null);
            ft2.commit();
        } else if (id == R.id.shownotification){
            ShowNotification f1=new ShowNotification(); //User defined fragment class
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container,f1);
            ft.addToBackStack(null);
            ft.commit();

        }else if(id==R.id.showcompany){
            ShowCompany f3=new ShowCompany(); //User defined fragment class
            FragmentTransaction ft3=getSupportFragmentManager().beginTransaction();
            ft3.replace(R.id.fragment_container,f3);
            ft3.addToBackStack(null);
            ft3.commit();
        }else if (id==R.id.selectstudent){
            ShowSeStudent f5=new ShowSeStudent(); //User defined fragment class
            FragmentTransaction ft5=getSupportFragmentManager().beginTransaction();
            ft5.replace(R.id.fragment_container,f5);
            ft5.addToBackStack(null);
            ft5.commit();

        }else if (id==R.id.pdf){
            startActivity(new Intent(TpoMain.this, AddPdf.class));
        }else if (id==R.id.showpdf){
            ShowPdf f4=new ShowPdf(); //User defined fragment class
            FragmentTransaction ft4=getSupportFragmentManager().beginTransaction();
            ft4.replace(R.id.fragment_container,f4);
            ft4.addToBackStack(null);
            ft4.commit();
        }else if (id==R.id.addimage){
            startActivity(new Intent(TpoMain.this, AddImage.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
