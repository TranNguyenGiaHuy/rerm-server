package com.huytran.rerm.ethcontract;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
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
    private static final String BINARY = "0x608060405234801561001057600080fd5b50604051604080611ff78339810180604052604081101561003057600080fd5b81019080805190602001909291908051906020019092919050505081601a8190555080601b819055505050611f8d8061006a6000396000f3fe608060405234801561001057600080fd5b50600436106100ea5760003560e01c806338cc48311161008c57806374ef3d601161006657806374ef3d6014610a3a5780638da5cb5b14610a58578063ddfb8a4b14610ca4578063f15ab20714610cc2576100ea565b806338cc48311461084e57806343df2c7514610898578063476f163114610a1c576100ea565b80632e543058116100c85780632e543058146104995780632e88ab0b146104db5780632fd949ca146107275780633433523e14610731576100ea565b806302fb8fe9146100ef578063139a334d1461013f57806322f3e2d414610477575b600080fd5b61011b6004803603602081101561010557600080fd5b8101908080359060200190929190505050610d04565b60405180848152602001838152602001828152602001935050505060405180910390f35b610475600480360361010081101561015657600080fd5b810190808035151590602001909291908035906020019064010000000081111561017f57600080fd5b82018360208201111561019157600080fd5b803590602001918460018302840111640100000000831117156101b357600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290803590602001909291908035906020019064010000000081111561022057600080fd5b82018360208201111561023257600080fd5b8035906020019184600183028401116401000000008311171561025457600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290803590602001906401000000008111156102b757600080fd5b8201836020820111156102c957600080fd5b803590602001918460018302840111640100000000831117156102eb57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290803590602001909291908035906020019064010000000081111561035857600080fd5b82018360208201111561036a57600080fd5b8035906020019184600183028401116401000000008311171561038c57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290803590602001906401000000008111156103ef57600080fd5b82018360208201111561040157600080fd5b8035906020019184600183028401116401000000008311171561042357600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290505050610d3b565b005b61047f611015565b604051808215151515815260200191505060405180910390f35b6104d9600480360360608110156104af57600080fd5b81019080803590602001909291908035906020019092919080359060200190929190505050611028565b005b6104e3611229565b6040518080602001898152602001806020018060200188815260200180602001806020018715151515815260200186810386528e818151815260200191508051906020019080838360005b8381101561054957808201518184015260208101905061052e565b50505050905090810190601f1680156105765780820380516001836020036101000a031916815260200191505b5086810385528c818151815260200191508051906020019080838360005b838110156105af578082015181840152602081019050610594565b50505050905090810190601f1680156105dc5780820380516001836020036101000a031916815260200191505b5086810384528b818151815260200191508051906020019080838360005b838110156106155780820151818401526020810190506105fa565b50505050905090810190601f1680156106425780820380516001836020036101000a031916815260200191505b50868103835289818151815260200191508051906020019080838360005b8381101561067b578082015181840152602081019050610660565b50505050905090810190601f1680156106a85780820380516001836020036101000a031916815260200191505b50868103825288818151815260200191508051906020019080838360005b838110156106e15780820151818401526020810190506106c6565b50505050905090810190601f16801561070e5780820380516001836020036101000a031916815260200191505b509d505050505050505050505050505060405180910390f35b61072f611564565b005b6107396116eb565b6040518080602001898152602001888152602001878152602001868152602001806020018581526020018415151515815260200183810383528b818151815260200191508051906020019080838360005b838110156107a557808201518184015260208101905061078a565b50505050905090810190601f1680156107d25780820380516001836020036101000a031916815260200191505b50838103825286818151815260200191508051906020019080838360005b8381101561080b5780820151818401526020810190506107f0565b50505050905090810190601f1680156108385780820380516001836020036101000a031916815260200191505b509a505050505050505050505060405180910390f35b61085661185e565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b610a1a600480360360e08110156108ae57600080fd5b81019080803590602001906401000000008111156108cb57600080fd5b8201836020820111156108dd57600080fd5b803590602001918460018302840111640100000000831117156108ff57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290803590602001909291908035906020019092919080359060200190929190803590602001909291908035906020019064010000000081111561098a57600080fd5b82018360208201111561099c57600080fd5b803590602001918460018302840111640100000000831117156109be57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f82011690508083019250505050505050919291929080359060200190929190505050611866565b005b610a246119c3565b6040518082815260200191505060405180910390f35b610a426119c9565b6040518082815260200191505060405180910390f35b610a606119cf565b6040518080602001898152602001806020018060200188815260200180602001806020018715151515815260200186810386528e818151815260200191508051906020019080838360005b83811015610ac6578082015181840152602081019050610aab565b50505050905090810190601f168015610af35780820380516001836020036101000a031916815260200191505b5086810385528c818151815260200191508051906020019080838360005b83811015610b2c578082015181840152602081019050610b11565b50505050905090810190601f168015610b595780820380516001836020036101000a031916815260200191505b5086810384528b818151815260200191508051906020019080838360005b83811015610b92578082015181840152602081019050610b77565b50505050905090810190601f168015610bbf5780820380516001836020036101000a031916815260200191505b50868103835289818151815260200191508051906020019080838360005b83811015610bf8578082015181840152602081019050610bdd565b50505050905090810190601f168015610c255780820380516001836020036101000a031916815260200191505b50868103825288818151815260200191508051906020019080838360005b83811015610c5e578082015181840152602081019050610c43565b50505050905090810190601f168015610c8b5780820380516001836020036101000a031916815260200191505b509d505050505050505050505050505060405180910390f35b610cac611d0a565b6040518082815260200191505060405180910390f35b610d0260048036036060811015610cd857600080fd5b81019080803590602001909291908035906020019092919080359060200190929190505050611d10565b005b601c8181548110610d1157fe5b90600052602060002090600302016000915090508060000154908060010154908060020154905083565b87808015610d595750600260070160009054906101000a900460ff16155b80610d7e575080158015610d7d5750600a60070160009054906101000a900460ff16155b5b610df0576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260178152602001807f4163636f756e742048617320416c72656164792053657400000000000000000081525060200191505060405180910390fd5b8815610f02576040518061010001604052808981526020018881526020018781526020018681526020018581526020018481526020018381526020016001151581525060026000820151816000019080519060200190610e51929190611ebc565b50602082015181600101556040820151816002019080519060200190610e78929190611ebc565b506060820151816003019080519060200190610e95929190611ebc565b506080820151816004015560a0820151816005019080519060200190610ebc929190611ebc565b5060c0820151816006019080519060200190610ed9929190611ebc565b5060e08201518160070160006101000a81548160ff02191690831515021790555090505061100a565b60405180610100016040528089815260200188815260200187815260200186815260200185815260200184815260200183815260200160011515815250600a6000820151816000019080519060200190610f5d929190611ebc565b50602082015181600101556040820151816002019080519060200190610f84929190611ebc565b506060820151816003019080519060200190610fa1929190611ebc565b506080820151816004015560a0820151816005019080519060200190610fc8929190611ebc565b5060c0820151816006019080519060200190610fe5929190611ebc565b5060e08201518160070160006101000a81548160ff0219169083151502179055509050505b505050505050505050565b600160009054906101000a900460ff1681565b600260070160009054906101000a900460ff1615806110575750600a60070160009054906101000a900460ff16155b806110725750601260070160009054906101000a900460ff16155b6110e4576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252600d8152602001807f496e666f204e6f742046756c6c0000000000000000000000000000000000000081525060200191505060405180910390fd5b600160009054906101000a900460ff16611166576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260138152602001807f436f6e7472616374204e6f74204163746976650000000000000000000000000081525060200191505060405180910390fd5b600060126003015482026012600201548402601260010154010190507f178456286b4e08a85b1aef76cf9672aaa4f04933517a7a5f580f2bd433d9b43a816040518082815260200191505060405180910390a1601c604051806060016040528086815260200160008152602001838152509080600181540180825580915050906001820390600052602060002090600302016000909192909190915060008201518160000155602082015181600101556040820151816002015550505050505050565b600a806000018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156112c35780601f10611298576101008083540402835291602001916112c3565b820191906000526020600020905b8154815290600101906020018083116112a657829003601f168201915b505050505090806001015490806002018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156113675780601f1061133c57610100808354040283529160200191611367565b820191906000526020600020905b81548152906001019060200180831161134a57829003601f168201915b505050505090806003018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156114055780601f106113da57610100808354040283529160200191611405565b820191906000526020600020905b8154815290600101906020018083116113e857829003601f168201915b505050505090806004015490806005018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156114a95780601f1061147e576101008083540402835291602001916114a9565b820191906000526020600020905b81548152906001019060200180831161148c57829003601f168201915b505050505090806006018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156115475780601f1061151c57610100808354040283529160200191611547565b820191906000526020600020905b81548152906001019060200180831161152a57829003601f168201915b5050505050908060070160009054906101000a900460ff16905088565b600260070160009054906101000a900460ff1615806115935750600a60070160009054906101000a900460ff16155b806115ae5750601260070160009054906101000a900460ff16155b611620576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252600d8152602001807f496e666f204e6f742046756c6c0000000000000000000000000000000000000081525060200191505060405180910390fd5b600160009054906101000a900460ff166116a2576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260138152602001807f436f6e7472616374204e6f74204163746976650000000000000000000000000081525060200191505060405180910390fd5b6000600160006101000a81548160ff0219169083151502179055507f3f8de663ba713a2f7f965a7611ca103289d9c9b45efd29c2f1d52f15dbd775e060405160405180910390a1565b6012806000018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156117855780601f1061175a57610100808354040283529160200191611785565b820191906000526020600020905b81548152906001019060200180831161176857829003601f168201915b505050505090806001015490806002015490806003015490806004015490806005018054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561183b5780601f106118105761010080835404028352916020019161183b565b820191906000526020600020905b81548152906001019060200180831161181e57829003601f168201915b5050505050908060060154908060070160009054906101000a900460ff16905088565b600033905090565b601260070160009054906101000a900460ff16156118ec576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260148152602001807f526f6f6d2048617320416c72656164792053657400000000000000000000000081525060200191505060405180910390fd5b6040518061010001604052808881526020018781526020018681526020018581526020018481526020018381526020018281526020016001151581525060126000820151816000019080519060200190611947929190611ebc565b506020820151816001015560408201518160020155606082015181600301556080820151816004015560a082015181600501908051906020019061198c929190611ebc565b5060c0820151816006015560e08201518160070160006101000a81548160ff02191690831515021790555090505050505050505050565b601a5481565b60005481565b6002806000018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015611a695780601f10611a3e57610100808354040283529160200191611a69565b820191906000526020600020905b815481529060010190602001808311611a4c57829003601f168201915b505050505090806001015490806002018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015611b0d5780601f10611ae257610100808354040283529160200191611b0d565b820191906000526020600020905b815481529060010190602001808311611af057829003601f168201915b505050505090806003018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015611bab5780601f10611b8057610100808354040283529160200191611bab565b820191906000526020600020905b815481529060010190602001808311611b8e57829003601f168201915b505050505090806004015490806005018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015611c4f5780601f10611c2457610100808354040283529160200191611c4f565b820191906000526020600020905b815481529060010190602001808311611c3257829003601f168201915b505050505090806006018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015611ced5780601f10611cc257610100808354040283529160200191611ced565b820191906000526020600020905b815481529060010190602001808311611cd057829003601f168201915b5050505050908060070160009054906101000a900460ff16905088565b601b5481565b600260070160009054906101000a900460ff161580611d3f5750600a60070160009054906101000a900460ff16155b80611d5a5750601260070160009054906101000a900460ff16155b611dcc576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252600d8152602001807f496e666f204e6f742046756c6c0000000000000000000000000000000000000081525060200191505060405180910390fd5b600160009054906101000a900460ff16611e4e576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260138152602001807f436f6e7472616374204e6f74204163746976650000000000000000000000000081525060200191505060405180910390fd5b601c60405180606001604052808581526020018481526020018381525090806001815401808255809150509060018203906000526020600020906003020160009091929091909150600082015181600001556020820151816001015560408201518160020155505050505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10611efd57805160ff1916838001178555611f2b565b82800160010185558215611f2b579182015b82811115611f2a578251825591602001919060010190611f0f565b5b509050611f389190611f3c565b5090565b611f5e91905b80821115611f5a576000816000905550600101611f42565b5090565b9056fea165627a7a723058208cb029c06f2e4c295bf828c361ecafbc04a519c35abf5f173dc08910294935a70029";

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

    public static final String FUNC_CONFIRMPAYMENT = "confirmPayment";

    public static final String FUNC_TERMINATECONTRACT = "terminateContract";

    public static final Event CONTRACTTERMINATE_EVENT = new Event("contractTerminate", 
            Arrays.<TypeReference<?>>asList());
    ;

    public static final Event PAYMENTREQUEST_EVENT = new Event("paymentRequest", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

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

    public RemoteCall<Tuple3<BigInteger, BigInteger, BigInteger>> rentPaids(BigInteger param0) {
        final Function function = new Function(FUNC_RENTPAIDS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple3<BigInteger, BigInteger, BigInteger>>(
                new Callable<Tuple3<BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple3<BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<BigInteger, BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue());
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

    public List<ContractTerminateEventResponse> getContractTerminateEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CONTRACTTERMINATE_EVENT, transactionReceipt);
        ArrayList<ContractTerminateEventResponse> responses = new ArrayList<ContractTerminateEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ContractTerminateEventResponse typedResponse = new ContractTerminateEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ContractTerminateEventResponse> contractTerminateEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, ContractTerminateEventResponse>() {
            @Override
            public ContractTerminateEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CONTRACTTERMINATE_EVENT, log);
                ContractTerminateEventResponse typedResponse = new ContractTerminateEventResponse();
                typedResponse.log = log;
                return typedResponse;
            }
        });
    }

    public Flowable<ContractTerminateEventResponse> contractTerminateEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CONTRACTTERMINATE_EVENT));
        return contractTerminateEventFlowable(filter);
    }

    public List<PaymentRequestEventResponse> getPaymentRequestEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PAYMENTREQUEST_EVENT, transactionReceipt);
        ArrayList<PaymentRequestEventResponse> responses = new ArrayList<PaymentRequestEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PaymentRequestEventResponse typedResponse = new PaymentRequestEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.money = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<PaymentRequestEventResponse> paymentRequestEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, PaymentRequestEventResponse>() {
            @Override
            public PaymentRequestEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PAYMENTREQUEST_EVENT, log);
                PaymentRequestEventResponse typedResponse = new PaymentRequestEventResponse();
                typedResponse.log = log;
                typedResponse.money = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<PaymentRequestEventResponse> paymentRequestEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PAYMENTREQUEST_EVENT));
        return paymentRequestEventFlowable(filter);
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

    public RemoteCall<TransactionReceipt> addPaymentRequest(BigInteger _timestampCreated, BigInteger _electricBill, BigInteger _waterBill) {
        final Function function = new Function(
                FUNC_ADDPAYMENTREQUEST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_timestampCreated), 
                new org.web3j.abi.datatypes.generated.Uint256(_electricBill), 
                new org.web3j.abi.datatypes.generated.Uint256(_waterBill)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> confirmPayment(BigInteger _timestampCreated, BigInteger _timestampPaid, BigInteger _value) {
        final Function function = new Function(
                FUNC_CONFIRMPAYMENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_timestampCreated), 
                new org.web3j.abi.datatypes.generated.Uint256(_timestampPaid), 
                new org.web3j.abi.datatypes.generated.Uint256(_value)), 
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

    public static class ContractTerminateEventResponse {
        public Log log;
    }

    public static class PaymentRequestEventResponse {
        public Log log;

        public BigInteger money;
    }
}
