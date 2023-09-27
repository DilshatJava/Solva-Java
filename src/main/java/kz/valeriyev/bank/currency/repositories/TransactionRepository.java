package kz.valeriyev.bank.currency.repositories;

import jakarta.transaction.Transactional;
import kz.valeriyev.bank.currency.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    @Query("SELECT COALESCE(SUM(t.sum), 0) FROM Transaction t WHERE t.expenseCategory = :expenseCategory " +
            "AND MONTH(t.dateTime) = MONTH(CURRENT_DATE())")
    Double getTotalSpentThisMonthByExpenseCategory(String expenseCategory);

    List<Transaction> findByLimitExceededTrueAndExpenseCategory(String category);

}
