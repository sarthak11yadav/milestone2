package net.milestone2.model;
import lombok.*;

import javax.persistence.*;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "username", nullable = false, unique = true, updatable = false)
    private  String username;

    @Column(name = "mobileno", nullable = false, unique = true, updatable = false)
    private  String mobileno;



    @Column(name = "password", nullable = false)
    private  String password;


    @Column(name = "email" ,nullable = false ,unique = true ,updatable = false)
    private String email;


    @Column(name = "address")
    private String address;

    @OneToOne
    @JoinColumn(name="walletId")
    private Wallet  wallet;

}
