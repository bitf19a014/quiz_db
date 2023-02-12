package com.ultralegends.quiz;

public class QuizData {

    private String selectedOption;
    private String correctOption;
    private boolean isCorrect;

    public QuizData(String so, String co, boolean ic) {
        this.selectedOption = so;
        this.correctOption = co;
        this.isCorrect = ic;
    }

    public QuizData() {
        this.selectedOption = "";
        this.correctOption = "";
        this.isCorrect = false;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }

    public String getCorrectOption() {
        return correctOption;
    }

    public void setCorrectOption(String correctOption) {
        this.correctOption = correctOption;
    }

    public boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public boolean isOptionCorrect() {
        return isCorrect;
    }

    @Override
    public String toString() {
        return "Student [selectedOption=" + selectedOption + ", correctOption=" + correctOption + ", isCorrect=" + isCorrect + "]";
    }

}