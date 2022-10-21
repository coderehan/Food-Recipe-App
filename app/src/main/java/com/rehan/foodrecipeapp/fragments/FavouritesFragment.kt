package com.rehan.foodrecipeapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.rehan.foodrecipeapp.R
import com.rehan.foodrecipeapp.activities.MainActivity
import com.rehan.foodrecipeapp.adapters.FavouritesMealsAdapter
import com.rehan.foodrecipeapp.databinding.FragmentFavouritesBinding
import com.rehan.foodrecipeapp.databinding.FragmentHomeBinding
import com.rehan.foodrecipeapp.viewmodels.HomeViewModel

class FavouritesFragment : Fragment() {

    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var favouritesMealsAdapter: FavouritesMealsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        prepareFavouritesRecyclerView()
        observeFavouritesMealsLiveData()

        // Deleting items on swiping left or right of items
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition       // Getting position of that item to be deleted
                viewModel.deleteMeal(favouritesMealsAdapter.differ.currentList[position])
                Snackbar.make(requireView(), "Meal deleted successfully", Snackbar.LENGTH_LONG).setAction(
                    "UNDO",
                View.OnClickListener{
                    // On clicking undo we are inserting meal again
                    viewModel.insertMeal(favouritesMealsAdapter.differ.currentList[position])
                }).show()
            }
        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavourites)
    }

    private fun prepareFavouritesRecyclerView() {
        favouritesMealsAdapter = FavouritesMealsAdapter()
        binding.rvFavourites.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = favouritesMealsAdapter
        }
    }

    private fun observeFavouritesMealsLiveData() {
        viewModel.observeFavouritesMealsLiveData().observe(requireActivity(), Observer {
            favouritesMealsAdapter.differ.submitList(it)
        })
    }


}