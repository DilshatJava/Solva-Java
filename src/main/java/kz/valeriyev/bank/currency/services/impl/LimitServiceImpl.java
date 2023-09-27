package kz.valeriyev.bank.currency.services.impl;

import kz.valeriyev.bank.currency.entities.Limit;
import kz.valeriyev.bank.currency.repositories.LimitRepository;
import kz.valeriyev.bank.currency.services.LimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LimitServiceImpl implements LimitService {
    @Autowired
    private LimitRepository limitRepository;

    @Override
    public Limit addNewLimit(Limit limit) {
        // Проверяем, был ли уже установлен лимит
        Limit existingLimit = limitRepository.findTopByExpenseCategoryOrderByDateDesc(limit.getExpenseCategory());
        if(existingLimit==null) {
            existingLimit=limit;
            existingLimit.setDate(LocalDateTime.now());
            existingLimit.setLimit_currency_shortname("USD");
            limitRepository.save(existingLimit);
        } else if(existingLimit.getLimit_sum() == 1000){
                // Устанавливаем текущую дату и время для нового лимита
                existingLimit.setLimit_sum(limit.getLimit_sum());
                existingLimit.setDate(LocalDateTime.now());
                existingLimit.setLimit_currency_shortname("USD");

            // Сохраняем новый лимит в базе данных
            limitRepository.save(existingLimit);
        }

        return existingLimit;
    }
}
