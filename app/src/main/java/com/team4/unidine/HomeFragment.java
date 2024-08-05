package com.team4.unidine;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ImageButton tandooriButton = view.findViewById(R.id.tandoori_button);
        ImageButton beefButton = view.findViewById(R.id.beef_button);
        ImageButton friedRiceButton = view.findViewById(R.id.fried_rice_button);
        ImageButton coffeeButton = view.findViewById(R.id.coffee_button);
        ImageButton chaiButton = view.findViewById(R.id.chai_button);

        tandooriButton.setOnClickListener(v -> openFoodDetailActivity(R.drawable.tandoor, "Tandoori Chicken", 18.90));
        beefButton.setOnClickListener(v -> openFoodDetailActivity(R.drawable.beef, "Beef Roast", 80.00));
        friedRiceButton.setOnClickListener(v -> openFoodDetailActivity(R.drawable.friedrice, "Chicken Fried Rice", 130.00));
        coffeeButton.setOnClickListener(v -> openFoodDetailActivity(R.drawable.coffee, "Coffee", 10.00));
        chaiButton.setOnClickListener(v -> openFoodDetailActivity(R.drawable.chai, "Chai", 10.00));

        return view;
    }

    private void openFoodDetailActivity(int imageResId, String name, double price) {
        Intent intent = new Intent(getActivity(), FoodDetailActivity.class);
        intent.putExtra("food_image", imageResId);
        intent.putExtra("food_name", name);
        intent.putExtra("food_price", price);
        startActivity(intent);
    }
}
