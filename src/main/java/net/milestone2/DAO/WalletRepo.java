package net.milestone2.DAO;

import net.milestone2.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;



public interface WalletRepo extends JpaRepository<Wallet,String> {
}
