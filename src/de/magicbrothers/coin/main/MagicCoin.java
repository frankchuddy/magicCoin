package de.magicbrothers.coin.main;

import de.magicbrothers.coin.node.*;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Scanner;

public class MagicCoin {

    public static int difficulty = 5;

    public static Blockchain blockchain = new Blockchain();

    public static Wallet genesisWallet;

    public static void main(String[] args) throws Exception {

        System.out.println("magicCoin startet...");

        genesis();

        System.out.println();

        welcome();

        System.out.println();

    }

    private static void genesis() throws Exception {
        genesisWallet = new Wallet(Utils.getPublicKeyFromString("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsSYji9sAo2ziDMDIYqM4nZ9AL0Tfgnfw1+VHz8/7WZUNIcnhL03+kVMU5HUZIHMUmHfsJ9RScv67/Dq7+qOQpfIZfruzUziBtZGQDr+BljdOxae87/bsy+TQ46AKvAoN9AgBpbspbDr7O08S/Mtg851O3xSUte7L0DQ9OOWvIvWhrnxVj4vX021EtCBWJ+VDANxf4/53azCQcq7ThFam0uiF8oplk7M2vQMmHJepfNgziHP3/V7uG6I3Vi2FhamuTb0yi3YiuCByFMLCi088vwOdcdGqdsPolU+dI4LI+sx+jALwVLRpT4fAOTioPEvNpVZYaLHPKYx9i2vUAEeUtQIDAQAB"), Utils.getPrivateKeyFromString("MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCxJiOL2wCjbOIMwMhiozidn0AvRN+Cd/DX5UfPz/tZlQ0hyeEvTf6RUxTkdRkgcxSYd+wn1FJy/rv8Orv6o5Cl8hl+u7NTOIG1kZAOv4GWN07Fp7zv9uzL5NDjoAq8Cg30CAGluylsOvs7TxL8y2DznU7fFJS17svQND045a8i9aGufFWPi9fTbUS0IFYn5UMA3F/j/ndrMJByrtOEVqbS6IXyimWTsza9AyYcl6l82DOIc/f9Xu4bojdWLYWFqa5NvTKLdiK4IHIUwsKLTzy/A51x0ap2w+iVT50jgsj6zH6MAvBUtGlPh8A5OKg8S82lVlhosc8pjH2La9QAR5S1AgMBAAECggEAGbmNHekerZzJMsroDpLfTNxsaLDauEqMmf18sSvltzuS3Z5AaissVZQ4C3kJvhGYIw//JkWJ95MQX290DtCj18VReHrvbp1dEK3jupCK0/KSKNfT8xDrkCxZPUTnaikXn6FuA9ArEsF57EHuYvFx0c69Pl3xvn2/8Ss03MdV6IB/Ckn16x+SJqsDqxtcq57ojHDG6KRntFPCBQhoJw7FT23P4IvQRbKn60Sa/D6khj8oX2cNALwiNJ4SQxVwX2jNF7/ASsYWhMvnmdPnz4s4agmxnfzXM0ICD4HUxJMDDJB6vTfe97JXCaIMA5MeB58qy3RDGcHrkPYvgB0c5NFhPQKBgQDkfYGZdymvL2RPPDPrOa4ZX7ZmfSjIS4IkXoPPMRQdaHf0FZc5nlpC3crShn2YXuefdloNzDA3AL82c6pNxljzXSWjgs0M4zHaTvSCa2VdxQ3W2TEl4dEQqpKvbwpqvvHhEWa184xqB4zehBG6uo9ls6MOGk2Tlrlvghvy5EXgjwKBgQDGejL5uBkQ76TwPypRnmqybfosQcJVjneC8uHYkxgGrh1qf/eyF1K46S9/KsitDgYiinbCk0fCkh+jN67/V/2f/7auFpxoal9sqRBMPn9FPYcs5w2JEkQ7YF8K9d4+4XVU1Q5UYDTct2T0cI40XcsU8XvPIlR86joKIMBs7vhQewKBgArn0p+4iEzMPkVp4jB/+EdNwi7yGlg1Amkp+bCR7xvYOKF1xloBrN00KNITiTsUjImX5IR0EnkNmBLJQn+jz+sNrxY20AKldWOuEQ2IgrYPCbIPe8ELHidlfMrSyfR+WJ0db4GPtkv5QEf+lboL3XxFLqslpj49G2yvERZuvscRAoGAIMueUq79zVOb0GrPhk8D2FndxaRUBSkWTFfvw4aCqM/qYmekuROrgoxdYcjjAd1YXCq+9uW5LMhSW/jefKjAasipGdj4nTQ+ckj5PW/yPbGt/VhsXyLajWkeK0R3JbLBXXQa+wwuj+3py44xoSuCXAKO76n/heh6EKRax2UxOLMCgYBevMwYRUXdbzp/+lgmFe9wr4PwCZ1/gbDdw88prdRCrcjZ4Kmd/HZElitzaAI5JyBVHrjNXhwD00psIl9nQcNFjW08xFXK/IrjNXL0I/8JDkUIDWUVQPw1N+CGH4Rf7HdGfvO2Tkur9xX5QTh3UwZnA+y0U8CUEqWOMcsI0sEh+w=="));
        System.out.println("Genesis-Adresse: " + Utils.keyToString(genesisWallet.getPublicKey()));
        System.out.println("GenesisPrivateKey: " + Utils.keyToString(genesisWallet.getPrivateKey()));

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

        System.out.println("Das GenesisWallet enthält nun " + genesisWallet.getBalance() + " magicCoins.");
    }

    public static void welcome() throws Exception {

        Scanner scanner = new Scanner(System.in);

        System.out.println();
        System.out.println("magicCoins Automat");
        System.out.println("Wähle eine Aktion:");
        System.out.println("1. Kontostand");
        System.out.println("2. Überweisen");
        System.out.println("3. Konto erstellen");

        int action = Integer.parseInt(scanner.nextLine());

        switch(action) {
            case 1:
                System.out.println("Bitte gib eine magicCoin-Adresse ein:");
                String address = scanner.nextLine();
                PublicKey pubKey = Utils.getPublicKeyFromString(address);
                Wallet wallet = new Wallet(pubKey, null);
                System.out.println("Kontostand: " + wallet.getBalance() + " magicCoins");
                break;
            case 2:
                System.out.println("Bitte gib die Empfänger-Adresse ein:");
                PublicKey pubKey1 = Utils.getPublicKeyFromString(scanner.nextLine());
                System.out.println("Bitte gib deine magicCoin-Adresse ein:");
                PublicKey pubKey2 = Utils.getPublicKeyFromString(scanner.nextLine());
                System.out.println("Bitte gib den zu überweisenden Betrag ein:");
                float amount = Float.parseFloat(scanner.nextLine());
                System.out.println("Bitte gib deinen PrivateKey ein:");
                PrivateKey privKey = Utils.getPrivateKeyFromString(scanner.nextLine());
                Wallet wallet1 = new Wallet(pubKey2, privKey);

                wallet1.send(pubKey1, amount);
                System.out.println("Erfolgreich überwiesen.");
                break;
            case 3:
                Wallet wallet2 = new Wallet();
                System.out.println("Deine Adresse ist: " + Utils.keyToString(wallet2.getPublicKey()));
                System.out.println("Dein PrivateKey ist: " + Utils.keyToString(wallet2.getPrivateKey()));
                break;
            case 4:
                System.out.println("UTXOs : " + Transaction.UTXOs);
        }

        welcome();

    }

}
