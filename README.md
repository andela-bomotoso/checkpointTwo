# checkpointTwo
## Git Repository for checkpoint2

This project uses the concept of Multithreading to read a file using a parserthread that gets data from a Parser. A Dbwriter thread loads the database with data gotten from the parser thread using a DBWriter.  

###**Description of Packages and Classes used**

**Db Package** 
The db package contains clases that manage activities related to a database. It has the following classes;

Database Manager: This class manages connection to the database, creation of database, table creation and record insertion.
DbWriter: This class manages data insertion into the database table.

**Parser Package** 
The parser package contains clases that manage file parsing activities. It has the following classes;

AttributeValue: This class depicts the structure of an attribute value file like the reactant file used in this project.
ReactantFile: This class depicts a Reactant File
FileParser: This class handles the file parsing activity

**Thread Package**
The thread package contains classes that manage thread activities. It has the following classes;

FileParserThread: This class depicts how a thread can read an attribute value file
DbWriterThread: This class depicts how a thread can write from a buffer to a database
LogWriterThread: This class depicts how FileParserThread and DbWriterThread activities can be logged.
ThreadLogger: This class tracks the activities of the DbWriterThread and FileParserThread that is used by the LogWriter
Main: Used for running the FileParserThread, DbWriterThread and LogWriterThread.

**Buffer**
The Buffer package manages the Buffer classes and Interface
Buffer: This is an Interface that every buffer implements
DataBuffer:This is a shared location accessible to the FileParserThread and the DbWriterThread. The FileParser writes to the buffer and the Dbwriter reads from the buffer before writing into the database
LogBuffer: This is a shared loation between the FileParseThread, DbWriterThread and the LogWriterThread. FileParserThread and DbWriterThread write into the log buffer and the LogWriterThread reads from the logbuffer and writes to a text file
