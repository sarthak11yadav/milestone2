package net.milestone2.service;


import net.milestone2.DAO.WalletRepo;
import net.milestone2.exception.ResourceNotFoundException;
import net.milestone2.model.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class WalletService {

    @Autowired
    WalletRepo walletRepo;



    public Wallet CreateWallet(Wallet wallet)
    {
            walletRepo.save(wallet);
            return wallet;

    }

    public Optional<Wallet> getWalletById(String walletId){
        return walletRepo.findById(walletId);
    }

    public Wallet DeleteWallet(String walletId)
    {
        Wallet x=walletRepo.getById(walletId);
        walletRepo.delete(x);
        return x;

    }
    public Wallet updateWallet(Wallet wallet){
        walletRepo.save(wallet);
        return wallet;
    }

}
