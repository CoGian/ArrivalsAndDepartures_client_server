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


##### Server : prompts for established connections or when a client leaves and if a thread is going to sleep or has taken/let a lock.

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

The implementation is quite simple all you need is to have a Concurrent Hashmap which is a shared data structure that I used to have concurrent actions.In the hashmap flights are saved and get hashed according their code. Although this is a trustworthy solution ,I have chosen to use ReadWrite locks additionally so I will be able to held some experiments, otherwise I should override builtin methods of Concurrent Hashmap.
I am using a hashmap with locks that has the same number of entries as Concurrent Hashmap has , every entry represents a flight and it has a key which is flight's code and a value which is a ReadWrite Lock this allows system to lock only one flight's information and not the whole Concurrent Hashmap .


##### 1st Experiment: 
A very slow writer alters a flight's info while three readers try to read the same flight(delay of writer is 30 sec)(delay of reader is 5 sec). 

On server's panel : 
```
Received request from /127.0.0.1
Received request from /127.0.0.1
Received request from /127.0.0.1
Received request from /127.0.0.1
A writer has taken write lock on LO2345 2019-04-25T22:48:05.531Z
A writer with write lock on (LO2345) is going to sleep 2019-04-25T22:48:05.531Z
A writer with write lock on (LO2345) has awaken 2019-04-25T22:48:35.532Z
A writer has let write lock on LO2345 2019-04-25T22:48:35.532Z
A reader has taken read lock on LO2345 2019-04-25T22:48:35.532Z
A reader with read lock on (LO2345) is going to sleep 2019-04-25T22:48:35.532Z
A reader has taken read lock on LO2345 2019-04-25T22:48:35.532Z
A reader has taken read lock on LO2345 2019-04-25T22:48:35.532Z
A reader with read lock on (LO2345) is going to sleep 2019-04-25T22:48:35.533Z
A reader with read lock on (LO2345) is going to sleep 2019-04-25T22:48:35.533Z
A reader with read lock on (LO2345) has awaken 2019-04-25T22:48:40.532Z
A reader has let read lock on LO2345 2019-04-25T22:48:40.532Z
A reader with read lock on (LO2345) has awaken 2019-04-25T22:48:40.533Z
A reader with read lock on (LO2345) has awaken 2019-04-25T22:48:40.533Z
A reader has let read lock on LO2345 2019-04-25T22:48:40.533Z
A reader has let read lock on LO2345 2019-04-25T22:48:40.533Z
```

On Client#1 (Writer)'s panel: 
```
Input> ALTER LO2345 5:46
Reply: WOKK received in 30.001 sec
```

On Client#2 (Reader1)'s panel: 
```
Input> READ LO2345
Reply: ROK LO2345 DEPARTURE 5:46 received in 29.298 sec
```

On Client#3 (Reader2)'s panel: 
```
Input> READ LO2345
Reply: ROK LO2345 DEPARTURE 5:46 received in 26.498 sec
```

On Client#4 (Reader2)'s panel: 
```
Input> READ LO2345
Reply: ROK LO2345 DEPARTURE 5:46 received in 27.553 sec
```

As you can see readers are waiting for writer to let write lock .Furthermore readers are taking almost simultaneously the read lock because it is not as strict as a write lock , so readers are running concurrently.

##### 2nd Experiment: 
A very slow reader reads a flight's info while three writers try to alter the same flight(delay of writer is 5 sec)(delay of reader is 30 sec).

On server's panel : 
```
Server is listening to port: 1398
Received request from /127.0.0.1
Received request from /127.0.0.1
Received request from /127.0.0.1
Received request from /127.0.0.1
A reader has taken read lock on LO2345 2019-04-25T22:57:12.831Z
A reader with read lock on (LO2345) is going to sleep 2019-04-25T22:57:12.831Z
A reader with read lock on (LO2345) has awaken 2019-04-25T22:57:42.832Z
A reader has let read lock on LO2345 2019-04-25T22:57:42.832Z
A writer has taken write lock on LO2345 2019-04-25T22:57:42.832Z
A writer with write lock on (LO2345) is going to sleep 2019-04-25T22:57:42.832Z
A writer with write lock on (LO2345) has awaken 2019-04-25T22:57:47.832Z
A writer has let write lock on LO2345 2019-04-25T22:57:47.832Z
A writer has taken write lock on LO2345 2019-04-25T22:57:47.832Z
A writer with write lock on (LO2345) is going to sleep 2019-04-25T22:57:47.832Z
A writer with write lock on (LO2345) has awaken 2019-04-25T22:57:52.833Z
A writer has let write lock on LO2345 2019-04-25T22:57:52.833Z
A writer has taken write lock on LO2345 2019-04-25T22:57:52.833Z
A writer with write lock on (LO2345) is going to sleep 2019-04-25T22:57:52.833Z
A writer with write lock on (LO2345) has awaken 2019-04-25T22:57:57.833Z
A writer has let write lock on LO2345 2019-04-25T22:57:57.833Z
```

