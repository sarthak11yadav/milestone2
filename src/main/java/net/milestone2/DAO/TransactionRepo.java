package net.milestone2.DAO;


import net.milestone2.model.Transaction;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TransactionRepo extends JpaRepository<Transaction,Long> {
    Transaction findByTxnId(Long txnId);


        List<Transaction> findAllByUserNumber(String userMobileNumber, Pageable pageable);



}
