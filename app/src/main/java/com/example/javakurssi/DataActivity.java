package com.example.javakurssi;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private String searchTerm;

    private JsonObjectRequest jsonObjectRequest;

    private JSONObject responseObject;
    private String url;

    private ProgressBar progressBar;

    private ArrayList itemList;

    private JSONArray resultsArray;

    private RecycleAdapter adapter;

    private ProgressBar progress;
    private ArrayList<ItemDataModel> dataset;

    private SearchView searchView;

    private LinearLayout linearLayout;

    private RecyclerView recycler;

    private TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_data);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);


            return insets;


        });


        //linearLayout = (LinearLayout) findViewById(R.id.recycler);
        recycler = (RecyclerView) findViewById(R.id.recycler);


        /*
        recycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(R.id.id == View.GONE )
                { //ei ole constraintteja helloViewvissä joten muut elementit eivät liiku, gone toimii
                    id.setVisibility(View.VISIBLE);
                } else {
                    helloView.setVisibility(View.GONE);
                }
            }
        });*/


        toolbar = (Toolbar) findViewById(R.id.data);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        progress = (ProgressBar) findViewById(R.id.progress);


        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }

        // get data via the key
        searchTerm = extras.getString("search");
        Log.e("datassa", searchTerm);
        if (searchTerm != null) {

            url = "https://avoindata.prh.fi/bis/v1?totalResults=false&maxResults=100&resultsFrom=0&name=" + searchTerm + "&companyRegistrationFrom=2001-01-01";
            Log.e("applyterm", url);

        }
/*
        public myOnClickListener(){

        }*/
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                itemList = new ArrayList<ItemDataModel>(); // item open utils kansiossa // itemdatamodel tutoriaalissa
                resultsArray = null;
                progress.setVisibility(View.VISIBLE);
                try {
                    resultsArray = response.getJSONArray("results");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                for (int i = 0; i < resultsArray.length(); i++) {
                    try {

                        //itemDataModel.setName.dataobj.getstring("name:"name""));
                        //itemDataModel.name = resultsArray.getString()
                        //Log.e("string",resultsArray.getString(i));


                        JSONObject firstObject = resultsArray.getJSONObject(i);
                        String name = firstObject.getString("name");
                        String id = firstObject.getString("businessId");
                        String companyForm = firstObject.getString("companyForm");
                        String registrationDate = firstObject.getString("registrationDate");

                        Log.e("datamodel", id);
                        ItemDataModel itemDataModel = new ItemDataModel(name, id,companyForm,registrationDate );

                        //itemDataModel.name = name;
                        Log.e("datamodel", itemDataModel.name);

                        itemList.add(itemDataModel);

                        String resultString = resultsArray.getString(i);
                        if (itemList != null) {
                            Log.e("itemlist", "tyjhä");

                        }

                        Log.e("Yritykset", resultString);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                setUpRecyclerView();
                progress.setVisibility(View.GONE);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
        requestQueue.add(jsonObjectRequest);

    }


    private void setUpRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        Log.e("itemlist", "täällä");
        if (itemList != null) {
            adapter = new RecycleAdapter(itemList);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        } else {
            Log.e("null", "vittu");
        }
    }

/*
override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.options_menu, menu)

    return true
}*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.e("menu", "menu");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("newText1",query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("newText",newText);
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }}
