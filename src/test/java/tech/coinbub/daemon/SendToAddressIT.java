package tech.coinbub.daemon;

import com.googlecode.jsonrpc4j.JsonRpcClientException;
import java.math.BigDecimal;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tech.coinbub.daemon.poli.Transaction;
import tech.coinbub.daemon.poli.TransactionDetail;
import static tech.coinbub.daemon.testutils.BeanMatcher.hasOnly;
import static tech.coinbub.daemon.testutils.BeanPropertyMatcher.property;
import tech.coinbub.daemon.testutils.Dockerized;

@ExtendWith(Dockerized.class)
public class SendToAddressIT {
    public static final String VALID_ADDRESS = "mm1PNXvwUo9PB7eScmoNyTjz93Wwt6qwDM";

    @Test
    public void throwsErrorOnInvalidAddress(final Poli poli) {
        final JsonRpcClientException ex = Assertions.assertThrows(JsonRpcClientException.class, () -> {
            poli.sendtoaddress("abc", BigDecimal.ONE);
        });
        assertThat(ex.getMessage(), is(equalTo("Invalid POLI address")));
    }

    @Test
    public void supportsNoComments(final Poli poli) {
        final String txid = poli.sendtoaddress(VALID_ADDRESS, BigDecimal.ONE);
        final Transaction tx = poli.gettransaction(txid);
        assertThat(tx, hasOnly(
                property("txid", is(equalTo(txid))),
                property("version", is(equalTo(1L))),
                property("time", is(not(nullValue()))),
                property("timereceived", is(not(nullValue()))),
                property("locktime", is(not(nullValue()))),
                property("vin", hasSize(1)),
                property("vout", hasSize(2)),
                property("confirmations", is(equalTo(0L))),
                property("amount", is(equalTo(new BigDecimal("-1.0")))),
                property("fee", is(equalTo(new BigDecimal("-0.00010")))),
                property("details", hasSize(1))
        ));
        
        final TransactionDetail detail = tx.details.get(0);
        assertThat(detail, hasOnly(
                property("account", isEmptyString()),
                property("address", is(equalTo(VALID_ADDRESS))),
                property("category", is(equalTo(TransactionDetail.Category.send))),
                property("amount", is(equalTo(new BigDecimal("-1.0")))),
                property("fee", is(equalTo(new BigDecimal("-0.00010"))))
        ));
    }

    @Test
    public void supportsSourceComment(final Poli poli) {
        final String txid = poli.sendtoaddress(VALID_ADDRESS, BigDecimal.ONE, "test transaction!");
        final Transaction tx = poli.gettransaction(txid);
        assertThat(tx, hasOnly(
                property("txid", is(equalTo(txid))),
                property("version", is(equalTo(1L))),
                property("time", is(not(nullValue()))),
                property("timereceived", is(not(nullValue()))),
                property("locktime", is(not(nullValue()))),
                property("vin", hasSize(1)),
                property("vout", hasSize(2)),
                property("confirmations", is(equalTo(0L))),
                property("amount", is(equalTo(new BigDecimal("-1.0")))),
                property("fee", is(equalTo(new BigDecimal("-0.00010")))),
                property("details", hasSize(1)),
                property("comment", is(equalTo("test transaction!")))
        ));
        
        final TransactionDetail detail = tx.details.get(0);
        assertThat(detail, hasOnly(
                property("account", isEmptyString()),
                property("address", is(equalTo(VALID_ADDRESS))),
                property("category", is(equalTo(TransactionDetail.Category.send))),
                property("amount", is(equalTo(new BigDecimal("-1.0")))),
                property("fee", is(equalTo(new BigDecimal("-0.00010"))))
        ));
    }

    @Test
    public void supportsDestinationComment(final Poli poli) {
        final String txid = poli.sendtoaddress(VALID_ADDRESS, BigDecimal.ONE, "test transaction!", "receiving test!");
        final Transaction tx = poli.gettransaction(txid);
        assertThat(tx, hasOnly(
                property("txid", is(equalTo(txid))),
                property("version", is(equalTo(1L))),
                property("time", is(not(nullValue()))),
                property("timereceived", is(not(nullValue()))),
                property("locktime", is(not(nullValue()))),
                property("vin", hasSize(1)),
                property("vout", hasSize(2)),
                property("confirmations", is(equalTo(0L))),
                property("amount", is(equalTo(new BigDecimal("-1.0")))),
                property("fee", is(equalTo(new BigDecimal("-0.00010")))),
                property("details", hasSize(1)),
                property("comment", is(equalTo("test transaction!"))),
                property("to", is(equalTo("receiving test!")))
        ));

        final TransactionDetail detail = tx.details.get(0);
        assertThat(detail, hasOnly(
                property("account", isEmptyString()),
                property("address", is(equalTo(VALID_ADDRESS))),
                property("category", is(equalTo(TransactionDetail.Category.send))),
                property("amount", is(equalTo(new BigDecimal("-1.0")))),
                property("fee", is(equalTo(new BigDecimal("-0.00010"))))
        ));
    }
}
