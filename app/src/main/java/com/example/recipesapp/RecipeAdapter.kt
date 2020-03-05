package com.example.recipesapp

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesapp.model.RecipeModel
import kotlinx.android.synthetic.main.item_recipe.view.*

class RecipeAdapter(var items : ArrayList<RecipeModel>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recipe, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.recipeName?.text = items.get(position).getname()
        var byteArray: ByteArray? = null
        byteArray = items.get(position).getimage()

        if (byteArray!=null){
            val bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size)
            holder.recipeImage.setImageBitmap(bmp)
        }



        holder.itemView.setOnClickListener {
            val intent = Intent(context, RecipeDetailsActivity::class.java)
            intent.putExtra("RecipeName", items.get(position).getname())
            intent.putExtra("Ingredients", items.get(position).getIngredients())
            intent.putExtra("Steps", items.get(position).getsteps())
            intent.putExtra("Image", items.get(position).getimage())
            context.startActivity(intent)
        }
    }

    fun refresh(itemsRecipe: List<RecipeModel>) {
        items = itemsRecipe as ArrayList<RecipeModel>
        notifyDataSetChanged()
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val recipeName = view.titleTxt
    val recipeImage = view.image_recipe
}

