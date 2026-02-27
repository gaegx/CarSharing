package com.example.car.presentation.cars;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.car.R;
import com.example.car.domain.model.Car;

public final class CarListAdapter extends ListAdapter<Car, CarListAdapter.ViewHolder> {

    private final OnCarClickListener listener;

    public interface OnCarClickListener {
        void onCarClick(Car car);
    }

    public CarListAdapter(@NonNull OnCarClickListener listener) {
        super(new DiffUtil.ItemCallback<Car>() {
            @Override
            public boolean areItemsTheSame(@NonNull Car oldItem, @NonNull Car newItem) {
                return oldItem.getId().equals(newItem.getId());
            }

            @Override
            public boolean areContentsTheSame(@NonNull Car oldItem, @NonNull Car newItem) {
                return oldItem.equals(newItem);
            }
        });
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Car car = getItem(position);
        holder.name.setText(car.getName());
        holder.description.setText(car.getDescription());
        holder.price.setText(holder.itemView.getContext().getString(R.string.price_per_min, car.getPricePerMinute()));
        holder.itemView.setOnClickListener(v -> listener.onCarClick(car));
    }

    static final class ViewHolder extends RecyclerView.ViewHolder {
        final TextView name;
        final TextView description;
        final TextView price;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_car_name);
            description = itemView.findViewById(R.id.item_car_description);
            price = itemView.findViewById(R.id.item_car_price);
        }
    }
}
