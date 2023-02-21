// SPDX-License-Identifier: AFL-3.0

pragma solidity ^0.8.16;

/// @title  EntryPointsGraph
/// @notice Models a bipartite graph implemented using adjacency lists where edges have a weight
/// between 0 (no trust) and 100 (full trust)
contract EntrypointsGraph {
    struct EntrypointTrust {
        string to;
        uint level;
    }

    mapping(bytes32 => bool) private contextNodes;
    mapping(bytes32 => bool) private issuerNodes;
    mapping(bytes32 => EntrypointTrust[]) private adjLists;
    mapping(bytes32 => mapping(bytes32 => bool)) private entrypointEdges;

    event ContextNodeAdded(string did);
    event IssuerNodeAdded(string did);
    event ContextNodeDeactivated(string did);
    event IssuerNodeDeactivated(string did);
    event EntrypointEdgeAdded(string from, string to, uint level);
    event EntrypointEdgeModified(string from, string to, uint level);

    modifier contextNodeExists(string calldata _did) {
        require(contextNodes[keccak256(abi.encode(_did))], "The context node does not exist");
        _;
    }

    modifier issuerNodeExists(string calldata _did) {
        require(issuerNodes[keccak256(abi.encode(_did))], "The issuer node does not exist");
        _;
    }

    modifier entrypointEdgeExists(string calldata _from, string calldata _to) {
        require(entrypointEdges[keccak256(abi.encode(_from))][keccak256(abi.encode(_to))],
            "The entrypoint edge does not exist");
        _;
    }

    /// @notice Insert a node on the graph
    /// @param _from the node did
    function insertContextNode(string calldata _from) public {
        contextNodes[keccak256(abi.encode(_from))] = true;

        emit ContextNodeAdded(_from);
    }

    /// @notice Deactivate a node on the graph
    /// @param _from the node did
    function deactivateContextNode(string calldata _from) contextNodeExists(_from) public {
        contextNodes[keccak256(abi.encode(_from))] = false;

        emit ContextNodeDeactivated(_from);
    }

    /// @notice Insert an entrypoint on the graph
    /// @param _from the entrypoint did
    function insertIssuerNode(string calldata _from) public {
        issuerNodes[keccak256(abi.encode(_from))] = true;

        emit IssuerNodeAdded(_from);
    }

    /// @notice Deactivate an entrypoint on the graph
    /// @param _from the entrypoint did
    function deactivateIssuerNode(string calldata _from) issuerNodeExists(_from) public {
        issuerNodes[keccak256(abi.encode(_from))] = false;

        emit IssuerNodeDeactivated(_from);
    }

    /// @notice Insert an edge struct into the adjacency list of the specified did, if the edge already
    //  exists then it doesn't do anything
    /// @param _from the node did
    /// @param _trustEdge trust edge data structure containing the entrypoint
    function insertEdge(string calldata _from, EntrypointTrust calldata _trustEdge) contextNodeExists(_from)
            issuerNodeExists(_trustEdge.to) public {
        bytes32 hashedFrom = keccak256(abi.encode(_from));
        bytes32 hashedTo = keccak256(abi.encode(_trustEdge.to));

        require(contextNodes[hashedFrom] && !issuerNodes[hashedFrom] && !contextNodes[hashedTo] && issuerNodes[hashedTo],
            "The edge should start from a context node");

        if (!entrypointEdges[hashedFrom][hashedTo]) {
            adjLists[hashedFrom].push(_trustEdge);
            entrypointEdges[hashedFrom][hashedTo] = true;
        }
        emit EntrypointEdgeAdded(_from, _trustEdge.to, _trustEdge.level);
    }

    /// @notice Modify an edge struct inside the adjacency list of the specified did, if the edge does
    //  not exists then it doesn't do anything
    /// @param _from the node did
    /// @param _trustEdge trust edge data structure
    function modifyEdgeWeight(string calldata _from, EntrypointTrust calldata _trustEdge) contextNodeExists(_from)
            issuerNodeExists(_trustEdge.to) entrypointEdgeExists(_from, _trustEdge.to) public {
        bytes32 hashed = keccak256(abi.encode(_from));
        for (uint i = 0; i < adjLists[hashed].length; i++) {
            if (keccak256(abi.encode(adjLists[hashed][i].to)) ==
                keccak256(abi.encode(_trustEdge.to))) {

                adjLists[hashed][i].level = _trustEdge.level;
                emit EntrypointEdgeModified(_from, adjLists[hashed][i].to, adjLists[hashed][i].level);
                break;
            }
        }
    }

    /// @notice Retrieve the adjacency list of the node with the specified did
    /// @param _from the node did
    /// @return a dynamic array containing EntrypointTrust data structures
    function getAdjList(string calldata _from) contextNodeExists(_from) public view returns (EntrypointTrust[] memory) {
        return adjLists[keccak256(abi.encode(_from))];
    }

    function isEntrypointEdge(string calldata _from, string calldata _to) public view returns (bool) {
        return entrypointEdges[keccak256(abi.encode(_from))][keccak256(abi.encode(_to))];
    }
}