#!/bin/bash

if ! command -v "rsvg-convert" &> /dev/null; then
    echo "No suitable SVG to PDF converter found";
    exit -1;
fi

for file in $(find . -name '*.svg');
do
    rsvg-convert -f pdf -o "${file%.*}.pdf" "$file";
done;

exit 0
