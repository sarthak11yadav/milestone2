package net.milestone2.controller;

import net.milestone2.DAO.WalletRepo;
import net.milestone2.Utilities.MyResponse;
import net.milestone2.exception.BadRequestException;
import net.milestone2.exception.ResourceNotFoundException;
import net.milestone2.model.Transaction;
import net.milestone2.model.User;
import net.milestone2.model.Wallet;
import net.milestone2.service.TransactionService;
import net.milestone2.service.UserService;
import net.milestone2.service.WalletService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class WalletController {

    @Autowired
    WalletService walletService;

    @Autowired
    UserService userService;

    @Autowired
    TransactionService transactionService;
    private static final Logger logger= Logger.getLogger(WalletController.class);



    @PostMapping("/wallet/{Mobileno}")
    public MyResponse createWallet(@PathVariable("Mobileno") String Mobileno)
    {


                    Optional<Wallet> TempW=walletService.getWalletById(Mobileno);
                    if(!TempW.isPresent()){
                    Wallet w = new Wallet();
                    w.setWalletId(Mobileno);
                    w.setCurr_balance(0.0f);
                    walletService.CreateWallet(w);
                    User user = userService.findByMobileno(Mobileno);
                    user.setWallet(w);
                    userService.updateUser(user);
                        logger.info("Wallet created at given number successfully");
                        return new MyResponse("Wallet creates successfully", HttpStatus.CREATED);
                                          }
                    else
                    {
                        logger.debug("wallet already exist");
                        throw new BadRequestException("User has already Wallet !");
                    }

    }

    @PostMapping("/wallet/{walletId}/{amount}")
    public MyResponse addMoney(@PathVariable String walletId,@PathVariable("amount") long amount)
    {
        try {
            User user = userService.findByMobileno(walletId);
        }
        catch (Exception e){
            throw  new BadRequestException("Invalid WalletId !");
        }

        if(amount<0)
            throw new BadRequestException("amount not valid");

        Wallet wallet=walletService.getWalletById(walletId).orElseThrow(() -> new ResourceNotFoundException("wallet with this id does not exitst"));

        try
        {
            wallet.setCurr_balance(wallet.getCurr_balance()+amount);
            walletService.updateWallet(wallet);

            Transaction txn=new Transaction();
            txn.setAmount(amount);
            txn.setPayerWalletId("By self");
            txn.setPayeeWalletId(wallet.getWalletId());
            txn.setStatus("Success");
            transactionService.CreateTransaction(txn);

            logger.info("Wallet money added");
            return new MyResponse("Amount added to wallet",HttpStatus.CREATED);




        }
        catch (Exception e)
        {
            logger.info("Unsuccessful addition of money");
            throw new BadRequestException("can not add money");

        }


    }
    @GetMapping("/wallet/{walletId}")
    public Optional<Wallet>getWalletDataById(@PathVariable String walletId)
    {
        logger.info("Wallet retrieved successfully");
        return walletService.getWalletById(walletId);
    }

    @DeleteMapping("/wallet/{walletId}")
    public ResponseEntity<String> DeleteWallet(@PathVariable("walletId") String id)
    {

        walletService.DeleteWallet(id);
        logger.info("Wallet deleted successfully");
        return new ResponseEntity<String>("Wallet deleted Sucessfully",HttpStatus.OK);
    }


}
