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
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
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
public class IssuersGraph extends Contract {
    public static final String BINARY = "6080806040523461001657610e18908161001c8239f35b600080fdfe60806040908082526004918236101561001757600080fd5b600091823560e01c908163478b8c2a14610988575080636408e63914610920578063714fe8a014610654578063a234dba71461057c578063a787e6ff146104af578063bf32c51b146103f55763e47c22bd1461007257600080fd5b346103f15761008036610bba565b8351602090818101828152816100998882018789610c08565b03916100ad601f1993848101835282610c29565b51902087528682526100c460ff8789205416610c61565b6100f76100eb6100d48580610cad565b92908951928391878301958887528c840191610c08565b03848101835282610c29565b519020875286825261010e60ff8789205416610c61565b855182810190838252610128816100eb8a8201898b610c08565b51902061015e61015261013b8680610cad565b92908a51928391888301958987528d840191610c08565b03858101835282610c29565b51902090808952600192838552888a20838b52855260ff898b205416156101e4575b89897f21e356f6b38ccde134f2396ac5649e91e81c1e693a05997197b2bc2509b47c228a8a8a8a6101d76101b48380610cad565b6101ca8951978897606089526060890191610c08565b9186830385880152610c08565b910135858301520390a151f35b818a5260028552888a20805490680100000000000000008210156103de5790610211918682018155610ce0565b9190916103cc579a67ffffffffffffffff9b61022d8880610cad565b9d8e939193116103b95750908c918c886102478654610d12565b91601f831161035b575b9050601f915093116001146102db57509b80918c9d7f21e356f6b38ccde134f2396ac5649e91e81c1e693a05997197b2bc2509b47c229c9d926102d0575b5050600019600383901b1c191690851b1781555b8385870135910155895281835287892090895282528688209060ff19825416179055869594388080610180565b01359050388061028f565b8d1691838d52878d20928d5b8181106103465750918793917f21e356f6b38ccde134f2396ac5649e91e81c1e693a05997197b2bc2509b47c229d9e9f80941061032c575b505050811b0181556102a3565b0135600019600384901b60f8161c1916905538808061031f565b838301358555938801939189019189016102e7565b86819694959652209089601f840160051c830193106103af575b8f949392918f90601f8b930160051c0192905b8382106103995790508a9150610251565b9080929394959650550190878f9493928f610388565b9091508190610375565b634e487b7160e01b8d526041905260248cfd5b634e487b7160e01b8b528a8c5260248bfd5b634e487b7160e01b8c5260418d5260248cfd5b5080fd5b5082346104ab57602090816003193601126104a75780359167ffffffffffffffff83116104a35761044d61049d927f8ea3e1177a41deb0290660bb0652f148a971c2835dc4eaa27031a1471df0809494369101610b87565b9290855183810190848252610477816104698a82018987610c08565b03601f198101835282610c29565b5190208752868352858720805460ff191660011790558551838152938493840191610c08565b0390a151f35b8480fd5b8380fd5b8280fd5b50903461057957816003193601126105795767ffffffffffffffff9280358481116104ab576104e19036908301610b87565b9190946024359081116104a7579161056661055a61050660ff96948896369101610b87565b9290938651908161052460209c8d938484019485528b840191610c08565b0391610538601f1993848101835282610c29565b519020865260018a528686209487519384918c8301968d88528a840191610c08565b03908101835282610c29565b5190208252855220541690519015158152f35b80fd5b5082346104ab57602090816003193601126104a75780359167ffffffffffffffff83116104a3576105d461049d927f39b20b43677689466b503fe35009740a0468292af86a892c1fc0a57c54a88c7594369101610b87565b92908551838101848152816105ec8982018886610c08565b0391610600601f1993848101835282610c29565b519020885287845261061760ff888a205416610c61565b8651610631858201928684528261055a8b82018a88610c08565b5190208752868352858720805460ff191690558551838152938493840191610c08565b50346103f1578161066436610bba565b959190845196602097888101898152816106818982018688610c08565b0391610695601f1993848101835282610c29565b51902086528589526106ac60ff8888205416610c61565b886106d46106ba8480610cad565b6100eb8b939293519384928684019687528d840191610c08565b51902086528589526106eb60ff8888205416610c61565b886106f68380610cad565b89809392935161071f816107138682019487865282018b8d610c08565b03878101835282610c29565b51902089526107478a6101528160019c8d875220958251948593878501978852840191610c08565b5190208952895260ff8789205416156108dd57978598875182810190838252610777816100eb8c8201898b610c08565b51902092899a5b61078757898951f35b838a526002808452898b20548c10156108d757896107c36107cf6107ae8f8f859020610ce0565b508351928391898301958a8752830190610d4c565b03868101835282610c29565b5190208a6107fa6107e08580610cad565b61071384939293519384928b8401968c8852840191610c08565b5190201461082957506000198b1461081657998701998761077e565b634e487b7160e01b8a526011875260248afd5b6108c095975061089a925061088c8c8b8d9e949d7f5b4043cd54cdb836c300e74b63214b210420487f7db2cd1a832fcbfb5553e6c29d9e89829e999a9c9e52858a52888a61087986868620610ce0565b509201359101558a815284895220610ce0565b50968c528452898b20610ce0565b500154926108b388519687966060808952880191610c08565b9185830390860152610d4c565b90858301520390a181903880808080808080898951f35b50898951f35b865162461bcd60e51b81528086018a9052601e60248201527f54686520697373756572206564676520646f6573206e6f7420657869737400006044820152606490fd5b5082346104ab5760203660031901126104ab57803567ffffffffffffffff81116104a75760209361097461046961095d60ff958795369101610b87565b929085519283918a8301958b875288840191610c08565b519020815280855220541690519015158152f35b9050346104ab57602090816003193601126104a75767ffffffffffffffff928535848111610b83576101526109c3610a199236908a01610b87565b9290868601878152866109d98782018785610c08565b03966109ed601f1998898101835282610c29565b5190208952888752610a0460ff868b205416610c61565b84519283918883019589875287840191610c08565b519020855260029586845281862096875490868211610b70578398969594985195610a49868460051b0188610c29565b8287528587019081988a52868a20908a925b858410610b085750505050505050805194828601938387525180945281860190828560051b880101959781935b868510610a955788880389f35b90919293949596603f19898203018552868a51918483518583528051908187850152885b828110610af05750506060818401810189905294840151848401529c83019c601f0116019091019796956001019401929190610a88565b818101860151858201606001528c9589945001610ab9565b879c98999a9c5188810181811084821117610b5d57808a5260019287928d92610b40603f1983610b38848c610d4c565b030182610c29565b815284870154838201528152019301930192919b9998979b610a5b565b634e487b7160e01b8e526041895260248efd5b634e487b7160e01b885260418352602488fd5b8580fd5b9181601f84011215610bb55782359167ffffffffffffffff8311610bb55760208381860195010111610bb557565b600080fd5b9060031990604082840112610bb55767ffffffffffffffff600435818111610bb55784610be991600401610b87565b94909493602435928311610bb55782604092030112610bb55760040190565b908060209392818452848401376000828201840152601f01601f1916010190565b90601f8019910116810190811067ffffffffffffffff821117610c4b57604052565b634e487b7160e01b600052604160045260246000fd5b15610c6857565b60405162461bcd60e51b815260206004820152601e60248201527f54686520697373756572206e6f646520646f6573206e6f7420657869737400006044820152606490fd5b903590601e1981360301821215610bb5570180359067ffffffffffffffff8211610bb557602001918136038313610bb557565b8054821015610cfc5760005260206000209060011b0190600090565b634e487b7160e01b600052603260045260246000fd5b90600182811c92168015610d42575b6020831014610d2c57565b634e487b7160e01b600052602260045260246000fd5b91607f1691610d21565b9060009291805491610d5d83610d12565b918282526001938481169081600014610dbf5750600114610d7f575b50505050565b90919394506000526020928360002092846000945b838610610dab575050505001019038808080610d79565b805485870183015294019385908201610d94565b9294505050602093945060ff191683830152151560051b01019038808080610d7956fea2646970667358221220e8a37434e3c58eaca2f116c63910fddc2cd091055a2e9232f5b5a915c9c872b764736f6c63430008100033";

