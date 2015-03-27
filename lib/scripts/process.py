#!/usr/bin/env python

import shlex
import sys
from subprocess import Popen, PIPE

from lib.scripts import debug


class Result(object):
    def __init__(self, exit_code, output, error):
        self.exit_code = exit_code
        self.output = output
        self.error = error


def run(command, cwd, encoding="utf-8", output_parser=lambda out: out):
    process = Popen(shlex.split(command), cwd=cwd, stdout=PIPE)
    (output, error) = process.communicate()
    exit_code = process.wait()
    if output is not None:
        output = output_parser(output.decode(encoding))
    if error is not None:
        error = output_parser(error.decode(encoding))
    return Result(exit_code, output, error)


def terminate_on_failure(process_result, error_message=""):
    if process_result.exit_code == 0:
        return

    if error_message != "":
        debug.error(error_message)

    sys.exit(process_result.exit_code)
