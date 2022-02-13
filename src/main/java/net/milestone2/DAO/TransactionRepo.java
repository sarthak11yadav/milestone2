package net.milestone2.DAO;


import net.milestone2.model.Transaction;

import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;


public interface TransactionRepo extends JpaRepository<Transaction,Long> {
    Transaction findByTxnId(Long txnId);




}
