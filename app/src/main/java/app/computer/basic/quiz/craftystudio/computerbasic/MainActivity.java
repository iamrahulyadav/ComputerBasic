package app.computer.basic.quiz.craftystudio.computerbasic;

import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import utils.FireBaseHandler;
import utils.KeyBoardShortcut;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private CardView mBasicComputerCardview, mShortKey, mMsExcel, mMsWord, mPowerPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                KeyBoardShortcut keyBoardShortcut = new KeyBoardShortcut();
                keyBoardShortcut.setShortKeyCode("ceg ");
                keyBoardShortcut.setShortKeyExplaination("hefih");
                FireBaseHandler fireBaseHandler = new FireBaseHandler();
                fireBaseHandler.uploadShortKeys(keyBoardShortcut, "Basic Shortcut Keys", new FireBaseHandler.OnDatalistener() {
                    @Override
                    public void onDataDownLoad(String itemData, boolean isSuccessful) {

                    }

                    @Override
                    public void onShortkeyDownload(ArrayList<KeyBoardShortcut> keyBoardShortcut, boolean isSuccessful) {

                    }

                    @Override
                    public void onDataUpload(boolean isSuccessful) {

                        if (isSuccessful) {
                            Toast.makeText(MainActivity.this, "Key Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mBasicComputerCardview = (CardView) findViewById(R.id.main_computer_cardview);
        mShortKey = (CardView) findViewById(R.id.main_shortkey_cardview);
        mMsExcel = (CardView) findViewById(R.id.main_msexcel_cardview);
        mMsWord = (CardView) findViewById(R.id.main_msword_cardview);
        mPowerPoint = (CardView) findViewById(R.id.main_powerpoint_cardview);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent = new Intent(MainActivity.this, ShortKeysActivity.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openBasicComputer(View view) {

        ArrayList<String> mArrayList = new ArrayList<>();
        mArrayList.add("INTRODUCTION");
        mArrayList.add("GENERATION");
        mArrayList.add("TYPES OF COMPUTER");
        mArrayList.add("COMPONENTS");
        mArrayList.add("MEMORY");
        mArrayList.add("OPERATING SYSTEM");
        mArrayList.add("INPUT & OUTPUT DEVICES");
        mArrayList.add("HARDWARE & SOFTWARE COMPONENT");
        mArrayList.add("INTERNET & INTRANET");


        Intent intent = new Intent(MainActivity.this, DiplayListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("ArrayList", mArrayList);
        bundle.putString("Name", "Basic Computer");
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void openShortKey(View view) {
        Intent intent = new Intent(MainActivity.this, ShortKeysActivity.class);
        startActivity(intent);

    }

    public void openMsExcel(View view) {

        ArrayList<String> mArrayList = new ArrayList<>();
        mArrayList.add("MS EXCEL BASIC");
        mArrayList.add("GETTING STARTED");
        mArrayList.add("ROW & COLUMN BASIC");
        mArrayList.add("INSERT DATA");
        mArrayList.add("WORKSHEET OPTIONS");
        mArrayList.add("FORMATTING CELL");
        mArrayList.add("FORMULA & FUNCTION");
        mArrayList.add("DATA FILTERING");
        mArrayList.add("DATA SORTING");
        mArrayList.add("DATA VALIDATION");
        mArrayList.add("CHARTS");


        Intent intent = new Intent(MainActivity.this, DiplayListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("ArrayList", mArrayList);
        bundle.putString("Name", "MsExcel");
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void openMsWord(View view) {
    }

    public void openPowerPoint(View view) {
    }
}
