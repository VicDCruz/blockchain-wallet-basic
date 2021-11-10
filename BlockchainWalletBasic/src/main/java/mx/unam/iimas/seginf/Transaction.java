package mx.unam.iimas.seginf;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class Transaction {
    private PrivateKey payer;
    private PublicKey payee;
    private Double amount;

    public Transaction(PrivateKey payer, PublicKey payee, double amount) {
        this.payer = payer;
        this.payee = payee;
        this.amount = amount;
    }

    @Override
    public String toString() {
        String payerEncode, payeeEncode;
        if (payer != null && payee != null) {
            payerEncode = Base64.getEncoder().encodeToString(payer.getEncoded());
            payeeEncode = Base64.getEncoder().encodeToString(payee.getEncoded());
            payerEncode = "..." + payerEncode.substring(payerEncode.length() - 15);
            payeeEncode = "..." + payeeEncode.substring(payeeEncode.length() - 15);
        } else {
            payerEncode = "genesis";
            payeeEncode = "genesis";
        }
        return "Transaction{" +
                "payer='" + payerEncode + '\'' +
                ", payee='" + payeeEncode + '\'' +
                ", amount=" + amount +
                '}';
    }
}
