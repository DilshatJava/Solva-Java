package kz.valeriyev.bank.currency.repositories;

import jakarta.transaction.Transactional;
import kz.valeriyev.bank.currency.entities.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
public interface LimitRepository extends JpaRepository<Limit, Long> {
    Limit findTopByExpenseCategoryOrderByDateDesc(String expenseCategory);

    Limit findTopByOrderByDateDesc();
}
