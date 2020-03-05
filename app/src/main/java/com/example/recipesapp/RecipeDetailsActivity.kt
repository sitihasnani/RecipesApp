package com.example.recipesapp

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_recipe_details.*


class RecipeDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_recipe_details)

        recipeTxt.setText(intent.getStringExtra("RecipeName"))
        ingredientTxt.setText(intent.getStringExtra("Ingredients"))
        stepsTxt.setText(intent.getStringExtra("Steps"))

        var byteArray: ByteArray? = null
        byteArray = intent.getByteArrayExtra("Image")

        if (byteArray != null){
            val bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            recipe_image.setImageBitmap(bmp)
        }

    }
}
