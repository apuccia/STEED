// SPDX-License-Identifier: AFL-3.0

pragma solidity ^0.8.16;

import "./IssuersGraph.sol";
import "./EntrypointsGraph.sol";

/// @title  ContextsGraph
/// @notice Models a graph implemented using adjacency lists where edges have a weight
/// between 0 (no trust) and 100 (full trust), additionally a context (topic) is provided
/// for each edge
contract ContextsGraph {
    struct ContextTrust {
        string to;
        uint level;
    }

    enum Layer { CONTEXTS, ISSUERS }

    struct Edge {
        string did;
        string context;
        Layer layer;
    }

    mapping(bytes32 => bool) private contextNodes;
    mapping(bytes32 => mapping(bytes32 => mapping(bytes32 => bool))) private contextEdges;
    mapping(bytes32 => mapping(bytes32 => ContextTrust[])) private adjLists;

    IssuersGraph private issuersGraph;
    EntrypointsGraph private entrypointsGraph;

    event ContextNodeAdded(string did);
    event ContextNodeDeactivated(string did);
    event ContextEdgeAdded(string from, string to, uint level, string context);
    event ContextEdgeModified(string from, string to, uint level, string context);
    event PathVerified(bool exists);

    modifier contextNodeExists(string calldata _did) {
        require(contextNodes[keccak256(abi.encode(_did))], "The context node does not exist");
        _;
    }

    modifier contextEdgeExists(string calldata _from, string calldata _to, string calldata _context) {
        require(contextEdges[keccak256(abi.encode(_from))][keccak256(abi.encode(_to))][keccak256(abi.encode
            (_context))], "The context edge does not exist");
        _;
    }

    constructor(address _issuersGraphAddress, address _entrypointsGraphAddress) {
        issuersGraph = IssuersGraph(_issuersGraphAddress);
        entrypointsGraph = EntrypointsGraph(_entrypointsGraphAddress);
    }

    /// @notice Insert a node on the graph
    /// @param _from the node did
    function insertContextNode(string calldata _from) public {
        contextNodes[keccak256(abi.encode(_from))] = true;

        emit ContextNodeAdded(_from);
    }

    /// @notice Check if a node is active
    /// @param _from the node did
    function isContextNodeActive(string calldata _from) public view returns (bool) {
        return contextNodes[keccak256(abi.encode(_from))];
    }

    /// @notice Deactivate a node on the graph
    /// @param _from the node did
    function deactivateContextNode(string calldata _from) contextNodeExists(_from) public {
        contextNodes[keccak256(abi.encode(_from))] = false;

        emit ContextNodeDeactivated(_from);
    }

    /// @notice Insert an edge struct into the adjacency list of the specified did, if the edge already
    //  exists then it doesn't do anything
    /// @param _from the node did
    /// @param _trustEdge trust edge data structure
    function insertContextEdge(string calldata _from, string calldata _context, ContextTrust calldata _trustEdge)
            contextNodeExists(_from) contextNodeExists(_trustEdge.to) public {
        bytes32 hashedFrom = keccak256(abi.encode(_from));
        bytes32 hashedTo = keccak256(abi.encode(_trustEdge.to));
        bytes32 hashedContext = keccak256(abi.encode(_context));

        if (!contextEdges[hashedFrom][hashedTo][hashedContext]) {
            adjLists[hashedFrom][hashedContext].push(_trustEdge);
            contextEdges[hashedFrom][hashedTo][hashedContext] = true;
        }
        emit ContextEdgeAdded(_from, _trustEdge.to, _trustEdge.level, _context);
    }

    /// @notice Modify an edge struct inside the adjacency list of the specified did, if the edge does
    //  not exists then it doesn't do anything
    /// @param _from the node did
    /// @param _trustEdge trust edge data structure
    function modifyContextEdgeWeight(string calldata _from, string calldata _context, ContextTrust calldata _trustEdge)
            contextNodeExists(_from) contextNodeExists(_trustEdge.to) contextEdgeExists(_from, _trustEdge.to, _context)
            public {

        bytes32 hashedFrom = keccak256(abi.encode(_from));
        bytes32 hashedContext = keccak256(abi.encode(_context));
        for (uint i = 0; i < adjLists[hashedFrom][hashedContext].length; i++) {
            if (keccak256(abi.encode(adjLists[hashedFrom][hashedContext][i].to)) ==
                keccak256(abi.encode(_trustEdge.to))) {

                adjLists[hashedFrom][hashedContext][i].level = _trustEdge.level;
                emit ContextEdgeModified(_from, adjLists[hashedFrom][hashedContext][i].to,
                    adjLists[hashedFrom][hashedContext][i].level, _context);
                break;
            }
        }
    }

    /// @notice Retrieve the adjacency list of the node with the specified did
    /// @param _from the node did
    /// @return a dynamic array containing ContextTrust data structures
    function getAdjList(string calldata _from, string calldata _context) contextNodeExists(_from) public view returns
            (ContextTrust[] memory) {
        return adjLists[keccak256(abi.encode(_from))][keccak256(abi.encode(_context))];
    }

    /// @notice Verify that a path given in input exists
    /// @param _path an array of edges that forms the path to verify
    function verifyPath(Edge[] calldata _path) public {
        bool verified = false;
        for (uint i = 0; i < _path.length - 1; i++) {
            Layer layer = _path[i].layer;
            if (layer == Layer.CONTEXTS && _path[i + 1].layer == Layer.CONTEXTS) {
                verified = (i + 1 != _path.length - 1) && contextEdges[keccak256(abi.encode(_path[i].did))]
                    [keccak256(abi.encode(_path[i + 1].did))][keccak256(abi.encode(_path[i].context))];
            } else if (layer == Layer.CONTEXTS && _path[i + 1].layer == Layer.ISSUERS) {
                verified = entrypointsGraph.isEntrypointEdge(_path[i].did, _path[i + 1].did);
            } else if (layer == Layer.ISSUERS) {
                verified = (i != 0) && issuersGraph.isIssuerEdge(_path[i].did, _path[i + 1].did);
            } else {
                verified = false;
            }
            if (!verified) {
                break;
            }
        }
        emit PathVerified(verified);
    }
}