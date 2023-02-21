package it.unipi.trustgraphmanager.contracts;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
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
 * <p>Generated with web3j version 1.4.2.
 */
@SuppressWarnings("rawtypes")
public class ContextsGraph extends Contract {
    public static final String BINARY = "60803461008d57601f6113e738819003918201601f19168301916001600160401b0383118484101761009257808492604094855283398101031261008d57610052602061004b836100a8565b92016100a8565b600380546001600160a01b039384166001600160a01b0319918216179091556004805492909316911617905560405161132a90816100bd8239f35b600080fd5b634e487b7160e01b600052604160045260246000fd5b51906001600160a01b038216820361008d5756fe6080806040526004908136101561001557600080fd5b60003560e01c9081632d0612dd14610c965750806330f2dcab146108fa578063d20ec4561461085a578063e891345f146105f4578063eb69280e14610574578063f697affb1461048b5763fe02d99b1461006e57600080fd5b3461042e5760208060031936011261042e5781359167ffffffffffffffff80841161042e573660238501121561042e578382013590811161042e576024808501948136918460051b01011161042e57600094600019830183811187815b610266578289101561045c575060406100e5898786611290565b013560028082101561042e578115908180610433575b1561027a5750505050600196878101978882116102665787600099848114159283610173575b505050905b811561013d57610135906111d5565b9790816100cb565b509450505050507f6d15359ce61426296e4a1677a4b60d2cbc18c5aac27d317c4344dd89ba09e8c892505b6040519015158152a1005b9091925061018b610185858a89611290565b80611136565b60408051808701968752959286926101a592840191611091565b03936101b9601f19958681018352826110b2565b5190206000528952886101fe6101d86101856040600020948b8a611290565b60408051858101958652939284926101f292840191611091565b038581018352826110b2565b51902060005288528760406000209161024d61022761021e868b8a611290565b84810190611136565b604080518681019687529492859261024192840191611091565b039081018352826110b2565b519020600052875260ff60406000205416873880610121565b84601188634e487b7160e01b600052526000fd5b92999281610402575b501561034e575085546001600160a01b03166102a3610185838887611290565b91906001840180851161033a57916102e793918a6102c66101858e968d8c611290565b60405160016215745760e01b031981529788968795869592949286016112ca565b03915afa90811561032e57600091610301575b5090610126565b6103219150883d8a11610327575b61031981836110b2565b8101906112b2565b386102fa565b503d61030f565b6040513d6000823e3d90fd5b8760118b634e487b7160e01b600052526000fd5b60019081036103f9578115159081610367575090610126565b6003549091506001600160a01b0316610384610185848988611290565b92840180851161033a57916103c293918a6103a46101858e968d8c611290565b60405163a787e6ff60e01b81529788968795869592949286016112ca565b03915afa90811561032e576000916103dc575b50386102fa565b6103f39150883d8a116103275761031981836110b2565b386103d5565b50600090610126565b6001915083820180851161033a5761041d6040918a89611290565b01359081101561042e571438610283565b600080fd5b5060018b01808c1161033a5761044c6040918a89611290565b01358181101561042e57156100fb565b955050505050507f6d15359ce61426296e4a1677a4b60d2cbc18c5aac27d317c4344dd89ba09e8c89250610168565b503461042e576020908160031936011261042e5780359167ffffffffffffffff831161042e576104e261056f927fc4e103aabdf7eb9dba199fc579dc1054898ea18de6e1c7e6eb504146091503c994369101610ffa565b9290604051838101848152816104fc604082018886611091565b0391610510601f19938481018352826110b2565b5190206000526000845261052b60ff604060002054166110ea565b6040516105478582019286845282610241604082018a88611091565b51902060005260008352604060002060ff198154169055604051938385948552840191611091565b0390a1005b503461042e57602036600319011261042e5780359067ffffffffffffffff821161042e576105ab6105c6916105d493369101610ffa565b92906040519283916020830195602087526040840191611091565b03601f1981018352826110b2565b5190206000526000602052602060ff604060002054166040519015158152f35b503461042e57604036600319011261042e5767ffffffffffffffff90803582811161042e576106269036908301610ffa565b91906024803585811161042e576106409036908501610ffa565b9590604051956106aa6106b6602096878a018881528a610664604082018785611091565b039a610678601f199c8d81018352826110b2565b5190206000526000885261069360ff604060002054166110ea565b604051928391898301958a87526040840191611091565b038981018352826110b2565b5190206000526002968785526106aa6106e5604060002093604051928391898301958a87526040840191611091565b5190206000528352604060002095865491808311610846579395969460405194610714888560051b01876110b2565b83865287860191829760005288600020916000935b8685106107da578b8b8b8b604051928084019181855251809252604084019460408360051b86010193956000915b8483106107645786860387f35b9091929394603f19878203018352848851918383516040835280519081604085015260005b8281106107c25750509160019491601f859485606095600087848801015201518685015201160101990193019301919694939290610757565b818101860151858201606001528a9588945001610789565b60409b9a98999b516040810181811084821117610832576001928892828d93604052610815603f198361080d848d6111fa565b0301826110b2565b815284880154838201528152019401940193929a9897999a610729565b89604186634e487b7160e01b600052526000fd5b83604187634e487b7160e01b600052526000fd5b503461042e576020908160031936011261042e5780359167ffffffffffffffff831161042e576108b161056f927f143d4b7ffeeadf836e16fffd8813b8a4a6cd5b1e3375d24f946eaeb68af03a8394369101610ffa565b9290604051838101908482526108cf816105c6604082018987611091565b519020600052600083526040600020600160ff19825416179055604051938385948552840191611091565b503461042e5761090936611028565b91604051602081019060208252610928816105c660408201898b611091565b519020600052600060205261094460ff604060002054166110ea565b6109546105c66105ab8580611136565b519020600052600060205261097060ff604060002054166110ea565b60405160208101906020825261098e816105c660408201898b611091565b5190206109a16105c66105ab8680611136565b5190206040516020810190602082526109c2816105c6604082018989611091565b5190209180600052600160205260406000208260005260205260406000208360005260205260ff6040600020541615610a67575b7fd3970b757da52ac4d2a2af85403b32510fb20e3fa9e0df4052533ae5e6be1e48888861056f8989896020610a52610a2e8580611136565b610a456040519a8b9a60808c5260808c0191611091565b91898303858b0152611091565b93013560408601528483036060860152611091565b806000526002602052604060002083600052602052604060002080549068010000000000000000821015610c815790610aa591600182018155611169565b610c6d57610ab38780611136565b909a67ffffffffffffffff8211610c585750610acf825461119b565b601f8111610c14575b506000601f8211600114610b8157908061056f99989796959493927fd3970b757da52ac4d2a2af85403b32510fb20e3fa9e0df4052533ae5e6be1e489d600092610b76575b50508160011b916000199060031b1c19161781555b6001602088013591015560005260016020526040600020906000526020526040600020906000526020526040600020600160ff1982541617905590919238806109f6565b013590503880610b1d565b601f198216908360005260206000209160005b818110610bfc5750917fd3970b757da52ac4d2a2af85403b32510fb20e3fa9e0df4052533ae5e6be1e489d849261056f9c9b9a999897969560019510610be2575b505050811b018155610b32565b0135600019600384901b60f8161c19169055388080610bd5565b8e830135845560019093019260209283019201610b94565b826000526020600020601f830160051c810160208410610c51575b601f830160051c82018110610c45575050610ad8565b60008155600101610c2f565b5080610c2f565b604190634e487b7160e01b6000525260246000fd5b60008a634e487b7160e01b82525260246000fd5b60418b634e487b7160e01b6000525260246000fd5b823461042e57610ca536611028565b959160209687840188815284610cbf60408201888a611091565b0394610cd3601f19968781018352826110b2565b51902060005260008852610cee60ff604060002054166110ea565b87610d22610cfc8380611136565b6040805185810195865293928492610d1692840191611091565b038781018352826110b2565b51902060005260008852610d3d60ff604060002054166110ea565b610d478180611136565b6040989198518a8101908b8252610d67816106aa8c8c6040840191611091565b519020600052896001998a8252610d966040600020936106aa6040519384928684019687526040840191611091565b519020600052895260406000206040518a8101908b8252610dbf816106aa604082018a8a611091565b519020600052895260ff6040600020541615610fb6575060405188810190898252610df281610d16604082018a8c611091565b51902093604051898101908a8252610e1e81610e12604082018989611091565b038481018352826110b2565b519020916000895b610e2c57005b866000526002808c526040600020856000528c52604060002054821015610fb45787600052808c526040600020856000528c528b610d16610e8b610e74856040600020611169565b5060405192839185830195865260408301906111fa565b5190208c610ec2610e9c8680611136565b6040805185810195865293928492610eb692840191611091565b038881018352826110b2565b51902014610ee1575090610ed88a9493926111d5565b90919293610e26565b7f0a788b3386545d91eaf45a5559d51a5a5612a9ea8075df1221b67750b5b0f0f19b935090610fa194610f7a9261056f9a9b9c948a60005281875260406000208360005287528587610f37866040600020611169565b50920135910155896000528086526040600020826000528652610f5e836040600020611169565b5099600052855260406000209060005284526040600020611169565b50015494610f94604051998a996080808c528b0191611091565b91888303908901526111fa565b9260408601528483036060860152611091565b005b60405162461bcd60e51b8152908101899052601f60248201527f54686520636f6e74657874206564676520646f6573206e6f74206578697374006044820152606490fd5b9181601f8401121561042e5782359167ffffffffffffffff831161042e576020838186019501011161042e57565b6003199060608183011261042e5767ffffffffffffffff9160043583811161042e578261105791600401610ffa565b9490949360243581811161042e578461107291600401610ffa565b9490949360443592831161042e578260409203011261042e5760040190565b908060209392818452848401376000828201840152601f01601f1916010190565b90601f8019910116810190811067ffffffffffffffff8211176110d457604052565b634e487b7160e01b600052604160045260246000fd5b156110f157565b60405162461bcd60e51b815260206004820152601f60248201527f54686520636f6e74657874206e6f646520646f6573206e6f74206578697374006044820152606490fd5b903590601e198136030182121561042e570180359067ffffffffffffffff821161042e5760200191813603831361042e57565b80548210156111855760005260206000209060011b0190600090565b634e487b7160e01b600052603260045260246000fd5b90600182811c921680156111cb575b60208310146111b557565b634e487b7160e01b600052602260045260246000fd5b91607f16916111aa565b60001981146111e45760010190565b634e487b7160e01b600052601160045260246000fd5b906000929180549161120b8361119b565b91828252600193848116908160001461126d575060011461122d575b50505050565b90919394506000526020928360002092846000945b838610611259575050505001019038808080611227565b805485870183015294019385908201611242565b9294505050602093945060ff191683830152151560051b01019038808080611227565b91908110156111855760051b81013590605e198136030182121561042e570190565b9081602091031261042e5751801515810361042e5790565b92906112e3906112f19593604086526040860191611091565b926020818503910152611091565b9056fea2646970667358221220fb5cbe3caeba1d998322f4db7755cf69a55ad51dad6b2102dca9d678ad31c81a64736f6c63430008100033";

