package net.milestone2.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.milestone2.DAO.WalletRepo;
import net.milestone2.exception.BadRequestException;
import net.milestone2.model.Wallet;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class WalletServiceTest {

    @MockBean
    private WalletRepo walletRepo;

    @InjectMocks
    private WalletService walletService;


    ObjectMapper objectMapper=new ObjectMapper();


    @Test
    void createWallet() throws IOException {

        String walletJson=new String(Files.readAllBytes(Paths.get("src/test/java/rec/WalletDetails.json")));

        Wallet wallet =objectMapper.readValue(walletJson, Wallet.class);
        Mockito.when(walletRepo.save(wallet)).thenReturn(wallet);
        Assert.assertEquals(walletService.CreateWallet(wallet),wallet);
    }

    @Test
    void getWalletById() throws IOException {

        String walletJson=new String(Files.readAllBytes(Paths.get("src/test/java/rec/WalletDetails.json")));

        Wallet wallet =objectMapper.readValue(walletJson,Wallet.class);
        Mockito.when(walletRepo.findById(wallet.getWalletId())).thenReturn(Optional.of(wallet));
        Wallet w=walletService.getWalletById(wallet.getWalletId()).orElseThrow(()->new BadRequestException("Id not found !"));
        Assert.assertEquals(w,wallet);
    }

    @Test
    void updateWallet() throws IOException {

        String walletJson=new String(Files.readAllBytes(Paths.get("src/test/java/rec/WalletDetails.json")));

        Wallet wallet =objectMapper.readValue(walletJson,Wallet.class);
        Mockito.when(walletRepo.save(wallet)).thenReturn(wallet);
        Assert.assertEquals(walletService.updateWallet(wallet),wallet);
    }
}
