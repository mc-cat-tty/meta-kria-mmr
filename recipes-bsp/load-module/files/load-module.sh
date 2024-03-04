#!/bin/sh

for MODULE in @@MODULE_NAMES@@ ;do
    MODULE_PATH=$(find /lib/modules/ -name ${MODULE})	
    if [ -e "${MODULE_PATH}" ] ; then
        echo "Loading module ${MODULE}"
	insmod ${MODULE_PATH}
    else
        echo "ERROR:${MODULE} not present"
    fi
done
