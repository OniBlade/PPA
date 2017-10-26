package clientrest.com.clientrest.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import clientrest.com.clientrest.DataBase.DAO.DBHelper;
import clientrest.com.clientrest.DataBase.Entity.HistoryObject;
import clientrest.com.clientrest.DataBase.Entity.Scenarios;
import clientrest.com.clientrest.Frament.Configuration_Fragment;
import clientrest.com.clientrest.Frament.History_List_Fragment;
import clientrest.com.clientrest.Frament.Request_Fragment;
import clientrest.com.clientrest.Frament.Request_List_Fragment;
import clientrest.com.clientrest.R;
import clientrest.com.clientrest.Service.MLP;
import clientrest.com.clientrest.dummy.DummyContent;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        Request_List_Fragment.OnListFragmentInteractionListener,
        Request_Fragment.OnListFragmentInteractionListener,
        History_List_Fragment.OnListFragmentInteractionListener,
        Configuration_Fragment.OnFragmentInteractionListener {

    private TextView tvScenarios;
    private Context context;
    private Button btnAutorizar, btnNegar, btnNegociar, btnContinue;
    private boolean gambi = false;

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


        context = getBaseContext();
        FirstTimeApp();

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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_exit) {
            gambi=true;
            FecharDialog();
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


    private void FecharDialog() {
        if (gambi) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Fechar Applicação")
                    .setMessage("Deseja realmente fechar?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //Stop the activity
                            MainActivity.this.finish();
                        }

                    })
                    .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            gambi=false;
                        }
                    })
                    .show();

        }
    }

    private void DialogList(List<Scenarios> scenariosList, int cont) {
        if (cont < scenariosList.size()) {
            final Dialog dialog = onCreateDialogPers(R.layout.activity_privacy_training);
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
            InitializesComponents(dialog, scenariosList, cont);
            dialog.setOnKeyListener(new Dialog.OnKeyListener() {

                @Override
                public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
                    // TODO Auto-generated method stub
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        Log.e("DialogList", "DialogList");
                        if (!gambi) {
                            gambi=true;
                            FecharDialog();

                        }
                    }
                    return true;
                }
            });
        }else{
            MLP mlp = new MLP(context);
            mlp.RetrainMLP();
        }
    }

    private void FirstTimeApp() {
        final List<Scenarios> scenariosList = getScenarios();
        if (scenariosList.size() > 0) {
            final Dialog dialog = onCreateDialogPers(R.layout.welcome_dialog);
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);

            dialog.setOnKeyListener(new Dialog.OnKeyListener() {

                @Override
                public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        Log.e("First", "FirstTimeAPP");
                        if (!gambi) {
                            gambi=true;
                            FecharDialog();

                        }
                    }
                    return true;
                }
            });

            btnContinue = dialog.findViewById(R.id.btnContinue);

            btnContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int cont = 0;
                    DialogList(scenariosList, cont);
                    dialog.dismiss();
                }
            });
        }
    }


    private void InitializesComponents(final Dialog dialog, final List<Scenarios> scenariosList, final int cont) {

        btnAutorizar = dialog.findViewById(R.id.btnAutorizar);
        btnNegar = dialog.findViewById(R.id.btnNegar);
        btnNegociar = dialog.findViewById(R.id.btnNegociar);
        tvScenarios = dialog.findViewById(R.id.tvScenarios);
        tvScenarios.setText(scenariosList.get(cont).getScenario());

        btnAutorizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper database = new DBHelper(context);
                scenariosList.get(cont).setDecision(1);
                database.updateScenariosID(scenariosList.get(cont));
                DialogList(scenariosList, cont + 1);
                dialog.dismiss();
            }
        });

        btnNegociar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper database = new DBHelper(context);
                scenariosList.get(cont).setDecision(3);
                database.updateScenariosID(scenariosList.get(cont));
                DialogList(scenariosList, cont + 1);
                dialog.dismiss();
            }
        });
        btnNegar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper database = new DBHelper(context);
                scenariosList.get(cont).setDecision(2);
                database.updateScenariosID(scenariosList.get(cont));
                DialogList(scenariosList, cont + 1);
                dialog.dismiss();
            }
        });

    }

    private List<Scenarios> getScenarios() {
        DBHelper database = new DBHelper(this.getBaseContext());
        return database.getScenarios();
    }


    public Dialog onCreateDialogPers(int layout_id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        builder.setView(inflater.inflate(layout_id, null));

        return builder.create();
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
