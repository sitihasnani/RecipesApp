package com.example.recipesapp.model

class RecipeModel{
    var id: Int = 0
    var name: String? = null
    var ingredient: String? = null
    var steps: String? = null
    var image: ByteArray? = null
    var recipeType: String? = null

    fun getname(): String?{
        return name
    }

    fun setname(Name: String?){
        name = Name
    }

    fun getIngredients(): String?{
        return ingredient
    }

    fun setIngredients(Ingredients: String?){
        ingredient = Ingredients
    }

    fun getsteps(): String?{
        return steps
    }

    fun setsteps(Steps: String?){
        steps = Steps
    }

    fun getimage(): ByteArray?{
        return image
    }

    fun setimage(Image: ByteArray?){
        image = Image
    }

    fun getrecipeType(): String?{
        return recipeType
    }

    fun setrecipeType(Recipe: String?){
        recipeType = Recipe
    }

}