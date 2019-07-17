package de.magicbrothers.coin.main;

import de.magicbrothers.coin.node.Block;
import de.magicbrothers.coin.node.Blockchain;

import java.util.Scanner;

public class MagicCoin {

    public static int difficulty = 5;

    public static void main(String[] args) throws Exception {

        System.out.println("magicCoin startet...");

        Blockchain blockchain = new Blockchain();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Daten für den Genesis Block:");
        Block genesis = new Block("", scanner.nextLine());
        genesis.mine();
        blockchain.addBlock(genesis);

        System.out.println("Daten für den ersten Block:");
        Block firstBlock = new Block(blockchain.getLastBlock().getHash(), scanner.nextLine());
        firstBlock.mine();
        blockchain.addBlock(firstBlock);

        System.out.println("Daten für den zweiten Block:");
        Block secondBlock = new Block(blockchain.getLastBlock().getHash(), scanner.nextLine());
        secondBlock.mine();
        blockchain.addBlock(secondBlock);

        System.out.println();
        System.out.println("Die Blockchain wird geprüft...");
        blockchain.isValid();

    }

}
