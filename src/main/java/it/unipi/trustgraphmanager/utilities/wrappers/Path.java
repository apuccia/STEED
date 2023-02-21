package it.unipi.trustgraphmanager.utilities.wrappers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Path {
    private List<Node> path;

    public void add(Node node) {
        path.add(node);
    }

    public Node get(Integer index) {
        return path.get(index);
    }

    public Integer size() {
        return path.size();
    }

    public Boolean contains(Node node) {
        return path.contains(node);
    }
}
