package kz.valeriyev.bank.currency.services;

import kz.valeriyev.bank.currency.entities.Limit;
import kz.valeriyev.bank.currency.entities.Transaction;
import java.util.List;

public interface TransactionService {
    List<Transaction> getAllUpLimit(String category);
    Transaction addTransaction(Transaction transaction);
}
