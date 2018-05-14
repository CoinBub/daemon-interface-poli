package tech.coinbub.daemon.poli;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Transaction {
    public String txid;
    public Long version;
    public Long time;
    public Long locktime;
    public List<TxInput> vin;
    public List<TxOutput> vout;
    
    // Long-form transaction data
    public BigDecimal amount;
    public BigDecimal fee;
    public Long confirmations;
    public Boolean generated;
    public String blockhash;
    public Long blockindex;
    public Long blocktime;
    public Long timereceived;
    public List<TransactionDetail> details;
    public String comment;
    public String to;
    
    public Transaction() {}
    public Transaction(final String txid) {
        this.txid = txid;
    }
}

//poli@test:~$ polid gettransaction d49181751e4fe02f16619d07e1826e214d52b38a8003eec48bf6f359f89a9f11
//{
//    "txid" : "d49181751e4fe02f16619d07e1826e214d52b38a8003eec48bf6f359f89a9f11",
//    "version" : 1,
//    "time" : 1526329237,
//    "locktime" : 22,
//    "vin" : [
//        {
//            "txid" : "14412d2a47e2b5e83e3292bcfe504e7cb0db198adb0d40e1ebe8b041be9e4e1f",
//            "vout" : 0,
//            "scriptSig" : {
//                "asm" : "3044022019fffc820334db9a4f75bbd23c32d7781f3e61a6e11af496be7d224bb948bf8502206c4c6c39b51a08eb1ada06c8ad5ef4cad449d6ddf70f73a25e9ba7e99debe98901 02f48dccaa07a252a9db5c96da4b73d9f114e4faf5a12d9d7c049b40febebbff3c",
//                "hex" : "473044022019fffc820334db9a4f75bbd23c32d7781f3e61a6e11af496be7d224bb948bf8502206c4c6c39b51a08eb1ada06c8ad5ef4cad449d6ddf70f73a25e9ba7e99debe989012102f48dccaa07a252a9db5c96da4b73d9f114e4faf5a12d9d7c049b40febebbff3c"
//            },
//            "sequence" : 4294967294
//        }
//    ],
//    "vout" : [
//        {
//            "value" : 999999989.99989998,
//            "n" : 0,
//            "scriptPubKey" : {
//                "asm" : "OP_DUP OP_HASH160 fa6ecc2905d0b4e4d9b7c179dac6db08bdcc244c OP_EQUALVERIFY OP_CHECKSIG",
//                "hex" : "76a914fa6ecc2905d0b4e4d9b7c179dac6db08bdcc244c88ac",
//                "reqSigs" : 1,
//                "type" : "pubkeyhash",
//                "addresses" : [
//                    "n4M7xMf3ZyqAYzodfdFbx2xgYCwveALhYQ"
//                ]
//            }
//        },
//        {
//            "value" : 10.00000000,
//            "n" : 1,
//            "scriptPubKey" : {
//                "asm" : "OP_DUP OP_HASH160 3c3935932c0a8d7a265902df2ec7a96e6961e190 OP_EQUALVERIFY OP_CHECKSIG",
//                "hex" : "76a9143c3935932c0a8d7a265902df2ec7a96e6961e19088ac",
//                "reqSigs" : 1,
//                "type" : "pubkeyhash",
//                "addresses" : [
//                    "mm1PNXvwUo9PB7eScmoNyTjz93Wwt6qwDM"
//                ]
//            }
//        }
//    ],
//    "amount" : -10.00000000,
//    "fee" : -0.00010000,
//    "confirmations" : 0,
//    "txid" : "d49181751e4fe02f16619d07e1826e214d52b38a8003eec48bf6f359f89a9f11",
//    "time" : 1526329237,
//    "timereceived" : 1526329237,
//    "details" : [
//        {
//            "account" : "",
//            "address" : "mm1PNXvwUo9PB7eScmoNyTjz93Wwt6qwDM",
//            "category" : "send",
//            "amount" : -10.00000000,
//            "fee" : -0.00010000
//        }
//    ]
//}
