package nithra.tamil.jokes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SubCatActivity extends AppCompatActivity implements RecyclerCatAdapter.OnCatClickAdapter{

    DBHelper dbHelper;
    RecyclerCatAdapter adapter;
    RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    List<Jokes> list;
    Intent intent;
    String id = "";
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_cat);
        bundle = getIntent().getExtras();
        id = bundle.getString("id");
        setView();

    }

    private void setView() {
        list =  new ArrayList<>();
        dbHelper = new DBHelper(this);
        list = dbHelper.getSubCat(id);

        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        adapter = new RecyclerCatAdapter(this, list);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onCatClickAdapter(String id, String title) {
        Toast.makeText(this, title, Toast.LENGTH_LONG).show();
    }
}
