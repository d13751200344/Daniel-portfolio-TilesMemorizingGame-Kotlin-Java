package com.example.MemorizingTilesGame.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.assignment3.R;
import com.example.assignment3.databinding.FragmentWelcomeBinding;

public class WelcomeFragment extends Fragment {

    private FragmentWelcomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment and view binding setup
        binding = FragmentWelcomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.saveNameButton.setOnClickListener(v -> {
            if (binding.userNameInput.getText().toString().isEmpty()) {
                binding.userNameInput.setError("Please enter a name");
                return;
            }
            String userName = binding.userNameInput.getText().toString();
            Bundle bundle = new Bundle();
            bundle.putString("userName", userName);
            GameFragment gameFragment = new GameFragment();
            gameFragment.setArguments(bundle);

            // Replace the current fragment with GameFragment
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.constraintLayoutInMain, gameFragment);
            transaction.addToBackStack(null);  // Add the transaction to the back stack
            transaction.commit();
        });
    }
}