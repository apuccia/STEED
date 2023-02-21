package it.unipi.trustgraphmanager.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MinMaxTrustPathDTO {

    private List<VerifyEdgeDTO> minTrustPath;

    private List<VerifyEdgeDTO> maxTrustPath;

    private Float maxTrust;

    private Float minTrust;
}
