#!/usr/bin/env python

import os
import sys


class Colors(object):
    HEADER = "\033[95m"
    SUCCESS = "\033[92m"
    SUCCESS_BLUE = "\033[94m"
    WARNING = "\033[93m"
    ERROR = "\033[91m"
    END = "\033[0m"
    BOLD = "\033[1m"
    UNDERLINE = "\033[4m"


def supports_color():
    plat = sys.platform
    supported_platform = plat != 'Pocket PC' and (plat != 'win32' or
                                                  'ANSICON' in os.environ)
    is_a_tty = hasattr(sys.stdout, 'isatty') and sys.stdout.isatty()
    if not supported_platform or not is_a_tty:
        return False
    return True


def ascii_format(message, *fmt):
    if not supports_color():
        return message

    ret = "".join(fmt)
    ret += message
    ret += Colors.END * len(fmt)
    return ret
