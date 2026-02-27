package com.example.car.presentation.cars;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.car.R;
import com.example.car.domain.model.Car;
import com.example.car.presentation.state.UiState;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public final class CarListFragment extends Fragment {

    private CarListViewModel viewModel;
    private CarListAdapter adapter;
    private View contentView;
    private ProgressBar progressBar;
    private View errorContainer;
    private TextView errorText;
    private TextView emptyText;
    private RecyclerView recyclerView;
    private View errorRetry;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_car_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(CarListViewModel.class);

        contentView = view.findViewById(R.id.car_list_content);
        progressBar = view.findViewById(R.id.car_list_progress);
        errorContainer = view.findViewById(R.id.car_list_error);
        errorText = view.findViewById(R.id.car_list_error_text);
        emptyText = view.findViewById(R.id.car_list_empty_text);
        recyclerView = view.findViewById(R.id.car_list_recycler);
        errorRetry = view.findViewById(R.id.car_list_error_retry);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new CarListAdapter(car -> {
            NavDirections action = CarListFragmentDirections.actionCarListToCarDetail(car.getId());
            NavHostFragment.findNavController(this).navigate(action);
        });
        recyclerView.setAdapter(adapter);

        if (errorRetry != null) {
            errorRetry.setOnClickListener(v -> viewModel.retry());
        }

        FloatingActionButton fabBookings = view.findViewById(R.id.fab_my_bookings);
        if (fabBookings != null) {
            fabBookings.setOnClickListener(v ->
                    NavHostFragment.findNavController(this).navigate(R.id.action_carList_to_bookingList));
        }

        viewModel.getCarListState().observe(getViewLifecycleOwner(), this::renderState);
    }

    private void renderState(UiState<List<Car>> state) {
        if (state == null) return;
        progressBar.setVisibility(state.isLoading() ? View.VISIBLE : View.GONE);
        contentView.setVisibility(state.isSuccess() ? View.VISIBLE : View.GONE);
        if (errorContainer != null) errorContainer.setVisibility(state.isError() ? View.VISIBLE : View.GONE);
        emptyText.setVisibility(state.isEmpty() ? View.VISIBLE : View.GONE);
        if (errorRetry != null) errorRetry.setVisibility(state.isError() ? View.VISIBLE : View.GONE);

        if (state.isError() && state.getMessage() != null) {
            errorText.setText(state.getMessage());
        }
        if (state.isSuccess() && state.getData() != null) {
            adapter.submitList(state.getData());
        }
    }
}
