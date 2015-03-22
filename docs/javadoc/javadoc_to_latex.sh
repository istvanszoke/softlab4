#!/bin/bash

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

cp "$DOCS_DIR/includes/javadoc_pre.tex" "$DOCS_DIR/javadoc/"

