package com.example.examemobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examemobile.adapter.RecipeAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Recipes extends AppCompatActivity implements RecipeAdapter.OnItemClickListener {

    public static List<Recipe> recipes = new ArrayList<Recipe>();
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private FloatingActionButton floatingActionButton;
    private FirebaseAuth auth;
    private boolean mTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        auth = FirebaseAuth.getInstance();
        if (findViewById(R.id.detail_container) != null) {
            mTwoPane = true;
        }

        floatingActionButton = findViewById(R.id.floatingActionButton);
        firebaseFirestore = FirebaseFirestore.getInstance();
        loadRecipes();
        recyclerView = findViewById(R.id.rec_view);
        recyclerView.setHasFixedSize(true);
        recipeAdapter = new RecipeAdapter(recipes, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recipeAdapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Recipes.this, NewRecipe.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onItemClick(int pos) {
        if (mTwoPane) {
            RecipeDetailFragment fragment = RecipeDetailFragment.newInstance(pos);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_container, fragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            Intent intent = new Intent(Recipes.this, RecipeDetailActivity.class);
            intent.putExtra("recipe_id", pos);
            startActivity(intent);
        }

    }

    private void loadRecipes() {
        firebaseFirestore.collection("recipes")
                .orderBy("name")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            recipes.clear();
                            String str = "";
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Recipe product = document.toObject(Recipe.class);
                                System.out.println(product.getId());
                                recipes.add(product);
                            }
                            recipeAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(Recipes.this, "Error getting documents.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_id:
                logOut();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logOut() {
        auth.signOut();
        startActivity(new Intent(Recipes.this, MainActivity.class));
    }

}
