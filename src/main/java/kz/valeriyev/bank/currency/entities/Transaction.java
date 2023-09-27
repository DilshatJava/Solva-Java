package kz.valeriyev.bank.currency.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction_bank")
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int account_from;
    private int account_to;
    private String currency_shortname;
    private Double sum;
    private String expenseCategory;
    private LocalDateTime dateTime;
    private Boolean limitExceeded;
}