    public static final String FUNC_DEACTIVATEISSUERNODE = "deactivateIssuerNode";

    public static final String FUNC_GETADJLIST = "getAdjList";

    public static final String FUNC_INSERTISSUEREDGE = "insertIssuerEdge";

    public static final String FUNC_INSERTISSUERNODE = "insertIssuerNode";

    public static final String FUNC_ISISSUEREDGE = "isIssuerEdge";

    public static final String FUNC_ISISSUERNODEACTIVE = "isIssuerNodeActive";

    public static final String FUNC_MODIFYISSUEREDGEWEIGHT = "modifyIssuerEdgeWeight";

    public static final Event ISSUEREDGEADDED_EVENT = new Event("IssuerEdgeAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event ISSUEREDGEMODIFIED_EVENT = new Event("IssuerEdgeModified", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event ISSUERNODEADDED_EVENT = new Event("IssuerNodeAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    public static final Event ISSUERNODEDEACTIVATED_EVENT = new Event("IssuerNodeDeactivated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected IssuersGraph(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected IssuersGraph(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected IssuersGraph(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected IssuersGraph(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<IssuerEdgeAddedEventResponse> getIssuerEdgeAddedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ISSUEREDGEADDED_EVENT, transactionReceipt);
        ArrayList<IssuerEdgeAddedEventResponse> responses = new ArrayList<IssuerEdgeAddedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            IssuerEdgeAddedEventResponse typedResponse = new IssuerEdgeAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.level = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<IssuerEdgeAddedEventResponse> issuerEdgeAddedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, IssuerEdgeAddedEventResponse>() {
            @Override
            public IssuerEdgeAddedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ISSUEREDGEADDED_EVENT, log);
                IssuerEdgeAddedEventResponse typedResponse = new IssuerEdgeAddedEventResponse();
                typedResponse.log = log;
                typedResponse.from = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.to = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.level = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<IssuerEdgeAddedEventResponse> issuerEdgeAddedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ISSUEREDGEADDED_EVENT));
        return issuerEdgeAddedEventFlowable(filter);
    }

    public static List<IssuerEdgeModifiedEventResponse> getIssuerEdgeModifiedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ISSUEREDGEMODIFIED_EVENT, transactionReceipt);
        ArrayList<IssuerEdgeModifiedEventResponse> responses = new ArrayList<IssuerEdgeModifiedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            IssuerEdgeModifiedEventResponse typedResponse = new IssuerEdgeModifiedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.level = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<IssuerEdgeModifiedEventResponse> issuerEdgeModifiedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, IssuerEdgeModifiedEventResponse>() {
            @Override
            public IssuerEdgeModifiedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ISSUEREDGEMODIFIED_EVENT, log);
                IssuerEdgeModifiedEventResponse typedResponse = new IssuerEdgeModifiedEventResponse();
                typedResponse.log = log;
                typedResponse.from = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.to = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.level = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<IssuerEdgeModifiedEventResponse> issuerEdgeModifiedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ISSUEREDGEMODIFIED_EVENT));
        return issuerEdgeModifiedEventFlowable(filter);
    }

    public static List<IssuerNodeAddedEventResponse> getIssuerNodeAddedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ISSUERNODEADDED_EVENT, transactionReceipt);
        ArrayList<IssuerNodeAddedEventResponse> responses = new ArrayList<IssuerNodeAddedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            IssuerNodeAddedEventResponse typedResponse = new IssuerNodeAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.did = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<IssuerNodeAddedEventResponse> issuerNodeAddedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, IssuerNodeAddedEventResponse>() {
            @Override
            public IssuerNodeAddedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ISSUERNODEADDED_EVENT, log);
                IssuerNodeAddedEventResponse typedResponse = new IssuerNodeAddedEventResponse();
                typedResponse.log = log;
                typedResponse.did = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<IssuerNodeAddedEventResponse> issuerNodeAddedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ISSUERNODEADDED_EVENT));
        return issuerNodeAddedEventFlowable(filter);
    }

    public static List<IssuerNodeDeactivatedEventResponse> getIssuerNodeDeactivatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ISSUERNODEDEACTIVATED_EVENT, transactionReceipt);
        ArrayList<IssuerNodeDeactivatedEventResponse> responses = new ArrayList<IssuerNodeDeactivatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            IssuerNodeDeactivatedEventResponse typedResponse = new IssuerNodeDeactivatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.did = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<IssuerNodeDeactivatedEventResponse> issuerNodeDeactivatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, IssuerNodeDeactivatedEventResponse>() {
            @Override
            public IssuerNodeDeactivatedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ISSUERNODEDEACTIVATED_EVENT, log);
                IssuerNodeDeactivatedEventResponse typedResponse = new IssuerNodeDeactivatedEventResponse();
                typedResponse.log = log;
                typedResponse.did = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<IssuerNodeDeactivatedEventResponse> issuerNodeDeactivatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ISSUERNODEDEACTIVATED_EVENT));
        return issuerNodeDeactivatedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> deactivateIssuerNode(String _from) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DEACTIVATEISSUERNODE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_from)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<List> getAdjList(String _from) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETADJLIST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_from)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<IssuerTrust>>() {}));
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

    public RemoteFunctionCall<TransactionReceipt> insertIssuerEdge(String _from, IssuerTrust _trustEdge) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_INSERTISSUEREDGE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_from), 
                _trustEdge), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> insertIssuerNode(String _from) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_INSERTISSUERNODE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_from)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> isIssuerEdge(String _from, String _to) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISISSUEREDGE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_from), 
                new org.web3j.abi.datatypes.Utf8String(_to)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> isIssuerNodeActive(String _from) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISISSUERNODEACTIVE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_from)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> modifyIssuerEdgeWeight(String _from, IssuerTrust _trustEdge) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_MODIFYISSUEREDGEWEIGHT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_from), 
                _trustEdge), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static IssuersGraph load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new IssuersGraph(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static IssuersGraph load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new IssuersGraph(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static IssuersGraph load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new IssuersGraph(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static IssuersGraph load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new IssuersGraph(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<IssuersGraph> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(IssuersGraph.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<IssuersGraph> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(IssuersGraph.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<IssuersGraph> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(IssuersGraph.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<IssuersGraph> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(IssuersGraph.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class IssuerTrust extends DynamicStruct {
        public String to;

        public BigInteger level;

        public IssuerTrust(String to, BigInteger level) {
            super(new org.web3j.abi.datatypes.Utf8String(to), 
                    new org.web3j.abi.datatypes.generated.Uint256(level));
            this.to = to;
            this.level = level;
        }

        public IssuerTrust(Utf8String to, Uint256 level) {
            super(to, level);
            this.to = to.getValue();
            this.level = level.getValue();
        }
    }

    public static class IssuerEdgeAddedEventResponse extends BaseEventResponse {
        public String from;

        public String to;

        public BigInteger level;
    }

    public static class IssuerEdgeModifiedEventResponse extends BaseEventResponse {
        public String from;

        public String to;

        public BigInteger level;
    }

    public static class IssuerNodeAddedEventResponse extends BaseEventResponse {
        public String did;
    }

    public static class IssuerNodeDeactivatedEventResponse extends BaseEventResponse {
        public String did;
    }
}
