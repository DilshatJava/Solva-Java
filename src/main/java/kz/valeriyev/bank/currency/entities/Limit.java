package kz.valeriyev.bank.currency.entities;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;

@Entity
@Table(name = "limit_bank")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Limit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String expenseCategory;
    private double limit_sum;
    private LocalDateTime date;
    private String limit_currency_shortname;
}
