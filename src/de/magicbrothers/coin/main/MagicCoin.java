package de.magicbrothers.coin.main;

import de.magicbrothers.coin.node.*;

public class MagicCoin {

    public static int difficulty = 5;

    public static Blockchain blockchain = new Blockchain();

    public static void main(String[] args) throws Exception {

        System.out.println("magicCoin startet...");

        genesis();

        System.out.println();
        System.out.println("Die Blockchain wird geprüft...");
        blockchain.isValid();

    }

    public static void genesis() throws Exception {
        Wallet genesisWallet = new Wallet();

        Transaction genesisTransaction = new Transaction(genesisWallet.getPublicKey(), genesisWallet.getPublicKey(), 100, null);
        genesisTransaction.sign(genesisWallet.getPrivateKey());
        genesisTransaction.transactionId = "0";
        TransactionOutput output = new TransactionOutput(genesisTransaction.to, genesisTransaction.amount, genesisTransaction.transactionId);
        genesisTransaction.outputs.add(output);
        Transaction.UTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0));

        System.out.println("GenesisBlock wird erstellt und gemined...");

        Block genesis = new Block("0");
        genesis.addTransaction(genesisTransaction);
        blockchain.addBlock(genesis);
    }

}
