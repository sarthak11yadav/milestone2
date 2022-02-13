package net.milestone2.service;


import net.milestone2.DAO.TransactionRepo;
import net.milestone2.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.awt.print.Pageable;
import java.util.List;

@Service
@Transactional
public class TransactionService {

    @Autowired
    TransactionRepo transactionRepo;

    public Transaction CreateTransaction(Transaction txn)
    {
        transactionRepo.save(txn);

        return txn;
    }

    public Transaction getTxnByById(Long txnId)
    {
        return transactionRepo.findByTxnId(txnId);
    }





}
