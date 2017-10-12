package clientrest.com.clientrest.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import clientrest.com.clientrest.DataBase.Entity.HistoryObject;
import clientrest.com.clientrest.Frament.Configuration_Fragment;
import clientrest.com.clientrest.Frament.History_List_Fragment;
import clientrest.com.clientrest.Frament.Request_Fragment;
import clientrest.com.clientrest.Frament.Request_List_Fragment;
import clientrest.com.clientrest.R;
import clientrest.com.clientrest.dummy.DummyContent;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        Request_List_Fragment.OnListFragmentInteractionListener,
        Request_Fragment.OnListFragmentInteractionListener,
        History_List_Fragment.OnListFragmentInteractionListener,
        Configuration_Fragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            Fragment fragment = null;
            Class fragmentClass = null;
            fragmentClass = Request_List_Fragment.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();

            } catch (Exception e) {
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                Class fragmentClass;
                fragmentClass = Request_List_Fragment.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                switchContent(R.id.content_main, fragment, "Notification_Main");
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        if (id == R.id.action_exit) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Bundle mBundle = new Bundle();
        String TAG = null;
        Boolean swap = true;
        Fragment fragment = null;
        Class fragmentClass = null;
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            TAG = "Notification_Main";
            fragmentClass = Request_List_Fragment.class;
            mBundle.putInt("column-count", 0);

        } else if (id == R.id.nav_gallery) {
            swap = false;

        } else if (id == R.id.nav_inferred_mechanism) {
            TAG = "History_List_Fragment";
            fragmentClass = History_List_Fragment.class;
            mBundle.putInt("column-count", 0);
            mBundle.putInt("CODE", 0);
        } else if (id == R.id.nav_inferred_user) {
            TAG = "History_List_Fragment";
            fragmentClass = History_List_Fragment.class;
            mBundle.putInt("column-count", 0);
            mBundle.putInt("CODE", 1);

        } else if (id == R.id.nav_slideshow) {
            TAG = "Configuration_Fragment";
            fragmentClass = Configuration_Fragment.class;

        } else if (id == R.id.nav_manage) {
            Intent mainIntent = new Intent(MainActivity.this, SettingsActivity.class);
            MainActivity.this.startActivity(mainIntent);
            MainActivity.this.finish();
            swap = false;

        } else if (id == R.id.nav_share) {
            swap = false;

        } else if (id == R.id.nav_send) {

            swap = false;
        }
        if (swap) {
            try {
                fragment = (Fragment) fragmentClass.newInstance();
                fragment.setArguments(mBundle);
            } catch (Exception e) {
                e.printStackTrace();
            }
            switchContent(R.id.content_main, fragment, TAG);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void switchContent(int id, Fragment fragment, String tag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(id, fragment, tag);
        if (!tag.equals("Notification_Main"))
            ft.addToBackStack(null);
        ft.commit();
    }


    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(HistoryObject item) {

    }
}
