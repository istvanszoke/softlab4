#!/usr/bin/env python

from __future__ import print_function

import shlex
import sys

from subprocess import Popen, PIPE

from lib.scripts import debug


def run_or_die(command, cwd, encoding="utf-8",
               output_function=lambda out: print(out),
               error_message=None):
    split_command = shlex.split(command)
    try:
        process = Popen(split_command, cwd=cwd, stdout=PIPE, stderr=None)
        (out, _) = process.communicate()
        exit_code = process.wait()

        result = out.decode(encoding)
        output_function(result)

        if exit_code != 0:
            if error_message is not None:
                debug.error(error_message)
            sys.exit(exit_code)

        return result

    except OSError:
        debug.error("{0} could not be found on this machine".format(split_command[0]))
        sys.exit(1)
