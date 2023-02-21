package it.unipi.trustgraphmanager.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionReceiptInfoDTO {

    private String contractAddress;

    private BigInteger gasUsed;

    private BigInteger gasPrice;

    private BigDecimal transactionCost;

    private String transactionHash;

    private String blockHash;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime blockDateTime;

    private Long blockTimestamp;

    private BigInteger blockNumber;

    private Integer blockSize;

    private String blockAuthor;

    private String blockMiner;
}