On Client#1 (Reader)'s panel: 
```
Input> READ LO2345
Reply: ROK LO2345 DEPARTURE 03:15 received in 30.003 sec
```

On Client#2 (Writer1)'s panel: 
```
Input> ALTER LO2345 15:46
Reply: WOKK received in 34.106 sec
```

On Client#3 (Writer2)'s panel: 
```
Input> ALTER LO2345 15:34
Reply: WOKK received in 38.419 sec
```

On Client#4 (Writer3)'s panel: 
```
Input> ALTER LO2345 5:34
Reply: WOKK received in 42.459 sec
```

This time writers are waiting for reader to let read lock.After that writers are taking the write lock according when they came and they are waiting to finish that previous one who has the write lock .


##### 3rd Experiment : 
A very slow writer alters a flight's info while a reader tries to read and an other slow writer tries to alter the same flight(delay of writer is 30 sec)(delay of reader is 5 sec).

On server's panel : 
```
Server is listening to port: 1398
Received request from /127.0.0.1
Received request from /127.0.0.1
Received request from /127.0.0.1
A writer has taken write lock on LO2345 2019-04-25T23:15:23.398Z
A writer with write lock on (LO2345) is going to sleep 2019-04-25T23:15:23.398Z
A writer with write lock on (LO2345) has awaken 2019-04-25T23:15:53.398Z
A writer has let write lock on LO2345 2019-04-25T23:15:53.398Z
A reader has taken read lock on LO2345 2019-04-25T23:15:53.398Z
A reader with read lock on (LO2345) is going to sleep 2019-04-25T23:15:53.398Z
A reader with read lock on (LO2345) has awaken 2019-04-25T23:15:58.399Z
A reader has let read lock on LO2345 2019-04-25T23:15:58.399Z
A writer has taken write lock on LO2345 2019-04-25T23:15:58.399Z
A writer with write lock on (LO2345) is going to sleep 2019-04-25T23:15:58.399Z
A writer with write lock on (LO2345) has awaken 2019-04-25T23:16:28.399Z
A writer has let write lock on LO2345 2019-04-25T23:16:28.399Z
```

On Client#1 (Writer1)'s panel: 
```
Input> ALTER LO2345 15:46
Reply: WOKK received in 30.001 sec
```

On Client#2 (Reader)'s panel: 
```
Input> READ LO2345
Reply: ROK LO2345 DEPARTURE 15:46 received in 33.531 sec
```

On Client#3 (Writer2)'s panel: 
```
Input> ALTER LO2345 15:34
Reply: WOKK received in 62.361 sec
```

As you can see one more time reader is waiting for 1st write to finish and let write lock as 2nd writer is waiting for reader to finish and let his red lock.  

##### 4th Experiment:
A very slow writer alters a flight's info while a reader tries to read an other flight(delay of writer is 30 sec)(delay of reader is 5 sec).

On server's panel : 
```
Server is listening to port: 1398
Received request from /127.0.0.1
Received request from /127.0.0.1
A writer has taken write lock on LO2345 2019-04-25T23:27:36.720Z
A writer with write lock on (LO2345) is going to sleep 2019-04-25T23:27:36.720Z
A reader has taken read lock on XU1800 2019-04-25T23:27:37.904Z
A reader with read lock on (XU1800) is going to sleep 2019-04-25T23:27:37.904Z
A reader with read lock on (XU1800) has awaken 2019-04-25T23:27:42.904Z
A reader has let read lock on XU1800 2019-04-25T23:27:42.904Z
A writer with write lock on (LO2345) has awaken 2019-04-25T23:28:06.721Z
A writer has let write lock on LO2345 2019-04-25T23:28:06.721Z
```

On Client#1 (Writer)'s panel: 
```
Input> ALTER LO2345 15:46
Reply: WOKK received in 30.001 sec
```

On Client#2 (Reader)'s panel: 
```
Input> READ XU1800
Reply: ROK XU1800 ARRIVAL 13:15 received in 5.0 sec
```

This time reader is not waiting for writer to finish and let his write lock because reader doesn't want his lock ,he wants the read lock of an other flight and he gets it.

