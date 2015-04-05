#!/usr/bin/env python

from __future__ import print_function

import os


def file_list(path, *extensions):
    return [os.path.join(root, filename)
            for root, _, filenames in os.walk(path)
            for filename in filenames if filename.endswith(extensions)]


def delete_files(path, *extensions):
    for file in file_list(path, *extensions):
        os.unlink(file)
