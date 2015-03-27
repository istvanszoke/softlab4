#!/usr/bin/env python


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


def ascii_format_regex(regex, text, *fmt):
    ret = text
    for match in regex.findall(text):
        ret = ret.replace(match, ascii_format(match, *fmt))
    return ret
