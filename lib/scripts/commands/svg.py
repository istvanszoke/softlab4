#!/usr/bin/env python

from __future__ import print_function

import multiprocessing
import os
import os.path
import sys

from distutils.spawn import find_executable
from multiprocessing import Pool

from lib.scripts import debug, dir, process, util


MESSAGE = "SVG conversion"


converters = [("rsvg-convert",
               "rsvg-convert -f pdf -o '{0}.pdf' '{0}.svg'"),

              ("inkscape",
               "inkscape --export-pdf='{0}.pdf' --export-ignore-filters --export-area-drawing '{0}.svg'"),

              ("convert",
               "convert '{0}.svg' '{0}.pdf'")]


def choose_converter():
    for converter in converters:
        if find_executable(converter[0]) is not None:
            return converter[1]
    debug.error("Failed to convert SVG files to PDF (no suitable converter found)")
    sys.exit(1)


def convert(converter, file, base_path):
    without_extension = os.extsep.join(file.split(os.extsep)[:-1])
    try:
        svg_modification = os.path.getmtime(file)
        pdf_modification = os.path.getmtime(without_extension + os.extsep + "pdf")
        if svg_modification < pdf_modification:
            return
    except os.error:
        pass

    process.run_or_die(converter.format(without_extension),
                       cwd=base_path,
                       output_function=lambda _: _,
                       error_message="SVG to PDF conversion failed (converter returned with an error)")


def convert_all():
    converter = choose_converter()
    files = util.file_list(dir.DOCS, ".svg")

    pool = Pool(processes=multiprocessing.cpu_count())
    [pool.apply_async(convert, args=(converter, file, dir.DOCS)) for file in files]
    pool.close()
    pool.join()


def build():
    convert_all()
