package com.creditfins.creditfinstask.ui.movieslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.creditfins.creditfinstask.data.models.Movie
import com.creditfins.creditfinstask.databinding.MovieListRowBinding
import com.creditfins.creditfinstask.databinding.ProgressBarBinding

class MoviesListAdapter(private val movies:ArrayList<Movie>,
                        private val onCLick:(movie: Movie)->Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val ITEM_ROW = 1;
    private val PROGRESS_ROW = 2;
    private val PAGINATION = "pagination"
    private var isLoadingAdded = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType==ITEM_ROW){
            MovieHolder(MovieListRowBinding
                .inflate(LayoutInflater.from(parent.context),parent,false))
        }else{
            ProgressHolder(ProgressBarBinding
                .inflate(LayoutInflater.from(parent.context),parent,false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun removePagination(){
        isLoadingAdded = false
        val position: Int = movies.size - 1
        movies.removeAt(position)
        notifyItemRemoved(position)
    }
    fun addPagination(){
        isLoadingAdded = true
        val paginationMovies = Movie(0,"","")
        movies.add(paginationMovies)
        notifyItemInserted(movies.size-1)
    }

    fun updateMoviesList(movies: List<Movie>){
        val oldSize = movies.size
        this.movies.addAll(movies)
        notifyItemRangeInserted(oldSize,this.movies.size)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == movies.size - 1 && isLoadingAdded) PROGRESS_ROW else ITEM_ROW
    }

    inner class MovieHolder(val binding:MovieListRowBinding ) :
        RecyclerView.ViewHolder(binding.root)

    inner class ProgressHolder(val binding:ProgressBarBinding) :
        RecyclerView.ViewHolder(binding.root)
}