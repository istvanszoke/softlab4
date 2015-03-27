#!/usr/bin/env python

from __future__ import print_function

import os


def has_extension(file, *extensions):
    for extension in extensions:
        if file.endswith(extension):
            return True

    return False


def file_list(path, *extensions):
    files = []
    for root, _, filenames in os.walk(path):
            for filename in filenames:
                if has_extension(filename, *extensions):
                    files.append(os.path.join(root, filename))
    return files


def delete_files(path, *extensions):
    for f in file_list(path, *extensions):
        if os.path.exists(f):
            os.unlink(f)