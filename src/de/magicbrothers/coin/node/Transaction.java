package de.magicbrothers.coin.node;

import de.magicbrothers.coin.main.Utils;

import java.security.PrivateKey;
import java.util.Date;

public class Transaction {

    public String from;
    public String to;
    public float amount;
    public long timeStamp;
    public String signature;



    public Transaction(String from, String to, float amount, PrivateKey privateKey) throws Exception {

        this.from = from;
        this.to = to;
        this.amount = amount;
        timeStamp = new Date().getTime();
        signature = Utils.sign(from + to + amount + timeStamp, privateKey);

    }

}
