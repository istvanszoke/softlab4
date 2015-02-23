#!/bin/bash

ncolors=$(tput colors)

function pdflatex_normalize() {
    if test -n "$ncolors" && test $ncolors -ge 8; then
        cat | 
        grep -v "^.*\\T1/" | 
        grep -v ".*\[\] \[\].*" | 
        awk '{ gsub("^(Underfull|Overfull).*$", "");
               gsub("^[[].*[]].*$", "");
               gsub("^[[].*$", "");
               gsub("^.*[(][/.].*$", "");
               gsub("[)][)].*$", "");

               gsub(".*[<{][./>].*$", "");    

               gsub("<use.*", "");
               gsub("^[ ]*[>})]$", "");

               gsub("^.*[Ww]arning:", "\033[1;33m&\033[0m");

               gsub("^.*[Ee]rror.*",  "\033[1;31m&\033[0m");
               gsub("^!.*$",          "\033[1;31m&\033[0m"); 
               gsub("^l.[0-9]{1,}.*$",   "\033[1m&\033[0m");

               gsub("[Oo]utput written.*[.]pdf.*", "\033[1;32m&\033[0m"); 

               gsub("^This is pdfTeX.*$", "\033[1m&\033[0m"); print}' | 
        uniq
    else 
        cat
    fi
}

function debug_success() {
    if test -n "$ncolors" && test $ncolors -ge 8; then
        echo -e "\e[1;32m$1\e[0m"
    else
        echo $1
    fi
}

function debug_warn() {
    if test -n "$ncolors" && test $ncolors -ge 8; then
        echo -e "\e[1;33m$1\e[0m"
    else
        echo $1
    fi
}

function debug_error() {
    if test -n "$ncolors" && test $ncolors -ge 8; then
        echo -e "\e[1;31m$1\e[0m"
    else
        echo $1
    fi
}
