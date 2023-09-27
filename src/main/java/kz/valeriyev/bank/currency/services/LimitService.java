package kz.valeriyev.bank.currency.services;

import kz.valeriyev.bank.currency.entities.Limit;

public interface LimitService {
    Limit addNewLimit(Limit limit);
}
