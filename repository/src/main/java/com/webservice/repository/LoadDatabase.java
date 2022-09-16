package com.webservice.repository;

import com.webservice.entity.Transaction;
import com.webservice.history.TransactionsHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);


    @Bean
    CommandLineRunner initDatabase(TransactionRepository transactionRepository) {

        return args -> {
            Transaction transaction1 = new Transaction(1L, 4000, "Marry Poppins", "deposit");
            Transaction transaction2 = new Transaction(2L, 350, "Emma Watson", "withdrawal");

            TransactionsHistory.addCustomer(transaction1.getCustomerName());
            TransactionsHistory.addCustomer(transaction2.getCustomerName());

            TransactionsHistory.increaseTransactions();
            TransactionsHistory.increaseTransactions();

            TransactionsHistory.increaseAmount(transaction1);
            TransactionsHistory.increaseAmount(transaction2);


            transactionRepository.save(transaction1);
            transactionRepository.save(transaction2);

            transactionRepository.findAll().forEach(transaction -> log.info("Preloaded" + transaction));

        };
    }
}