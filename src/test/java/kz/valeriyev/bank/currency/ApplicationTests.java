package kz.valeriyev.bank.currency;

import kz.valeriyev.bank.currency.controllers.LimitController;
import kz.valeriyev.bank.currency.controllers.TransactionController;
import kz.valeriyev.bank.currency.entities.Limit;
import kz.valeriyev.bank.currency.entities.Transaction;
import kz.valeriyev.bank.currency.repositories.LimitRepository;
import kz.valeriyev.bank.currency.repositories.TransactionRepository;
import kz.valeriyev.bank.currency.services.LimitService;
import kz.valeriyev.bank.currency.services.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ApplicationTests {

	@Autowired
	private TransactionController transactionController;

	@Autowired
	private LimitController limitController;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private LimitRepository limitRepository;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private LimitService limitService;

	@Test
	void contextLoads() {
		assertNotNull(transactionController);
		assertNotNull(limitController);
		assertNotNull(transactionService);
		assertNotNull(limitService);
	}

	@Test
	void testAddTransaction() {

		Transaction transaction = new Transaction();
		transaction.setId(1L);
		transaction.setAccount_from(1);
		transaction.setAccount_to(2);
		transaction.setCurrency_shortname("USD");
		transaction.setDateTime(LocalDateTime.now());
		transaction.setSum(100.00);
		transaction.setExpenseCategory("product");

		transactionRepository.deleteAll();
		Transaction result = transactionService.addTransaction(transaction);

		assertNotNull(result);
		assertNotEquals(0, result.getId());
		assertEquals(transaction.getAccount_from(), result.getAccount_from());
		assertEquals(transaction.getAccount_to(), result.getAccount_to());
		assertEquals(transaction.getCurrency_shortname(), result.getCurrency_shortname());
		assertEquals(transaction.getSum(), result.getSum());
		assertEquals(transaction.getExpenseCategory(), result.getExpenseCategory());
		assertNotNull(result.getDateTime());

	}

	@Test
	void testAddNewLimit() {

		Limit limit = new Limit();
		limit.setId(1L);
		limit.setLimit_sum(2000.00);
		limit.setExpenseCategory("product");
		limit.setDate(LocalDateTime.now());
		limit.setLimit_currency_shortname("USD");

		limitRepository.deleteAll();
		Limit result = limitService.addNewLimit(limit);

		Assertions.assertNotNull(result);
		Assertions.assertNotEquals(0, result.getId());
		Assertions.assertEquals(limit.getExpenseCategory(), result.getExpenseCategory());
		Assertions.assertEquals(limit.getLimit_sum(), result.getLimit_sum());
		Assertions.assertNotNull(result.getDate());
		Assertions.assertNotNull(result.getLimit_currency_shortname());
	}

	@Test
	void testGetAllUpLimit() {
		// Создаем несколько транзакций, некоторые из которых превышают лимит
		Transaction transaction1 = new Transaction();
		transaction1.setExpenseCategory("product");
		transaction1.setSum(1500.00);
		transactionRepository.deleteAll();
		transactionService.addTransaction(transaction1);

		Transaction transaction2 = new Transaction();
		transaction2.setExpenseCategory("product");
		transaction2.setSum(600.00);
		transactionService.addTransaction(transaction2);

		Transaction transaction3 = new Transaction();
		transaction3.setExpenseCategory("product");
		transaction3.setSum(2000.00);
		transactionService.addTransaction(transaction3);

		// Получаем список транзакций, превышающих лимит
		List<Transaction> exceededLimitTransactions = transactionService.getAllUpLimit("product");

		// Проверяем, что список не пустой и содержит ожидаемое количество транзакций
		assertNotNull(exceededLimitTransactions);
		assertEquals(2, exceededLimitTransactions.size());

		// Проверяем, что все транзакции в списке действительно превышают лимит
		for (Transaction transaction : exceededLimitTransactions) {
			assertTrue(transaction.getLimitExceeded());
		}
	}

}
