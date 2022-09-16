package com.webservice.controller;

import com.webservice.service.TransactionService;
import com.webservice.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
public class TransactionController  {
    @Autowired
    private TransactionService service;

    @GetMapping("/transactions")
    public CollectionModel<EntityModel<Transaction>> getallTransactions() {

        List<EntityModel<Transaction>> transactions = service.getTransactions();

        return CollectionModel.of(transactions, linkTo(methodOn(TransactionController.class)
                .getallTransactions()).withSelfRel());
    }

    @GetMapping(value = "/transactions/number")
    public ResponseEntity<String> getTransactionsNumber() {

        return new ResponseEntity<>(service.getNumber(), HttpStatus.OK);
    }

    @GetMapping("/transactions/total")
    public CollectionModel<String> getTransactionsTotal() {

        Link link = linkTo(methodOn(TransactionController.class)
                .getTransactionsTotal()).withSelfRel();

        return CollectionModel.of(service.getTotal(), link);

    }

    //get a list of customers
    @GetMapping("/transactions/customers")
    public CollectionModel<String> getCustomers() {

        Link link = linkTo(methodOn(TransactionController.class)
                .getCustomers()).withSelfRel();
        return CollectionModel.of(service.getCustomers(), link);
    }

    //create transaction
    @PostMapping("/transactions")
    public ResponseEntity<?> createTransaction(@Validated @RequestBody Transaction transaction) {

        EntityModel<Transaction> transactionEntityModel = service.createTransaction(transaction);

        return ResponseEntity
                .created(transactionEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(transactionEntityModel);
    }

    //get transaction by id
    @GetMapping("transactions/{id}")
    public EntityModel<Transaction> getTransactionById(@PathVariable(value = "id") long id) {

        return service.getTransactionById(id);
    }

    //get customerName for transaction

    @GetMapping("transactions/customers/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable(value = "id") long id) {

        return new ResponseEntity<> (service.getCustomerById(id), HttpStatus.OK);
    }


    // update transaction
    @PutMapping("transactions/{id}")
    public ResponseEntity<?> updateTransaction(@PathVariable(value = "id") long id,
                                               @RequestBody Transaction transactionDetails) {

        EntityModel<Transaction> transactionEntityModel = service.updateTransaction(id, transactionDetails);

        return ResponseEntity
                .created(transactionEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(transactionEntityModel);

    }

    // delete transaction by id
    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable(value = "id") long id) {

        service.deleteTransaction(id);
        return ResponseEntity.noContent().build();

    }


}
