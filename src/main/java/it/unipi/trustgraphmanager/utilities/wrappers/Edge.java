package it.unipi.trustgraphmanager.utilities.wrappers;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Edge {
    private String from;

    private String to;

    private Float weight;

    private String context;
}
