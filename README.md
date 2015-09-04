# message-test
Demonstrates the use of RabbitMQ to send and receive a message using Spring.

## Setup
1. This requires that Lombok be installed in your IDE to compile; see https://projectlombok.org/download.html for details.
1. Install RabbitMQ (`brew install rabbitmq`)
1. Make sure that `/usr/local/sbin` is in your path
1. Start the broker (`rabbitmq-server`)

## Running the example
1. `gradle bootRun`

* Every second, a persistent, transactional message will be sent to the queue and consumed by the listener.
* Every 5th message will be rolled back and then immediately re-consumed.
* Notes on persistence: https://www.rabbitmq.com/blog/2011/01/20/rabbitmq-backing-stores-databases-and-disks

## Troubleshooting
### Purging the tasks Queue
`rabbitmqadmin purge queue name=tasks`

### Management Console
http://localhost:15672 (`guest/guest`)