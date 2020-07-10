/*
    Gabriel Teles - 827333
*/
package com.example.examemobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.examemobile.R;
import com.example.examemobile.activitys.RecipeListActivity;
import com.example.examemobile.models.Recipe;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeDetailFragment extends Fragment {

    public Recipe mRecipe;

    public RecipeDetailFragment() {
        // Required empty public constructor
    }

    public static RecipeDetailFragment newInstance(int selected) {
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putInt("recipe_id", selected);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        if (getArguments().containsKey("recipe_id")) {
            mRecipe = RecipeListActivity.recipes.get(getArguments().getInt("recipe_id"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewRoot = inflater.inflate(R.layout.fragmant_recipe_detail, container, false);

        if (mRecipe != null) {
            ((TextView) viewRoot.findViewById(R.id.recipe_name)).setText(mRecipe.getName());
            ((TextView) viewRoot.findViewById(R.id.ingre_text)).setText(mRecipe.getIngredients());
            ((TextView) viewRoot.findViewById(R.id.how_text)).setText(mRecipe.getHowToMake());
        }

        return viewRoot;
    }
}
