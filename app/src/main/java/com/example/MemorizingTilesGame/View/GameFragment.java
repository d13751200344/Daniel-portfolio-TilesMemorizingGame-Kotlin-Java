package com.example.MemorizingTilesGame.View;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.assignment3.R;
import com.example.assignment3.databinding.FragmentGameBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameFragment extends Fragment {

    private FragmentGameBinding binding;
    private boolean gameActive = false;
    private int[] gridState = new int[36];
    private List<Integer> initialTiles;
    private int clickedTiles = 0;

    private int score = 0;
    private int selectTimeRemaining = 0; // Remaining time for tile selection phase
    private int hideTimeRemaining = 0; // Remaining time for hiding tiles phase
    private CountDownTimer selectTilesTimer; // Timer for tile selection phase
    private CountDownTimer hideTilesTimer; // Timer for hiding tiles phase

    private int successfulRounds = 0; // Counter to track successful rounds

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment and view binding setup
        binding = FragmentGameBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    // This method is called after the view is created and ready to be used
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get the user input TextView from WelcomeFragment
        // Retrieve the user input passed from WelcomeFragment
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("userName")) {
            String userInput = bundle.getString("userName");
            binding.userNameTextView.setText(userInput);
        }


        if (!gameActive) {
            binding.startBtn.setOnClickListener(v -> {
                score = 0; // Reset score
                successfulRounds = 0; // Reset successful rounds counter
                binding.roundsTextView.setText("Successful Rounds: " + successfulRounds); // Update successful rounds counter
                // Cancel timers if they are not null and active
                if (hideTilesTimer != null) {
                    hideTilesTimer.cancel();
                    binding.timerForTilesHideTextView.setText("Time for Hiding: 3s.");
                }
                if (selectTilesTimer != null) {
                    selectTilesTimer.cancel();
                    binding.timerForSelectTextView.setText("Time for checked tiles selection: 5s.");
                }


                setUpGrid();

                // Start the game
                startGame();

                // Start timers
                startHideTimer();

                binding.scoreTextView.setText("Your Score: " + score); // Update score
            });
        }
    }

    private void startHideTimer() {
        hideTimeRemaining = 3; // 3 seconds for hiding tiles phase
        hideTilesTimer = new CountDownTimer(hideTimeRemaining * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                hideTimeRemaining--;
                binding.timerForTilesHideTextView.setText("Time for Hiding: " + hideTimeRemaining + "s.");
            }

            @Override
            public void onFinish() {
                for (int tileIndex : initialTiles) {
                    ImageView tile = (ImageView) binding.gridLayout.getChildAt(tileIndex);
                    tile.setImageResource(R.drawable.whitetile);
                }
                startSelectTimer(); // Start tile selection phase
                hideInitialTiles(); // Hide initial 4 tiles
                gameActive = true; // Set gameActive to true after hiding initial tiles
            }
        }.start();
    }
    private void startSelectTimer() {
        selectTimeRemaining = 5; // 5 seconds for tile selection phase
        selectTilesTimer = new CountDownTimer(selectTimeRemaining*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                selectTimeRemaining--;
                binding.timerForSelectTextView.setText("Time for checked tiles selection: " + selectTimeRemaining + "s.");
            }

            @Override
            public void onFinish() {
                gameActive = false; // Game over if time's up

                binding.timerForSelectTextView.setText("Time's up! Want to play again?");
            }
        }.start();
    }

    private void setUpGrid() {

        // Reset game state
        clickedTiles = 0;
        gameActive = false;

        for (int i = 0; i < gridState.length; i++) {
            gridState[i] = 0; // Initialize grid state
        }

        // Set click listeners for grid tiles
        for (int i = 0; i < binding.gridLayout.getChildCount(); i++) {
            ImageView tile = (ImageView) binding.gridLayout.getChildAt(i);
            tile.setTag(i); // Set tag to identify tile position
            tile.setImageResource(R.drawable.whitetile); // Set default tile image

            // Set click listener for each tile: clickTile method
            tile.setOnClickListener(this::clickTile);
        }
    }

    private void startGame() {
        initialTiles = generateInitialTiles(successfulRounds); // Generate 4 random tiles
        displayInitialTiles(); // Display initial 4 tiles
    }

    private List<Integer> generateInitialTiles(int succeedingRounds) {
        List<Integer> tiles = new ArrayList<>();
        for (int i = 0; i < 36; i++) {
            tiles.add(i);
        }
        Collections.shuffle(tiles);
        if(successfulRounds > 2) {
            return tiles.subList(0, 5); // Select 5 random tiles
        }
        return tiles.subList(0, 4); // Select 4 random tiles
    }

    private void displayInitialTiles() {
        for (int tileIndex : initialTiles) {
            ImageView tile = (ImageView) binding.gridLayout.getChildAt(tileIndex);
            tile.setImageResource(R.drawable.checktile);
        }
    }

    private void hideInitialTiles() {
        // Hide initial 4 tiles
        for (int tileIndex : initialTiles) {
            ImageView tile = (ImageView) binding.gridLayout.getChildAt(tileIndex);
            tile.setImageResource(R.drawable.whitetile);
        }
    }

    private void clickTile(View view) {
        // Check if game is active and not over
        if (!gameActive) return;

        // transform view to ImageView
        ImageView tile = (ImageView) view;
        // transform the tag of the ImageView to int to get the position of the tile
        int clickedTile = (int) tile.getTag();

        // Check if clicked tile is one of the initial tiles
        if (initialTiles.contains(clickedTile)) {
            tile.setImageResource(R.drawable.checktile);
            clickedTiles++;
        } else {
            // Game over if wrong tile clicked
            gameActive = false;
            tile.setImageResource(R.drawable.blacktile); // Indicate wrong tile
            // cancel the startSelectTimer
            selectTilesTimer.cancel();
            binding.timerForSelectTextView.setText("Wrong tile clicked! Game Over!");
        }

        if (clickedTiles == (successfulRounds > 2 ? 5 : 4)) {
            gameActive = false;

            selectTilesTimer.cancel();  // Cancel hide timer because player clicked all tiles
            successfulRounds++;
            binding.roundsTextView.setText("Successful Rounds: " + successfulRounds); // Update successful rounds counter
            score += (successfulRounds > 3 ? 20 : 10); // Adjust score based on successful rounds
            binding.scoreTextView.setText("Your Score: " + score);

            // Store username and score if username is entered
            if (!binding.userNameTextView.getText().toString().isEmpty()) {
                SharedPreferences preferences = requireActivity().getSharedPreferences("HighScores", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt(binding.userNameTextView.getText().toString(), score);
                editor.apply();
            }


            // keep playing!
            setUpGrid();
            startGame();
            startHideTimer();
            binding.timerForTilesHideTextView.setText("Time for Hiding: 3s.");
            binding.timerForSelectTextView.setText("Time for checked tiles selection: 5s.");
        }
    }
}