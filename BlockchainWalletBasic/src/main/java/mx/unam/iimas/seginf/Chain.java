package mx.unam.iimas.seginf;

import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.ArrayList;

public class Chain {
    private ArrayList<Block> blocks = new ArrayList<>();
    private static Chain chain;

    public Chain() {
        System.out.println("Init chain with genesis...");
        this.blocks.add(new Block("",
                new Transaction(null, null, Wallet.genesis.getTotal())));
    }

    public static Chain getChain() {
        if (chain == null) {
            System.out.println("No chain detected... creating one.");
            chain = new Chain();
        }
        return chain;
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public Block getLastBlock() {
        return this.blocks.get(this.blocks.size() - 1);
    }

    public int mine(int nonce) {
        Integer solution = -1;
        System.out.println("🔨 mining...");
        while (true) {
            Integer tmp = nonce + solution;
            String attempt = DigestUtils.md5Hex(tmp.toString()).toUpperCase();
            if (attempt.substring(0, 5).equals("00000")) {
                System.out.println("Solved: " + solution);
                return solution;
            }
            solution ++;
        }
    }

    public void addBlock(Transaction transaction, PublicKey senderPublicKey, byte[] signature) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature verifier = Signature.getInstance("SHA256withRSA");
        verifier.initVerify(senderPublicKey);
        verifier.update(transaction.toString().getBytes(StandardCharsets.UTF_8));
        boolean isValid = verifier.verify(signature);
        if (isValid) {
            Block block = new Block(this.getLastBlock().getActualHash(), transaction);
            this.mine(block.getNonce());
            this.blocks.add(block);
        } else {
            System.out.println("Signature NOT valid!");
        }
    }
}
