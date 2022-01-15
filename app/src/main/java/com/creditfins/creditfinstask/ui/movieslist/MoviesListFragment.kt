package com.creditfins.creditfinstask.ui.movieslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.creditfins.creditfinstask.R
import com.creditfins.creditfinstask.databinding.FragmentMoviesListBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MoviesListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MoviesListFragment : Fragment() {
    private lateinit var binding:FragmentMoviesListBinding
    private lateinit var moviesListViewModel:MoviesListViewModel
    private lateinit var moviesListAdapter: MoviesListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    private fun setupPaginationListener(){
        binding.list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if(!moviesListViewModel.isLoading()) {
                    if (dy > 0) //check for scroll down
                    {
                        val visibleItemCount = recyclerView.layoutManager?.childCount!!;
                        val totalItemCount = recyclerView.layoutManager?.itemCount!!;
                        val pastVisiblesItems = (recyclerView.layoutManager as LinearLayoutManager)
                            .findFirstVisibleItemPosition()
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            moviesListViewModel.setIsLoading(true)
                            loadMovies()
                        }
                    }
                }
            }
        })
    }

    private fun loadMovies(){
        if (moviesListViewModel.isFirstPage()) {
            binding.list.visibility = View.GONE
            binding.progressCircular.visibility = View.VISIBLE
        } else {
            newsAdapter.addPagination()
        }
        moviesListViewModel.loadMovies()
    }
}