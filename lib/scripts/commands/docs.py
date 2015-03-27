#!/usr/bin/env python

from __future__ import print_function

import re

from lib.scripts import debug, dir, format, process, util
from lib.scripts.format import Colors


def strip(text, *regexes):
    for regex in regexes:
        text = regex.sub("", text)

    return text


def normalize(output):
    full = re.compile(r"^(Under|Over)full.*$", re.M)
    parens = re.compile(r"^[[)][0-9]*[]]*.*$", re.M)
    sysincludes = re.compile(r"^.*[(<{][/\(]+.*$", re.M)
    local_includes = re.compile(r"^[(]+[./)]+.*$", re.M)
    t1 = re.compile(r"^\\T1/ptm.*$", re.M)
    angle_brackets = re.compile(r"^.*[<>]+.*$", re.M)
    newlines = re.compile(r" *\n\n+")

    ret = strip(output, full, sysincludes, local_includes, parens, t1, angle_brackets)
    return newlines.sub("\n\n", ret)


def colorize(output):
    intro = re.compile(r"^.*this is pdftex.*$", re.M | re.I)
    warning = re.compile(r"^.*warning:.*$", re.M | re.I)
    error = re.compile(r"^.*error.*$", re.M | re.I)
    success = re.compile(r"^.*written.*pdf.*$", re.M | re.I)

    ret = intro.sub(format.ascii_format(intro.match(output).group(), Colors.BOLD), output)
    ret = format.ascii_format_regex(warning, ret, Colors.WARNING)
    ret = format.ascii_format_regex(error, ret, Colors.ERROR)
    ret = format.ascii_format_regex(success, ret, Colors.SUCCESS)

    return ret


def generate():
    debug.separator("Generating Documentation")
    result = process.run("pdflatex -halt-on-error szoftlab4.tex",
                         cwd=dir.DOCS,
                         encoding="windows-1252",
                         output_parser=lambda out: normalize(colorize(out)))
    print(result.output)
    process.terminate_on_failure(result, error_message="Documentation generation failed (pdflatex error)")


def build():
    generate()
    generate()
    generate()
    util.delete_files(dir.DOCS, ".aux", ".lof", ".log", ".out", ".toc")
    debug.success("Documentation generation")
