package com.alepaucar.expense_tracker.model;

public enum TransactionType {
    INCOME("income")
    ,EXPENSE("expense");

    private String value;

    TransactionType(String value){
        this.value=value;
    }

    public static TransactionType from(String value) {
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
