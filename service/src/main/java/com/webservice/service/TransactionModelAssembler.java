package com.webservice.service;

import com.webservice.entity.Transaction;
import com.webservice.controller.TransactionController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;


import java.io.Serializable;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class TransactionModelAssembler implements RepresentationModelAssembler<Transaction, EntityModel<Transaction>>, Serializable {

    @Override
    public EntityModel<Transaction> toModel(Transaction transaction) {
        return EntityModel.of(transaction,
                WebMvcLinkBuilder.linkTo(methodOn(TransactionController.class).getTransactionById(transaction.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(methodOn(TransactionController.class).getTransactions()).withRel("Transactions"));
    }








}