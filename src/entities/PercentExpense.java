package entities;

import java.util.List;

public class PercentExpense extends Expense {

    public PercentExpense(double amount, User paidBy, List<Split> splits, ExpenseMetadata expenseMetadata) {
        super(amount, paidBy, splits, expenseMetadata);
    }

    @Override
    public boolean validate() {
        for (Split split: getSplits()) {
            if (!(split instanceof PercentSplit)) {
                return false;
            }
        }

        double totalPercent = 100;
        double totalSplitPercent = 0;
        for (Split split: getSplits()) {
            PercentSplit percentSplit = (PercentSplit) split;
            totalSplitPercent += percentSplit.getPercent();
        }

        return totalPercent ==  totalSplitPercent;
    }

}
