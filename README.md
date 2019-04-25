# ArrivalsAndDepartures_client_server

Demonstrates the use of multithreading , ReadWrite Locks and Sockets to help writers and readers to write , alter , delete and read a flight's info from a server using TCP connection.  

### Usage 

To start server type in cmd : 
``` 
java MultithreadedServerTCP 
```

To start a client writer type in cmd : 
```
java WriterClientTCP
```

To start a client reader type in cmd : 
```
java ReaderClientTCP
```


###### Server : prompts for connections,orders and if a thread is going to sleep or has taken a lock.
###### Writer Client : can write a new flight,alter or delete an existing flight.
   To write type : ``` WRITE <Flight's code> <State> <Time>  ```
        
   To alter type : ``` ALTER <Flight's code> <new_State> <new_time>  ```
   
   To delete type : ```DELETE <Flight's code>```