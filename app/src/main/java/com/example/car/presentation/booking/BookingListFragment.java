package com.example.car.presentation.booking;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.car.R;
import com.example.car.domain.model.Booking;
import com.example.car.presentation.state.UiState;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public final class BookingListFragment extends Fragment {

    private BookingListViewModel viewModel;
    private BookingListAdapter adapter;
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
        return inflater.inflate(R.layout.fragment_booking_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(BookingListViewModel.class);
        viewModel.loadBookings();

        contentView = view.findViewById(R.id.booking_list_content);
        progressBar = view.findViewById(R.id.booking_list_progress);
        errorContainer = view.findViewById(R.id.booking_list_error);
        errorText = view.findViewById(R.id.booking_list_error_text);
        emptyText = view.findViewById(R.id.booking_list_empty_text);
        recyclerView = view.findViewById(R.id.booking_list_recycler);
        errorRetry = view.findViewById(R.id.booking_list_error_retry);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new BookingListAdapter(new BookingListAdapter.OnBookingActionsListener() {
            @Override
            public void onBookingClick(Booking booking) {
                Bundle args = new Bundle();
                args.putLong("bookingId", booking.getId());
                args.putString("carId", "");
                NavHostFragment.findNavController(BookingListFragment.this)
                        .navigate(R.id.action_bookingList_to_bookingEdit, args);
            }

            @Override
            public void onBookingDelete(Booking booking) {
                viewModel.deleteBooking(booking);
            }
        });
        recyclerView.setAdapter(adapter);

        if (errorRetry != null) errorRetry.setOnClickListener(v -> viewModel.retry());

        FloatingActionButton fab = view.findViewById(R.id.booking_list_fab);
        if (fab != null) {
            fab.setOnClickListener(v -> {
                Bundle args = new Bundle();
                args.putLong("bookingId", 0L);
                args.putString("carId", "");
                NavHostFragment.findNavController(this).navigate(R.id.action_bookingList_to_bookingEdit, args);
            });
        }

        viewModel.getBookingsState().observe(getViewLifecycleOwner(), this::renderState);
    }

    private void renderState(UiState<List<Booking>> state) {
        if (state == null) return;
        progressBar.setVisibility(state.isLoading() ? View.VISIBLE : View.GONE);
        contentView.setVisibility(state.isSuccess() ? View.VISIBLE : View.GONE);
        if (errorContainer != null) errorContainer.setVisibility(state.isError() ? View.VISIBLE : View.GONE);
        emptyText.setVisibility(state.isEmpty() ? View.VISIBLE : View.GONE);
        if (errorRetry != null) errorRetry.setVisibility(state.isError() ? View.VISIBLE : View.GONE);

        if (state.isError() && state.getMessage() != null) errorText.setText(state.getMessage());
        if (state.isSuccess() && state.getData() != null) adapter.submitList(state.getData());
    }
}
