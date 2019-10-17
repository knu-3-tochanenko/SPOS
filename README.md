SystemProgramming

# Lab 1 : Tasks synchronization and parallelization 

Task: [Google Drive](https://drive.google.com/open?id=0B-BUpwNPP_9JeDhKYXZudXZ3U0E)

**Variant: 9**\
Use Java, processes and process exit statud for result of function computation.

Done using ProcessBuilder and Thread. Cancellation via periodic user prompt.

### Notices:

1. You can't use KeyListener in console.\
Source: [StackOverflow](https://stackoverflow.com/questions/4005574/java-key-listener-in-commandline)
2. You can't listen for a key and prompt parameter as Scanner is blocking input.\
I was using separate thread with Scanner to listen for new line with 'q' symbol. Unfortunately, the only way to kill thread is to use flag to check if thread should be running or not. Scanner is blocking input, so sleeping is pointless, as it will be blocked by awaiting Scanner.