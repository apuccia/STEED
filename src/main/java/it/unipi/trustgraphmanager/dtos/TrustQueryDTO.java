package it.unipi.trustgraphmanager.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrustQueryDTO {

    private String source;

    private String target;

    private Integer maxHops;

    private Float trustThreshold;

    private String context;
}
