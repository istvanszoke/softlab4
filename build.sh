#!/bin/bash

# Set the pipe's return value to the last non-zero value in the pipe (or 0 if everything succeeded)
set -o pipefail

# Enviromental variables so we can reuse them in our other scripts
export TOP_DIR="$PWD"
export SRC_DIR="$TOP_DIR/src"
export LIB_DIR="$TOP_DIR/lib"
export DOCS_DIR="$TOP_DIR/docs"

if [ "$1" == "docs" ]; then
    "$LIB_DIR"/build/docs.sh "$2"    
elif [ "$1" == "svg" ]; then
    "$LIB_DIR"/build/svg.sh force
elif [ "$1" == "latex-file-list" ]; then
    "$LIB_DIR"/build/latex_file_list.sh
fi


