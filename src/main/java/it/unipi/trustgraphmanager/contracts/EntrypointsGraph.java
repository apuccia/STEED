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
public class EntrypointsGraph extends Contract {
    public static final String BINARY = "6080806040523461001657611048908161001c8239f35b600080fdfe608060408181526004918236101561001657600080fd5b600090813560e01c908163478b8c2a14610b6a57508063a234dba714610a90578063b5166497146107a8578063b6a4116f14610374578063bf32c51b146102d9578063d20ec45614610231578063f697affb1461014f5763ffea8ba91461007c57600080fd5b3461014c578160031936011261014c5767ffffffffffffffff928035848111610148576100ac9036908301610d6b565b91909460243590811161014457916101316101256100d160ff96948896369101610d6b565b929093865190816100ef60209c8d938484019485528b840191610dec565b0391610103601f1993848101835282610e0d565b519020865260038a528686209487519384918c8301968d88528a840191610dec565b03908101835282610e0d565b5190208252855220541690519015158152f35b8380fd5b8280fd5b80fd5b50913461014857602090816003193601126101445780359167ffffffffffffffff831161022d576101a7610227927fc4e103aabdf7eb9dba199fc579dc1054898ea18de6e1c7e6eb504146091503c994369101610d6b565b92908551838101848152816101bf8982018886610dec565b03916101d3601f1993848101835282610e0d565b51902088528784526101ea60ff888a205416610e45565b865161020485820192868452826101258b82018a88610dec565b5190208752868352858720805460ff191690558551838152938493840191610dec565b0390a151f35b8480fd5b50913461014857602090816003193601126101445780359167ffffffffffffffff831161022d57610289610227927f143d4b7ffeeadf836e16fffd8813b8a4a6cd5b1e3375d24f946eaeb68af03a8394369101610d6b565b92908551838101908482526102b3816102a58a82018987610dec565b03601f198101835282610e0d565b5190208752868352858720805460ff191660011790558551838152938493840191610dec565b50913461014857602090816003193601126101445780359167ffffffffffffffff831161022d57610331610227927f8ea3e1177a41deb0290660bb0652f148a971c2835dc4eaa27031a1471df0809494369101610d6b565b929085518381019084825261034d816102a58a82018987610dec565b519020875260018352858720600160ff198254161790558551938385948552840191610dec565b5090346107a45761038436610d9e565b83516020908181018281528161039d8882018789610dec565b03916103b1601f1993848101835282610e0d565b519020875286825260ff6103c981888a205416610e45565b6103fc6103f06103d98680610edd565b92908a51928391888301958987528d840191610dec565b03858101835282610e0d565b519020885260019182845261041582898b205416610e91565b87518481019085825261043b8161042f8c82018b8d610dec565b03848101835282610e0d565b519020908861046761044d8880610edd565b61042f84939293519384928b8401968c8852840191610dec565b51902092828b528a8652808a8c20541680610794575b80610781575b8061076f575b1561071a57828b5260038652898b20848c528652898b2054161561050c575b89897fe754702c1e556c17ca3d697c982f8675669e2973553f7cf2c944179b65879f248a8a8a8a6104ff6104dc8380610edd565b6104f28951978897606089526060890191610dec565b9186830385880152610dec565b910135858301520390a151f35b818a5260028552888a20805490680100000000000000008210156107075790610539918682018155610f10565b9190916106f5579a67ffffffffffffffff9b6105558880610edd565b9d8e939193116106e25750908c918c8861056f8654610f42565b91601f8311610684575b9050601f9150931160011461060457509b80918c9d7fe754702c1e556c17ca3d697c982f8675669e2973553f7cf2c944179b65879f249c9d926105f9575b5050600019600383901b1c191690851b1781555b838587013591015589526003835287892090895282528688209060ff198254161790558695943880806104a8565b0135905038806105b7565b8d1691838d52878d20928d5b81811061066f5750918793917fe754702c1e556c17ca3d697c982f8675669e2973553f7cf2c944179b65879f249d9e9f809410610655575b505050811b0181556105cb565b0135600019600384901b60f8161c19169055388080610648565b83830135855593880193918901918901610610565b86819694959652209089601f840160051c830193106106d8575b8f949392918f90601f8b930160051c0192905b8382106106c25790508a9150610579565b9080929394959650550190878f9493928f6106b1565b909150819061069e565b634e487b7160e01b8d526041905260248cfd5b634e487b7160e01b8b528a8c5260248bfd5b634e487b7160e01b8c5260418d5260248cfd5b895162461bcd60e51b8152808d01879052602960248201527f54686520656467652073686f756c642073746172742066726f6d206120636f6e60448201526874657874206e6f646560b81b6064820152608490fd5b50838b52848652808a8c205416610489565b50838b528a8652808a8c20541615610483565b50848652808a8c2054161561047d565b5080fd5b5090346107a457816107b936610d9e565b959190845196602097888101898152816107d68982018688610dec565b03916107ea601f1993848101835282610e0d565b519020865285895261080160ff8888205416610e45565b8861082961080f8480610edd565b61042f8b939293519384928684019687528d840191610dec565b519020865283896108826108a08a6103f08160019c8d875261085060ff8383205416610e91565b8a61087661085e8c80610edd565b98909a85519283918c8301958d875288840191610dec565b038c8101835282610e0d565b51902081526003875220958251948593878501978852840191610dec565b5190208952895260ff878920541615610a42579785988751828101908382526108d08161042f8c8201898b610dec565b51902092899a5b6108e057898951f35b838a526002808452898b20548c1015610a3c578961091c6109286109078f8f859020610f10565b508351928391898301958a8752830190610f7c565b03868101835282610e0d565b5190208a61095f6109398580610edd565b61095384939293519384928b8401968c8852840191610dec565b03878101835282610e0d565b5190201461098e57506000198b1461097b5799870199876108d7565b634e487b7160e01b8a526011875260248afd5b610a259597506109ff92506109f18c8b8d9e949d7f66210b8656438502a399dad6fbc5dc2f15edc2ec42830e994f5922954adfaf349d9e89829e999a9c9e52858a52888a6109de86868620610f10565b509201359101558a815284895220610f10565b50968c528452898b20610f10565b50015492610a1888519687966060808952880191610dec565b9185830390860152610f7c565b90858301520390a181903880808080808080898951f35b50898951f35b865162461bcd60e51b81528086018a9052602260248201527f54686520656e747279706f696e74206564676520646f6573206e6f74206578696044820152611cdd60f21b6064820152608490fd5b50913461014857602090816003193601126101445780359167ffffffffffffffff831161022d57610ae8610227927f39b20b43677689466b503fe35009740a0468292af86a892c1fc0a57c54a88c7594369101610d6b565b9290855183810184815281610b008982018886610dec565b0391610b14601f1993848101835282610e0d565b519020885260018452610b2c60ff888a205416610e91565b8651610b4685820192868452826101258b82018a88610dec565b519020875260018352858720805460ff191690558551838152938493840191610dec565b849391503461014857602090816003193601126101445767ffffffffffffffff928535848111610d67576103f0610ba7610bfd9236908a01610d6b565b929086860187815286610bbd8782018785610dec565b0396610bd1601f1998898101835282610e0d565b5190208952888752610be860ff868b205416610e45565b84519283918883019589875287840191610dec565b519020855260029586845281862096875490868211610d54578398969594985195610c2d868460051b0188610e0d565b8287528587019081988a52868a20908a925b858410610cec5750505050505050805194828601938387525180945281860190828560051b880101959781935b868510610c795788880389f35b90919293949596603f19898203018552868a51918483518583528051908187850152885b828110610cd45750506060818401810189905294840151848401529c83019c601f0116019091019796956001019401929190610c6c565b818101860151858201606001528c9589945001610c9d565b879c98999a9c5188810181811084821117610d4157808a5260019287928d92610d24603f1983610d1c848c610f7c565b030182610e0d565b815284870154838201528152019301930192919b9998979b610c3f565b634e487b7160e01b8e526041895260248efd5b634e487b7160e01b885260418352602488fd5b8580fd5b9181601f84011215610d995782359167ffffffffffffffff8311610d995760208381860195010111610d9957565b600080fd5b9060031990604082840112610d995767ffffffffffffffff600435818111610d995784610dcd91600401610d6b565b94909493602435928311610d995782604092030112610d995760040190565b908060209392818452848401376000828201840152601f01601f1916010190565b90601f8019910116810190811067ffffffffffffffff821117610e2f57604052565b634e487b7160e01b600052604160045260246000fd5b15610e4c57565b60405162461bcd60e51b815260206004820152601f60248201527f54686520636f6e74657874206e6f646520646f6573206e6f74206578697374006044820152606490fd5b15610e9857565b60405162461bcd60e51b815260206004820152601e60248201527f54686520697373756572206e6f646520646f6573206e6f7420657869737400006044820152606490fd5b903590601e1981360301821215610d99570180359067ffffffffffffffff8211610d9957602001918136038313610d9957565b8054821015610f2c5760005260206000209060011b0190600090565b634e487b7160e01b600052603260045260246000fd5b90600182811c92168015610f72575b6020831014610f5c57565b634e487b7160e01b600052602260045260246000fd5b91607f1691610f51565b9060009291805491610f8d83610f42565b918282526001938481169081600014610fef5750600114610faf575b50505050565b90919394506000526020928360002092846000945b838610610fdb575050505001019038808080610fa9565b805485870183015294019385908201610fc4565b9294505050602093945060ff191683830152151560051b01019038808080610fa956fea2646970667358221220edb69cfd797aa803c45eb9ac1cbac2509c03fd11e8133c52a818ed8e3e36dab864736f6c63430008100033";

