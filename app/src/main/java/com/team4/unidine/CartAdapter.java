package com.team4.unidine;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private final List<CartItem> cartItems;
    private final CartFragment cartFragment;

    public CartAdapter(List<CartItem> cartItems, CartFragment cartFragment) {
        this.cartItems = cartItems;
        this.cartFragment = cartFragment;
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
        holder.itemName.setText(item.getName());
        holder.itemQuantity.setText("Quantity: " + item.getQuantity());

        // Retrieve price and quantity from CartItem
        double price = item.getPrice();
        int quantity = item.getQuantity();

        // Calculate total price
        double totalPrice = price * quantity;
        holder.itemTotalPrice.setText("Total: ₹" + String.format("%.2f", totalPrice));

        // Load image using resource ID
        holder.itemImage.setImageResource(item.getImageResId());

        holder.removeItemIcon.setOnClickListener(v -> {
            String itemId = item.getId(); // Retrieve the unique ID
            cartFragment.removeCartItem(itemId);
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage, removeItemIcon;
        TextView itemName, itemQuantity, itemTotalPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.cart_item_image);
            itemName = itemView.findViewById(R.id.cart_item_name);
            itemQuantity = itemView.findViewById(R.id.cart_item_quantity);
            itemTotalPrice = itemView.findViewById(R.id.cart_item_total_price);
            removeItemIcon = itemView.findViewById(R.id.remove_item_icon);
        }
    }
}
