package it.unipi.trustgraphmanager.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EdgeDTO {

    private String from;

    private String to;

    private Float weight;

    private String context;
}
