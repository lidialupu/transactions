package com.webservice.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Table(name="transactions_history")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@RedisHash("Transaction")

public class Transaction implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
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

    @Override
    public String toString() {
        return "Transaction with id[" + this.id + "]:\nCustomer Name: "
                + this.customerName + "\nSum: " + this.sum + "\nTransaction Type: " + this.transactionType;
    }
}