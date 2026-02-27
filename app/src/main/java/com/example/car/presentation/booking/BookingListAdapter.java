package com.example.car.presentation.booking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.car.R;
import com.example.car.domain.model.Booking;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class BookingListAdapter extends ListAdapter<Booking, BookingListAdapter.ViewHolder> {

    private final OnBookingActionsListener listener;

    public interface OnBookingActionsListener {
        void onBookingClick(Booking booking);
        void onBookingDelete(Booking booking);
    }

    public BookingListAdapter(@NonNull OnBookingActionsListener listener) {
        super(new DiffUtil.ItemCallback<Booking>() {
            @Override
            public boolean areItemsTheSame(@NonNull Booking oldItem, @NonNull Booking newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Booking oldItem, @NonNull Booking newItem) {
                return oldItem.equals(newItem);
            }
        });
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Booking b = getItem(position);
        holder.title.setText(b.getTitle());
        holder.description.setText(b.getDescription());
        holder.date.setText(new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(new Date(b.getDateTimestamp())));
        holder.itemView.setOnClickListener(v -> listener.onBookingClick(b));
        View deleteBtn = holder.itemView.findViewById(R.id.item_booking_delete);
        if (deleteBtn != null) {
            deleteBtn.setOnClickListener(v -> listener.onBookingDelete(b));
        }
    }

    static final class ViewHolder extends RecyclerView.ViewHolder {
        final TextView title;
        final TextView description;
        final TextView date;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_booking_title);
            description = itemView.findViewById(R.id.item_booking_description);
            date = itemView.findViewById(R.id.item_booking_date);
        }
    }
}
