#!/bin/bash

# Set the pipe's return value to the last non_zero value in the pipe (or 0 if everything succeeded)
set -o pipefail

# Enviromental variables so we can reuse them in our other scripts
export TOP_DIR="$PWD"
export SRC_DIR="$TOP_DIR/src"
export LIB_DIR="$TOP_DIR/lib"
export DOCS_DIR="$TOP_DIR/docs"
export BUILD_SCRIPTS="$LIB_DIR/build"

source $LIB_DIR/debug_print.sh

build_file_list() {
    "$BUILD_SCRIPTS/file_list.sh" || { exit 1; }
}

build_docs() {
    "$BUILD_SCRIPTS/docs.sh" || { exit 1; }
}

build_java() {
    "$TOP_DIR/gradlew" build || { exit 1; }
}

build_svg() {
    "$BUILD_SCRIPTS/svg.sh" || { exit 1; }
}

build_javadoc() {
    "$BUILD_SCRIPTS/javadoc.sh" || { exit 1; }
}

docs_rebuild_full() {
    debug_separator
    build_svg
    debug_separator
    build_file_list
    debug_separator
    build_javadoc
    debug_separator
    build_docs
    debug_separator
}   

if [ "$1" == "docs" ]; then
    docs_rebuild_full
elif [ "$1" == "svg" ]; then
    build_svg force
elif [ "$1" == "file-list" ]; then
    build_file_list
    debug_separator
    build_docs
    debug_separator
elif [ "$1" == "java" ]; then
    build_java
elif [ "$1" == "javadoc" ]; then
    build_javadoc
    debug_separator
    build_docs
elif [ "$1" == "all" ]; then
    docs_rebuild_full
    build_java
    debug_separator
else
    docs_rebuild_full
fi


