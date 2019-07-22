package com.huytran.rerm.ethcontract;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple6;
import org.web3j.tuples.generated.Tuple8;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.3.0.
 */
public class RentHouseContract extends Contract {
    private static final String BINARY = "0x608060405260018060006101000a81548160ff02191690831515021790555034801561002a57600080fd5b50604051604080611dff8339810180604052604081101561004a57600080fd5b81019080805190602001909291908051906020019092919050505081601a8190555080601b819055505050611d7b806100846000396000f3fe608060405234801561001057600080fd5b50600436106100cf5760003560e01c806338cc48311161008c57806374ef3d601161006657806374ef3d60146109f25780638da5cb5b14610a10578063c101c8f714610c5c578063ddfb8a4b14610cbc576100cf565b806338cc48311461080657806343df2c7514610850578063476f1631146109d4576100cf565b806302fb8fe9146100d4578063139a334d1461013957806322f3e2d4146104715780632e88ab0b146104935780632fd949ca146106df5780633433523e146106e9575b600080fd5b610100600480360360208110156100ea57600080fd5b8101908080359060200190929190505050610cda565b60405180878152602001868152602001858152602001848152602001838152602001828152602001965050505050505060405180910390f35b61046f600480360361010081101561015057600080fd5b810190808035151590602001909291908035906020019064010000000081111561017957600080fd5b82018360208201111561018b57600080fd5b803590602001918460018302840111640100000000831117156101ad57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290803590602001909291908035906020019064010000000081111561021a57600080fd5b82018360208201111561022c57600080fd5b8035906020019184600183028401116401000000008311171561024e57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290803590602001906401000000008111156102b157600080fd5b8201836020820111156102c357600080fd5b803590602001918460018302840111640100000000831117156102e557600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290803590602001909291908035906020019064010000000081111561035257600080fd5b82018360208201111561036457600080fd5b8035906020019184600183028401116401000000008311171561038657600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290803590602001906401000000008111156103e957600080fd5b8201836020820111156103fb57600080fd5b8035906020019184600183028401116401000000008311171561041d57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290505050610d23565b005b610479610ffd565b604051808215151515815260200191505060405180910390f35b61049b611010565b6040518080602001898152602001806020018060200188815260200180602001806020018715151515815260200186810386528e818151815260200191508051906020019080838360005b838110156105015780820151818401526020810190506104e6565b50505050905090810190601f16801561052e5780820380516001836020036101000a031916815260200191505b5086810385528c818151815260200191508051906020019080838360005b8381101561056757808201518184015260208101905061054c565b50505050905090810190601f1680156105945780820380516001836020036101000a031916815260200191505b5086810384528b818151815260200191508051906020019080838360005b838110156105cd5780820151818401526020810190506105b2565b50505050905090810190601f1680156105fa5780820380516001836020036101000a031916815260200191505b50868103835289818151815260200191508051906020019080838360005b83811015610633578082015181840152602081019050610618565b50505050905090810190601f1680156106605780820380516001836020036101000a031916815260200191505b50868103825288818151815260200191508051906020019080838360005b8381101561069957808201518184015260208101905061067e565b50505050905090810190601f1680156106c65780820380516001836020036101000a031916815260200191505b509d505050505050505050505050505060405180910390f35b6106e761134b565b005b6106f16114a6565b6040518080602001898152602001888152602001878152602001868152602001806020018581526020018415151515815260200183810383528b818151815260200191508051906020019080838360005b8381101561075d578082015181840152602081019050610742565b50505050905090810190601f16801561078a5780820380516001836020036101000a031916815260200191505b50838103825286818151815260200191508051906020019080838360005b838110156107c35780820151818401526020810190506107a8565b50505050905090810190601f1680156107f05780820380516001836020036101000a031916815260200191505b509a505050505050505050505060405180910390f35b61080e611619565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b6109d2600480360360e081101561086657600080fd5b810190808035906020019064010000000081111561088357600080fd5b82018360208201111561089557600080fd5b803590602001918460018302840111640100000000831117156108b757600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290803590602001909291908035906020019092919080359060200190929190803590602001909291908035906020019064010000000081111561094257600080fd5b82018360208201111561095457600080fd5b8035906020019184600183028401116401000000008311171561097657600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f82011690508083019250505050505050919291929080359060200190929190505050611621565b005b6109dc61177e565b6040518082815260200191505060405180910390f35b6109fa611784565b6040518082815260200191505060405180910390f35b610a1861178a565b6040518080602001898152602001806020018060200188815260200180602001806020018715151515815260200186810386528e818151815260200191508051906020019080838360005b83811015610a7e578082015181840152602081019050610a63565b50505050905090810190601f168015610aab5780820380516001836020036101000a031916815260200191505b5086810385528c818151815260200191508051906020019080838360005b83811015610ae4578082015181840152602081019050610ac9565b50505050905090810190601f168015610b115780820380516001836020036101000a031916815260200191505b5086810384528b818151815260200191508051906020019080838360005b83811015610b4a578082015181840152602081019050610b2f565b50505050905090810190601f168015610b775780820380516001836020036101000a031916815260200191505b50868103835289818151815260200191508051906020019080838360005b83811015610bb0578082015181840152602081019050610b95565b50505050905090810190601f168015610bdd5780820380516001836020036101000a031916815260200191505b50868103825288818151815260200191508051906020019080838360005b83811015610c16578082015181840152602081019050610bfb565b50505050905090810190601f168015610c435780820380516001836020036101000a031916815260200191505b509d505050505050505050505050505060405180910390f35b610cba600480360360c0811015610c7257600080fd5b81019080803590602001909291908035906020019092919080359060200190929190803590602001909291908035906020019092919080359060200190929190505050611ac5565b005b610cc4611ca4565b6040518082815260200191505060405180910390f35b601c8181548110610ce757fe5b90600052602060002090600602016000915090508060000154908060010154908060020154908060030154908060040154908060050154905086565b87808015610d415750600260070160009054906101000a900460ff16155b80610d66575080158015610d655750600a60070160009054906101000a900460ff16155b5b610dd8576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260178152602001807f4163636f756e742048617320416c72656164792053657400000000000000000081525060200191505060405180910390fd5b8815610eea576040518061010001604052808981526020018881526020018781526020018681526020018581526020018481526020018381526020016001151581525060026000820151816000019080519060200190610e39929190611caa565b50602082015181600101556040820151816002019080519060200190610e60929190611caa565b506060820151816003019080519060200190610e7d929190611caa565b506080820151816004015560a0820151816005019080519060200190610ea4929190611caa565b5060c0820151816006019080519060200190610ec1929190611caa565b5060e08201518160070160006101000a81548160ff021916908315150217905550905050610ff2565b60405180610100016040528089815260200188815260200187815260200186815260200185815260200184815260200183815260200160011515815250600a6000820151816000019080519060200190610f45929190611caa565b50602082015181600101556040820151816002019080519060200190610f6c929190611caa565b506060820151816003019080519060200190610f89929190611caa565b506080820151816004015560a0820151816005019080519060200190610fb0929190611caa565b5060c0820151816006019080519060200190610fcd929190611caa565b5060e08201518160070160006101000a81548160ff0219169083151502179055509050505b505050505050505050565b600160009054906101000a900460ff1681565b600a806000018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156110aa5780601f1061107f576101008083540402835291602001916110aa565b820191906000526020600020905b81548152906001019060200180831161108d57829003601f168201915b505050505090806001015490806002018054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561114e5780601f106111235761010080835404028352916020019161114e565b820191906000526020600020905b81548152906001019060200180831161113157829003601f168201915b505050505090806003018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156111ec5780601f106111c1576101008083540402835291602001916111ec565b820191906000526020600020905b8154815290600101906020018083116111cf57829003601f168201915b505050505090806004015490806005018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156112905780601f1061126557610100808354040283529160200191611290565b820191906000526020600020905b81548152906001019060200180831161127357829003601f168201915b505050505090806006018054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561132e5780601f106113035761010080835404028352916020019161132e565b820191906000526020600020905b81548152906001019060200180831161131157829003601f168201915b5050505050908060070160009054906101000a900460ff16905088565b600260070160009054906101000a900460ff16158061137a5750600a60070160009054906101000a900460ff16155b806113955750601260070160009054906101000a900460ff16155b611407576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252600d8152602001807f496e666f204e6f742046756c6c0000000000000000000000000000000000000081525060200191505060405180910390fd5b600160009054906101000a900460ff16611489576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260138152602001807f436f6e7472616374204e6f74204163746976650000000000000000000000000081525060200191505060405180910390fd5b6000600160006101000a81548160ff021916908315150217905550565b6012806000018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156115405780601f1061151557610100808354040283529160200191611540565b820191906000526020600020905b81548152906001019060200180831161152357829003601f168201915b505050505090806001015490806002015490806003015490806004015490806005018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156115f65780601f106115cb576101008083540402835291602001916115f6565b820191906000526020600020905b8154815290600101906020018083116115d957829003601f168201915b5050505050908060060154908060070160009054906101000a900460ff16905088565b600033905090565b601260070160009054906101000a900460ff16156116a7576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260148152602001807f526f6f6d2048617320416c72656164792053657400000000000000000000000081525060200191505060405180910390fd5b6040518061010001604052808881526020018781526020018681526020018581526020018481526020018381526020018281526020016001151581525060126000820151816000019080519060200190611702929190611caa565b506020820151816001015560408201518160020155606082015181600301556080820151816004015560a0820151816005019080519060200190611747929190611caa565b5060c0820151816006015560e08201518160070160006101000a81548160ff02191690831515021790555090505050505050505050565b601a5481565b60005481565b6002806000018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156118245780601f106117f957610100808354040283529160200191611824565b820191906000526020600020905b81548152906001019060200180831161180757829003601f168201915b505050505090806001015490806002018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156118c85780601f1061189d576101008083540402835291602001916118c8565b820191906000526020600020905b8154815290600101906020018083116118ab57829003601f168201915b505050505090806003018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156119665780601f1061193b57610100808354040283529160200191611966565b820191906000526020600020905b81548152906001019060200180831161194957829003601f168201915b505050505090806004015490806005018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015611a0a5780601f106119df57610100808354040283529160200191611a0a565b820191906000526020600020905b8154815290600101906020018083116119ed57829003601f168201915b505050505090806006018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015611aa85780601f10611a7d57610100808354040283529160200191611aa8565b820191906000526020600020905b815481529060010190602001808311611a8b57829003601f168201915b5050505050908060070160009054906101000a900460ff16905088565b600260070160009054906101000a900460ff161580611af45750600a60070160009054906101000a900460ff16155b80611b0f5750601260070160009054906101000a900460ff16155b611b81576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252600d8152602001807f496e666f204e6f742046756c6c0000000000000000000000000000000000000081525060200191505060405180910390fd5b600160009054906101000a900460ff16611c03576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260138152602001807f436f6e7472616374204e6f74204163746976650000000000000000000000000081525060200191505060405180910390fd5b601c6040518060c001604052808881526020018781526020018681526020018581526020018481526020018381525090806001815401808255809150509060018203906000526020600020906006020160009091929091909150600082015181600001556020820151816001015560408201518160020155606082015181600301556080820151816004015560a08201518160050155505050505050505050565b601b5481565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10611ceb57805160ff1916838001178555611d19565b82800160010185558215611d19579182015b82811115611d18578251825591602001919060010190611cfd565b5b509050611d269190611d2a565b5090565b611d4c91905b80821115611d48576000816000905550600101611d30565b5090565b9056fea165627a7a7230582035b9627eae3b337c25c21e92d13afa28ac0c4aeb9f9b502dda630741daf8c2200029";

