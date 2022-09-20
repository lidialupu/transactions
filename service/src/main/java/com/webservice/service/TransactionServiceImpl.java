package com.webservice.service;

import com.webservice.exceptions.BadRequestException;
import com.webservice.exceptions.DuplicateException;
import com.webservice.exceptions.ResourceNotFoundException;

import com.webservice.repository.TransactionRepository;
import com.webservice.entity.Transaction;
import com.webservice.history.TransactionsHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private final TransactionRepository repository;
    private final TransactionModelAssembler assembler;
    private int counter;

    public TransactionServiceImpl(TransactionRepository repository, TransactionModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @Override
    public EntityModel<Transaction> createTransaction(Transaction transaction) {
        if(counter < 1) {
            this.updateData();
        }

        if(checkingJson(transaction)) {
            throw new BadRequestException("Bad Request");
        }

        counter++;
        List<Transaction> transactions = repository.findAll();
        for(Transaction t: transactions) {
            if (t.equals(transaction)) {
                throw new DuplicateException("DUPLICATE");
            }
        }

        TransactionsHistory.increaseTransactions();
        TransactionsHistory.increaseAmount(transaction);
        TransactionsHistory.addCustomer(transaction.getCustomerName());

        return assembler.toModel(repository.save(transaction));
    }

    @Override
    public EntityModel<Transaction> updateTransaction(Long id, Transaction transactionDetails) {
        if(counter < 1) {
            this.updateData();
        }
        if(checkingJson(transactionDetails)) {
            throw new BadRequestException("Bad Request");
        }

        counter++;
        TransactionsHistory.addCustomer(transactionDetails.getCustomerName());
        TransactionsHistory.updateAmount(repository.getReferenceById(id), transactionDetails);

        Transaction updatedTransaction = repository.findById(id)
                .map(transaction -> {
                    transaction.setTransactionType(transactionDetails.getTransactionType());
                    transaction.setSum(transactionDetails.getSum());
                    transaction.setCustomerName(transactionDetails.getCustomerName());
                    return repository.save(transaction);
                })
                .orElseGet(() -> {
                    transactionDetails.setId(id);
                    return repository.save(transactionDetails);
                });

        return assembler.toModel(updatedTransaction);
    }

    @Override
    public void deleteTransaction(Long id) {
        if(repository.getReferenceById(id).equals(null)) {
            throw new ResourceNotFoundException(id);
        }
        if(counter < 1) {
            this.updateData();
        }
        counter++;
        TransactionsHistory.decreaseTransactions();
        TransactionsHistory.decreaseAmount(repository.getReferenceById(id));

        repository.deleteById(id);
    }

    @Override
    public List<EntityModel<Transaction>> getTransactions() {
        if(counter < 1) {
            this.updateData();
        }
        counter++;
        return  repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public EntityModel<Transaction> getTransactionById(Long id) {
        if(counter < 1) {
            this.updateData();
        }
        counter++;
        Transaction transaction = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        return assembler.toModel(transaction);
    }

    @Override
    public String getNumber() {
        if(counter < 1) {
            this.updateData();
        }
        counter++;
        return "Total transactions: " + TransactionsHistory.getTotalTransactions();
    }

    @Override
    public List<String> getTotal() {
        if(counter < 1) {
            this.updateData();
        }
        counter++;
        String totalDeposits = "Total deposits: " + TransactionsHistory.getDepositAmount() + " ";
        String totalWithdrawals = "Total withdrawals: " + TransactionsHistory.getWithdrawalAmount();

        List<String> totals = new ArrayList<>();
        totals.add(totalDeposits);
        totals.add(totalWithdrawals);

        return totals;
    }

    @Override
    public List<String> getCustomers() {
        if(counter < 1) {
            this.updateData();
        }
        counter++;
        return TransactionsHistory.getCustomers();
    }

    @Override
    public String getCustomerById(Long id) {
        if(counter < 1) {
            this.updateData();
        }
        counter++;

        Transaction transaction = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        String customerName = transaction.getCustomerName();
        return "Transaction with id[" + id +"] was made by: " + customerName;
    }

    private boolean checkingJson(Transaction transaction) {

        return transaction.getCustomerName().equals("") ||
                (!(transaction.getTransactionType().equals("deposit")) &&
                        !(transaction.getTransactionType().equals("withdrawal")))
                || transaction.getSum() == 0;
    }

    private void updateData(){
        TransactionsHistory.setTotalTransactions((int)repository.count());
        TransactionsHistory.setCustomers(findCustomers());
        TransactionsHistory.setDepositAmount(findDeposits());
        TransactionsHistory.setWithdrawalAmount(findWithdrawals());
    }

    private List<String> findCustomers() {
        List <String> customers = new ArrayList<>();
        for(Transaction t : repository.findAll()) {
            customers.add(t.getCustomerName());
        }
        return customers;
    }

    private double findDeposits() {
        double deposits = 0;
        for(Transaction t: repository.findAll()) {
            if(t.getTransactionType().equals("deposit")) {
                deposits += t.getSum();
            }
        }
        return deposits;
    }

    private double findWithdrawals() {
        double withdrawals = 0;
        for(Transaction t: repository.findAll()) {
            if(t.getTransactionType().equals("withdrawal")) {
                withdrawals += t.getSum();
            }
        }
        return withdrawals;
    }
}
