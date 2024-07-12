package com.example.quizmate.entity;

public class Quiz {
    private String name;
    private String description;
    private int max_time;

    public Quiz() {
        // Default constructor required for calls to DataSnapshot.getValue(Quiz.class)
    }

    public Quiz(String name, String description, int max_time) {
        this.name = name;
        this.description = description;
        this.max_time = max_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMax_time() {
        return max_time;
    }

    public void setMax_time(int max_time) {
        this.max_time = max_time;
    }
}
