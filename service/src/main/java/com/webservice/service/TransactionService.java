package com.webservice.service;

import com.webservice.entity.Transaction;
import org.springframework.hateoas.EntityModel;

import java.util.List;

public interface TransactionService {

    Transaction createTransaction(Transaction transaction);
    //EntityModel<Transaction> updateTransaction(Long id, Transaction transaction);
    Transaction updateTransaction(Long id, Transaction transaction);
    void deleteTransaction(Long id);
//    List<EntityModel<Transaction>> getTransactions();
    List<Transaction> getTransactions();
   // EntityModel<Transaction> getTransactionById(Long id);
    Transaction getTransactionById(Long id);
    String getNumber();
    List<String> getTotal();
    List<String> getCustomers();
    String getCustomerById(Long id);

}
