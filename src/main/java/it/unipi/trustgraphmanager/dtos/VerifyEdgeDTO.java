package it.unipi.trustgraphmanager.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerifyEdgeDTO {

    public String did;

    public String layer;

    public String context;
}
