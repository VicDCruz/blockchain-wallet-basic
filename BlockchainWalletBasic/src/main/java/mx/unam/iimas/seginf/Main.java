package mx.unam.iimas.seginf;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.util.stream.Collectors;

import static mx.unam.iimas.seginf.Wallet.satoshi;

public class Main {
    public static void main(String[] args) {
        try {
            Wallet bob = new Wallet(new User("Bob"));
            Wallet alice = new Wallet(new User("Alice"));
            satoshi.sendMoney(50, bob);
            bob.sendMoney(23, alice);
            bob.sendMoney(23, satoshi);
            alice.sendMoney(5, bob);
            System.out.println(
                    Chain.getChain().getBlocks()
                            .stream().map(block -> "\n" + block)
                            .collect(Collectors.toList()));
            alice.sendMoney(100, bob);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
