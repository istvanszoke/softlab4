#!/bin/bash

source "$LIB_DIR/debug_print.sh"

cd "$TOP_DIR"

# Get a description for the file with the given path (passed as the 2nd argument). This is either 
# the first line of the class JavaDoc comment (very crudely parsed), or a generic description in the veins of:
# This file containts the implementation/declaration of <class>/<interface>
get_description() {
    # Parse the first line of the class JavaDoc comment. Very crude solution, relies on perfect formatting on the 
    # side of the developers.
    # TODO: More robust JavaDoc parsing
    local description=$(cat "$2" | grep "^ \* .*$" | head -n 1 | cut -c4-)

    # If we couldn't parse the JavaDoc comment then generate the generic description
    if [ -z "$description" ]; then
        # We get the class name from the filename, since Java has a 1:1 relation with them (except for the extension)
        class="${1%.*}"

        # The Hungarian equivalent of the a / an destinction. Luckily, it's much easier to figure out which one to use.
        pre="A"
        if echo "$class" | grep "^[AaEeIiOoUu].*" > /dev/null; then
            pre="Az"
        fi

        # Class / Interface destinction. 
        description="$pre $class osztály implementációját tartalmazza."
        if cat "$2" | grep "^.*public interface.*$" > /dev/null; then
            description="$pre $class interfész deklarációját tartalmazza."
        fi
    fi

    echo "$description"
}

# Generates the full file list in LaTeX format and prints it to stdout
generate_file_list() {
    # The basic LaTeX table structure that is used for the file listing.
    echo "\\begin{tabularx}{\linewidth}{| l | l | l | X |}"
    echo "\\hline"
    echo "\\textbf{Fájl neve} & \\textbf{Méret} & \\textbf{Keletkezés ideje} & \\textbf{Tartalom} \\tabularnewline"
    echo "\\hline \\hline"
    echo "\\endhead"
    
    # Get all Java source files relative to the root directory and store their information in the following format:
    # <filename>;<path relative to root>;<size>\n
    # The results are sorted in alphabetical increasing order based on their full path. Also, the ./ prefix at the 
    # beginning of every path is removed
    files=$(find -type f -name *.java -printf '%f;%p;%s\n' | sed s/\\.\\///g | sort -t';' -k2)
    
    printf "%s" "$files" |
    while IFS= read -r line;
    do
        # Split the line on the ; separators and assign variables according to the order specified by the find command 
        # that assigns the value to our $files variable
        IFS=';' read name path size < <(echo "$line")
        if [ -z "$name" ] || [ -z "$path" ] || [ -z "$size" ]; then
            debug_error "File list generation ended (failed to split file information)"
            exit -1
        fi
    
        # Get the creation date directly from git. This should work regardless of renames/moves. Also make the formatting
        # compatibile with the file listing format provided by the LaTeX template we were given
        date=$(git log --diff-filter=A --follow --format=%ai -1 -- "$path" | awk '{gsub(" \\+[0-9]+.*$", ""); gsub(" ", "~"); gsub(":[0-9]+$", "~"); gsub("-", "."); print}')
        if [ -z "$date" ]; then
            debug_error "File list generation ended (failed to get creation time)"
            exit -1
        fi
    
        # Get the file description. This is either the first line of the JavaDoc comment or a generic description
        description=$(get_description "$name" "$path")
        if [ -z "$description" ]; then
            debug_error "File list generation ended (failed to generate description)"
            exit -1
        fi


        echo "\\fajl"
        printf "{%s}\n{%d byte}\n{%s}\n{%s}\n\n" "$path" "$size" "$date" "$description"
    done
    
    # The end of the LaTeX table
    echo "\\end{tabularx}"
}

debug_info "File list generation started"

generate_file_list > "$DOCS_DIR"/includes/file_list.tex

if [ $? -ne 0 ]; then
    debug_error "File list generation ended"
    exit -1
fi

debug_success "File list generation ended"

