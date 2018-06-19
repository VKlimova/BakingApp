package com.amargodigits.bakingapp.model;

public class Recipe {
    private String id;
    private String name;
    private String servings;
    private String image;

    public Recipe(String id, String name, String servings, String image ){
        this.id = id;
        this.name = name;
        this.servings = servings;
        this.image = image;
    }

    public String getId() { return this.id;}
    public String getName() {return this.name;}
    public String getServings() {return this.servings;}
    public String getImage() {return this.image;}

}
