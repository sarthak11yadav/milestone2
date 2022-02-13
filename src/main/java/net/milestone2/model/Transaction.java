package net.milestone2.model;


import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long txnId;

    @Column(name = "payerWalletId")
    public String payerWalletId;

    @Column(name = "payeeWalletId")
    public String payeeWalletId;

    @Column(name = "amount")
    public float amount;


    public String status;






}
