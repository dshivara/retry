# retry
retry-module implementation

Implementation of `retry-module` using producer-consumer model, and a dedicated worker for handling failed tasks. 
The failed tasks worker uses a delay queue for handling backoffs.
