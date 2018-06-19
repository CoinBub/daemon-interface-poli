package tech.coinbub.daemon.poli;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.util.List;

/**
 * Represents a single block in the blockchain.
 * 
 * Received when calling `getblock`.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Block {
    public String hash;
    public Long confirmations;
    public Long size;
    public Long height;
    public Long version;
    public String merkleroot;
    public BigDecimal mint;
    public Long time;
    public Long nonce;
    public String bits;
    public BigDecimal difficulty;
    public String blocktrust;
    public String chaintrust;
    public String previousblockhash;
    public String nextblockhash;
    public String flags;
    public String proofhash;
    public Long entropybit;
    public String modifier;
    public String modifierv2;
    public List<Transaction> tx;
    // Only present in 3c2700446a627004ad8f2403daec030b780495523ed6a11013be54cc999134a1. Unable to replicate presently
    public String signature;
}

//poli@test:~$ polid getblock da381cce924f5865a8cd21d59c972376b8920262a19b436f8c1113b8f9416d9a
//{
//    "hash" : "da381cce924f5865a8cd21d59c972376b8920262a19b436f8c1113b8f9416d9a",
//    "confirmations" : 3,
//    "size" : 175,
//    "height" : 31,
//    "version" : 7,
//    "merkleroot" : "371fa5e751bb27c9e908c30ddff3b98b96651c8b278c71a4da93151cbc563ca5",
//    "mint" : 0.00000000,
//    "time" : 1526327417,
//    "nonce" : 90112000,
//    "bits" : "1f00ced8",
//    "difficulty" : 0.00001888,
//    "blocktrust" : "13cd6",
//    "chaintrust" : "413e0",
//    "previousblockhash" : "b210ba1f4c84192690868e48130b41c06c800a38650cb9592fb71f878d5625ef",
//    "nextblockhash" : "df3c2ec2337250daa07bbc8db81d32eeaa7fe0ebd0b1bfbe04c57f6065e04d2c",
//    "flags" : "proof-of-work",
//    "proofhash" : "00008e76d602b82f849d95cdb6f927e2777141947b7671e7f7f562ae1717267e",
//    "entropybit" : 0,
//    "modifier" : "00000000051b1e05",
//    "modifierv2" : "b981e8ea496550cc18a1e3d9945b1fc784703ca646b81b44beee04d0016611d5",
//    "tx" : [
//        "371fa5e751bb27c9e908c30ddff3b98b96651c8b278c71a4da93151cbc563ca5"
//    ]
//}
//poli@test:~$ polid getblock da381cce924f5865a8cd21d59c972376b8920262a19b436f8c1113b8f9416d9a true
//{
//    "hash" : "da381cce924f5865a8cd21d59c972376b8920262a19b436f8c1113b8f9416d9a",
//    "confirmations" : 3,
//    "size" : 175,
//    "height" : 31,
//    "version" : 7,
//    "merkleroot" : "371fa5e751bb27c9e908c30ddff3b98b96651c8b278c71a4da93151cbc563ca5",
//    "mint" : 0.00000000,
//    "time" : 1526327417,
//    "nonce" : 90112000,
//    "bits" : "1f00ced8",
//    "difficulty" : 0.00001888,
//    "blocktrust" : "13cd6",
//    "chaintrust" : "413e0",
//    "previousblockhash" : "b210ba1f4c84192690868e48130b41c06c800a38650cb9592fb71f878d5625ef",
//    "nextblockhash" : "df3c2ec2337250daa07bbc8db81d32eeaa7fe0ebd0b1bfbe04c57f6065e04d2c",
//    "flags" : "proof-of-work",
//    "proofhash" : "00008e76d602b82f849d95cdb6f927e2777141947b7671e7f7f562ae1717267e",
//    "entropybit" : 0,
//    "modifier" : "00000000051b1e05",
//    "modifierv2" : "b981e8ea496550cc18a1e3d9945b1fc784703ca646b81b44beee04d0016611d5",
//    "tx" : [
//        {
//            "txid" : "371fa5e751bb27c9e908c30ddff3b98b96651c8b278c71a4da93151cbc563ca5",
//            "txid" : "371fa5e751bb27c9e908c30ddff3b98b96651c8b278c71a4da93151cbc563ca5",
//            "version" : 1,
//            "time" : 1526327417,
//            "locktime" : 0,
//            "vin" : [
//                {
//                    "coinbase" : "011f0101",
//                    "sequence" : 4294967295
//                }
//            ],
//            "vout" : [
//                {
//                    "value" : 0.00000000,
//                    "n" : 0,
//                    "scriptPubKey" : {
//                        "asm" : "OP_DUP OP_HASH160 5a6ebf95354abd9237e07969d87f20ec24fe6e1c OP_EQUALVERIFY OP_CHECKSIG",
//                        "hex" : "76a9145a6ebf95354abd9237e07969d87f20ec24fe6e1c88ac",
//                        "reqSigs" : 1,
//                        "type" : "pubkeyhash",
//                        "addresses" : [
//                            "mom7nFUjn8Xnn2RGLQpwsQys76AxrwrZgN"
//                        ]
//                    }
//                }
//            ]
//        }
//    ]
//}
