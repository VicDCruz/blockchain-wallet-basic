package mx.unam.iimas.seginf;

import java.nio.charset.StandardCharsets;
import java.security.*;

public class Wallet {
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private User owner;
    private double total;

    public static Wallet genesis = null;
    public static Wallet satoshi = null;

    static {
        try {
            genesis = new Wallet(new User("Genesis"));
            genesis.total = 1000;
            satoshi = new Wallet(new User("Satoshi"));
            genesis.sendMoney(genesis.total, satoshi);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Wallet(User owner) throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        SecureRandom random = new SecureRandom();
        keyGen.initialize(2048, random);
        KeyPair pair = keyGen.generateKeyPair();
        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();
        this.owner = owner;
        this.total = 0.0;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public double getTotal() {
        return total;
    }

    public void sendMoney(double amount, Wallet payeeWallet) throws Exception {
        if (this.total < amount) {
            throw new Exception("Balance of " + this.owner.getName() + " is not enough to transfer $" + amount + "...");
        }
        System.out.println("Transferring $" + amount + " from " + this.owner.getName() + " to " + payeeWallet.owner.getName());
        Transaction transaction = new Transaction(this.privateKey, payeeWallet.getPublicKey(), amount);
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(this.privateKey);
        signature.update(transaction.toString().getBytes(StandardCharsets.UTF_8));
        byte[] sig = signature.sign();
        Chain.getChain().addBlock(transaction, this.publicKey, sig);
        this.total -= amount;
        payeeWallet.total = payeeWallet.total + amount;
        System.out.println("Balances updated: " +
                this.owner.getName() + " has " + this.total + " and " +
                payeeWallet.owner.getName() + " has " + payeeWallet.total);
    }
}
