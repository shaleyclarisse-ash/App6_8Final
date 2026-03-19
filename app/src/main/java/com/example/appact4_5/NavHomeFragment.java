package com.example.appact4_5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NavHomeFragment extends Fragment {

    public NavHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nav_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnToast = view.findViewById(R.id.btn_toast);
        if (btnToast != null) {
            btnToast.setOnClickListener(v -> {
                Toast.makeText(requireContext(), "THIS IS A TOAST MESSAGE", Toast.LENGTH_LONG).show();
            });
        }
    }
}