    public static final String FUNC_DEACTIVATECONTEXTNODE = "deactivateContextNode";

    public static final String FUNC_GETADJLIST = "getAdjList";

    public static final String FUNC_INSERTCONTEXTEDGE = "insertContextEdge";

    public static final String FUNC_INSERTCONTEXTNODE = "insertContextNode";

    public static final String FUNC_ISCONTEXTNODEACTIVE = "isContextNodeActive";

    public static final String FUNC_MODIFYCONTEXTEDGEWEIGHT = "modifyContextEdgeWeight";

    public static final String FUNC_VERIFYPATH = "verifyPath";

    public static final Event CONTEXTEDGEADDED_EVENT = new Event("ContextEdgeAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event CONTEXTEDGEMODIFIED_EVENT = new Event("ContextEdgeModified", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event CONTEXTNODEADDED_EVENT = new Event("ContextNodeAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    public static final Event CONTEXTNODEDEACTIVATED_EVENT = new Event("ContextNodeDeactivated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    public static final Event PATHVERIFIED_EVENT = new Event("PathVerified", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
    ;

    @Deprecated
    protected ContextsGraph(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ContextsGraph(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ContextsGraph(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ContextsGraph(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<ContextEdgeAddedEventResponse> getContextEdgeAddedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(CONTEXTEDGEADDED_EVENT, transactionReceipt);
        ArrayList<ContextEdgeAddedEventResponse> responses = new ArrayList<ContextEdgeAddedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ContextEdgeAddedEventResponse typedResponse = new ContextEdgeAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.level = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.context = (String) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ContextEdgeAddedEventResponse> contextEdgeAddedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ContextEdgeAddedEventResponse>() {
            @Override
            public ContextEdgeAddedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CONTEXTEDGEADDED_EVENT, log);
                ContextEdgeAddedEventResponse typedResponse = new ContextEdgeAddedEventResponse();
                typedResponse.log = log;
                typedResponse.from = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.to = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.level = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.context = (String) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ContextEdgeAddedEventResponse> contextEdgeAddedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CONTEXTEDGEADDED_EVENT));
        return contextEdgeAddedEventFlowable(filter);
    }

    public static List<ContextEdgeModifiedEventResponse> getContextEdgeModifiedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(CONTEXTEDGEMODIFIED_EVENT, transactionReceipt);
        ArrayList<ContextEdgeModifiedEventResponse> responses = new ArrayList<ContextEdgeModifiedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ContextEdgeModifiedEventResponse typedResponse = new ContextEdgeModifiedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.level = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.context = (String) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ContextEdgeModifiedEventResponse> contextEdgeModifiedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ContextEdgeModifiedEventResponse>() {
            @Override
            public ContextEdgeModifiedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CONTEXTEDGEMODIFIED_EVENT, log);
                ContextEdgeModifiedEventResponse typedResponse = new ContextEdgeModifiedEventResponse();
                typedResponse.log = log;
                typedResponse.from = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.to = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.level = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.context = (String) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ContextEdgeModifiedEventResponse> contextEdgeModifiedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CONTEXTEDGEMODIFIED_EVENT));
        return contextEdgeModifiedEventFlowable(filter);
    }

