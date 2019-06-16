package com.amaro.bakingapp.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amaro.bakingapp.R;
import com.amaro.bakingapp.model.Ingredient;
import com.amaro.bakingapp.view.adapter.IngredientAdapter;
import com.amaro.bakingapp.viewmodel.IngredientViewModel;
import com.amaro.bakingapp.viewmodel.IngredientViewModelFactory;

import java.util.ArrayList;
import java.util.List;


public class IngredientFragment extends Fragment {

    private static final String TAG = "RecipeDetailActivity";
    private static final String EXTRA_BUNDLE = "ingredients_extra";
    private RecyclerView recyclerView;

    public static IngredientFragment newInstance(List<Ingredient> ingredients) {


        ArrayList<Ingredient> items = new ArrayList<Ingredient>();
        items.addAll(ingredients);

        Bundle args = new Bundle();
        args.putParcelableArrayList(EXTRA_BUNDLE, items);

        IngredientFragment fragment = new IngredientFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public IngredientFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        setupRecyclerView();

        return view;
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            ArrayList<Ingredient> ingredients = args.getParcelableArrayList(EXTRA_BUNDLE);
            setupViewModel(ingredients);
        }
    }

    private void setupViewModel(List<Ingredient> ingredients) {
        IngredientViewModel mViewModel =  ViewModelProviders.of(this, new IngredientViewModelFactory(getActivity().getApplication(), ingredients)).get(IngredientViewModel.class);
        IngredientAdapter adapter = new IngredientAdapter(getContext(), mViewModel.getIngredients());
        recyclerView.setAdapter(adapter);
    }
}
