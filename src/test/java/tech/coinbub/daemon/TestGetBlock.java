package tech.coinbub.daemon;

import java.math.BigDecimal;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tech.coinbub.daemon.poli.Block;
import tech.coinbub.daemon.poli.ScriptPublicKey;
import tech.coinbub.daemon.poli.Transaction;
import tech.coinbub.daemon.poli.TxInput;
import tech.coinbub.daemon.poli.TxOutput;
import static tech.coinbub.daemon.testutils.BeanMatcher.hasOnly;
import static tech.coinbub.daemon.testutils.BeanPropertyMatcher.property;
import tech.coinbub.daemon.testutils.Dockerized;

@ExtendWith(Dockerized.class)
public class TestGetBlock {
    @Test
    public void supportsShortTransactionList(final Poli poli) {
        final Block block = poli.getblock("da381cce924f5865a8cd21d59c972376b8920262a19b436f8c1113b8f9416d9a");
        assertThat(block, hasOnly(
                property("hash", is(equalTo("da381cce924f5865a8cd21d59c972376b8920262a19b436f8c1113b8f9416d9a"))),
                property("confirmations", is(equalTo(3L))),
                property("size", is(equalTo(175L))),
                property("height", is(equalTo(31L))),
                property("version", is(equalTo(7L))),
                property("merkleroot", is(equalTo("371fa5e751bb27c9e908c30ddff3b98b96651c8b278c71a4da93151cbc563ca5"))),
                property("mint", is(equalTo(new BigDecimal("0.0")))),
                property("time", is(equalTo(1526327417L))),
                property("nonce", is(equalTo(90112000L))),
                property("bits", is(equalTo("1f00ced8"))),
                property("difficulty", is(equalTo(new BigDecimal("0.00001888")))),
                property("blocktrust", is(equalTo("13cd6"))),
                property("chaintrust", is(equalTo("413e0"))),
                property("previousblockhash", is(equalTo("b210ba1f4c84192690868e48130b41c06c800a38650cb9592fb71f878d5625ef"))),
                property("nextblockhash", is(equalTo("df3c2ec2337250daa07bbc8db81d32eeaa7fe0ebd0b1bfbe04c57f6065e04d2c"))),
                property("flags", is(equalTo("proof-of-work"))),
                property("proofhash", is(equalTo("00008e76d602b82f849d95cdb6f927e2777141947b7671e7f7f562ae1717267e"))),
                property("entropybit", is(equalTo(0L))),
                property("modifier", is(equalTo("00000000051b1e05"))),
                property("modifierv2", is(equalTo("b981e8ea496550cc18a1e3d9945b1fc784703ca646b81b44beee04d0016611d5"))),
                property("tx", hasSize(1))
        ));

        final Transaction tx = block.tx.get(0);
        assertThat(tx, hasOnly(
                property("txid", is(equalTo("371fa5e751bb27c9e908c30ddff3b98b96651c8b278c71a4da93151cbc563ca5"))),
                property("version", is(nullValue())),
                property("time", is(nullValue())),
                property("locktime", is(nullValue())),
                property("vin", is(nullValue())),
                property("vout", is(nullValue()))
        ));
    }

    @Test
    public void supportsLongTransactionList(final Poli poli) {
        final Block block = poli.getblock("da381cce924f5865a8cd21d59c972376b8920262a19b436f8c1113b8f9416d9a", true);
        // Block details verified in `supportsShortTransactionList()`
        final Transaction tx = block.tx.get(0);
        assertThat(tx, hasOnly(
                property("txid", is(equalTo("371fa5e751bb27c9e908c30ddff3b98b96651c8b278c71a4da93151cbc563ca5"))),
                property("version", is(equalTo(1L))),
                property("time", is(equalTo(1526327417L))),
                property("locktime", is(equalTo(0L))),
                property("vin", hasSize(1)),
                property("vout", hasSize(1))
        ));

        final TxInput in = tx.vin.get(0);
        assertThat(in, hasOnly(
                property("coinbase", is(equalTo("011f0101"))),
                property("sequence", is(equalTo(4294967295L)))
        ));

        final TxOutput out = tx.vout.get(0);
        assertThat(out, hasOnly(
                property("value", is(equalTo(new BigDecimal("0.0")))),
                property("n", is(equalTo(0L))),
                property("scriptPubKey", is(not(nullValue())))
        ));

        final ScriptPublicKey key = out.scriptPubKey;
        assertThat(key, hasOnly(
                property("asm", is(equalTo("OP_DUP OP_HASH160 5a6ebf95354abd9237e07969d87f20ec24fe6e1c OP_EQUALVERIFY OP_CHECKSIG"))),
                property("hex", is(equalTo("76a9145a6ebf95354abd9237e07969d87f20ec24fe6e1c88ac"))),
                property("type", is(equalTo(ScriptPublicKey.Type.pubkeyhash))),
                property("reqSigs", is(equalTo(1L))),
                property("addresses", hasSize(1))
        ));
    }
    
}
