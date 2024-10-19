package orchestrator;

import entities.Expense;
import entities.ExpenseMetadata;
import entities.ExpenseType;
import entities.Split;
import entities.User;
import service.ExpenseService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseManager {
    List<Expense> expenses;
    Map<String, User> userMap;
    Map<String, Map<String, Double>> balanceSheet;

    public ExpenseManager() {
        expenses = new ArrayList<>();
        userMap = new HashMap<>();
        balanceSheet = new HashMap<>();
    }

    public void addUser(User user) {
        userMap.put(user.getId(), user);
        balanceSheet.put(user.getId(), new HashMap<>());
    }

    public void addExpense(ExpenseType expenseType, double amount, String paidBy, List<Split> splits,
                           ExpenseMetadata expenseMetadata) {
        Expense expense = ExpenseService.createExpense(expenseType, amount, userMap.get(paidBy), splits, expenseMetadata);
        expenses.add(expense);
        for (Split split: splits) {
            String paidTo = split.getUser().getId();
            Map<String, Double> balances = balanceSheet.get(paidBy);

            if (!balances.containsKey(paidTo)) {
               balances.put(paidTo, 0.0);
            }
            balances.put(paidTo, balances.get(paidTo) + split.getAmount());


            balances = balanceSheet.get(paidTo);

            if (!balances.containsKey(paidBy)) {
                balances.put(paidBy, 0.0);
            }
            balances.put(paidTo, balances.get(paidBy) - split.getAmount());

        }
    }

    public void showBalance() {
        for (Map.Entry<String, Map<String, Double>> allBalances: balanceSheet.entrySet()) {
            for (Map.Entry<String, Double> userBalance: allBalances.getValue().entrySet()) {
                if(userBalance.getValue() != 0) {
                    printBalance(allBalances.getKey(), userBalance.getKey(), userBalance.getValue());
                }
            }
        }
    }

    public void printBalance(String user1, String user2, double amount) {
        String name1 = userMap.get(user1).getName();
        String name2 = userMap.get(user2).getName();
        if (amount < 0) {
            System.out.println(name1 + " owes " + name2 + ": " + Math.abs(amount));
        } else if (amount > 0) {
            System.out.println(name2 + " owes " + name1 + ": " + Math.abs(amount));
        }
    }
}
