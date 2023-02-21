#!/bin/sh

GRAPHS_ABI_DIR=src/solidity/build/contracts/graphs/abi
GRAPHS_BIN_DIR=src/solidity/build/contracts/graphs/bin

GRAPHS_PACKAGE=it.unipi.trustgraphmanager.contracts

for abi_filepath in $GRAPHS_ABI_DIR/*.abi; do
    abi_filename=$(basename -- "$abi_filepath");
    filename="${abi_filename%.*}"
    web3j generate solidity -a $abi_filepath -b $GRAPHS_BIN_DIR/$filename.bin -o src/main/java -p $GRAPHS_PACKAGE
done;