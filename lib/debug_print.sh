#!/bin/bash

# The number of colors the current shell supports
ncolors=$(tput colors)

# Greps and regexes line-by-line:
# grep.1: removes lines under the Underfull|Overfull diagnosic (the regex gets stuck on unknown characters for some reason) 
# -------
# awk.1:  removes Underfull/Overfull diagnostics, these will be ignored anyway
# awk.2:  removes lines that contain only numbers of nothing inside square brackets (with any amount of leading/trailing spaces or brackets)
# awk.3:  removes lines that contain a "comment" (line contains: '<', '(', '{') that is probably not useful. 
#         Useful comments are usually immediately followed up by an alphanumeric character. (This could produce false positives)
# awk.4:  This is somewhat project specific and an ugly trick, but this gets rid of an \includegrapic diagnostic
# awk.5:  Removes lines that only have a single bracket in them
# -------
# uniq: collapses multiple empty lines into one
pdflatex_normalize() {
    cat | 
    grep -v "^.*\\T1\/.*" | 
    awk '{ gsub("^(Underfull|Overfull).*$", "");
           gsub("^( *[<>{}()\\[\\]]* *[[][0-9]*[\\]]* *)*", "");
           gsub(".*[<({][/.>].*$", "");
           gsub("^<use.*$", "");
           gsub("^ *[<>{}()\\[\\]] *$", "");
           print}' |  
    uniq
}

# We can only use this function if we are on a terminal that supports colors, hence the if-else check
# awk.1:  Colors lines containing "Warning:" to yellow (up till the colon)
# awk.2:  Colors lines containing "Error" to red
# awk.3:  LaTeX usually signals errors with !, so we color these lines red as well
# awk.4:  Make the line number indicating the error bold
# awk.5:  Color the successully written output line to green
# awk.6:  Make the introductory text bold, so it's easier to distinguish between consequent runs
pdflatex_colorize() {
    if test -n "$ncolors" && test $ncolors -ge 8; then
        cat |
        awk '{gsub("^.*[Ww]arning:", "\033[1;33m&\033[0m");

              gsub("^.*[Ee]rror.*",  "\033[1;31m&\033[0m");
              gsub("^!.*$",          "\033[1;31m&\033[0m"); 
              gsub("^l.[0-9]{1,}.*$",   "\033[1m&\033[0m");

              gsub("[Oo]utput written.*[.]pdf.*", "\033[1;32m&\033[0m"); 

              gsub("^This is pdfTeX.*$", "\033[1m&\033[0m"); 
             
              print}'

    else 
        cat
    fi
}

# Prints green text
debug_success() {
    if test -n "$ncolors" && test $ncolors -ge 8; then
        >&2 echo -e "\e[1;32m$1 [SUCCESS]\e[0m"
    else
        >&2 echo $1 "[SUCCESS]"
    fi
}

# Prints yellow text
debug_warn() {
    if test -n "$ncolors" && test $ncolors -ge 8; then
        >&2 echo -e "\e[1;33m$1 [WARNING]\e[0m"
    else
        >&2 echo $1 "[WARNING]"
    fi
}

# Prints red text
debug_error() {
    if test -n "$ncolors" && test $ncolors -ge 8; then
        >&2 echo -e "\e[1;31m$1 [ERROR]\e[0m"
    else
        >&2 echo $1 "[ERROR]" 
    fi
}

# Prints bold text
debug_info() {
    if test -n "$ncolors" && test $ncolors -ge 8; then
        >&2 echo -e "\e[1m$1 [INFO]\e[0m"
    else
        >&2 echo $1 "[INFO]"
    fi
}

debug_separator() {
    local cols=$(tput cols)
    >&2 printf "%0.s=" $(seq 1 $cols)
    >&2 echo
}
