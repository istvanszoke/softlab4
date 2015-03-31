#!/usr/bin/env python

from __future__ import print_function

import struct
import sys

from lib.scripts import format


def get_terminal_size():
    import platform
    current_os = platform.system()

    if current_os == "Windows":
        return _get_terminal_size_windows()
    else:
        return _get_terminal_size_linux()


def _get_terminal_size_windows():
    try:
        from ctypes import windll, create_string_buffer
        h = windll.kernel32.GetStdHandle(-12)
        str_buffer = create_string_buffer(22)
        res = windll.kernel32.GetConsoleScreenBufferInfo(h, str_buffer)
    except OSError:
        return 80, 25

    if res is not None:
        (_, _, _, _, _,
         left, top, right, bottom, _, _) = struct.unpack("hhhhHhhhhhh", str_buffer.raw)
        size_x = right - left + 1
        size_y = bottom - top + 1
        return size_x, size_y
    else:
        return 80, 25


def _get_terminal_size_linux():
    import fcntl
    import termios
    try:
        h, w, _, _ = struct.unpack('HHHH',
                                   fcntl.ioctl(sys.stdout.fileno(),
                                               termios.TIOCGWINSZ,
                                               struct.pack('HHHH', 0, 0, 0, 0)))
        return w, h
    except OSError:
        return 80, 25


def info(message):
    print(format.ascii_format(message + " [INFO]", format.Colors.BOLD))


def success(message):
    print(format.ascii_format(message + " [SUCCESS]", format.Colors.SUCCESS))


def warning(message):
    print(format.ascii_format(message + " [WARNING]", format.Colors.WARNING))


def error(message):
    print(format.ascii_format(message + " [ERROR]", format.Colors.ERROR))


def separator(message=""):
    (columns, _) = get_terminal_size()
    print(format.ascii_format(message.center(columns, '='), format.Colors.BOLD))