    public static List<ContextNodeAddedEventResponse> getContextNodeAddedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(CONTEXTNODEADDED_EVENT, transactionReceipt);
        ArrayList<ContextNodeAddedEventResponse> responses = new ArrayList<ContextNodeAddedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ContextNodeAddedEventResponse typedResponse = new ContextNodeAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.did = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ContextNodeAddedEventResponse> contextNodeAddedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ContextNodeAddedEventResponse>() {
            @Override
            public ContextNodeAddedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CONTEXTNODEADDED_EVENT, log);
                ContextNodeAddedEventResponse typedResponse = new ContextNodeAddedEventResponse();
                typedResponse.log = log;
                typedResponse.did = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ContextNodeAddedEventResponse> contextNodeAddedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CONTEXTNODEADDED_EVENT));
        return contextNodeAddedEventFlowable(filter);
    }

    public static List<ContextNodeDeactivatedEventResponse> getContextNodeDeactivatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(CONTEXTNODEDEACTIVATED_EVENT, transactionReceipt);
        ArrayList<ContextNodeDeactivatedEventResponse> responses = new ArrayList<ContextNodeDeactivatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ContextNodeDeactivatedEventResponse typedResponse = new ContextNodeDeactivatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.did = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ContextNodeDeactivatedEventResponse> contextNodeDeactivatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ContextNodeDeactivatedEventResponse>() {
            @Override
            public ContextNodeDeactivatedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CONTEXTNODEDEACTIVATED_EVENT, log);
                ContextNodeDeactivatedEventResponse typedResponse = new ContextNodeDeactivatedEventResponse();
                typedResponse.log = log;
                typedResponse.did = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ContextNodeDeactivatedEventResponse> contextNodeDeactivatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CONTEXTNODEDEACTIVATED_EVENT));
        return contextNodeDeactivatedEventFlowable(filter);
    }

    public static List<PathVerifiedEventResponse> getPathVerifiedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(PATHVERIFIED_EVENT, transactionReceipt);
        ArrayList<PathVerifiedEventResponse> responses = new ArrayList<PathVerifiedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PathVerifiedEventResponse typedResponse = new PathVerifiedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.exists = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<PathVerifiedEventResponse> pathVerifiedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, PathVerifiedEventResponse>() {
            @Override
            public PathVerifiedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PATHVERIFIED_EVENT, log);
                PathVerifiedEventResponse typedResponse = new PathVerifiedEventResponse();
                typedResponse.log = log;
                typedResponse.exists = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<PathVerifiedEventResponse> pathVerifiedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PATHVERIFIED_EVENT));
        return pathVerifiedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> deactivateContextNode(String _from) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DEACTIVATECONTEXTNODE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_from)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<List> getAdjList(String _from, String _context) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETADJLIST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_from), 
                new org.web3j.abi.datatypes.Utf8String(_context)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<ContextTrust>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> insertContextEdge(String _from, String _context, ContextTrust _trustEdge) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_INSERTCONTEXTEDGE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_from), 
                new org.web3j.abi.datatypes.Utf8String(_context), 
                _trustEdge), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> insertContextNode(String _from) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_INSERTCONTEXTNODE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_from)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> isContextNodeActive(String _from) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISCONTEXTNODEACTIVE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_from)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> modifyContextEdgeWeight(String _from, String _context, ContextTrust _trustEdge) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_MODIFYCONTEXTEDGEWEIGHT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_from), 
                new org.web3j.abi.datatypes.Utf8String(_context), 
                _trustEdge), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> verifyPath(List<Edge> _path) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_VERIFYPATH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<Edge>(Edge.class, _path)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static ContextsGraph load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ContextsGraph(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ContextsGraph load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ContextsGraph(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ContextsGraph load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ContextsGraph(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ContextsGraph load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ContextsGraph(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<ContextsGraph> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _issuersGraphAddress, String _entrypointsGraphAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _issuersGraphAddress), 
                new org.web3j.abi.datatypes.Address(160, _entrypointsGraphAddress)));
        return deployRemoteCall(ContextsGraph.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<ContextsGraph> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _issuersGraphAddress, String _entrypointsGraphAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _issuersGraphAddress), 
                new org.web3j.abi.datatypes.Address(160, _entrypointsGraphAddress)));
        return deployRemoteCall(ContextsGraph.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<ContextsGraph> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _issuersGraphAddress, String _entrypointsGraphAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _issuersGraphAddress), 
                new org.web3j.abi.datatypes.Address(160, _entrypointsGraphAddress)));
        return deployRemoteCall(ContextsGraph.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<ContextsGraph> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _issuersGraphAddress, String _entrypointsGraphAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _issuersGraphAddress), 
                new org.web3j.abi.datatypes.Address(160, _entrypointsGraphAddress)));
        return deployRemoteCall(ContextsGraph.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class ContextTrust extends DynamicStruct {
        public String to;

        public BigInteger level;

        public ContextTrust(String to, BigInteger level) {
            super(new org.web3j.abi.datatypes.Utf8String(to), 
                    new org.web3j.abi.datatypes.generated.Uint256(level));
            this.to = to;
            this.level = level;
        }

        public ContextTrust(Utf8String to, Uint256 level) {
            super(to, level);
            this.to = to.getValue();
            this.level = level.getValue();
        }
    }

    public static class Edge extends DynamicStruct {
        public String did;

        public String context;

        public BigInteger layer;

        public Edge(String did, String context, BigInteger layer) {
            super(new org.web3j.abi.datatypes.Utf8String(did), 
                    new org.web3j.abi.datatypes.Utf8String(context), 
                    new org.web3j.abi.datatypes.generated.Uint8(layer));
            this.did = did;
            this.context = context;
            this.layer = layer;
        }

        public Edge(Utf8String did, Utf8String context, Uint8 layer) {
            super(did, context, layer);
            this.did = did.getValue();
            this.context = context.getValue();
            this.layer = layer.getValue();
        }
    }

    public static class ContextEdgeAddedEventResponse extends BaseEventResponse {
        public String from;

        public String to;

        public BigInteger level;

        public String context;
    }

    public static class ContextEdgeModifiedEventResponse extends BaseEventResponse {
        public String from;

        public String to;

        public BigInteger level;

        public String context;
    }

    public static class ContextNodeAddedEventResponse extends BaseEventResponse {
        public String did;
    }

    public static class ContextNodeDeactivatedEventResponse extends BaseEventResponse {
        public String did;
    }

    public static class PathVerifiedEventResponse extends BaseEventResponse {
        public Boolean exists;
    }
}
