package com.example.MemorizingTilesGame.Model;

public class Player {
    String name;
    Integer score;

    // Constructor
    public Player(String name, Integer score) {
        this.name = name;
        this.score = score;
    }

    // Getter
    public String getName() {
        return name;
    }
    public Integer getScore() {
        return score;
    }

    // Setter
    public void setName(String name) {
        this.name = name;
    }
    public void setScore(Integer score) {
        this.score = score;
    }
}
