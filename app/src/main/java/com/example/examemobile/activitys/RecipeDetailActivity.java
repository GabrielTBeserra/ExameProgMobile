/*
    Gabriel Teles - 827333
*/
package com.example.examemobile.activitys;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.examemobile.R;
import com.example.examemobile.fragments.RecipeDetailFragment;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        if (savedInstanceState == null) {
            int selectedSong = getIntent().getIntExtra("recipe_id", 0);
            RecipeDetailFragment fragment = RecipeDetailFragment.newInstance(selectedSong);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_container, fragment)
                    .commit();
        }
    }
}
