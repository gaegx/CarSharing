package com.example.car.presentation.cardetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.car.R;
import com.example.car.domain.model.Car;
import com.example.car.presentation.state.UiState;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public final class CarDetailFragment extends Fragment {

    private CarDetailViewModel viewModel;
    private String carId;
    private View contentView;
    private ProgressBar progressBar;
    private View errorView;
    private TextView errorText;
    private TextView nameText;
    private TextView descriptionText;
    private TextView priceText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_car_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        carId = CarDetailFragmentArgs.fromBundle(getArguments()).getCarId();
        viewModel = new ViewModelProvider(this).get(CarDetailViewModel.class);
        viewModel.loadCar(carId);

        contentView = view.findViewById(R.id.car_detail_content);
        progressBar = view.findViewById(R.id.car_detail_progress);
        errorView = view.findViewById(R.id.car_detail_error);
        errorText = view.findViewById(R.id.car_detail_error_text);
        nameText = view.findViewById(R.id.car_detail_name);
        descriptionText = view.findViewById(R.id.car_detail_description);
        priceText = view.findViewById(R.id.car_detail_price);

        View retryBtn = view.findViewById(R.id.car_detail_retry);
        if (retryBtn != null) retryBtn.setOnClickListener(v -> viewModel.retry(carId));
        View bookBtn = view.findViewById(R.id.car_detail_book);
        if (bookBtn != null) {
            bookBtn.setOnClickListener(v -> {
                Bundle args = new Bundle();
                args.putLong("bookingId", 0L);
                args.putString("carId", carId != null ? carId : "");
                NavHostFragment.findNavController(this).navigate(R.id.action_carDetail_to_bookingEdit, args);
            });
        }

        viewModel.getCarState().observe(getViewLifecycleOwner(), this::renderState);
    }

    private void renderState(UiState<Car> state) {
        if (state == null) return;
        progressBar.setVisibility(state.isLoading() ? View.VISIBLE : View.GONE);
        contentView.setVisibility(state.isSuccess() ? View.VISIBLE : View.GONE);
        errorView.setVisibility(state.isError() ? View.VISIBLE : View.GONE);

        if (state.isError() && state.getMessage() != null) {
            errorText.setText(state.getMessage());
        }
        if (state.isSuccess() && state.getData() != null) {
            Car car = state.getData();
            nameText.setText(car.getName());
            descriptionText.setText(car.getDescription());
            priceText.setText(getString(R.string.price_per_min, car.getPricePerMinute()));
        }
    }
}
