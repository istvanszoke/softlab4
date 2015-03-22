#!/bin/bash

# Set the pipe's return value to the last non-zero value in the pipe (or 0 if everything succeeded)
set -o pipefail

# Enviromental variables so we can reuse them in our other scripts
export TOP_DIR="$PWD"
export SRC_DIR="$TOP_DIR/src"
export LIB_DIR="$TOP_DIR/lib"
export DOCS_DIR="$TOP_DIR/docs"

source $LIB_DIR/debug_print.sh

latex_file_list() {
    "$LIB_DIR"/build/latex_file_list.sh > "$DOCS_DIR"/includes/file_list.tex
    
    if [ $? -eq 0 ]; then
        debug_success "LaTeX file list generation successful. [SUCCESS]"
    fi
}

if [ "$1" == "docs" ]; then
    latex_file_list
    "$LIB_DIR"/build/docs.sh "$2"    
elif [ "$1" == "svg" ]; then
    "$LIB_DIR"/build/svg.sh force
elif [ "$1" == "latex-file-list" ]; then
    latex_file_list
elif [ "$1" == "java" ]; then
    "$TOP_DIR"/gradlew build
elif [ "$1" == "all" ]; then
    latex_file_list
    "$LIB_DIR"/build/svg.sh force
    "$LIB_DIR"/build/docs.sh
    "$TOP_DIR"/gradlew build
else
    "$LIB_DIR"/build/docs.sh 
fi


