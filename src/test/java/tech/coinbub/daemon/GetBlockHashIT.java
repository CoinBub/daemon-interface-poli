package tech.coinbub.daemon;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tech.coinbub.daemon.testutils.Dockerized;

@ExtendWith(Dockerized.class)
public class GetBlockHashIT {
    public static final Long HEIGHT = 22L;

    @Test
    public void canGetBlockHash(final Poli poli) {
        final String best = poli.getblockhash(HEIGHT);
        assertThat(best, is(equalTo("10e8298ecc7a4668273cf69aa2f66324b100cacfd0916cf60b8e46df14d1b9f4")));
    }
}
