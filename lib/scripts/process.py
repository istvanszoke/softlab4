#!/usr/bin/env python

from __future__ import print_function

import shlex
import subprocess
import sys

from lib.scripts import debug


def run_or_die(command, cwd, encoding="utf-8",
               output_function=lambda out: print(out),
               error_message=None):
    try:
        result = subprocess.check_output(shlex.split(command), cwd=cwd).decode(encoding)
        output_function(result)
        return result

    except subprocess.CalledProcessError as error:
        result = error.output.decode(encoding)
        output_function(result)
        if error_message is not None:
            debug.error(error_message)
        sys.exit(error.returncode)
