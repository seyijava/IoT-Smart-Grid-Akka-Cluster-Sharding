# IoT-Smart-Grid-Akka-Cluster-Sharding


Start HIVEMQ MQTT server

sudo docker run -p 8080:8080 -p 1883:1883 hivemq/hivemq4

Start Cassandara Server

cd Cassandara <br>

sudo docker-compose up

Start  Kafka 

cd kafka

git clone https://github.com/confluentinc/examples

cd examples

docker-compose up -d --build

docker-compose ps

     Name                    Command               State                Ports
------------------------------------------------------------------------------------------
broker            /etc/confluent/docker/run        Up      0.0.0.0:29092->29092/tcp,
                                                           0.0.0.0:9092->9092/tcp
connect           /etc/confluent/docker/run        Up      0.0.0.0:8083->8083/tcp,
                                                           9092/tcp
control-center    /etc/confluent/docker/run        Up      0.0.0.0:9021->9021/tcp
ksql-cli          ksql http://localhost:8088       Up
ksql-datagen      bash -c echo Waiting for K ...   Up
ksql-server       /etc/confluent/docker/run        Up      0.0.0.0:8088->8088/tcp
rest-proxy        /etc/confluent/docker/run        Up      0.0.0.0:8082->8082/tcp
schema-registry   /etc/confluent/docker/run        Up      0.0.0.0:8081->8081/tcp
zookeeper         /etc/confluent/docker/run        Up      0.0.0.0:2181->2181/tcp,
                                                           2888/tcp, 3888/tcp


Run IOT Device Simulator

cd smart-iot-device-simulator 

go run main.go

