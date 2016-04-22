#!/bin/sh
#
#Sample syntax
#
#./execute.sh "select * Where ID > 1 Order by Salary DESC" ./src/main/resources/employees.txt
#

java -cp ./database.jar com.kavi.database.Main "$1" "$2"