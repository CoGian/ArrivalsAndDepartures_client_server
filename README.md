# ArrivalsAndDepartures_client_server

Simultaneous Read and Write operations by Multiple Threads on same or different segments of ConcurrentHashMap
Read/Get Operation :- Two Threads T1 and T2 can read data from same or different segment of ConcurrentHashMap at the same time without blocking each other.

Write/Put/Remove Operation :- Two Threads T1 and T2 can write data on different segment at the same time without blocking the other.

But Two threads can’t write data on same segments at the same time. One has to wait for other to complete the operation.

Read-Write Operation :- Two threads can read and write data on different segments at the same time without blocking each other. 


In general, Retrieval operations do not block, so may overlap with write (put/remove) operations.
 Latest updated value will be returned by get operation which is most recently updated value by write operation (including put/remove).