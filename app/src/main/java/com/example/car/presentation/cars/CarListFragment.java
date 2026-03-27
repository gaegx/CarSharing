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
import com.example.car.data.network.NetworkStatus;
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
    private View banner;
    private TextView bannerText;
    private View importButton;
    private View importProgress;

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
        banner = view.findViewById(R.id.car_list_banner);
        bannerText = view.findViewById(R.id.car_list_banner_text);
        importButton = view.findViewById(R.id.car_list_import_button);
        importProgress = view.findViewById(R.id.car_list_import_progress);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new CarListAdapter(car -> {
            NavDirections action = CarListFragmentDirections.actionCarListToCarDetail(car.getId());
            NavHostFragment.findNavController(this).navigate(action);
        });
        recyclerView.setAdapter(adapter);

        if (errorRetry != null) {
            errorRetry.setOnClickListener(v -> viewModel.retry());
        }

        if (importButton != null) {
            importButton.setOnClickListener(v -> viewModel.importCars(false));
        }

        FloatingActionButton fabBookings = view.findViewById(R.id.fab_my_bookings);
        if (fabBookings != null) {
            fabBookings.setOnClickListener(v ->
                    NavHostFragment.findNavController(this).navigate(R.id.action_carList_to_bookingList));
        }

        viewModel.getCarListState().observe(getViewLifecycleOwner(), this::renderState);
        viewModel.getNetworkStatus().observe(getViewLifecycleOwner(), this::renderNetwork);
        viewModel.getBannerText().observe(getViewLifecycleOwner(), this::renderBannerText);
        viewModel.getImportState().observe(getViewLifecycleOwner(), state -> {
            if (state == null) return;
            if (importProgress != null) importProgress.setVisibility(state.isLoading() ? View.VISIBLE : View.GONE);
            if (importButton != null) {
                importButton.setEnabled(!state.isLoading());
                if (importButton instanceof android.widget.Button) {
                    ((android.widget.Button) importButton).setText(state.isLoading()
                            ? getString(R.string.import_in_progress)
                            : getString(R.string.import_data));
                }
            }
            if (state.isSuccess() && state.getData() != null && state.getData() > 0) {
                Toast.makeText(requireContext(), getString(R.string.import_success, state.getData()), Toast.LENGTH_LONG).show();
            } else if (state.isError() && state.getMessage() != null) {
                Toast.makeText(requireContext(), state.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
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

    private void renderNetwork(@Nullable NetworkStatus status) {
        boolean offline = status == NetworkStatus.OFFLINE;
        if (offline) {
            showBanner(getString(R.string.offline_banner), false);
        } else {
            // online: banner text (if any) will decide
            String txt = viewModel.getBannerText().getValue();
            if (txt == null || txt.trim().isEmpty()) {
                hideBanner();
            } else {
                showBanner(txt, true);
            }
        }
    }

    private void renderBannerText(@Nullable String txt) {
        NetworkStatus status = viewModel.getNetworkStatus().getValue();
        if (status == NetworkStatus.OFFLINE) {
            showBanner(getString(R.string.offline_banner), false);
            return;
        }
        if (txt == null || txt.trim().isEmpty()) {
            hideBanner();
        } else {
            showBanner(txt, true);
        }
    }

    private void showBanner(@NonNull String text, boolean showImportAction) {
        if (banner == null || bannerText == null) return;
        banner.setVisibility(View.VISIBLE);
        bannerText.setText(text);
        if (importButton != null) importButton.setVisibility(showImportAction ? View.VISIBLE : View.GONE);
    }

    private void hideBanner() {
        if (banner != null) banner.setVisibility(View.GONE);
    }
}
