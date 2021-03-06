package com.example.meetfinance.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.meetfinance.R;
import com.example.meetfinance.Singletons;
import com.example.meetfinance.presentation.controller.SecondController;
import com.example.meetfinance.presentation.model.Symbol;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ListAdapter mAdapter;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // on initialise nos éléments
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        // on se sert du controller
        SecondController controller = new SecondController(
                this,
                Singletons.getGson(),
                Singletons.getSharedPreferences(getApplicationContext())
        );
        controller.onStart();

        // M. Etienne est trop chaud, il fait du FRENGLISH :)

        // gestion de la barre de recherche dans la recyclerview
        EditText editText = findViewById(R.id.editText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString()); // on filtre la liste en fonction de ce qu'on a écrit
            }
        });
    }

    public void showList(List<Symbol> symbolsList) {
        // on initialise nos éléments
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // on définit un adapter
        mAdapter = new ListAdapter(symbolsList, new ListAdapter.OnItemClickListener() {
            public void onItemClick(Symbol symbolsList) {
                // on crée un Intent pour envoyer des info dans une nouvelle activité
                Intent intent = new Intent(getApplicationContext(), ThirdActivity.class);
                intent.putExtra("txtHeader", symbolsList.getName()); // on prend nom, symbol, prix et exchange de l'entreprise
                intent.putExtra("txtFooter", symbolsList.getSymbol());
                intent.putExtra("txt3", symbolsList.getPrice());
                intent.putExtra("txt4", symbolsList.getExchange());
                YoYo.with(Techniques.RubberBand) // animation avec Yoyo, l'écran de la recyclerview se déforme et revient à la normale
                        .duration(2000)
                        .repeat(1)
                        .playOn(mDrawerLayout);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    public void showError() {
        Toast.makeText(this, "Api Error", Toast.LENGTH_SHORT).show();
    }

    // gestion des clicks sur les items dans la navigationView
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.isChecked()) { // on vérifie si l'item sur lequel on clique est coché
            item.setChecked(false);
        } else {
            item.setChecked(true);
        }
        mDrawerLayout.closeDrawers();

        switch (item.getItemId()) { // en fonction des cas l'action est différente
            case R.id.home:
                Intent intent1 = new Intent(getApplicationContext(), FirstActivity.class);
                startActivity(intent1);
                finish();
                return true;
            case R.id.back:
                Intent intent2 = new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(intent2);
                finish();
                return true;
            case R.id.quit:
                Toast.makeText(getApplicationContext(), "Quitter...", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.settings:
                Toast.makeText(getApplicationContext(), "Settings...", Toast.LENGTH_SHORT).show();
                return true;
        }
        return true;
    }

    public void filter(String text) { // gestion du filtre de la recyclerview
        ArrayList<Symbol> filteredList = new ArrayList<>();
        List<Symbol> symbolsList = SecondController.symbolsList;
        for (Symbol symbol : symbolsList) {
            if (symbol.getSymbol().toUpperCase().contains(text.toUpperCase())) {
                filteredList.add(symbol);
            }
        }
        mAdapter.filterList(filteredList);
    }

}

