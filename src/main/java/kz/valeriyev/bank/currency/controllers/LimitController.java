package kz.valeriyev.bank.currency.controllers;

import kz.valeriyev.bank.currency.entities.Limit;
import kz.valeriyev.bank.currency.services.LimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "limit")
public class LimitController {
    @Autowired
    private LimitService limitService;

    @PostMapping
    public Limit addNewLimit(@RequestBody Limit limit){
        return limitService.addNewLimit(limit);
    }

}
