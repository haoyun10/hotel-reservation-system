rem Copyright(C) 2007 National Institute of Informatics, All rights reserved.
java -classpath ..\lib\hsqldb.jar org.hsqldb.util.DatabaseManager -url jdbc:hsqldb:hsql://localhost
cd ..
cd lib
java -cp hsqldb.jar org.hsqldb.util.DatabaseManager