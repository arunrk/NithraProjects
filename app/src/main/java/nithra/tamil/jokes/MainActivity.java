package nithra.tamil.jokes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , RecyclerCatAdapter.OnCatClickAdapter{

    Toolbar toolbar;
    DBHelper dbHelper;
    RecyclerCatAdapter adapter;
    RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    List<Jokes> list;
    SharedPreference pref;
    private GoogleApiClient mClient;
    private Uri mUrl;
    private String mTitle;
    private String mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        versionControl();
        setDrawer();
        setView();
       appIndexing();
    }

    @Override
    protected void onResume() {
        super.onResume();
/*
        Intent in = getIntent();
        Uri data = in.getData();
        String v = data.toString().substring(data.toString().indexOf("id=") + 3);
*/
        /*if (v != null){
            Toast.makeText(this, "----" + v, Toast.LENGTH_SHORT).show();
        }*/
    }

    private void appIndexing() {
        mClient = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        mUrl = Uri.parse("http://www.nithra.edu/whatnext");
        mTitle = "arun";
        mDescription = "This is the first page that has been deep linked in this app";
    }


    public Action getAction() {
        Thing object = new Thing.Builder() .setName(mTitle) .setDescription(mDescription) .setUrl(mUrl) .build();
        return new Action.Builder(Action.TYPE_VIEW) .setObject(object) .setActionStatus(Action.STATUS_TYPE_COMPLETED) .build();
     }

    @Override public void onStart() {
        super.onStart();
        mClient.connect();
        AppIndex.AppIndexApi.start(mClient, getAction());
    }
    @Override public void onStop() {
        super.onStop();
        AppIndex.AppIndexApi.end(mClient, getAction());
        mClient.disconnect();
    }

    private void versionControl() {
        int versionCode = BuildConfig.VERSION_CODE;
        String versionName = BuildConfig.VERSION_NAME;
            try {
                dbHelper.createDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
            dbHelper.close();
            dbHelper.openDataBase();
            dbHelper.close();
    }

    private void setView() {
        list =  new ArrayList<>();

        list = dbHelper.getJokesCat();

        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        adapter = new RecyclerCatAdapter(this, list);
        mRecyclerView.setAdapter(adapter);


    }

    private void setDrawer() {
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

    @Override
    public void onCatClickAdapter(String id, String title) {
        Bundle b = new Bundle();
        b.putString("id", id);
        startActivity(new Intent(MainActivity.this, SubCatActivity.class).putExtras(b));
    }
}