    public static final String FUNC_RENTPAIDS = "rentPaids";

    public static final String FUNC_ISACTIVE = "isActive";

    public static final String FUNC_RENTER = "renter";

    public static final String FUNC_ROOM = "room";

    public static final String FUNC_TIMESTAMPCONTRACTSTART = "timestampContractStart";

    public static final String FUNC_TIMESTAMPCREATED = "timestampCreated";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_TIMESTAMPCONTRACTEND = "timestampContractEnd";

    public static final String FUNC_GETADDRESS = "getAddress";

    public static final String FUNC_SETACCOUNT = "setAccount";

    public static final String FUNC_SETROOM = "setRoom";

    public static final String FUNC_ADDPAYMENTREQUEST = "addPaymentRequest";

    public static final String FUNC_TERMINATECONTRACT = "terminateContract";

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
    }

    @Deprecated
    protected RentHouseContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected RentHouseContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected RentHouseContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected RentHouseContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<Tuple6<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>> rentPaids(BigInteger param0) {
        final Function function = new Function(FUNC_RENTPAIDS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple6<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>>(
                new Callable<Tuple6<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple6<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple6<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue());
                    }
                });
    }

    public RemoteCall<Boolean> isActive() {
        final Function function = new Function(FUNC_ISACTIVE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<Tuple8<String, BigInteger, String, String, BigInteger, String, String, Boolean>> renter() {
        final Function function = new Function(FUNC_RENTER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Bool>() {}));
        return new RemoteCall<Tuple8<String, BigInteger, String, String, BigInteger, String, String, Boolean>>(
                new Callable<Tuple8<String, BigInteger, String, String, BigInteger, String, String, Boolean>>() {
                    @Override
                    public Tuple8<String, BigInteger, String, String, BigInteger, String, String, Boolean> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple8<String, BigInteger, String, String, BigInteger, String, String, Boolean>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (String) results.get(5).getValue(), 
                                (String) results.get(6).getValue(), 
                                (Boolean) results.get(7).getValue());
                    }
                });
    }

    public RemoteCall<Tuple8<String, BigInteger, BigInteger, BigInteger, BigInteger, String, BigInteger, Boolean>> room() {
        final Function function = new Function(FUNC_ROOM, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}));
        return new RemoteCall<Tuple8<String, BigInteger, BigInteger, BigInteger, BigInteger, String, BigInteger, Boolean>>(
                new Callable<Tuple8<String, BigInteger, BigInteger, BigInteger, BigInteger, String, BigInteger, Boolean>>() {
                    @Override
                    public Tuple8<String, BigInteger, BigInteger, BigInteger, BigInteger, String, BigInteger, Boolean> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple8<String, BigInteger, BigInteger, BigInteger, BigInteger, String, BigInteger, Boolean>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (String) results.get(5).getValue(), 
                                (BigInteger) results.get(6).getValue(), 
                                (Boolean) results.get(7).getValue());
                    }
                });
    }

    public RemoteCall<BigInteger> timestampContractStart() {
        final Function function = new Function(FUNC_TIMESTAMPCONTRACTSTART, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> timestampCreated() {
        final Function function = new Function(FUNC_TIMESTAMPCREATED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Tuple8<String, BigInteger, String, String, BigInteger, String, String, Boolean>> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Bool>() {}));
        return new RemoteCall<Tuple8<String, BigInteger, String, String, BigInteger, String, String, Boolean>>(
                new Callable<Tuple8<String, BigInteger, String, String, BigInteger, String, String, Boolean>>() {
                    @Override
                    public Tuple8<String, BigInteger, String, String, BigInteger, String, String, Boolean> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple8<String, BigInteger, String, String, BigInteger, String, String, Boolean>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (String) results.get(5).getValue(), 
                                (String) results.get(6).getValue(), 
                                (Boolean) results.get(7).getValue());
                    }
                });
    }

    public RemoteCall<BigInteger> timestampContractEnd() {
        final Function function = new Function(FUNC_TIMESTAMPCONTRACTEND, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> getAddress() {
        final Function function = new Function(FUNC_GETADDRESS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> setAccount(Boolean _isOwner, String _name, BigInteger _timestampDateOfBirth, String _placeOfPermanent, String _idCard, BigInteger _timestampCardDated, String _phoneNumber, String _placeOfIssueOfIdentityCard) {
        final Function function = new Function(
                FUNC_SETACCOUNT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(_isOwner), 
                new org.web3j.abi.datatypes.Utf8String(_name), 
                new org.web3j.abi.datatypes.generated.Uint256(_timestampDateOfBirth), 
                new org.web3j.abi.datatypes.Utf8String(_placeOfPermanent), 
                new org.web3j.abi.datatypes.Utf8String(_idCard), 
                new org.web3j.abi.datatypes.generated.Uint256(_timestampCardDated), 
                new org.web3j.abi.datatypes.Utf8String(_phoneNumber), 
                new org.web3j.abi.datatypes.Utf8String(_placeOfIssueOfIdentityCard)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> setRoom(String _address, BigInteger _price, BigInteger _electricityPrice, BigInteger _waterPrice, BigInteger _square, String _term, BigInteger _prepaid) {
        final Function function = new Function(
                FUNC_SETROOM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_address), 
                new org.web3j.abi.datatypes.generated.Uint256(_price), 
                new org.web3j.abi.datatypes.generated.Uint256(_electricityPrice), 
                new org.web3j.abi.datatypes.generated.Uint256(_waterPrice), 
                new org.web3j.abi.datatypes.generated.Uint256(_square), 
                new org.web3j.abi.datatypes.Utf8String(_term), 
                new org.web3j.abi.datatypes.generated.Uint256(_prepaid)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> addPaymentRequest(BigInteger _timestampStart, BigInteger _timestampEnd, BigInteger _timestampPaid, BigInteger _roomBill, BigInteger _electricityBill, BigInteger _waterBill) {
        final Function function = new Function(
                FUNC_ADDPAYMENTREQUEST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_timestampStart), 
                new org.web3j.abi.datatypes.generated.Uint256(_timestampEnd), 
                new org.web3j.abi.datatypes.generated.Uint256(_timestampPaid), 
                new org.web3j.abi.datatypes.generated.Uint256(_roomBill), 
                new org.web3j.abi.datatypes.generated.Uint256(_electricityBill), 
                new org.web3j.abi.datatypes.generated.Uint256(_waterBill)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> terminateContract() {
        final Function function = new Function(
                FUNC_TERMINATECONTRACT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static RentHouseContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new RentHouseContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static RentHouseContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new RentHouseContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static RentHouseContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new RentHouseContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static RentHouseContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new RentHouseContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<RentHouseContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, BigInteger _tsContractStart, BigInteger _tsContractEnd) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_tsContractStart), 
                new org.web3j.abi.datatypes.generated.Uint256(_tsContractEnd)));
        return deployRemoteCall(RentHouseContract.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<RentHouseContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, BigInteger _tsContractStart, BigInteger _tsContractEnd) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_tsContractStart), 
                new org.web3j.abi.datatypes.generated.Uint256(_tsContractEnd)));
        return deployRemoteCall(RentHouseContract.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<RentHouseContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger _tsContractStart, BigInteger _tsContractEnd) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_tsContractStart), 
                new org.web3j.abi.datatypes.generated.Uint256(_tsContractEnd)));
        return deployRemoteCall(RentHouseContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<RentHouseContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger _tsContractStart, BigInteger _tsContractEnd) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_tsContractStart), 
                new org.web3j.abi.datatypes.generated.Uint256(_tsContractEnd)));
        return deployRemoteCall(RentHouseContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }
}
