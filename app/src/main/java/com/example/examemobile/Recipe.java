package com.example.examemobile;

import com.google.firebase.firestore.DocumentId;

public class Recipe {
    @DocumentId
    private String id;
    private String name;
    private String ingredients;
    private String howToMake;

    public Recipe(){

    }

    public Recipe(String id, String name, String ingredients, String howToMake) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.howToMake = howToMake;
    }

    public Recipe(String name, String ingredients, String howToMake) {
        this.name = name;
        this.ingredients = ingredients;
        this.howToMake = howToMake;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getHowToMake() {
        return howToMake;
    }

    public void setHowToMake(String howToMake) {
        this.howToMake = howToMake;
    }

    public void copy(Recipe recipe){
        this.name = recipe.getName();
        this.howToMake = recipe.getHowToMake();
        this.ingredients = recipe.getIngredients();
    }
}
