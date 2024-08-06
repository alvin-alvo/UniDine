package com.team4.unidine;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<CartItem> cartItems;

    public CartAdapter(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        holder.foodName.setText(item.getName());
        holder.quantity.setText("Quantity: " + item.getQuantity());
        holder.foodImage.setImageResource(item.getImageResId());

        // Calculate and set total price for the item
        double totalPrice = item.getPrice() * item.getQuantity();
        holder.totalPrice.setText(String.format("%.2f", totalPrice));
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView foodImage;
        TextView foodName, totalPrice, quantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.cart_item_image);
            foodName = itemView.findViewById(R.id.cart_item_name);
            totalPrice = itemView.findViewById(R.id.cart_item_total_price);
            quantity = itemView.findViewById(R.id.cart_item_quantity);
        }
    }
}
