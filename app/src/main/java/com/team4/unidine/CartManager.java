package com.team4.unidine;

import android.content.Context;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import java.util.ArrayList;
import java.util.List;

public class CartManager {

    private static CartManager instance;
    private DatabaseReference cartRef;
    private List<CartItem> cartItems;

    private CartManager(Context context) {
        cartRef = FirebaseDatabase.getInstance().getReference().child("cart");
        cartItems = new ArrayList<>();
    }

    public static CartManager getInstance(Context context) {
        if (instance == null) {
            instance = new CartManager(context);
        }
        return instance;
    }

    public void getCartItems(ValueEventListener listener) {
        cartRef.addValueEventListener(listener);
    }

    public List<CartItem> getCartItemsSnapshot() {
        return cartItems;
    }

    // Call this method to set cart items from Firebase snapshot
    public void setCartItemsFromSnapshot(DataSnapshot dataSnapshot) {
        cartItems.clear();
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            CartItem item = snapshot.getValue(CartItem.class);
            cartItems.add(item);
        }
    }
}
