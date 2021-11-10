package mx.unam.iimas.seginf;

import org.apache.commons.codec.digest.DigestUtils;

import java.time.LocalDateTime;

public class Block {
    private Long nonce = Math.round(Math.random() * 999999999);
    private String prevHash;
    private String actualHash;
    private Transaction transaction;
    private LocalDateTime timestamp = LocalDateTime.now();

    public Block(String prevHash, Transaction transaction) {
        this.prevHash = prevHash;
        this.transaction = transaction;
        this.actualHash = "";
    }

    public String getActualHash() {
        String str = this.toString();
        this.actualHash = DigestUtils.sha256Hex(str);
        return this.actualHash;
    }

    public int getNonce() {
        return Math.toIntExact(nonce);
    }

    @Override
    public String toString() {
        return "Block{" +
                "nonce=" + nonce +
                ", prevHash='" + (prevHash.length() > 10 ? "..." + prevHash.substring(prevHash.length() - 10) : "") + '\'' +
                ", actualHash='" + (actualHash.length() > 10 ? "..." + actualHash.substring(actualHash.length() - 10) : "") + '\'' +
                ", \n\ttransaction=" + transaction +
                ", \n\ttimestamp=" + timestamp +
                '}';
    }
}
