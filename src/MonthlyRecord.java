class MonthlyRecord {
    int quantity;
    int sumOfOne;
    String name;
    boolean isExpense;

    MonthlyRecord(String nameValue, boolean isExpenseValue, int quantityValue, int sumOfOneValue) {
        name = nameValue;
        quantity = quantityValue;
        sumOfOne = sumOfOneValue;
        isExpense = isExpenseValue;
    }
}