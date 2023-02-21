// SPDX-License-Identifier: AFL-3.0

pragma solidity ^0.8.16;

/// @title  IssuersGraph
/// @notice Models a graph implemented using adjacency lists where edges have a weight
/// between 0 (no trust) and 100 (full trust)
contract IssuersGraph {
    struct IssuerTrust {
        string to;
        uint level;
    }

    mapping(bytes32 => bool) private issuerNodes;
    mapping(bytes32 => mapping(bytes32 => bool)) private issuerEdges;
    mapping(bytes32 => IssuerTrust[]) private adjLists;

    event IssuerNodeAdded(string did);
    event IssuerNodeDeactivated(string did);
    event IssuerEdgeAdded(string from, string to, uint level);
    event IssuerEdgeModified(string from, string to, uint level);

    modifier issuerNodeExists(string calldata _did) {
        require(issuerNodes[keccak256(abi.encode(_did))], "The issuer node does not exist");
        _;
    }

    modifier issuerEdgeExists(string calldata _from, string calldata _to) {
        require(issuerEdges[keccak256(abi.encode(_from))][keccak256(abi.encode(_to))],
            "The issuer edge does not exist");
        _;
    }

    /// @notice Insert a node on the graph
    /// @param _from the node did
    function insertIssuerNode(string calldata _from) public {
        issuerNodes[keccak256(abi.encode(_from))] = true;

        emit IssuerNodeAdded(_from);
    }

    /// @notice Check if a node is active
    /// @param _from the node did
    function isIssuerNodeActive(string calldata _from) public view returns (bool) {
        return issuerNodes[keccak256(abi.encode(_from))];
    }

    /// @notice Deactivate a node on the graph
    /// @param _from the node did
    function deactivateIssuerNode(string calldata _from) issuerNodeExists(_from) public {
        issuerNodes[keccak256(abi.encode(_from))] = false;

        emit IssuerNodeDeactivated(_from);
    }

    /// @notice Insert an edge struct into the adjacency list of the specified did, if the edge already
    /// exists then it doesn't do anything
    /// @param _from the node did
    /// @param _trustEdge trust edge data structure
    function insertIssuerEdge(string calldata _from, IssuerTrust calldata _trustEdge) issuerNodeExists(_from)
            issuerNodeExists(_trustEdge.to) public {
        bytes32 hashedFrom = keccak256(abi.encode(_from));
        bytes32 hashedTo = keccak256(abi.encode(_trustEdge.to));

        if (!issuerEdges[hashedFrom][hashedTo]) {
            adjLists[hashedFrom].push(_trustEdge);
            issuerEdges[hashedFrom][hashedTo] = true;
        }
        emit IssuerEdgeAdded(_from, _trustEdge.to, _trustEdge.level);
    }

    /// @notice Modify an edge struct inside the adjacency list of the specified did, if the edge does
    //  not exists then it doesn't do anything
    /// @param _from the node did
    /// @param _trustEdge trust edge data structure
    function modifyIssuerEdgeWeight(string calldata _from, IssuerTrust calldata _trustEdge) issuerNodeExists(_from)
            issuerNodeExists(_trustEdge.to) issuerEdgeExists(_from, _trustEdge.to) public {
        bytes32 hashed = keccak256(abi.encode(_from));
        for (uint i = 0; i < adjLists[hashed].length; i++) {
            if (keccak256(abi.encode(adjLists[hashed][i].to)) ==
                keccak256(abi.encode(_trustEdge.to))) {

                adjLists[hashed][i].level = _trustEdge.level;
                emit IssuerEdgeModified(_from, adjLists[hashed][i].to, adjLists[hashed][i].level);
                break;
            }
        }
    }

    /// @notice Retrieve the adjacency list of the node with the specified did
    /// @param _from the node did
    /// @return a dynamic array containing Trust data structures
    function getAdjList(string calldata _from) issuerNodeExists(_from) public view returns (IssuerTrust[] memory) {
        return adjLists[keccak256(abi.encode(_from))];
    }

    function isIssuerEdge(string calldata _from, string calldata _to) public view returns (bool) {
        return issuerEdges[keccak256(abi.encode(_from))][keccak256(abi.encode(_to))];
    }
}