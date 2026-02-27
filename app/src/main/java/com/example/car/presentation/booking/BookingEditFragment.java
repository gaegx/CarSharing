package com.example.car.presentation.booking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.car.R;
import com.example.car.domain.model.Booking;
import com.example.car.presentation.state.UiState;
import com.google.android.material.textfield.TextInputEditText;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public final class BookingEditFragment extends Fragment {

    private CreateEditBookingViewModel viewModel;
    private long bookingId = 0L;
    private String carId = null;
    private EditText titleInput;
    private EditText descriptionInput;
    private EditText dateInput;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_booking_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BookingEditFragmentArgs args = BookingEditFragmentArgs.fromBundle(getArguments());
        bookingId = args.getBookingId();
        String carIdArg = args.getCarId();
        carId = (carIdArg != null && !carIdArg.isEmpty()) ? carIdArg : null;

        viewModel = new ViewModelProvider(this).get(CreateEditBookingViewModel.class);

        titleInput = view.findViewById(R.id.booking_edit_title);
        descriptionInput = view.findViewById(R.id.booking_edit_description);
        dateInput = view.findViewById(R.id.booking_edit_date);

        if (bookingId > 0) {
            viewModel.loadBooking(bookingId);
        } else {
            long now = System.currentTimeMillis();
            dateInput.setText(String.valueOf(now));
        }

        viewModel.getBookingState().observe(getViewLifecycleOwner(), state -> {
            if (state != null && state.isSuccess() && state.getData() != null) {
                Booking b = state.getData();
                titleInput.setText(b.getTitle());
                descriptionInput.setText(b.getDescription());
                dateInput.setText(String.valueOf(b.getDateTimestamp()));
            }
        });

        viewModel.getSaveSuccess().observe(getViewLifecycleOwner(), success -> {
            if (Boolean.TRUE.equals(success)) {
                viewModel.clearSaveResult();
                NavHostFragment.findNavController(this).navigateUp();
            }
        });
        viewModel.getSaveError().observe(getViewLifecycleOwner(), msg -> {
            if (msg != null) {
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });

        view.findViewById(R.id.booking_edit_save).setOnClickListener(v -> save());
    }

    private void save() {
        String title = titleInput.getText() != null ? titleInput.getText().toString() : "";
        String description = descriptionInput.getText() != null ? descriptionInput.getText().toString() : "";
        long dateTimestamp;
        try {
            dateTimestamp = Long.parseLong(dateInput.getText() != null ? dateInput.getText().toString() : "0");
        } catch (NumberFormatException e) {
            dateTimestamp = System.currentTimeMillis();
        }
        viewModel.saveBooking(bookingId, title, description, dateTimestamp, carId);
    }
}