    public static final String FUNC_DEACTIVATECONTEXTNODE = "deactivateContextNode";

    public static final String FUNC_DEACTIVATEISSUERNODE = "deactivateIssuerNode";

    public static final String FUNC_GETADJLIST = "getAdjList";

    public static final String FUNC_INSERTCONTEXTNODE = "insertContextNode";

    public static final String FUNC_INSERTEDGE = "insertEdge";

    public static final String FUNC_INSERTISSUERNODE = "insertIssuerNode";

    public static final String FUNC_ISENTRYPOINTEDGE = "isEntrypointEdge";

    public static final String FUNC_MODIFYEDGEWEIGHT = "modifyEdgeWeight";

    public static final Event CONTEXTNODEADDED_EVENT = new Event("ContextNodeAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    public static final Event CONTEXTNODEDEACTIVATED_EVENT = new Event("ContextNodeDeactivated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    public static final Event ENTRYPOINTEDGEADDED_EVENT = new Event("EntrypointEdgeAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event ENTRYPOINTEDGEMODIFIED_EVENT = new Event("EntrypointEdgeModified", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event ISSUERNODEADDED_EVENT = new Event("IssuerNodeAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    public static final Event ISSUERNODEDEACTIVATED_EVENT = new Event("IssuerNodeDeactivated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected EntrypointsGraph(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected EntrypointsGraph(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected EntrypointsGraph(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected EntrypointsGraph(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
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

    public static List<EntrypointEdgeAddedEventResponse> getEntrypointEdgeAddedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ENTRYPOINTEDGEADDED_EVENT, transactionReceipt);
        ArrayList<EntrypointEdgeAddedEventResponse> responses = new ArrayList<EntrypointEdgeAddedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            EntrypointEdgeAddedEventResponse typedResponse = new EntrypointEdgeAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.level = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<EntrypointEdgeAddedEventResponse> entrypointEdgeAddedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, EntrypointEdgeAddedEventResponse>() {
            @Override
            public EntrypointEdgeAddedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ENTRYPOINTEDGEADDED_EVENT, log);
                EntrypointEdgeAddedEventResponse typedResponse = new EntrypointEdgeAddedEventResponse();
                typedResponse.log = log;
                typedResponse.from = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.to = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.level = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<EntrypointEdgeAddedEventResponse> entrypointEdgeAddedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ENTRYPOINTEDGEADDED_EVENT));
        return entrypointEdgeAddedEventFlowable(filter);
    }

    public static List<EntrypointEdgeModifiedEventResponse> getEntrypointEdgeModifiedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ENTRYPOINTEDGEMODIFIED_EVENT, transactionReceipt);
        ArrayList<EntrypointEdgeModifiedEventResponse> responses = new ArrayList<EntrypointEdgeModifiedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            EntrypointEdgeModifiedEventResponse typedResponse = new EntrypointEdgeModifiedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.level = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<EntrypointEdgeModifiedEventResponse> entrypointEdgeModifiedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, EntrypointEdgeModifiedEventResponse>() {
            @Override
            public EntrypointEdgeModifiedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ENTRYPOINTEDGEMODIFIED_EVENT, log);
                EntrypointEdgeModifiedEventResponse typedResponse = new EntrypointEdgeModifiedEventResponse();
                typedResponse.log = log;
                typedResponse.from = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.to = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.level = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<EntrypointEdgeModifiedEventResponse> entrypointEdgeModifiedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ENTRYPOINTEDGEMODIFIED_EVENT));
        return entrypointEdgeModifiedEventFlowable(filter);
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

    public RemoteFunctionCall<TransactionReceipt> deactivateContextNode(String _from) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DEACTIVATECONTEXTNODE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_from)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<EntrypointTrust>>() {}));
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

    public RemoteFunctionCall<TransactionReceipt> insertContextNode(String _from) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_INSERTCONTEXTNODE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_from)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> insertEdge(String _from, EntrypointTrust _trustEdge) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_INSERTEDGE, 
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

    public RemoteFunctionCall<Boolean> isEntrypointEdge(String _from, String _to) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISENTRYPOINTEDGE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_from), 
                new org.web3j.abi.datatypes.Utf8String(_to)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> modifyEdgeWeight(String _from, EntrypointTrust _trustEdge) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_MODIFYEDGEWEIGHT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_from), 
                _trustEdge), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static EntrypointsGraph load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new EntrypointsGraph(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static EntrypointsGraph load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new EntrypointsGraph(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static EntrypointsGraph load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new EntrypointsGraph(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static EntrypointsGraph load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new EntrypointsGraph(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<EntrypointsGraph> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(EntrypointsGraph.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<EntrypointsGraph> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(EntrypointsGraph.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<EntrypointsGraph> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(EntrypointsGraph.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<EntrypointsGraph> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(EntrypointsGraph.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class EntrypointTrust extends DynamicStruct {
        public String to;

        public BigInteger level;

        public EntrypointTrust(String to, BigInteger level) {
            super(new org.web3j.abi.datatypes.Utf8String(to), 
                    new org.web3j.abi.datatypes.generated.Uint256(level));
            this.to = to;
            this.level = level;
        }

        public EntrypointTrust(Utf8String to, Uint256 level) {
            super(to, level);
            this.to = to.getValue();
            this.level = level.getValue();
        }
    }

    public static class ContextNodeAddedEventResponse extends BaseEventResponse {
        public String did;
    }

    public static class ContextNodeDeactivatedEventResponse extends BaseEventResponse {
        public String did;
    }

    public static class EntrypointEdgeAddedEventResponse extends BaseEventResponse {
        public String from;

        public String to;

        public BigInteger level;
    }

    public static class EntrypointEdgeModifiedEventResponse extends BaseEventResponse {
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
