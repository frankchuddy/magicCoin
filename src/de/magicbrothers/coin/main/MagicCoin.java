package de.magicbrothers.coin.main;

import de.magicbrothers.coin.node.Block;
import de.magicbrothers.coin.node.Blockchain;
import de.magicbrothers.coin.node.Transaction;
import de.magicbrothers.coin.node.Wallet;

public class MagicCoin {

    public static int difficulty = 5;

    public static void main(String[] args) throws Exception {

        System.out.println("magicCoin startet...");

        Blockchain blockchain = new Blockchain();

        Wallet alice = new Wallet();
        Wallet bob = new Wallet();

        Block genesis = new Block("", "Ich bin der Genesis Block");
        genesis.mine();
        blockchain.addBlock(genesis);

        Block firstBlock = new Block(blockchain.getLastBlock().getHash(), "Ich bin der erste Block.");
        firstBlock.mine();
        blockchain.addBlock(firstBlock);

        Block secondBlock = new Block(blockchain.getLastBlock().getHash(), "Ich bin der zweite Block.");
        secondBlock.mine();
        blockchain.addBlock(secondBlock);

        System.out.println("Ist die Blockchain korrekt? " + blockchain.isValid());

    }

}
