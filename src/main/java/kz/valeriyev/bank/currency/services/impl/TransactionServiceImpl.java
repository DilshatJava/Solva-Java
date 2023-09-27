package kz.valeriyev.bank.currency.services.impl;

import kz.valeriyev.bank.currency.entities.Limit;
import kz.valeriyev.bank.currency.entities.Transaction;
import kz.valeriyev.bank.currency.repositories.LimitRepository;
import kz.valeriyev.bank.currency.repositories.TransactionRepository;
import kz.valeriyev.bank.currency.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private LimitRepository limitRepository;

    @Override
    public List<Transaction> getAllUpLimit(String category) {
        return transactionRepository.findByLimitExceededTrueAndExpenseCategory(category);
    }

    @Override
    public Transaction addTransaction(Transaction transaction) {
        transaction.setDateTime(LocalDateTime.now());


        Limit lastLimit = limitRepository.findTopByExpenseCategoryOrderByDateDesc(transaction.getExpenseCategory());

        if(lastLimit == null){
            // Установка лимита по умолчанию
            Limit newLimit = Limit.builder()
                    .expenseCategory(transaction.getExpenseCategory())
                    .limit_sum(1000)
                    .date(LocalDateTime.now())
                    .limit_currency_shortname("USD")
                    .build();
            limitRepository.save(newLimit);

        }

        double limitAmount = lastLimit != null ? lastLimit.getLimit_sum() : 1000.00;
        Double totalSpent = transactionRepository.getTotalSpentThisMonthByExpenseCategory(transaction.getExpenseCategory());

        transaction.setLimitExceeded(totalSpent + transaction.getSum() > limitAmount);

        transactionRepository.save(transaction);

        return transaction;
    }


}
