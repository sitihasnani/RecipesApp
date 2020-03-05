package com.example.recipesapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.recipesapp.model.RecipeModel

class MindOrksDBOpenHelper(context: Context,
                           factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME,
        factory, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_PRODUCTS_TABLE = ("CREATE TABLE " +
                TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME + " TEXT," +
                COLUMN_INGREDIENTS + " TEXT," +
                COLUMN_STEPS + " TEXT," +
                COLUMN_IMAGE + " BLOB," +
                COLUMN_TYPE + " TEXT" +
                ")")
        db.execSQL(CREATE_PRODUCTS_TABLE)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun addRecipe(recipe: RecipeModel) {
        val values = ContentValues()
        values.put(COLUMN_NAME, recipe.name)
        values.put(COLUMN_INGREDIENTS, recipe.ingredient)
        values.put(COLUMN_STEPS, recipe.steps)
        values.put(COLUMN_IMAGE, recipe.image)
        values.put(COLUMN_TYPE, recipe.recipeType)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllName(recipetype: String): Cursor? {
        val db = this.readableDatabase
        if (recipetype != ""){
            return db.rawQuery("SELECT * FROM "+ TABLE_NAME + " WHERE recipetype='"+recipetype+"'", null)
        } else {
            return db.rawQuery("SELECT * FROM "+ TABLE_NAME , null)
        }
    }
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "Recipe.db"
        val TABLE_NAME = "recipe_table"
        val COLUMN_ID = "id"
        val COLUMN_NAME = "name"
        val COLUMN_INGREDIENTS = "ingredients"
        val COLUMN_STEPS = "steps"
        val COLUMN_IMAGE = "image"
        val COLUMN_TYPE = "recipetype"
    }
}