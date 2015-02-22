#!/bin/bash

# Enviromental variables so we can reuse them in our other scripts
export TOP_DIR="$PWD"
export SRC_DIR="$TOP_DIR/src"
export LIB_DIR="$TOP_DIR/lib"
export DOCS_DIR="$TOP_DIR/docs"

# We will have to generate the docs multiple times, but on a fail it's easier to debug only
# one of the LaTeX outputs. Also note that this function doesn't clean up the temporaries,
# which can be useful in the debugging process
function generate_docs {
    pdflatex -halt-on-error "$DOCS_DIR/szoftlab4.tex"
    if [ $? -ne 0 ]; then
        echo "Generating of the documentation ended. [FAILED]"
        exit -1
    fi
}

echo "Generating of the documentation started."

cd "$DOCS_DIR"

# This script will generate the necessary LaTeX sources from the comments in the source code
javadoc/javadoc_to_latex.sh
if [ $? -ne 0 ]; then
  echo 'Generation of LaTeX JavaDoc failed. [FAILED]'
  exit -1
fi

# We have to convert all our exported SVG files into PDFs, because LaTeX's SVG support is dreadful.
./svg_preprocess.sh
if [ $? -ne 0 ]; then
    echo "SVG to PDF conversion failed. [FAILED]"
    exit -1
fi

# Calling generate_docs 3 times, because of LaTeX's quirks: the first run generates
# the base document, the second fixes table headers and generates a Table of Contents (ToC)
# the third fixes up page numbers that are potentially ruined by the insertion of the ToC
generate_docs
generate_docs
generate_docs

# Deleting the temporary files generated by LaTeX at the end of the generation, this helps
# keeping the working directory clean (not all LaTeX distributions support separate temporary
# directories).
rm -f *.{aux,lof,log,out,toc}

cd "$TOP_DIR"

echo "Generating of the documentation ended. [SUCCESS]"
