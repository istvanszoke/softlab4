#!/bin/bash

# We default to rsvg-convert, since this is by far the most lightweight alterative for the conversion
command -v "rsvg-convert" &> /dev/null
if [ $? -eq 0 ]; then
    for file in $(find "$DOCS_DIR" -name '*.svg');
    do
        rsvg-convert -f pdf -o "${file%.*}.pdf" "$file"
    done
    
    exit $?
fi

# Inkscape is fairly popular and produces the same output as rsvg-convert (vector graphics PDF), so 
# it is a sane second choice
command -v "inkscape" &> /dev/null
if [ $? -eq 0 ]; then
    for file in $(find "$DOCS_DIR" -name '*.svg');
    do
        inkscape --export-pdf="${file%.*}.pdf" --export-ignore-filters --export-area-drawing "$file"
    done
    
    exit $?
fi

# Convert (part of ImageMagick) is widely installed on *nix machines, unfortunately it rasterizes the image.
# Since this will result in a loss of quality, it's reasonable to ask for confirmation. 
command -v "convert" &> /dev/null
if [ $? -eq 0 ]; then
    read -p "Only rasterizing SVG to PDF converter found. This will reduce the image quality. Continue? [Y/n] " -n 1 -r
    echo # New line
    if [[ $REPLY =~ ^[Yy]$ ]]; then
        for file in $(find "$DOCS_DIR" -name '*.svg');
        do
            convert "$file" "${file%.*}.pdf" 
        done
        
        exit $?
    fi
fi

# Signal the error if we couldn't convert the SVG files
echo "No suitable SVG to PDF converter found."
exit -1

