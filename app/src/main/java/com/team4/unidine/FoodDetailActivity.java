package com.team4.unidine;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class FoodDetailActivity extends AppCompatActivity {

    ImageView foodImage, backButton;
    TextView foodName, foodPrice, quantityText, totalPriceText;
    Button decreaseQuantityButton, increaseQuantityButton, cartButton;
    int quantity = 1;
    double price;

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
        backButton = findViewById(R.id.back_button); // Initialize back button
        cartButton = findViewById(R.id.cart);

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
        backButton.setOnClickListener(v -> {
            // Navigate back to the home fragment
            finish(); // Or use FragmentTransaction to navigate to the home fragment if using fragments
        });

        cartButton.setOnClickListener(v -> {
            // Handle add to cart action
        });
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
