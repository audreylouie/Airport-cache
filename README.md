# Airport-cache
Simulation of page replacement algorithms for FIFO and LRU
We will use airport.csv file as a file backing up the cache, and simulate the situation where a user searches the information about an airport using its three-letter code (e.g., JFK, LGA, etc.)

# Methods - Interface Cache
get - The method takes 3-letter airport code as an input and returns Airport object if the object is found in the cache. Being retrieved by get method is considered as being used.

add - The method adds the mapping between 3-letter airport code and a corresponding Airport object to the cache. Being inserted by add method is considered as being used. Note that the cache could contain the limited number of records; if the cache is full, a record must be evicted before a new record is added.
