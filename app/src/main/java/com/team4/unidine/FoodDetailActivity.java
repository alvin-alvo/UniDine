package com.team4.unidine;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class FoodDetailActivity extends AppCompatActivity {

    ImageView foodImage, backButton;
    TextView foodName, foodPrice, quantityText, totalPriceText;
    Button decreaseQuantityButton, increaseQuantityButton, cartButton;
    int quantity = 1;
    double price;

    // Firebase references
    private DatabaseReference cartRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        // Initialize views
        foodImage = findViewById(R.id.food_image);
        foodName = findViewById(R.id.food_name);
        foodPrice = findViewById(R.id.food_price);
        quantityText = findViewById(R.id.quantity_text);
        totalPriceText = findViewById(R.id.total_price);
        decreaseQuantityButton = findViewById(R.id.decrease_quantity_button);
        increaseQuantityButton = findViewById(R.id.increase_quantity_button);
        backButton = findViewById(R.id.back_button);
        cartButton = findViewById(R.id.cart);

        // Initialize Firebase Database reference
        cartRef = FirebaseDatabase.getInstance().getReference("cart_items");

        // Retrieve the data passed from the previous activity
        int imageResId = getIntent().getIntExtra("food_image", -1);
        String name = getIntent().getStringExtra("food_name");
        price = getIntent().getDoubleExtra("food_price", 0.0);

        // Set the image, name, and price
        if (imageResId != -1) {
            foodImage.setImageResource(imageResId);
        } else {
            foodImage.setImageResource(R.drawable.default_image); // Use a default placeholder image
        }

        foodName.setText(name);
        foodPrice.setText("₹" + price);

        // Set initial quantity and total price
        updateQuantityDisplay();
        updateTotalPrice();

        // Set up button click listeners
        decreaseQuantityButton.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                updateQuantityDisplay();
                updateTotalPrice();
            }
        });

        increaseQuantityButton.setOnClickListener(v -> {
            quantity++;
            updateQuantityDisplay();
            updateTotalPrice();
        });

        // Set up back button click listener
        backButton.setOnClickListener(v -> finish());

        // Add item to cart when cart button is clicked
        cartButton.setOnClickListener(v -> {
            addToCart(imageResId, name, price, quantity);
        });
    }

    private void addToCart(int imageResId, String name, double price, int quantity) {
        // Generate a unique ID for each cart item
        String itemId = cartRef.push().getKey();

        if (itemId != null) {
            // Create a map for the cart item
            Map<String, Object> cartItem = new HashMap<>();
            cartItem.put("imageResId", imageResId);
            cartItem.put("name", name);
            cartItem.put("price", price);
            cartItem.put("quantity", quantity);

            // Add the item to the cart in Firebase
            cartRef.child(itemId).setValue(cartItem).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Failed to add item to cart", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    // Method to update quantity display
    private void updateQuantityDisplay() {
        quantityText.setText(String.valueOf(quantity));
    }

    // Method to update total price display
    private void updateTotalPrice() {
        double totalPrice = price * quantity;
        totalPriceText.setText("₹" + String.format("%.2f", totalPrice));
    }
}
