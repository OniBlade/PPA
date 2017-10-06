package clientrest.com.clientrest.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
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

import clientrest.com.clientrest.Frament.BlankFragment;
import clientrest.com.clientrest.Frament.FragmentConfiguracao;
import clientrest.com.clientrest.Frament.NotificationFragment_item;
import clientrest.com.clientrest.Frament.NotificationFrament;
import clientrest.com.clientrest.R;
import clientrest.com.clientrest.dummy.DummyContent;
import clientrest.com.clientrest.Service.MQTTService;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        NotificationFragment_item.OnListFragmentInteractionListener,
        NotificationFrament.OnFragmentInteractionListener,
        BlankFragment.OnListFragmentInteractionListener,
        FragmentConfiguracao.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            Fragment fragment = null;
            Class fragmentClass = null;
            fragmentClass = NotificationFragment_item.class;
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
                fragmentClass = NotificationFragment_item.class;
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
        if (id == R.id.action_settings) {
            FragmentConfiguracao mFragment = new FragmentConfiguracao();
            Bundle mBundle = new Bundle();
            mFragment.setArguments(mBundle);
            switchContent(R.id.content_main, mFragment,"FragmentConfiguracao");

            return true;
        }else{
             if (id == R.id.action_exit) {
                 finish();
             }
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
            swap = false;
        } else if (id == R.id.nav_gallery) {
            TAG = "Notification_Main";
            fragmentClass = NotificationFragment_item.class;
            mBundle.putInt("column-count", 0);
        } else if (id == R.id.nav_slideshow) {
            TAG ="FragmentConfiguracao";
            fragmentClass = FragmentConfiguracao.class;

        } else if (id == R.id.nav_manage) {
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
}
