# MSSLTypeSystem
* Summary
__________
Synchronous Reactive Languages are an excellent choice for IoT programming due to their clear system-environment interaction semantics. However, when it comes to safety- critial/resource-constrained systems, recent proposals like Fairthreads or ReactiveML face a well-known issue. Manual memory management (as in Fairthreads) can lead to errors, while garbage collection (as in ReactiveML) ensures memory safety but at the cost of an execution overhead. We revisit FairThreads by introducing a Rust-like type system, thus ensuring memory safety without execution overhead. Our proposal introduces a new type of smart pointers for data sharing. These pointers can be safely accessed without the need to resort to a locking discipline and are therefore well- suited to the Fairthreads’ cooperative scheduling

* An outline of the syntax used in the source level of MSSL.
_________________________________
1. program:  ` functions* block EOF ` 
2. block: ` { exp } `
3. exp: ` v | n | expr.n | lval.clone | &[mut] lval | box(exp) | trc(exp) `
       ` (exp,exp,*) | spawn(f(exp)) | exp* | exp (==) exp | 
          if(cond) block else block | cooperate | Sig s | emit(s) | when(s) block | watch(s) block | print!(lval)  `      
4. lval: ` x | *x `

* Testing
__________________
1. Build the project (i.e. gradle)
2. execute the following command into the terminal:
     - ` ./gradlew --quiet run  --args "path to the directory" `
     - For example: ` ./gradlew --quiet run  --args "src/main/ExampleMSSL/TrcMove/strongUpdate.mssl" `
3. According to the main file :
   - the type system statically checks the program given on the command line, applying the typing rules
   - then an operational check, applying the semantic rules
   - If everything is OK, a translation into C will be performed automatically.
4. The memory management in C is done automatically

* Exection of the code in C using Fairthreads library
----------------------------------------------------
1. The MSSL to C translation code is automatically generated in the ` src/main/MSSLtoC/* `directory (specified in the Main file, can be modified manually)
2. In ` src/main/MSSLtoC/* ` directory, there are the Fairthread library and the Trc implementation in C
3. To run the code :
   - make install the library ( in FT-Trc Library directory there is a readme)
   - and then: 
     - ` gcc -Wall -O3 -D_REENTRANT -c code.c -I ./FT-TrcLibrary/include/ -L ./FT-TrcLibrary/lib/  -lfthread -lpthread `
     - ` gcc -Wall -O3 -D_REENTRANT code.o -o code -I ./FT-TrcLibrary/include -L ./FT-TrcLibrary/lib  -lfthread -lpthread `
     -  `  ./code`
* Currently, the majority of examples on GitHub to test our contribution are centered around "Trc" instead of threaded scenarios. However, we have ongoing plans to consistently introduce new examples in the future.