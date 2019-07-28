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
    private static final String BINARY = "0x608060405260018060006101000a81548160ff02191690831515021790555034801561002a57600080fd5b50604051604080611dff8339810180604052604081101561004a57600080fd5b81019080805190602001909291908051906020019092919050505081601a8190555080601b819055505050611d7b806100846000396000f3fe608060405234801561001057600080fd5b50600436106100cf5760003560e01c80633433523e1161008c578063476f163111610066578063476f163114610a3457806374ef3d6014610a525780638da5cb5b14610a70578063ddfb8a4b14610cbc576100cf565b80633433523e1461074957806338cc48311461086657806343df2c75146108b0576100cf565b806302fb8fe9146100d4578063139a334d1461013957806322f3e2d414610471578063233a0d75146104935780632e88ab0b146104f35780632fd949ca1461073f575b600080fd5b610100600480360360208110156100ea57600080fd5b8101908080359060200190929190505050610cda565b60405180878152602001868152602001858152602001848152602001838152602001828152602001965050505050505060405180910390f35b61046f600480360361010081101561015057600080fd5b810190808035151590602001909291908035906020019064010000000081111561017957600080fd5b82018360208201111561018b57600080fd5b803590602001918460018302840111640100000000831117156101ad57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290803590602001909291908035906020019064010000000081111561021a57600080fd5b82018360208201111561022c57600080fd5b8035906020019184600183028401116401000000008311171561024e57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290803590602001906401000000008111156102b157600080fd5b8201836020820111156102c357600080fd5b803590602001918460018302840111640100000000831117156102e557600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290803590602001909291908035906020019064010000000081111561035257600080fd5b82018360208201111561036457600080fd5b8035906020019184600183028401116401000000008311171561038657600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290803590602001906401000000008111156103e957600080fd5b8201836020820111156103fb57600080fd5b8035906020019184600183028401116401000000008311171561041d57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290505050610d23565b005b610479610ffd565b604051808215151515815260200191505060405180910390f35b6104f1600480360360c08110156104a957600080fd5b81019080803590602001909291908035906020019092919080359060200190929190803590602001909291908035906020019092919080359060200190929190505050611010565b005b6104fb6111ef565b6040518080602001898152602001806020018060200188815260200180602001806020018715151515815260200186810386528e818151815260200191508051906020019080838360005b83811015610561578082015181840152602081019050610546565b50505050905090810190601f16801561058e5780820380516001836020036101000a031916815260200191505b5086810385528c818151815260200191508051906020019080838360005b838110156105c75780820151818401526020810190506105ac565b50505050905090810190601f1680156105f45780820380516001836020036101000a031916815260200191505b5086810384528b818151815260200191508051906020019080838360005b8381101561062d578082015181840152602081019050610612565b50505050905090810190601f16801561065a5780820380516001836020036101000a031916815260200191505b50868103835289818151815260200191508051906020019080838360005b83811015610693578082015181840152602081019050610678565b50505050905090810190601f1680156106c05780820380516001836020036101000a031916815260200191505b50868103825288818151815260200191508051906020019080838360005b838110156106f95780820151818401526020810190506106de565b50505050905090810190601f1680156107265780820380516001836020036101000a031916815260200191505b509d505050505050505050505050505060405180910390f35b61074761152a565b005b610751611685565b6040518080602001898152602001888152602001878152602001868152602001806020018581526020018415151515815260200183810383528b818151815260200191508051906020019080838360005b838110156107bd5780820151818401526020810190506107a2565b50505050905090810190601f1680156107ea5780820380516001836020036101000a031916815260200191505b50838103825286818151815260200191508051906020019080838360005b83811015610823578082015181840152602081019050610808565b50505050905090810190601f1680156108505780820380516001836020036101000a031916815260200191505b509a505050505050505050505060405180910390f35b61086e6117f8565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b610a32600480360360e08110156108c657600080fd5b81019080803590602001906401000000008111156108e357600080fd5b8201836020820111156108f557600080fd5b8035906020019184600183028401116401000000008311171561091757600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f82011690508083019250505050505050919291929080359060200190929190803590602001909291908035906020019092919080359060200190929190803590602001906401000000008111156109a257600080fd5b8201836020820111156109b457600080fd5b803590602001918460018302840111640100000000831117156109d657600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f82011690508083019250505050505050919291929080359060200190929190505050611800565b005b610a3c61195d565b6040518082815260200191505060405180910390f35b610a5a611963565b6040518082815260200191505060405180910390f35b610a78611969565b6040518080602001898152602001806020018060200188815260200180602001806020018715151515815260200186810386528e818151815260200191508051906020019080838360005b83811015610ade578082015181840152602081019050610ac3565b50505050905090810190601f168015610b0b5780820380516001836020036101000a031916815260200191505b5086810385528c818151815260200191508051906020019080838360005b83811015610b44578082015181840152602081019050610b29565b50505050905090810190601f168015610b715780820380516001836020036101000a031916815260200191505b5086810384528b818151815260200191508051906020019080838360005b83811015610baa578082015181840152602081019050610b8f565b50505050905090810190601f168015610bd75780820380516001836020036101000a031916815260200191505b50868103835289818151815260200191508051906020019080838360005b83811015610c10578082015181840152602081019050610bf5565b50505050905090810190601f168015610c3d5780820380516001836020036101000a031916815260200191505b50868103825288818151815260200191508051906020019080838360005b83811015610c76578082015181840152602081019050610c5b565b50505050905090810190601f168015610ca35780820380516001836020036101000a031916815260200191505b509d505050505050505050505050505060405180910390f35b610cc4611ca4565b6040518082815260200191505060405180910390f35b601c8181548110610ce757fe5b90600052602060002090600602016000915090508060000154908060010154908060020154908060030154908060040154908060050154905086565b87808015610d415750600260070160009054906101000a900460ff16155b80610d66575080158015610d655750600a60070160009054906101000a900460ff16155b5b610dd8576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260178152602001807f4163636f756e742048617320416c72656164792053657400000000000000000081525060200191505060405180910390fd5b8815610eea576040518061010001604052808981526020018881526020018781526020018681526020018581526020018481526020018381526020016001151581525060026000820151816000019080519060200190610e39929190611caa565b50602082015181600101556040820151816002019080519060200190610e60929190611caa565b506060820151816003019080519060200190610e7d929190611caa565b506080820151816004015560a0820151816005019080519060200190610ea4929190611caa565b5060c0820151816006019080519060200190610ec1929190611caa565b5060e08201518160070160006101000a81548160ff021916908315150217905550905050610ff2565b60405180610100016040528089815260200188815260200187815260200186815260200185815260200184815260200183815260200160011515815250600a6000820151816000019080519060200190610f45929190611caa565b50602082015181600101556040820151816002019080519060200190610f6c929190611caa565b506060820151816003019080519060200190610f89929190611caa565b506080820151816004015560a0820151816005019080519060200190610fb0929190611caa565b5060c0820151816006019080519060200190610fcd929190611caa565b5060e08201518160070160006101000a81548160ff0219169083151502179055509050505b505050505050505050565b600160009054906101000a900460ff1681565b600260070160009054906101000a900460ff16158061103f5750600a60070160009054906101000a900460ff16155b8061105a5750601260070160009054906101000a900460ff16155b6110cc576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252600d8152602001807f496e666f204e6f742046756c6c0000000000000000000000000000000000000081525060200191505060405180910390fd5b600160009054906101000a900460ff1661114e576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260138152602001807f436f6e7472616374204e6f74204163746976650000000000000000000000000081525060200191505060405180910390fd5b601c6040518060c001604052808881526020018781526020018681526020018581526020018481526020018381525090806001815401808255809150509060018203906000526020600020906006020160009091929091909150600082015181600001556020820151816001015560408201518160020155606082015181600301556080820151816004015560a08201518160050155505050505050505050565b600a806000018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156112895780601f1061125e57610100808354040283529160200191611289565b820191906000526020600020905b81548152906001019060200180831161126c57829003601f168201915b505050505090806001015490806002018054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561132d5780601f106113025761010080835404028352916020019161132d565b820191906000526020600020905b81548152906001019060200180831161131057829003601f168201915b505050505090806003018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156113cb5780601f106113a0576101008083540402835291602001916113cb565b820191906000526020600020905b8154815290600101906020018083116113ae57829003601f168201915b505050505090806004015490806005018054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561146f5780601f106114445761010080835404028352916020019161146f565b820191906000526020600020905b81548152906001019060200180831161145257829003601f168201915b505050505090806006018054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561150d5780601f106114e25761010080835404028352916020019161150d565b820191906000526020600020905b8154815290600101906020018083116114f057829003601f168201915b5050505050908060070160009054906101000a900460ff16905088565b600260070160009054906101000a900460ff1615806115595750600a60070160009054906101000a900460ff16155b806115745750601260070160009054906101000a900460ff16155b6115e6576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252600d8152602001807f496e666f204e6f742046756c6c0000000000000000000000000000000000000081525060200191505060405180910390fd5b600160009054906101000a900460ff16611668576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260138152602001807f436f6e7472616374204e6f74204163746976650000000000000000000000000081525060200191505060405180910390fd5b6000600160006101000a81548160ff021916908315150217905550565b6012806000018054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561171f5780601f106116f45761010080835404028352916020019161171f565b820191906000526020600020905b81548152906001019060200180831161170257829003601f168201915b505050505090806001015490806002015490806003015490806004015490806005018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156117d55780601f106117aa576101008083540402835291602001916117d5565b820191906000526020600020905b8154815290600101906020018083116117b857829003601f168201915b5050505050908060060154908060070160009054906101000a900460ff16905088565b600033905090565b601260070160009054906101000a900460ff1615611886576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260148152602001807f526f6f6d2048617320416c72656164792053657400000000000000000000000081525060200191505060405180910390fd5b60405180610100016040528088815260200187815260200186815260200185815260200184815260200183815260200182815260200160011515815250601260008201518160000190805190602001906118e1929190611caa565b506020820151816001015560408201518160020155606082015181600301556080820151816004015560a0820151816005019080519060200190611926929190611caa565b5060c0820151816006015560e08201518160070160006101000a81548160ff02191690831515021790555090505050505050505050565b601a5481565b60005481565b6002806000018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015611a035780601f106119d857610100808354040283529160200191611a03565b820191906000526020600020905b8154815290600101906020018083116119e657829003601f168201915b505050505090806001015490806002018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015611aa75780601f10611a7c57610100808354040283529160200191611aa7565b820191906000526020600020905b815481529060010190602001808311611a8a57829003601f168201915b505050505090806003018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015611b455780601f10611b1a57610100808354040283529160200191611b45565b820191906000526020600020905b815481529060010190602001808311611b2857829003601f168201915b505050505090806004015490806005018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015611be95780601f10611bbe57610100808354040283529160200191611be9565b820191906000526020600020905b815481529060010190602001808311611bcc57829003601f168201915b505050505090806006018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015611c875780601f10611c5c57610100808354040283529160200191611c87565b820191906000526020600020905b815481529060010190602001808311611c6a57829003601f168201915b5050505050908060070160009054906101000a900460ff16905088565b601b5481565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10611ceb57805160ff1916838001178555611d19565b82800160010185558215611d19579182015b82811115611d18578251825591602001919060010190611cfd565b5b509050611d269190611d2a565b5090565b611d4c91905b80821115611d48576000816000905550600101611d30565b5090565b9056fea165627a7a72305820eacd7c9d58039f768b4068871392720ff70978e9424f5852cba4ac7e0ef2f6ea0029";

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

    public static final String FUNC_ADDPAYMENT = "addPayment";

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

    public RemoteCall<TransactionReceipt> addPayment(BigInteger _timestampStart, BigInteger _timestampEnd, BigInteger _timestampPaid, BigInteger _roomBill, BigInteger _electricityBill, BigInteger _waterBill) {
        final Function function = new Function(
                FUNC_ADDPAYMENT, 
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
