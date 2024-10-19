package service;

import entities.EqualExpense;
import entities.EqualSplit;
import entities.ExactExpense;
import entities.Expense;
import entities.ExpenseMetadata;
import entities.ExpenseType;
import entities.PercentExpense;
import entities.PercentSplit;
import entities.Split;
import entities.User;

import java.util.List;

import static entities.ExpenseType.*;

public class ExpenseService {

    public static Expense createExpense(ExpenseType expenseType, double amount, User paidBy, List<Split> splits,
                                        ExpenseMetadata expenseMetadata) {
        switch (expenseType) {
            case EXACT:
                return new ExactExpense(amount, paidBy, splits, expenseMetadata);
            case EQUAL:
                int totalSplits = splits.size();
                double splitAmount = ((double) Math.round(amount * 100 / totalSplits)) / 100.0;
                for (Split split: splits) {
                    split.setAmount(splitAmount);
                }
                splits.get(0).setAmount(splitAmount + (amount - splitAmount * totalSplits));
                return new EqualExpense(amount, paidBy, splits, expenseMetadata);
            case PERCENT:
                for (Split split: splits) {
                    PercentSplit percentSplit = (PercentSplit) split;
                    split.setAmount((amount * percentSplit.getPercent()) / 100.0);
                }
                return new PercentExpense(amount, paidBy, splits, expenseMetadata);
            default:
                return null;
        }
    }
}
