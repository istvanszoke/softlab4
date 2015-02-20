#!/bin/bash

echo "Generating of the documentation started."

for file in docs/*.{aux,lof,log,out,toc};
do
    rm -f "$file"
done

texdoclet/javadoc_to_latex.sh
if [ $? -ne 0 ]; then
  echo 'Generation of LaTeX JavaDoc failed. [FAILED]'
  exit -1
fi

cd docs
pdflatex -halt-on-error szoftlab4.tex
if [ $? -ne 0 ]; then
  echo "Generating of the documentation ended. [FAILED]"
  exit -1
fi
cd ..
echo "Generating of the documentation ended. [SUCCESS]"
exit 0
