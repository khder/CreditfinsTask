package com.creditfins.creditfinstask.data.utils

import com.creditfins.creditfinstask.data.models.Movie
import org.json.JSONArray
import org.json.JSONObject

object JsonParserUtils {
    fun parseJson(json:String):List<Movie>{
        val results: JSONArray = JSONObject(json).getJSONArray("results")
        val length :Int = results.length()
        val movies:ArrayList<Movie> = ArrayList()
        (0 until length).forEach {
            val movie : JSONObject = results.getJSONObject(it)
            movies.add(Movie(movie.getInt("id"),
                movie.getString("title"),movie.getString("poster_path")))
        }
        return movies
    }
}