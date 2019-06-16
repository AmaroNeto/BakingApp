package com.amaro.bakingapp.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amaro.bakingapp.R;
import com.amaro.bakingapp.model.Step;
import com.amaro.bakingapp.view.adapter.StepAdapter;
import com.amaro.bakingapp.viewmodel.StepViewModel;
import com.amaro.bakingapp.viewmodel.StepViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class StepFragment extends Fragment {

    private static final String EXTRA_BUNDLE = "steps_extra";
    private RecyclerView recyclerView;

    public static StepFragment newInstance(List<Step> steps) {

        ArrayList<Step> items = new ArrayList<Step>();
        items.addAll(steps);

        Bundle args = new Bundle();
        args.putParcelableArrayList(EXTRA_BUNDLE, items);

        StepFragment fragment = new StepFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public StepFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        setupRecyclerView();

        return view;
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            ArrayList<Step> steps = args.getParcelableArrayList(EXTRA_BUNDLE);
            setupViewModel(steps);
        }
    }

    private void setupViewModel(List<Step> steps){
        StepViewModel viewModel = ViewModelProviders.of(this, new StepViewModelFactory(getActivity().getApplication(), steps)).get(StepViewModel.class);
        StepAdapter adapter = new StepAdapter(getActivity(),viewModel.getSteps());
        adapter.setOnListItemClick((StepAdapter.ListItemClickListener) getActivity());
        recyclerView.setAdapter(adapter);
    }
}
