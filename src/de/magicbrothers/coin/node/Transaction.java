package de.magicbrothers.coin.node;

import de.magicbrothers.coin.main.Utils;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Transaction {

    public String transactionId;
    public PublicKey from;
    public PublicKey to;
    public float amount;
    private long timeStamp;
    private String signature;

    private ArrayList<TransactionInput> inputs;
    public ArrayList<TransactionOutput> outputs = new ArrayList<>();

    public static HashMap<String, TransactionOutput> UTXOs = new HashMap<>();

    public Transaction(PublicKey from, PublicKey to, float amount, ArrayList<TransactionInput> inputs) {

        this.from = from;
        this.to = to;
        this.amount = amount;
        timeStamp = new Date().getTime();
        this.inputs = inputs;

    }

    public String calcHash() {
        return Utils.sha256(Utils.keyToString(from) + Utils.keyToString(to) + amount + timeStamp);
    }

    public void sign(PrivateKey privateKey) throws Exception {
        String data = Utils.keyToString(from) + Utils.keyToString(to) + amount + timeStamp;

        signature = Utils.sign(data, privateKey);
    }

    public boolean verify() throws Exception {
        String data = Utils.keyToString(from) + Utils.keyToString(to) + amount + timeStamp;

        return Utils.verify(data, signature, from);
    }

    public boolean processTransaction() throws Exception {

        if(!verify()) {
            System.out.println("Die Transaktion ist nicht korrekt verifiziert.");
            return false;
        }

        for(TransactionInput i : inputs) {
            i.UTXO = UTXOs.get(i.transactionOutputId);
        }

        float leftOver = getInputsValue() - amount;
        transactionId = calcHash();
        outputs.add(new TransactionOutput(to, amount, transactionId));
        outputs.add(new TransactionOutput(from, leftOver, transactionId));

        for(TransactionOutput i : outputs) {
            UTXOs.put(i.id, i);
        }

        for(TransactionInput i : inputs) {
            if(i.UTXO == null) continue;
            UTXOs.remove(i.UTXO.id);
        }

        return true;
    }

    private float getInputsValue() {
        float total = 0;

        for(TransactionInput i : inputs) {
            if(i.UTXO == null) continue;
            total += i.UTXO.amount;
        }

        return total;
    }

    public float getOutputsValue() {
        float total = 0;

        for(TransactionOutput i : outputs) {
            total += i.amount;
        }

        return total;
    }

}
