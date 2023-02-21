package it.unipi.trustgraphmanager.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerifyEdgeTransactionResultDTO {

    private List<VerifyEdgeDTO> verifyEdge;

    private Boolean exists;

    private TransactionReceiptInfoDTO transactionReceiptInfo;
}
