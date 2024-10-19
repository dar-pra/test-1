package entities;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class Expense {

    private String id;
    private double amount;
    private User paidBy;
    private List<Split> splits;
    private ExpenseMetadata expenseMetadata;

    public Expense(double amount, User paidBy, List<Split> splits, ExpenseMetadata expenseMetadata) {
        this.amount = amount;
        this.paidBy = paidBy;
        this.splits = splits;
        this.expenseMetadata = expenseMetadata;
    }

    public abstract boolean validate();

}
