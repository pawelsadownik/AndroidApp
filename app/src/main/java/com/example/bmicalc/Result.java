package com.example.bmicalc;

public class Result {

    private float BMI;
    private String description;
    private int amount;

    public Result() {
    }

    public Result(float BMI, String description, int amount) {
        this.BMI = BMI;
        this.description = description;
        this.amount = amount;
    }

    public float getBMI() {
        return BMI;
    }

    public void setBMI(float BMI) {
        this.BMI = BMI;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
