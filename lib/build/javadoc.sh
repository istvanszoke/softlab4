#!/bin/bash

source "$LIB_DIR/debug_print.sh"

debug_info "JavaDoc generation started"

rm -f "$DOCS_DIR/javadoc/*.{tex,map}"

javadoc -docletpath "$LIB_DIR/TeXDoclet.jar" \
	-doclet org.stfm.texdoclet.TeXDoclet \
	-tree \
	-noindex \
	-hyperref \
	-output "$DOCS_DIR/javadoc/javadoc.tex" \
	-sourcepath "$SRC_DIR/main" \
	-subpackages agents buff commands feedback field game \
	-include \
	-sectionlevel section \
	-serial \
	-private \

if [ $? -ne 0 ]; then
    debug_error "JavaDoc generation ended (see the JavaDoc output for more information)"
    exit -1
fi

cp "$DOCS_DIR/includes/javadoc_pre.tex" "$DOCS_DIR/javadoc/"

if [ $? -ne 0 ]; then
    debug_error "JavaDoc generation ended (failed to copy necessary files)"
fi

debug_success "JavaDoc generation ended"
