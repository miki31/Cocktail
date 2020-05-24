package com.devlight.test.cocktail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.devlight.test.cocktail.bean.model.Cocktail;
import com.devlight.test.cocktail.bean.model.Drinks;
import com.devlight.test.cocktail.fragments.ListViewFragment;
import com.devlight.test.cocktail.network.NetworkService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

//    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
//        getSupportActionBar().setTitle("kjh");

        if (fragment == null) {
            fragment = new ListViewFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

//        mTextView = findViewById(R.id.textid);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(v -> {
//            //TODO TEST
//            testREST();
//        });



    }




}
