package com.example.appact4_5;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView google_map, google_play;
    private DrawerLayout drawerLayout;

    // Notification variables
    private NotificationManagerCompat notificationManagerCompat;
    private Notification notification;
    private static final int NOTIF_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main_drawer);

        try {
            // 2. Initialize Views
            google_map = findViewById(R.id.GoogleMap);
            google_play = findViewById(R.id.GooglePlay);
            drawerLayout = findViewById(R.id.drawer_layout);

            // Local variables to clear "Field can be converted to local" warnings
            Button buttonNext = findViewById(R.id.buttonNext);
            NavigationView navigationView = findViewById(R.id.navigation_view);
            ImageView menuIcon = findViewById(R.id.imageView);

            // 3. Set Click Listeners
            if (google_map != null) google_map.setOnClickListener(this);
            if (google_play != null) google_play.setOnClickListener(this);
            if (buttonNext != null) buttonNext.setOnClickListener(v -> openActivity2());

            // 4. Drawer Toggle Logic
            if (menuIcon != null && drawerLayout != null) {
                menuIcon.setOnClickListener(view -> {
                    if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        drawerLayout.openDrawer(GravityCompat.START);
                    } else {
                        drawerLayout.closeDrawer(GravityCompat.START);
                    }
                });
            }

            // 5. Navigation Component Setup
            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.navHostFragment);

            if (navHostFragment != null && navigationView != null) {
                NavController navController = navHostFragment.getNavController();
                NavigationUI.setupWithNavController(navigationView, navController);
            }

            // 6. Notification Setup
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("MyCh", "My Channel", NotificationManager.IMPORTANCE_DEFAULT);
                NotificationManager manager = getSystemService(NotificationManager.class);
                if (manager != null) manager.createNotificationChannel(channel);
            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "MyCh")
                    .setSmallIcon(android.R.drawable.btn_star_big_on)
                    .setContentTitle("Message Received")
                    .setContentText("You have received a message")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            notification = builder.build();
            notificationManagerCompat = NotificationManagerCompat.from(this);

            // 7. Back Press Handling
            getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        drawerLayout.closeDrawer(GravityCompat.START);
                    } else {
                        setEnabled(false);
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

    public void sample(View v) {
        Toast.makeText(MainActivity.this, "This is a TOAST MESSAGE", Toast.LENGTH_LONG).show();
    }

    // UPDATED: Logic to handle Android 13+ permission requirements
    public void notif(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, NOTIF_PERMISSION_CODE);
                return;
            }
        }
        showNotification();
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private void showNotification() {
        // Double check for Android 13 permission to satisfy the IDE warning
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManagerCompat.notify(1, notification);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == NOTIF_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                showNotification();
            } else {
                Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}