package com.eastflag.question;

public class QuestionRoomConfig {
    //
    private String questionType;
    private int questionLevel;
    private int questionSolveSecond;
    private int watingSecond;
    private int maxParticipant;

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public int getQuestionLevel() {
        return questionLevel;
    }

    public void setQuestionLevel(int questionLevel) {
        this.questionLevel = questionLevel;
    }

    public int getQuestionSolveSecond() {
        return questionSolveSecond;
    }

    public void setQuestionSolveSecond(int questionSolveSecond) {
        this.questionSolveSecond = questionSolveSecond;
    }

    public int getWatingSecond() {
        return watingSecond;
    }

    public void setWatingSecond(int watingSecond) {
        this.watingSecond = watingSecond;
    }

    public int getMaxParticipant() {
        return maxParticipant;
    }

    public void setMaxParticipant(int maxParticipant) {
        this.maxParticipant = maxParticipant;
    }
}
