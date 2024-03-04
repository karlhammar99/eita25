##Project 2

    #To run in vscode:
        Choose run configuration -> "Run Server and Client"

    #User instructions

        A doctor can write:
            read patient1 recordIndex
            create patient1 nurse1 <And write some data>
            write patient1 recordIndex <And write some data>
        
        A nurse can write:
            read patient1 recordIndex
            write patient1 recordIndex
        
        A patient can write:
            read recordIndex

        A government organization can write:
            delete patient1 recordIndex
            read patient1 recordIndex