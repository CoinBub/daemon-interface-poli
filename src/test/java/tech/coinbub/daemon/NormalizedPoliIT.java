package tech.coinbub.daemon;

import java.math.BigDecimal;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static tech.coinbub.daemon.GetBlockHashIT.HEIGHT;
import tech.coinbub.daemon.normalization.model.Block;
import tech.coinbub.daemon.normalization.model.Transaction;
import tech.coinbub.daemon.normalization.model.TransactionDetail;
import static tech.coinbub.daemon.testutils.BeanMatcher.hasOnly;
import static tech.coinbub.daemon.testutils.BeanPropertyMatcher.property;
import tech.coinbub.daemon.testutils.Dockerized;

@ExtendWith(Dockerized.class)
public class NormalizedPoliIT {
    @Test
    public void testGetblockhash(final NormalizedPoli normalized) {
        assertThat(normalized.getblockhash(HEIGHT), is(equalTo("10e8298ecc7a4668273cf69aa2f66324b100cacfd0916cf60b8e46df14d1b9f4")));
    }

    @Test
    public void testGetblock(final NormalizedPoli normalized) {
        final Block block = normalized.getblock("da381cce924f5865a8cd21d59c972376b8920262a19b436f8c1113b8f9416d9a");
        assertThat(block, hasOnly(
                property("hash", is(equalTo("da381cce924f5865a8cd21d59c972376b8920262a19b436f8c1113b8f9416d9a"))),
                property("confirmations", is(equalTo(3L))),
                property("size", is(equalTo(175L))),
                property("height", is(equalTo(31L))),
                property("time", is(equalTo(1526327417L))),
                property("previousblockhash", is(equalTo("b210ba1f4c84192690868e48130b41c06c800a38650cb9592fb71f878d5625ef"))),
                property("nextblockhash", is(equalTo("df3c2ec2337250daa07bbc8db81d32eeaa7fe0ebd0b1bfbe04c57f6065e04d2c"))),
                property("tx", hasSize(1))
        ));

        assertThat(block.tx.get(0), is(equalTo("371fa5e751bb27c9e908c30ddff3b98b96651c8b278c71a4da93151cbc563ca5")));
    }

    @Test
    public void testGettransaction(final NormalizedPoli normalized) {
        final Transaction tx = normalized.gettransaction("371fa5e751bb27c9e908c30ddff3b98b96651c8b278c71a4da93151cbc563ca5");
        assertThat(tx, hasOnly(
                property("id", is(equalTo("371fa5e751bb27c9e908c30ddff3b98b96651c8b278c71a4da93151cbc563ca5"))),
                property("time", is(equalTo(1526327417L))),
                property("amount", is(equalTo(new BigDecimal("0.0")))),
                property("confirmations", is(equalTo(3L))),
                property("blockhash", is(equalTo("da381cce924f5865a8cd21d59c972376b8920262a19b436f8c1113b8f9416d9a"))),
                property("details", hasSize(1))
        ));
        
        final TransactionDetail detail = tx.details.get(0);
        assertThat(detail, hasOnly(
                property("address", is(equalTo("mom7nFUjn8Xnn2RGLQpwsQys76AxrwrZgN"))),
                property("amount", is(equalTo(new BigDecimal("0.0"))))
        ));
    }

    @Test
    public void testGetnewaddress(final NormalizedPoli normalized) {
        assertThat(normalized.getnewaddress().length(), is(equalTo(34)));
    }

    @Test
    public void testSendToAddressNoComments(final NormalizedPoli normalized) {
        final String txid = normalized.sendtoaddress(SendToAddressIT.VALID_ADDRESS, BigDecimal.ONE);
        final Transaction tx = normalized.gettransaction(txid);
        assertThat(tx, hasOnly(
                property("id", not(isEmptyString())),
                property("amount", is(equalTo(new BigDecimal("-1.0")))),
                property("fee", is(closeTo(new BigDecimal("-0.0001"), new BigDecimal("0.0001")))),
                property("confirmations", is(equalTo(0L))),
                property("time", is(not(nullValue()))),
                property("details", hasSize(1))
        ));

        assertThat(tx.details.get(0), hasOnly(
                property("address", is(equalTo(SendToAddressIT.VALID_ADDRESS))),
                property("amount", is(equalTo(new BigDecimal("-1.0")))),
                property("fee", is(closeTo(new BigDecimal("-0.0001"), new BigDecimal("0.0001"))))
        ));
    }

    @Test
    public void testSendToAddressSourceComment(final NormalizedPoli normalized) {
        final String txid = normalized.sendtoaddress(SendToAddressIT.VALID_ADDRESS, BigDecimal.ONE, "test transaction!");
        final Transaction tx = normalized.gettransaction(txid);
        assertThat(tx, hasOnly(
                property("id", not(isEmptyString())),
                property("amount", is(equalTo(new BigDecimal("-1.0")))),
                property("fee", is(closeTo(new BigDecimal("-0.0001"), new BigDecimal("0.0001")))),
                property("confirmations", is(equalTo(0L))),
                property("time", is(not(nullValue()))),
                property("comment_from", is(equalTo("test transaction!"))),
                property("details", hasSize(1))
        ));

        assertThat(tx.details.get(0), hasOnly(
                property("address", is(equalTo(SendToAddressIT.VALID_ADDRESS))),
                property("amount", is(equalTo(new BigDecimal("-1.0")))),
                property("fee", is(closeTo(new BigDecimal("-0.0001"), new BigDecimal("0.0001"))))
        ));
    }

    @Test
    public void testSendToAddressDestComment(final NormalizedPoli normalized) {
        final String txid = normalized.sendtoaddress(SendToAddressIT.VALID_ADDRESS, BigDecimal.ONE, "test transaction!", "receiving test!");
        final Transaction tx = normalized.gettransaction(txid);
        assertThat(tx, hasOnly(
                property("id", not(isEmptyString())),
                property("amount", is(equalTo(new BigDecimal("-1.0")))),
                property("fee", is(closeTo(new BigDecimal("-0.0001"), new BigDecimal("0.0001")))),
                property("time", is(not(nullValue()))),
                property("confirmations", is(equalTo(0L))),
                property("details", hasSize(1)),
                property("comment_from", is(equalTo("test transaction!"))),
                property("comment_to", is(equalTo("receiving test!")))
        ));
    
        final TransactionDetail detail = tx.details.get(0);
        assertThat(detail, hasOnly(
                property("address", is(equalTo(SendToAddressIT.VALID_ADDRESS))),
                property("amount", is(equalTo(new BigDecimal("-1.0")))),
                property("fee", is(closeTo(new BigDecimal("-0.0001"), new BigDecimal("0.0001"))))
        ));
    }

}
