#!/bin/sh

for filepath in src/solidity/contracts/graphs/*.sol; do
    solc $filepath --bin --optimize --via-ir -o src/solidity/build/contracts/graphs/bin --overwrite
    solc $filepath --abi --optimize --via-ir -o src/solidity/build/contracts/graphs/abi --overwrite
done;