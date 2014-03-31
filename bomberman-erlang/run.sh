#!/bin/sh
export ERL_LIBS=./erlibs/
make bomberman.beam
escript my_ai.erl $1

