package com.webservice.history;


import com.webservice.entity.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionsHistory {

    private static int totalTransactions;
    private static double depositAmount;
    private static double withdrawalAmount;
    private static List<String> customers = new ArrayList<>();

    public static List<String> getCustomers() {
        return customers;
    }

    public static void setCustomers(List<String> customers) {
        TransactionsHistory.customers = customers;
    }

    public static void addCustomer(String customerName) {
        if (customers.contains(customerName)) {
            return;
        }
        customers.add(customerName);
    }

    public static void increaseTransactions() {
        totalTransactions++;
    }

    public static void decreaseTransactions() {
        totalTransactions--;
    }

    public static void increaseAmount(Transaction transaction) {

        switch (transaction.getTransactionType()) {
            case "deposit":
                depositAmount += transaction.getSum();
                break;
            case "withdrawal":
                withdrawalAmount += transaction.getSum();
                break;
        }

    }

    public static void decreaseAmount(Transaction transaction) {

        switch (transaction.getTransactionType()) {
            case "deposit":
                depositAmount -= transaction.getSum();
                break;
            case "withdrawal":
                withdrawalAmount -= transaction.getSum();
                break;
        }

    }

    public static void updateAmount(Transaction oldTransaction, Transaction newTransaction) {
        decreaseAmount(oldTransaction);
        increaseAmount(newTransaction);
    }

    public static int getTotalTransactions() {
        return totalTransactions;
    }

    public static void setTotalTransactions(int totalTransactions) {
        TransactionsHistory.totalTransactions = totalTransactions;
    }

    public static double getDepositAmount() {
        return depositAmount;
    }

    public static void setDepositAmount(double depositAmount) {
        TransactionsHistory.depositAmount = depositAmount;
    }

    public static double getWithdrawalAmount() {
        return withdrawalAmount;
    }

    public static void setWithdrawalAmount(double withdrawalAmount) {
        TransactionsHistory.withdrawalAmount = withdrawalAmount;
    }
}
