package de.magicbrothers.coin.node;

import de.magicbrothers.coin.main.Utils;

import java.security.PublicKey;

public class TransactionOutput {

    public String id;
    public PublicKey to;
    public float amount;
    public String parentTransactionId;

    public TransactionOutput(PublicKey to, float amount, String parentTransactionId) {
        this.to = to;
        this.amount = amount;
        this.parentTransactionId = parentTransactionId;
        this.id = Utils.sha256(Utils.keyToString(to) + amount + parentTransactionId);
    }

    public boolean isMine(PublicKey publicKey) {
        return publicKey.equals(to);
    }

}
