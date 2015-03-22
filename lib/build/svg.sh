#!/bin/bash

source $LIB_DIR/debug_print.sh

force="$1"

# We default to rsvg-convert, since this is by far the most lightweight alterative for the conversion
command -v "rsvg-convert" &> /dev/null
if [ $? -eq 0 ]; then
    echo 'SVG preprocess opted for rsvg-convert';
    for file in $(find "$DOCS_DIR" -name '*.svg');
    do
        if [ ! -f "${file%.*}.pdf" ] || [ "$force" == "force" ]; then
            rsvg-convert -f pdf -o "${file%.*}.pdf" "$file"
        fi
    done
    
    exit $?
fi

# Inkscape is fairly popular and produces the same output as rsvg-convert (vector graphics PDF), so 
# it is a sane second choice
command -v "inkscape" &> /dev/null
if [ $? -eq 0 ]; then
    echo 'SVG preprocess opted for Inkscape';
    for file in $(find "$DOCS_DIR" -name '*.svg');
    do
        if [ ! -f "${file%.*}.pdf" ] || [ "$force" == "force" ]; then
            inkscape --export-pdf="${file%.*}.pdf" --export-ignore-filters --export-area-drawing "$file"
        fi
    done
    
    exit $?
fi

# Convert (part of ImageMagick) is widely installed on *nix machines, unfortunately it rasterizes the image.
# Since this will result in a loss of quality, it's reasonable to ask for confirmation. 
command -v "convert" &> /dev/null
if [ $? -eq 0 ]; then
    echo 'SVG preprocess opted for ImageMagick';
    debug_warn "Only rasterizing SVG to PDF converter found. This will reduce the image quality."
    read -p "Continue? [Y/n] " -n 1 -r
    echo # New line
    if [[ $REPLY =~ ^[Yy]$ ]]; then
        for file in $(find "$DOCS_DIR" -name '*.svg');
        do
            if [ ! -f "${file%.*}.pdf" ] || [ "$force" == "force" ]; then
                convert "$file" "${file%.*}.pdf" 
            fi
        done
        
        exit $?
    fi
fi

exit -1

