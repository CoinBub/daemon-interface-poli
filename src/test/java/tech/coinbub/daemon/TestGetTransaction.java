package tech.coinbub.daemon;

import java.math.BigDecimal;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tech.coinbub.daemon.poli.ScriptPublicKey;
import tech.coinbub.daemon.poli.ScriptSignature;
import tech.coinbub.daemon.poli.Transaction;
import tech.coinbub.daemon.poli.TransactionDetail;
import tech.coinbub.daemon.poli.TxInput;
import tech.coinbub.daemon.poli.TxOutput;
import static tech.coinbub.daemon.testutils.BeanMatcher.hasOnly;
import static tech.coinbub.daemon.testutils.BeanPropertyMatcher.property;
import tech.coinbub.daemon.testutils.Dockerized;

@ExtendWith(Dockerized.class)
public class TestGetTransaction {
    @Test
    public void canGetTransaction(final Poli poli) {
        final Transaction tx = poli.gettransaction("371fa5e751bb27c9e908c30ddff3b98b96651c8b278c71a4da93151cbc563ca5");
        assertThat(tx, hasOnly(
                property("txid", is(equalTo("371fa5e751bb27c9e908c30ddff3b98b96651c8b278c71a4da93151cbc563ca5"))),
                property("version", is(equalTo(1L))),
                property("time", is(equalTo(1526327417L))),
                property("locktime", is(equalTo(0L))),
                property("vin", hasSize(1)),
                property("vout", hasSize(1)),
                property("amount", is(equalTo(new BigDecimal("0.0")))),
                property("confirmations", is(equalTo(3L))),
                property("generated", is(equalTo(true))),
                property("blockhash", is(equalTo("da381cce924f5865a8cd21d59c972376b8920262a19b436f8c1113b8f9416d9a"))),
                property("blockindex", is(equalTo(0L))),
                property("blocktime", is(equalTo(1526327417L))),
                property("timereceived", is(equalTo(1526327419L))),
                property("details", hasSize(1))
        ));
        
        final TransactionDetail detail = tx.details.get(0);
        assertThat(detail, hasOnly(
                property("account", isEmptyString()),
                property("address", is(equalTo("mom7nFUjn8Xnn2RGLQpwsQys76AxrwrZgN"))),
                property("category", is(equalTo(TransactionDetail.Category.immature))),
                property("amount", is(equalTo(new BigDecimal("0.0"))))
        ));
        
        // Identical to that found in `TestGetBlock.supportsShortTransactionList`
        final TxInput in = tx.vin.get(0);
        assertThat(in, hasOnly(
                property("coinbase", is(equalTo("011f0101"))),
                property("sequence", is(equalTo(4294967295L)))
        ));

        // Identical to that found in `TestGetBlock.supportsShortTransactionList`
        final TxOutput out = tx.vout.get(0);
        assertThat(out, hasOnly(
                property("value", is(equalTo(new BigDecimal("0.0")))),
                property("n", is(equalTo(0L))),
                property("scriptPubKey", is(not(nullValue())))
        ));

        // Identical to that found in `TestGetBlock.supportsShortTransactionList`
        final ScriptPublicKey key = out.scriptPubKey;
        assertThat(key, hasOnly(
                property("asm", is(equalTo("OP_DUP OP_HASH160 5a6ebf95354abd9237e07969d87f20ec24fe6e1c OP_EQUALVERIFY OP_CHECKSIG"))),
                property("hex", is(equalTo("76a9145a6ebf95354abd9237e07969d87f20ec24fe6e1c88ac"))),
                property("type", is(equalTo(ScriptPublicKey.Type.pubkeyhash))),
                property("reqSigs", is(equalTo(1L))),
                property("addresses", hasSize(1))
        ));
    }

    @Test
    public void supportsMultipleVouts(final Poli poli) {
        final Transaction tx = poli.gettransaction("d49181751e4fe02f16619d07e1826e214d52b38a8003eec48bf6f359f89a9f11"); // ### THIS IS RIGHT!
        assertThat(tx, hasOnly(
                property("txid", is(equalTo("d49181751e4fe02f16619d07e1826e214d52b38a8003eec48bf6f359f89a9f11"))),
                property("version", is(equalTo(1L))),
                property("time", is(equalTo(1526329237L))),
                property("locktime", is(equalTo(22L))),
                property("vin", hasSize(1)),
                property("vout", hasSize(2)),
                property("amount", is(equalTo(new BigDecimal("-10.0")))),
                property("fee", is(equalTo(new BigDecimal("-0.00010")))),
                property("confirmations", is(equalTo(1L))),
                property("generated", is(nullValue())),
                property("blockhash", is(equalTo("e1a3773cfef6012f2498bbfd56256ea497a8d3202736ae31e1f6a837ccd4d848"))),
                property("blockindex", is(equalTo(2L))),
                property("blocktime", is(equalTo(1526331650L))),
                property("timereceived", is(equalTo(1526329237L))),
                property("details", hasSize(1))
        ));
        
        final TransactionDetail detail = tx.details.get(0);
        assertThat(detail, hasOnly(
                property("account", isEmptyString()),
                property("address", is(equalTo("mm1PNXvwUo9PB7eScmoNyTjz93Wwt6qwDM"))),
                property("category", is(equalTo(TransactionDetail.Category.send))),
                property("amount", is(equalTo(new BigDecimal("-10.0")))),
                property("fee", is(equalTo(new BigDecimal("-0.00010"))))
        ));
        
        final TxInput in = tx.vin.get(0);
        assertThat(in, hasOnly(
                property("txid", is(equalTo("14412d2a47e2b5e83e3292bcfe504e7cb0db198adb0d40e1ebe8b041be9e4e1f"))),
                property("sequence", is(equalTo(4294967294L))),
                property("vout", is(equalTo(0L))),
                property("scriptSig", is(not(nullValue())))
        ));

        final ScriptSignature sig = in.scriptSig;
        assertThat(sig, hasOnly(
                property("asm", is(equalTo("3044022019fffc820334db9a4f75bbd23c32d7781f3e61a6e11af496be7d224bb948bf8502206c4c6c39b51a08eb1ada06c8ad5ef4cad449d6ddf70f73a25e9ba7e99debe98901 02f48dccaa07a252a9db5c96da4b73d9f114e4faf5a12d9d7c049b40febebbff3c"))),
                property("hex", is(equalTo("473044022019fffc820334db9a4f75bbd23c32d7781f3e61a6e11af496be7d224bb948bf8502206c4c6c39b51a08eb1ada06c8ad5ef4cad449d6ddf70f73a25e9ba7e99debe989012102f48dccaa07a252a9db5c96da4b73d9f114e4faf5a12d9d7c049b40febebbff3c")))
        ));

        final TxOutput out = tx.vout.get(0);
        assertThat(out, hasOnly(
                property("value", is(equalTo(new BigDecimal("999999989.9999")))),
                property("n", is(equalTo(0L))),
                property("scriptPubKey", is(not(nullValue())))
        ));

        final ScriptPublicKey key = out.scriptPubKey;
        assertThat(key, hasOnly(
                property("asm", is(equalTo("OP_DUP OP_HASH160 fa6ecc2905d0b4e4d9b7c179dac6db08bdcc244c OP_EQUALVERIFY OP_CHECKSIG"))),
                property("hex", is(equalTo("76a914fa6ecc2905d0b4e4d9b7c179dac6db08bdcc244c88ac"))),
                property("type", is(equalTo(ScriptPublicKey.Type.pubkeyhash))),
                property("reqSigs", is(equalTo(1L))),
                property("addresses", hasSize(1))
        ));
    }
}
