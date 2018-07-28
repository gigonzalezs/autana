# Welcome to autana 
## the Business Process automator

![cerro autana, Amazonas, Venezuela](https://i.pinimg.com/originals/3a/4a/1b/3a4a1b279bb9f23cba3a00d85e1674b3.jpg)


## About autana

Autana is a declarative framework for Java that allows the implementation of complex business processes with the ability to recover from execution interruptions

## Main Components

### Compositions

Compositions are the result of a declarative structure of chained containers. Compositions can be executed by a **Director** with a given **payload**.

### Containers

 - **Sequences**: Has a list of java steps o nested containers to be executed on sequence.
 -  **Parallel**: Has a list of java steps o nested containers to be executed on parallel.
 -  **Yaw**: a Yaw is a switch, a **condition**, a list of steps to be executed only if the evaluation of the predicate is true
  - **Loop**: a list of steps to be executed **while** if the evaluation of the predicate is true

### Payload

It's a data package with a **request** to be send to the **composition** and a response extracted from the same composition at runtime.

### Director

It's the runtime, the composition executor

### Composers 

Composer are a set of declarative builders of containers and steps, used to build compositions.

### Monitors

Monitors are attached to Directors in order to listen to execution events. Monitors are useful for debugging (yes debug with breakpoints and conditional breakpoints), logging, tracing and execution persistence. The execution persistence is mandatory in order to recover a process after a crash.

> Written with [StackEdit](https://stackedit.io/).