package com.amargodigits.bakingapp.model;

public class Step {
    private String id;
    private String recipeId;
    private String shortDescription;
    private String description;
    private String videoUrl;
    private String thumbnailUrl;

    public Step(String id, String recipeId, String shortDescription, String description, String videoUrl, String thumbnailUrl) {
        this.id = id;
        this.recipeId = recipeId;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getId() {
        return this.id;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public String getShortDescription() {
        return this.shortDescription;
    }

    public String getDescription() {
        return this.description;
    }

    public String getVideoUrl() {
        return this.videoUrl;
    }

    public String getThumbnailUrl() {
        return this.thumbnailUrl;
    }
}
