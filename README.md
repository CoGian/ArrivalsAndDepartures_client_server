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


##### Server : prompts for connections,orders and if a thread is going to sleep or has taken a lock.

##### Writer Client : can write a new flight,alter or delete an existing flight.
   To write type : ``` WRITE <Flight's code> <State> <Time>  ```
   
   Example : ``` WRITE XU1800 ARRIVAL 15:34  ```  
        
   To alter type : ``` ALTER <Flight's code> <new_State> or/and <new_time>  ```
   
   Example : ``` ALTER XU1800 15:34  ```  *this alters flight's time 
   
   To delete type : ```DELETE <Flight's code>```
   
   Example : ``` DELETE XU1800```  

##### Reader Client : can read an existing flight.
   To read type :   ```READ <Flight's code>```
     
   Example : ``` READ XU1800 ```  
   
### Implementation & Experiments

The implementation is quite simple all you need is to have a concurrent hashmap which is a shared data structure
 that I used to have concurrent actions.In the hashmap flights are saved and get hashed according their code. 
 Although this is a trustworthy solution ,I have chosen to use ReadWrite locks additionally so I will be able to held some experiments , 
 otherwise I should override builtin methods of Concurrent Hashmap .    

