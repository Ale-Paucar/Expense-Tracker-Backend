package com.alepaucar.expense_tracker.model;

public enum CategoryType {
    INCOME("income")
    ,EXPENSE("expense");

    private String value;

    CategoryType(String value){
        this.value=value;
    }

    public static CategoryType from(String value) {
        //depending on the string sent by the client...
        return switch (value.toLowerCase()) {
            case "income" -> INCOME;
            case "expense" -> EXPENSE;
            default -> throw new IllegalArgumentException("Invalid category type");
        };
    }

    public String getValue() {
        return value;
    }

}
