/*
    Gabriel Teles - 827333
*/
package com.example.examemobile.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.examemobile.R;
import com.example.examemobile.models.Recipe;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterRecipeActivity extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore;
    private Button newButton;
    private EditText name;
    private EditText ingredients;
    private EditText howToMake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);
        firebaseFirestore = FirebaseFirestore.getInstance();
        newButton = findViewById(R.id.cad_button);
        name = findViewById(R.id.edit_name);
        ingredients = findViewById(R.id.edit_ing);
        howToMake = findViewById(R.id.edit_how);
    }

    private void addRecipe(final Recipe recipe) {
        firebaseFirestore.collection("recipes")
                .add(recipe)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //Toast.makeText(NewRecipe.this, "Data added with ID: " + documentReference.getId(), Toast.LENGTH_LONG).show();
                        recipe.setId(documentReference.getId());
                        RecipeListActivity.recipes.add(recipe);
                        Intent intent = new Intent(RegisterRecipeActivity.this, RecipeListActivity.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterRecipeActivity.this, "Error adding document", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void newRecipe(View view) {
        String namef = name.getText().toString();
        String ing = ingredients.getText().toString();
        String htm = howToMake.getText().toString();


        if (namef.equalsIgnoreCase("") || ing.equalsIgnoreCase("") || htm.equalsIgnoreCase("") || namef == null || ing == null || htm == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Informacoes Invalidas");
            builder.setMessage("Por favor insira as informacoes necessarias");
            builder.create().show();
            return;
        }


        Recipe recipe = new Recipe(namef, ing, htm);
        addRecipe(recipe);
    }


}
