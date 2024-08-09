package com.team4.unidine;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartFragment extends Fragment {

    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItemList = new ArrayList<>();
    private DatabaseReference cartRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        cartRecyclerView = view.findViewById(R.id.cart_recycler_view);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize Firebase
        cartRef = FirebaseDatabase.getInstance().getReference("cart_items");

        cartAdapter = new CartAdapter(cartItemList, this);
        cartRecyclerView.setAdapter(cartAdapter);

        // Load cart items
        loadCartItems();

        return view;
    }

    private void loadCartItems() {
        cartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("CartFragment", "Data snapshot: " + dataSnapshot.toString());
                cartItemList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Map<String, Object> cartItemMap = (Map<String, Object>) snapshot.getValue();
                    if (cartItemMap != null) {
                        String id = snapshot.getKey();
                        String name = (String) cartItemMap.get("name");
                        double price = ((Number) cartItemMap.get("price")).doubleValue();
                        int quantity = ((Long) cartItemMap.get("quantity")).intValue();
                        int imageResId = ((Long) cartItemMap.get("imageResId")).intValue();

                        // Add item to list
                        CartItem cartItem = new CartItem(id, name, price, quantity, imageResId);
                        cartItemList.add(cartItem);
                    }
                }

                // Notify adapter to update the RecyclerView
                cartAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to load cart items", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void removeCartItem(String itemId) {
        cartRef.child(itemId).removeValue();
    }
}
