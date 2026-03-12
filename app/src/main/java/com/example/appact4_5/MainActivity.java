package com.example.appact4_5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView google_map, google_play;
    private Button buttonNext;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageView menuIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // 2. Load the layout that contains the Drawer
        setContentView(R.layout.activity_main_drawer);

        try {
            // 3. Initialize Views
            google_map = findViewById(R.id.GoogleMap);
            google_play = findViewById(R.id.GooglePlay);
            buttonNext = findViewById(R.id.buttonNext);
            drawerLayout = findViewById(R.id.drawer_layout);
            navigationView = findViewById(R.id.navigation_view);
            menuIcon = findViewById(R.id.imageView);

            // 4. Set Click Listeners
            if (google_map != null) google_map.setOnClickListener(this);
            if (google_play != null) google_play.setOnClickListener(this);
            if (buttonNext != null) buttonNext.setOnClickListener(v -> openActivity2());

            // 5. Drawer Toggle Logic
            if (menuIcon != null && drawerLayout != null) {
                menuIcon.setOnClickListener(view -> {
                    if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        drawerLayout.openDrawer(GravityCompat.START);
                    } else {
                        drawerLayout.closeDrawer(GravityCompat.START);
                    }
                });
            }

            // 6. Navigation Component Setup
            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.navHostFragment);

            if (navHostFragment != null && navigationView != null) {
                NavController navController = navHostFragment.getNavController();
                NavigationUI.setupWithNavController(navigationView, navController);
            }

            // 7. Modern Back Press Handling (Fix for your dependency version)
            getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        drawerLayout.closeDrawer(GravityCompat.START);
                    } else {
                        setEnabled(false); // Disable this callback and let system handle back
                        onBackPressed();
                    }
                }
            });

        } catch (Exception e) {
            Toast.makeText(this, "Setup Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void openActivity2() {
        startActivity(new Intent(this, Activity2.class));
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.GoogleMap) {
            startActivity(new Intent(this, GoogleMap.class));
        } else if (id == R.id.GooglePlay) {
            startActivity(new Intent(this, BottomNav.class));
        }
    }
}