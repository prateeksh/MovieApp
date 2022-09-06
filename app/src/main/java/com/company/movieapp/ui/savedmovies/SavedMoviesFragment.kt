package com.company.movieapp.ui.savedmovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.company.movieapp.MainApplication
import com.company.movieapp.adapter.SavedMoviesAdapter
import com.company.movieapp.databinding.SavedMoviesFragmentBinding
import com.company.movieapp.ui.home.HomeViewModel
import com.company.movieapp.ui.home.HomeViewModelFactory
import javax.inject.Inject

class SavedMoviesFragment : Fragment() {

    private var _binding: SavedMoviesFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @Inject
    lateinit var savedModelFactory: SavedViewModelFactory

    private lateinit var viewModel: SavedMoviesViewModel
    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter: SavedMoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = SavedMoviesFragmentBinding.inflate(inflater, container, false)
        recyclerView = binding.savedMatchesView
        (requireActivity().application as MainApplication).applicationComponent.injectSaved(this@SavedMoviesFragment)

        viewModel =
            ViewModelProvider(this, savedModelFactory).get(SavedMoviesViewModel::class.java)

        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)

        /*childAdapter = ChildAdapter()
        recyclerView.adapter = childAdapter*/
        recyclerView.setHasFixedSize(true)

        viewModel.saved.observe(viewLifecycleOwner, Observer {
            if(it!= null) {
                adapter = SavedMoviesAdapter(it)
                recyclerView.adapter = adapter
            }else{
                Toast.makeText(requireContext(),"No data in database",Toast.LENGTH_LONG).show()
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}