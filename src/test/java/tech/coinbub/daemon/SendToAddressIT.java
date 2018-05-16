package tech.coinbub.daemon;

import com.googlecode.jsonrpc4j.JsonRpcClientException;
import java.math.BigDecimal;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tech.coinbub.daemon.poli.Transaction;
import tech.coinbub.daemon.poli.TransactionDetail;
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
        assertThat(tx.amount, is(equalTo(new BigDecimal("-1.0"))));
    }

    @Test
    public void supportsSourceComment(final Poli poli) {
        final String txid = poli.sendtoaddress(VALID_ADDRESS, BigDecimal.ONE, "test transaction!");
        final Transaction tx = poli.gettransaction(txid);
        Assertions.assertAll(
                () -> { assertThat(tx.amount, is(equalTo(new BigDecimal("-1.0")))); },
                () -> { assertThat(tx.details, hasSize(1)); },
                () -> { assertThat(tx.comment, is(equalTo("test transaction!"))); }
        );
        
        final TransactionDetail detail = tx.details.get(0);
        Assertions.assertAll(
                () -> { assertThat(detail.account, isEmptyString()); },
                () -> { assertThat(detail.address, is(equalTo(VALID_ADDRESS))); },
                () -> { assertThat(detail.category, is(equalTo(TransactionDetail.Category.send))); },
                () -> { assertThat(detail.amount, is(equalTo(new BigDecimal("-1.0")))); }
        );
    }

    @Test
    public void supportsDestinationComment(final Poli poli) {
        final String txid = poli.sendtoaddress(VALID_ADDRESS, BigDecimal.ONE, "test transaction!", "receiving test!");
        final Transaction tx = poli.gettransaction(txid);
        Assertions.assertAll(
                () -> { assertThat(tx.amount, is(equalTo(new BigDecimal("-1.0")))); },
                () -> { assertThat(tx.details, hasSize(1)); },
                () -> { assertThat(tx.comment, is(equalTo("test transaction!"))); },
                () -> { assertThat(tx.to, is(equalTo("receiving test!"))); }
        );
        
        final TransactionDetail detail = tx.details.get(0);
        Assertions.assertAll(
                () -> { assertThat(detail.account, isEmptyString()); },
                () -> { assertThat(detail.address, is(equalTo(VALID_ADDRESS))); },
                () -> { assertThat(detail.category, is(equalTo(TransactionDetail.Category.send))); },
                () -> { assertThat(detail.amount, is(equalTo(new BigDecimal("-1.0")))); }
        );
    }
}
