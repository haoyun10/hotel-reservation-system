# hotel-reservation-system
Database:
    server mode: dev_program_DB/mydb/runManager.sh
    database UI: dev_program_DB/mydb/runManager.sh
SystemUI: Waseda-SE_2/src/app/cui/CUI.java

Todo:
- [X] Waseda-SE_2/src/app/checkout/CheckOutRoomControl.java
  - [X] Clear room
  - [X] Consume payment
- [X] Waseda-SE_2/src/app/checkout/CheckOutRoomForm.java 
  - [X] CheckOut()

Way to run this system:
    (open database)
    cd dev_program_DB/mydb/ \
    java -classpath ../lib/hsqldb.jar org.hsqldb.Server -database mydb\
    cd ..
    cd lib
    java -classpath hsqldb.jar org.hsqldb.util.DatabaseManager

    (run CUI)
    java -cp "Waseda-SE/bin:Waseda-SE/lib/hsqldb.jar" app.cui.CUI