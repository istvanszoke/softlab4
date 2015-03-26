#!/usr/bin/env python

from __future__ import print_function

import fnmatch
import os

BUILD_SCRIPTS = os.path.dirname(__file__)
TOP_DIR = os.path.abspath(os.path.join(BUILD_SCRIPTS, os.pardir))
DOCS_DIR = os.path.join(TOP_DIR, "docs")
LIB_DIR = os.path.join(TOP_DIR, "lib")
SRC_DIR = os.path.join(TOP_DIR, "src")


class Colors(object):
    HEADER = "\033[95m"
    SUCCESS = "\033[92m"
    SUCCESS_BLUE = "\033[94m"
    WARNING = "\033[93m"
    ERROR = "\033[91m"
    END = "\033[0m"
    BOLD = "\033[1m"
    UNDERLINE = "\033[4m"


def ascii_format(message, *fmt):
    ret = ""
    for f in fmt:
        ret += f
    ret += message
    ret += Colors.END * len(fmt)
    return ret


def format_all(regex, text, *fmt):
    ret = text
    for match in regex.findall(text):
        ret = ret.replace(match, ascii_format(match, *fmt))
    return ret


def file_list(path, file_filter="*"):
    files = []
    for root, dirnames, filenames in os.walk(path):
        for filename in fnmatch.filter(filenames, file_filter):
            files.append(os.path.join(root, filename))
    return files


def delete_files(path, *filters):
    for f in file_list(path):
        for t in filters:
            if f.find(t) != -1:
                if os.path.exists(f):
                    os.unlink(f)