package com.example.meetfinance.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.meetfinance.ApiRequest;
import com.example.meetfinance.CountryAdapter;
import com.example.meetfinance.CountryItem;
import com.example.meetfinance.HistoryActivity;
import com.example.meetfinance.MySingleton;
import com.example.meetfinance.R;
import com.example.meetfinance.SecondActivity;

import java.util.ArrayList;

public class FirstActivity extends AppCompatActivity {

    EditText etCompany;
    Button searchButton;
    Button listButton;
    ProgressBar searchBar;
    RequestQueue queue;
    String ticker;
    public ArrayList<CountryItem> mCountryList;
    private CountryAdapter mAdapter;
    private ApiRequest request;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initList();
        Spinner spinner = findViewById(R.id.spinner);
        mAdapter = new CountryAdapter(this, mCountryList);
        spinner.setAdapter(mAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CountryItem clickedItem = (CountryItem) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // on initialise nos éléments

        queue = MySingleton.getInstance(this).getRequestQueue();
        request = new ApiRequest(queue, this);
        handler = new Handler();
        etCompany = (EditText) findViewById(R.id.et_company);
        searchButton = (Button) findViewById(R.id.buttonSend);
        searchBar = (ProgressBar) findViewById(R.id.progress_bar);
        listButton = (Button) findViewById(R.id.buttonSend2);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String research = etCompany.getText().toString().trim();

                if (research.length() > 0) {
                    searchBar.setVisibility(View.VISIBLE);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(FirstActivity.this, HistoryActivity.class); // getApplicationContext()
                            // on crée un Bundle pour ajouter des informations qu'on va passer dans l'autre activité
                            ticker = etCompany.getText().toString().toUpperCase();

                            intent.putExtra("ticker", ticker);
                            startActivity(intent);
                           /* request.checkCompanyName(research, new ApiRequest.CheckCompanyCallback() {
                                @Override
                                public void onSuccess(String symbol, double price) {
                                    searchBar.setVisibility(View.INVISIBLE); // on rend la barre de progression invisible

                                    // on crée un Intent pour passer dans une autre activité
                                    Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
                                    // on crée un Bundle pour ajouter des informations qu'on va passer dans l'autre activité
                                    intent.putExtra("ticker", ticker.getBytes());
                                    startActivity(intent);

                                }

                                @Override
                                public void notExist(String message) {
                                    searchBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onError(String message) {
                                    searchBar.setVisibility(View.INVISIBLE);

                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                }
                            }); */

                        }
                    }, 2000);

                } else {
                    Toast.makeText(getApplicationContext(), "You must type a company name !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                searchBar.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.Tada)
                        .duration(1000)
                        .repeat(1)
                        .playOn(listButton);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        searchBar.setVisibility(View.INVISIBLE); // on rend la barre de progression invisible

                        // on crée un Intent pour passer dans une autre activité
                        Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                        // on crée un Bundle pour ajouter des informations qu'on va passer dans l'autre activité
                        //Bundle extras = new Bundle();
                        //intent.putExtras(extras);

                        startActivity(intent);
                    }
                }, 2000);
            }
        });
    }

    private void initList() {
        mCountryList = new ArrayList<>();
        mCountryList.add(new CountryItem(R.drawable.france));
        mCountryList.add(new CountryItem(R.drawable.allemagne));
        mCountryList.add(new CountryItem(R.drawable.italie));
        mCountryList.add(new CountryItem(R.drawable.russie));
        mCountryList.add(new CountryItem(R.drawable.chinne2));
        mCountryList.add(new CountryItem(R.drawable.espagne));
    }

}