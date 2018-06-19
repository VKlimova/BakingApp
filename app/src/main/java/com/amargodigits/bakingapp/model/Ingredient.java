package com.amargodigits.bakingapp.model;

public class Ingredient {
    private String id;
    private String recipeId;
    private String quantity;
    private String measure;
    private String ingredient;

    public Ingredient(String id, String recipeId, String quantity, String measure, String ingredient ){
        this.id = id;
        this.recipeId = recipeId;
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public String getId() { return this.id;}
    public String getRecipeId() { return  this.recipeId; }
    public String getQuantity() {return this.quantity;}
    public String getMeasure() {return this.measure;}
    public String getIngredient() {return this.ingredient;}

}
