#!/bin/sh

 base_dir=$(dirname $0)/..
 CLASSPATH=$(echo $base_dir/target/*.jar $base_dir/plugins/*.jar $base_dir/lib/*.jar| tr " " :)

 # OTHER PARAMETERS
 USER_ARGS="$@"
 JVM_ARGS="-Xmx1024m"
 EXTERNAL_LIBS="/usr/local/lib"
 MAIN_CLASS="com.mungolab.TestInternetAvability.Main"

 LOGCONF="${base_dir}/src/main/resources/log4j.properties"

 # RUN APPLICATION
 echo "Starting: java -server -cp $CLASSPATH -Djava.library.path=${EXTERNAL_LIBS} ${JVM_ARGS} ${MAIN_CLASS} ${USER_ARGS}"
 java -cp $CLASSPATH -Djava.library.path=${EXTERNAL_LIBS} -Dlog4j.configuration=file:${LOGCONF} ${JVM_ARGS} ${MAIN_CLASS} ${USER_ARGS}