package kz.valeriyev.bank.currency.controllers;

import kz.valeriyev.bank.currency.entities.Limit;
import kz.valeriyev.bank.currency.entities.Transaction;
import kz.valeriyev.bank.currency.repositories.LimitRepository;
import kz.valeriyev.bank.currency.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private LimitRepository limitRepository;

    @GetMapping(value = "/{category}")
    public List<Transaction> getAllWhereSumUpLimit(@PathVariable String category){
        return transactionService.getAllUpLimit(category);
    }

    @PostMapping
    public Transaction addTransaction(@RequestBody Transaction transaction){
        return transactionService.addTransaction(transaction);
    }

}
