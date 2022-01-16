package com.creditfins.creditfinstask.ui.movieslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.creditfins.creditfinstask.data.models.Movie
import com.creditfins.creditfinstask.data.models.Resource
import com.creditfins.creditfinstask.data.models.Status
import com.creditfins.creditfinstask.databinding.FragmentMoviesListBinding
import com.creditfins.creditfinstask.di.ObjectsProvider

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
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMoviesListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupPaginationListener()
        setupViewModel()
        loadMovies()
        moviesListAdapter = MoviesListAdapter(
            ArrayList(),
            ::onCLick)
        binding.list.adapter = moviesListAdapter
        addDividerDecorator()
    }

    private fun setupViewModel(){
        moviesListViewModel = ViewModelProvider(this,MoviesListViewModelFactory(
            ObjectsProvider.getMoviesListRepository(requireContext().applicationContext)
        )).get(MoviesListViewModel::class.java)
    }

    private fun loadMovies(){
        val liveData = moviesListViewModel.getMovies()
        val observer:Observer<Resource<List<Movie>>> = object : Observer<Resource<List<Movie>>> {
            override fun onChanged(resource: Resource<List<Movie>>?) {
                when (resource!!.status) {
                    Status.LOADING -> {
                        if (moviesListViewModel.isFirstPage()) {
                            binding.list.visibility = View.GONE
                            binding.progressCircular.visibility = View.VISIBLE
                        } else {
                            moviesListAdapter.addPagination()
                        }
                    }
                    Status.SUCCESS -> {
                        if (moviesListViewModel.isFirstPage()) {
                            binding.list.visibility = View.VISIBLE
                            binding.progressCircular.visibility = View.GONE
                        } else {
                            moviesListAdapter.removePagination()
                        }
                        moviesListAdapter.updateMoviesList(resource.data!!)
                        moviesListViewModel.increasePages()
                        moviesListViewModel.setIsLoading(false)
                        liveData.removeObserver(this)
                    }
                    Status.ERROR -> {
                        Toast.makeText(
                            requireContext(),
                            resource.message,
                            Toast.LENGTH_LONG
                        ).show()
                        liveData.removeObserver(this)
                    }
                }
            }
        }
        liveData.observe(viewLifecycleOwner,observer)
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

    private fun addDividerDecorator(){
        binding.list.addItemDecoration(
            DividerItemDecoration(
                binding.list.context,
                (binding.list.layoutManager as LinearLayoutManager).orientation
            )
        )
    }

    private fun onCLick(movie: Movie){

    }
}