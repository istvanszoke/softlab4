#!/bin/bash

rm -f "$DOCS_DIR/javadoc/*.{tex,map}"

javadoc -docletpath "$DOCS_DIR/javadoc/TeXDoclet.jar" \
	-doclet org.stfm.texdoclet.TeXDoclet \
	-tree \
	-noindex \
	-hyperref \
	-output "$DOCS_DIR/javadoc/javadoc.tex" \
	-sourcepath "$SRC_DIR" \
	-subpackages org:main \
	-include \
	-sectionlevel section \
	-serial \
	-private \

cp "$DOCS_DIR/includes/javadoc_pre.tex" "$DOCS_DIR/javadoc/"

