## Build

./gradlew.bat clean build
./gradlew clean build

## Watch src file changes and continously build. Run in two separate terminals.
./gradlew.bat -t build 
./gradlew.bat bootRun


./gradlew -t build 
./gradlew bootRun

## Start RocketMQ locally within Docker

docker run -d -p 9876:9876 -v ~/Development/local/rocketmq-volume/data/namesrv/logs:/home/rocketmq/logs -v ~/Development/local/rocketmq-volume/data/namesrv/store:/home/rocketmq/store --name rmqnamesrv -e "MAX_POSSIBLE_HEAP=100000000" rocketmqinc/rocketmq:4.4.0 sh mqnamesrv

docker run -d -p 10911:10911 -p 10909:10909 -v ~/Development/local/rocketmq-volume/data/broker/logs:/home/rocketmq/logs -v ~/Development/local/rocketmq-volume/data/broker/store:/home/rocketmq/store -v ~/Development/local/rocketmq-volume/data/broker/conf/broker.conf:/opt/rocketmq-4.4.0/conf/broker.conf --name rmqbroker --link rmqnamesrv:namesrv -e "NAMESRV_ADDR=namesrv:9876" -e "MAX_POSSIBLE_HEAP=200000000" rocketmqinc/rocketmq:4.4.0 sh mqbroker autoCreateTopicEnable=true -c /opt/rocketmq-4.4.0/conf/broker.conf


docker exec -i -t CONTAINER_ID /bin/bash

## Start RocketMQ locally
cd ~/Development/local/rocketmq-all-4.4.0/distribution/target/apache-rocketmq

export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_221.jdk/Contents/Home

nohup sh bin/mqnamesrv &
tail -f ~/logs/rocketmqlogs/namesrv.log

nohup sh bin/mqbroker -n localhost:9876 autoCreateTopicEnable=true &
tail -f ~/logs/rocketmqlogs/broker.log