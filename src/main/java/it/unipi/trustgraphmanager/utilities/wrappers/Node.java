package it.unipi.trustgraphmanager.utilities.wrappers;

import lombok.*;

import java.util.Objects;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Node {

    private String did;

    private Float trust;

    private Integer depth;

    private String type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(did, node.did);
    }

    @Override
    public int hashCode() {
        return Objects.hash(did);
    }
}
