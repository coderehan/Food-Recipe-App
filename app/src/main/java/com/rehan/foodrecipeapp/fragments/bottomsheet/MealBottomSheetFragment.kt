package com.rehan.foodrecipeapp.fragments.bottomsheet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rehan.foodrecipeapp.activities.MainActivity
import com.rehan.foodrecipeapp.activities.MealActivity
import com.rehan.foodrecipeapp.databinding.FragmentMealBottomSheetBinding
import com.rehan.foodrecipeapp.fragments.HomeFragment
import com.rehan.foodrecipeapp.viewmodels.HomeViewModel

private const val MEAL_ID = "param1"

class MealBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentMealBottomSheetBinding
    private lateinit var viewModel: HomeViewModel

    private var mealId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(MEAL_ID)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentMealBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        mealId?.let {
            viewModel.getMealById(it)
        }
        observeBottomSheetMealLiveData()
        onBottomSheetDialogClick()
    }

    private var mealName: String? = null
    private var mealThumb: String? = null

    private fun observeBottomSheetMealLiveData() {
        viewModel.observeBottomSheetMealLiveData().observe(viewLifecycleOwner, Observer {
            Glide.with(this).load(it.strMealThumb).into(binding.ivBottomSheet)
            binding.tvArea.text = it.strArea
            binding.tvCategory.text = it.strCategory
            binding.tvMealName.text = it.strMeal

            mealName = it.strMeal
            mealThumb = it.strMealThumb
        })
    }

    private fun onBottomSheetDialogClick() {
        binding.bottomSheet.setOnClickListener{
            if(mealName != null && mealThumb != null){
                val intent = Intent(activity, MealActivity::class.java)
                intent.apply {
                    putExtra(HomeFragment.MEAL_ID, mealId)
                    putExtra(HomeFragment.MEAL_NAME, mealName)
                    putExtra(HomeFragment.MEAL_THUMB, mealThumb)
                }
                startActivity(intent)
            }
        }
    }

    companion object {
        @JvmStatic fun newInstance(param1: String) =
                MealBottomSheetFragment().apply {
                    arguments = Bundle().apply {
                        putString(MEAL_ID, param1)
                    }
                }
    }
}