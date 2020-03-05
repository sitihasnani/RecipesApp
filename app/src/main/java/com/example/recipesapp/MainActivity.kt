package com.example.recipesapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipesapp.model.RecipeModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    var dbHandler: MindOrksDBOpenHelper? = null
    var recipeAdapter: RecipeAdapter?= null
    var arrayList: ArrayList<RecipeModel> = ArrayList<RecipeModel>()
    var recipeType: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHandler = MindOrksDBOpenHelper(this, null)
        listOfRecipe.layoutManager = LinearLayoutManager(this)
        recipeAdapter = RecipeAdapter( arrayList, this)
        listOfRecipe.adapter = recipeAdapter
        fab.setOnClickListener {
            val intent = Intent(this, AddRecipeActivity::class.java)
            this.startActivity(intent)
        }

        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                arrayList.clear()
                onLoadList(recipeType)
                recipeAdapter!!.refresh(arrayList)
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                if (position == 0){
                    arrayList.clear()
                    onLoadList("")
                    recipeAdapter!!.refresh(arrayList)
                }
                else {
                    arrayList.clear()
                    onLoadList(spinner.selectedItem.toString())
                    recipeAdapter!!.refresh(arrayList)
                }


            }

        }




    }

    private fun onLoadList(recipeType: String) {
        try {
            val cursor = dbHandler!!.getAllName(recipeType)
            if (cursor != null ){
                if (cursor.moveToFirst()){
                    do {
                        var recipeModel: RecipeModel?= RecipeModel()
                        recipeModel?.setname(cursor.getString(1))
                        recipeModel?.setIngredients(cursor.getString(2))
                        recipeModel?.setsteps(cursor.getString(3))
                        recipeModel?.setimage(cursor.getBlob(4))
                        recipeModel?.setrecipeType(cursor.getString(5))
                        arrayList.add(recipeModel!!)

                    }
                    while (cursor.moveToNext())

                }

            } else {
                Toast.makeText(this, "No data", Toast.LENGTH_LONG).show()
            }

            recipeAdapter!!.notifyDataSetChanged()
        }
        catch (e: Exception) {
            // handler
            print(e)
        }


         }

    override fun onResume() {
        super.onResume()
        Log.d("BACK", "HERE")
        arrayList.clear()
        if (spinner.selectedItem.toString().equals("-- Please choose your recipe type --") ){
            onLoadList("")
        } else {
            onLoadList(spinner.selectedItem.toString())
        }

        recipeAdapter!!.refresh(arrayList)
    }


}
