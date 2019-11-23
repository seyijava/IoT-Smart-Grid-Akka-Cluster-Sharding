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

Run IOT Device Simulator

cd smart-iot-device-simulator 

go run main.go

Start IoT Smart Grid

cd iot-smart-grid-monitoring

staring master Node and all the nodes. You can start up as much node as u wanted with a distinct port on same JVM

sbt "runMain com.bigdataconcept.akka.distributed.iot.smart.grid.IoTSmartGridCluster"  <br>
sbt "runMain com.bigdataconcept.akka.distributed.iot.smart.grid.IoTSmartGridCluster 2553" <br>
sbt "runMain com.bigdataconcept.akka.distributed.iot.smart.grid.IoTSmartGridCluster 2554"
sbt "runMain com.bigdataconcept.akka.distributed.iot.smart.grid.IoTSmartGridCluster 2555"
sbt "runMain com.bigdataconcept.akka.distributed.iot.smart.grid.IoTSmartGridCluster 2556"



