package de.magicbrothers.coin.main;

import de.magicbrothers.coin.node.*;

public class MagicCoin {

    public static int difficulty = 5;

    public static void main(String[] args) throws Exception {

        System.out.println("magicCoin startet...");

        Blockchain blockchain = new Blockchain();

        Wallet alice = new Wallet();
        Wallet bob = new Wallet();
        Wallet genesisWallet = new Wallet();

        Transaction genesisTransaction = new Transaction(genesisWallet.getPublicKey(), alice.getPublicKey(), 100, null);
        genesisTransaction.sign(genesisWallet.getPrivateKey());
        genesisTransaction.transactionId = "0";
        TransactionOutput output = new TransactionOutput(genesisTransaction.to, genesisTransaction.amount, genesisTransaction.transactionId);
        genesisTransaction.outputs.add(output);
        Transaction.UTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0));

        System.out.println("GenesisBlock wird erstellt und gemined...");

        Block genesis = new Block("0");
        genesis.addTransaction(genesisTransaction);
        blockchain.addBlock(genesis);

        System.out.println("Alice hat " + alice.getBalance() + " Coins.");
        System.out.println("Bob hat " + bob.getBalance() + " Coins.");

        Block firstBlock = new Block(blockchain.getLastBlock().getHash());
        firstBlock.addTransaction(alice.send(bob.getPublicKey(), 20));
        blockchain.addBlock(firstBlock);

        System.out.println("Alice hat " + alice.getBalance() + " Coins.");
        System.out.println("Bob hat " + bob.getBalance() + " Coins.");

        Block secondBlock = new Block(blockchain.getLastBlock().getHash());
        secondBlock.addTransaction(bob.send(alice.getPublicKey(), 7.3f));
        blockchain.addBlock(secondBlock);

        System.out.println("Alice hat " + alice.getBalance() + " Coins.");
        System.out.println("Bob hat " + bob.getBalance() + " Coins.");

        System.out.println();
        System.out.println("Die Blockchain wird geprüft...");
        blockchain.isValid();

    }

}
