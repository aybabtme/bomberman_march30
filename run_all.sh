#!/bin/sh
cd javaai
sh run.sh 40001&
cd ..

cd bomberman-erlang
sh run.sh 40002&
cd ..

cd bomberman_ai
sh run.sh 40003&
cd ..
