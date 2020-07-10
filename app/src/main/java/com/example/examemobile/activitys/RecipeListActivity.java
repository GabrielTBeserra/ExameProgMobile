/*
    Gabriel Teles - 827333
*/
package com.example.examemobile.activitys;

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

import com.example.examemobile.R;
import com.example.examemobile.adapter.RecipeAdapter;
import com.example.examemobile.fragments.RecipeDetailFragment;
import com.example.examemobile.models.Recipe;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RecipeListActivity extends AppCompatActivity implements RecipeAdapter.OnItemClickListener {

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
                Intent intent = new Intent(RecipeListActivity.this, RegisterRecipeActivity.class);
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
            Intent intent = new Intent(RecipeListActivity.this, RecipeDetailActivity.class);
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
                            Toast.makeText(RecipeListActivity.this, "Error getting documents.", Toast.LENGTH_LONG).show();
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

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Toast.makeText(this, "Remove", Toast.LENGTH_LONG).show();
        switch (item.getItemId()) {
            case 456456:
                removeRecipe(item.getGroupId());
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void logOut() {
        auth.signOut();
        startActivity(new Intent(RecipeListActivity.this, MainActivity.class));
    }

    private void removeRecipe(final int index) {
        String id = recipes.get(index).getId();


        firebaseFirestore.collection("recipes")
                .document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        recipes.remove(index);
                        Toast.makeText(RecipeListActivity.this, "Receita removida com sucesso", Toast.LENGTH_LONG).show();
                        recipeAdapter.notifyItemRemoved(index);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RecipeListActivity.this, "Error deleting data", Toast.LENGTH_LONG).show();
                    }
                });
    }

}
