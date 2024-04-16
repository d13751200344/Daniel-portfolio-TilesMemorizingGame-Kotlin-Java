package com.example.MemorizingTilesGame.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PlayerViewModel extends ViewModel {
    private MutableLiveData<String> playerName = new MutableLiveData<>();
    private MutableLiveData<Integer> playerScore = new MutableLiveData<>();

    public LiveData<String> getPlayerName() {
        return playerName;
    }

    public LiveData<Integer> getPlayerScore() {
        return playerScore;
    }

    public void setPlayerName(String name) {
        playerName.setValue(name);
    }

    public void setPlayerScore(int score) {
        playerScore.setValue(score);
    }
}