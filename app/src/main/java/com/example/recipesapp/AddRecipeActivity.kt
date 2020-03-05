package com.example.recipesapp

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.recipesapp.model.RecipeModel
import kotlinx.android.synthetic.main.activity_add_recipe.*
import java.io.ByteArrayOutputStream


class AddRecipeActivity : AppCompatActivity() {
    var dbHandler: MindOrksDBOpenHelper? = null
    var recipe : RecipeModel? = null
    var byteArray: ByteArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_add_recipe)
        val recipe = RecipeModel()
        dbHandler = MindOrksDBOpenHelper(this, null)


        uploadBtn.setOnClickListener {
            //check runtime permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE)
                }
                else{
                    //permission already granted
                    pickImageFromGallery()
                }
            }
            else{
                //system OS is < Marshmallow
                pickImageFromGallery()
            }
        }

        cancelBtn.setOnClickListener {
            finish()
        }


        saveBtn.setOnClickListener {

//            Toast.makeText(this, "Spinner 1 " + spinner.selectedItem.toString() , Toast.LENGTH_LONG).show()

            recipe.name = recipeNameEt.text.toString()
            recipe.ingredient = ingredientsEt.text.toString()
            recipe.steps = stepsEt.text.toString()
            recipe.image = byteArray
            if (spinner.selectedItem.toString().equals("-- Please choose your recipe type --")){
                recipe.recipeType = ""
            } else {
                recipe.recipeType = spinner.selectedItem.toString()
            }

            dbHandler!!.addRecipe(recipe)
            Toast.makeText(this, "Successfully added to database", Toast.LENGTH_LONG).show()
        }
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){

            if (data != null) {
                val imageUri: Uri = data.data
                val bitmap =
                    MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)


                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                byteArray = stream.toByteArray()

                image_view.setImageURI(data.data)
            }

        }
    }


    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000
        //Permission code
        private val PERMISSION_CODE = 1001
    }


}




