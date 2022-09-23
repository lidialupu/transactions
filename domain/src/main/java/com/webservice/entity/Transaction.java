package com.webservice.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Table(name="transactions_history")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Transaction {

    private @Id @GeneratedValue long id;

    @Column(name = "sum_value")
    private double sum;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "transaction_type")
    private String transactionType;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Transaction other = (Transaction) o;
        if (this.customerName.equals(other.customerName)
                && this.transactionType.equals(other.transactionType)
                && this.sum == other.getSum()) {

            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sum, customerName, transactionType);
    }
}