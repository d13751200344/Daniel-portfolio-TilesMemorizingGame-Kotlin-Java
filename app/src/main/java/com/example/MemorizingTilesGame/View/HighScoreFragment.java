package com.example.MemorizingTilesGame.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.assignment3.R;
import com.example.assignment3.databinding.FragmentHighScoreBinding;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap;


public class HighScoreFragment extends Fragment {

    private FragmentHighScoreBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment and view binding setup
        binding = FragmentHighScoreBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences preferences = requireActivity().getSharedPreferences("HighScores", Context.MODE_PRIVATE);

        // Retrieve all usernames and scores
        Map<String, ?> allEntries = preferences.getAll();

        // Convert map entries to list for sorting
        // Create a new list to hold the entries
        List<Map.Entry<String, Integer>> list = new LinkedList<>();

        // Add each entry from the SharedPreferences to the list
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (entry.getValue() instanceof Integer) {
                list.add(new AbstractMap.SimpleEntry<>(entry.getKey(), (Integer) entry.getValue()));
            }
        }

        // Sort the list by score in descending order
        Collections.sort(list, (o1, o2) -> (o2.getValue()).compareTo(o1.getValue()));

        // Display top three scores if available
        int count = 0;
        for (Map.Entry<String, Integer> entry : list) {
            String username = entry.getKey();
            int score = entry.getValue();
            // Display username and score in your TextViews or any other UI components
            if (count == 0) {
                binding.firstScoreTextView.setText(username + ": " + score);
            } else if (count == 1) {
                binding.secondScoreTextView.setText(username + ": " + score);
            } else if (count == 2) {
                binding.thirdScoreTextView.setText(username + ": " + score);
            }
            count++;
            if (count >= 3) {
                break; // Display only top three scores
            }
        }
    }
}