package de.magicbrothers.coin.node;

import de.magicbrothers.coin.main.MagicCoin;
import de.magicbrothers.coin.main.Utils;

import java.util.ArrayList;

public class Blockchain {

    private ArrayList<Block> blocks;

    public Blockchain() {
        blocks = new ArrayList<>();
    }

    public void addBlock(Block block) {
        block.mine();
        blocks.add(block);
    }

    public boolean isValid() {
        String currentBlockHash;
        String previousBlockHash;

        for(int i = 1; i < blocks.size(); i++) {
            currentBlockHash = blocks.get(i).getHash();
            previousBlockHash = blocks.get(i-1).getHash();

            if(!currentBlockHash.equals(blocks.get(i).calcHash())) {
                System.out.println("Block " + i + " wurde verändert. Blockchain nicht korrekt!");
                return false;
            }

            if(!previousBlockHash.equals(blocks.get(i-1).getHash())) {
                System.out.println("Block " + i + " passt nicht auf den darunterliegenden Block. Blockchain nicht korrekt!");
                return false;
            }

            if(!currentBlockHash.substring(0, MagicCoin.difficulty).equals(Utils.getTarget(MagicCoin.difficulty))) {
                System.out.println("Der Block " + i + " wurde noch nicht gemined. Blockchain nicht vollständig korrekt!");
                return false;
            }
        }

        System.out.println("Die Blockchain ist korrekt!");

        return true;
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public Block getLastBlock() {
        return blocks.get(blocks.size() - 1);
    }
}
