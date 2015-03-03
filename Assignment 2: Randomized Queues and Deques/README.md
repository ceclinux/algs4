Compilation:  PASSED
Style:        PASSED
Findbugs:     No potential bugs found.
API:          PASSED

Correctness:  35/35 tests passed
Memory:       51/51 tests passed
Timing:       43/43 tests passed

Aggregate score: 100.00% [Correctness: 65%, Memory: 10%, Timing: 25%, Style: 0%]

##Notice

This solution failed one normal memory test for `Deque.java`


>Test 7: Worst-case constant memory allocated or deallocated
>        per deque operation?
>  *  T = 128 random operations
>      -  failed on trial 26 of 128
>      -  when current size of Deque was 16 objects;
>      -  the call to addFirst()
    >      -  caused a change in memory of 128 bytes
    >      -  any change of more than 96 bytes fails the test
    >  *  T = 256 random operations
    >      -  failed on trial 22 of 256
    >      -  when current size of Deque was 16 objects;
>      -  the call to addLast()
    >      -  caused a change in memory of 128 bytes
    >      -  any change of more than 96 bytes fails the test
    >  *  T = 512 random operations
    >      -  failed on trial 26 of 512
    >      -  when current size of Deque was 16 objects;
>      -  the call to addFirst()
    >      -  caused a change in memory of 128 bytes
    >      -  any change of more than 96 bytes fails the test
    >==> FAILED

    Because my `Deque.java` is implemented by array, when enlarge the array, it is unavoidable to allocate a lot of memory to the new larger array.
    This is kind of trade-off, generally, the memory usage is a quarter of the node implementation. In addition, the array implementation is also faster than Node implementation.

    The Aggregate score still sums up to 100 because of the bonus.

    >Test 3 (bonus): Check that maximum size of any or Deque or RandomizedQueue object
                     >                created is <= k
                     >  * filename = tale.txt, N = 138653, k = 5
                     >  * filename = tale.txt, N = 138653, k = 50
                     >  * filename = tale.txt, N = 138653, k = 500
                     >  * filename = tale.txt, N = 138653, k = 5000
                     >  * filename = tale.txt, N = 138653, k = 50000
                     >==> passed

                     The hint is Reservoir sampling.
