package tech.coinbub.daemon.poli;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Unspent {
    public String txid;
    public Long vout;
    public String address;
    public String account;
    public String scriptPubKey;
    public BigDecimal amount;
    public Long confirmations;
    public Boolean spendable;
}

//poli@test:~$ polid listunspent   
//[
//    {
//        "txid" : "135b3a7afbad6086f6f08b68810692113276b00f782440581bfa03b98da35506",
//        "vout" : 0,
//        "address" : "mieSGq93s7GVt4HZEVrnJLuecbXFynG4xd",
//        "scriptPubKey" : "76a9142252a50b2b2352d50be9b1a3466f9ec41bda592a88ac",
//        "amount" : 0.00000000,
//        "confirmations" : 22,
//        "spendable" : true
//    },
//    {
//        "txid" : "1f98cc8907a48e600625b3549cd47320946eadb510b6d90e918b2c0df3c1e680",
//        "vout" : 0,
//        "address" : "mjrKwNRznLQ8vVrtPc7XzyRC7TcifvS7C6",
//        "scriptPubKey" : "76a9142f8a79308442e640d16c94334b0f6dfa1a6cc7c788ac",
//        "amount" : 0.00000000,
//        "confirmations" : 12,
//        "spendable" : true
//    },
//    {
//        "txid" : "32af73c86773974899e10ec17df71fbe610c57f8f2176502884cec9cc8361d28",
//        "vout" : 0,
//        "address" : "mgGnapnxEQfay2DvuMkSAroyTyUCswbJsM",
//        "scriptPubKey" : "76a914084a32345194ad1c3d275f7e31a8d747924e923288ac",
//        "amount" : 0.00000000,
//        "confirmations" : 14,
//        "spendable" : true
//    },
//    ...
//]