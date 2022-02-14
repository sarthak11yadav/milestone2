package net.milestone2.controller;
import net.bytebuddy.pool.TypePool;
import net.milestone2.Utilities.MyResponse;
import net.milestone2.exception.BadRequestException;
import net.milestone2.exception.ResourceNotFoundException;
import net.milestone2.model.Transaction;
import net.milestone2.model.User;
import net.milestone2.model.Wallet;
import net.milestone2.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
public class TransactionController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @PostMapping("/transaction")
    public MyResponse TransferMoney(@RequestBody Transaction txn)
    {
        if(txn.getAmount()<=0) {
            MyResponse res=new MyResponse("Please request positive amount value", HttpStatus.BAD_REQUEST);
            return res;
        }

        String payer_wid=txn.getPayerWalletId();
        String payee_wid=txn.getPayeeWalletId();

        Wallet PayerWallet=walletService.getWalletById(payee_wid).orElseThrow(() -> new TypePool.Resolution.NoSuchTypeException("Payer with this wallet id does not exist"));
        Wallet PayeeWallet=walletService.getWalletById(payer_wid).orElseThrow(() -> new TypePool.Resolution.NoSuchTypeException("Payee with this wallet id does not exist"));

        Float cur_bal_payer=PayerWallet.getCurr_balance();

        if(cur_bal_payer<txn.getAmount())
        {
            MyResponse res= new MyResponse("Payer does not contain sufficient amount to transfer",HttpStatus.BAD_REQUEST);
            return res;
        }

        Float cur_bal_payee=PayeeWallet.getCurr_balance();


        Wallet TempPayerWallet=PayerWallet;
        Wallet TempPayeeWallet=PayeeWallet;

        try
        {
            cur_bal_payer-= txn.getAmount();
            cur_bal_payee+= txn.getAmount();

            PayeeWallet.setCurr_balance(cur_bal_payee);
            PayerWallet.setCurr_balance(cur_bal_payer);

            walletService.updateWallet(PayeeWallet);
            walletService.updateWallet(PayerWallet);
            txn.setStatus("Success");
            Transaction txn1=txn;
            Transaction txn2=txn;

            txn1.setUserNumber(payee_wid);
            txn2.setUserNumber(payer_wid);

            transactionService.CreateTransaction(txn1);
            transactionService.CreateTransaction(txn2);


            return new MyResponse("Money transferred successfully",HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            walletService.updateWallet(TempPayeeWallet);
            walletService.updateWallet(TempPayerWallet);

            txn.setStatus("Failed");

            transactionService.CreateTransaction(txn);

            throw  new BadRequestException("Unsuccessful transaction !");
        }

    }

    @GetMapping("/transaction/{txnId}")
    public String getStatusOfTransaction(@PathVariable Long txnId)
    {
        Transaction txn;
        try
        {
            txn=transactionService.getTxnByById(txnId);

        }
        catch (Exception e)
        {
            throw new ResourceNotFoundException("Transaction with this id does not exist");
        }
        return txn.getStatus();
    }
    @GetMapping(value="/gettransaction/{id}")
    public Object transactionSummary(@PathVariable long id, Pageable pageable)
    {
        User val = userService.getUserById(id);

        //User with a given userId must be present

            String userMobileNumber=val.getMobileno();
            List<Transaction> userTransactions= transactionService.findAllByUserNumber(userMobileNumber,pageable);
            return userTransactions;


    }





}
