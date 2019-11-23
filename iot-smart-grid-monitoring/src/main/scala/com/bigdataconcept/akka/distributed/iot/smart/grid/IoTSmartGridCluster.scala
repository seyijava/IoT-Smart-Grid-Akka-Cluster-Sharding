package com.bigdataconcept.akka.distributed.iot.smart.grid

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory;
import com.bigdataconcept.akka.distributed.iot.smart.grid.actors.IOTSmartDeviceManager
import com.bigdataconcept.akka.distributed.iot.smart.grid.actors.InboundIoTMQTTTelemetryManagerActor
import akka.cluster.sharding.ClusterSharding
import com.bigdataconcept.akka.distributed.iot.smart.grid.actors.InboundIoTMQTTTelemetryManagerActor.SubscribeToTopicRequest
import com.bigdataconcept.akka.distributed.iot.smart.grid.kafka.MessageBroker
import com.bigdataconcept.akka.distributed.iot.smart.grid.kafka.KafkaProducerActor
import akka.stream.ActorMaterializer

object IoTSmartGridCluster {
  
  def main(args: Array[String]): Unit = {
  if(args.length == 0){
      startup(Seq("2551","2552","0"))
  }else{
         startup(Seq(args(0)))
  }
    
  }
  
   def startup(ports: Seq[String]): Unit ={
       ports foreach { port =>
      
         System.out.println("Node Port" + port)
       
        val config = ConfigFactory.parseString("akka.remote.artery.canonical.port=" + port).withFallback(ConfigFactory.load())   
       
        implicit val smartGridMonitoringSystem  = ActorSystem("SmartGridMonitoringSystem",config);
       
        implicit val materializer = ActorMaterializer()
       
         
        val configuration = ConfigFactory.load("application.conf")
         
        val kafkaProducerActor = smartGridMonitoringSystem.actorOf(KafkaProducerActor.props(configuration.getString("app.kafkaBroker")))
       
        smartGridMonitoringSystem.actorOf(MessageBroker.props(kafkaProducerActor))
       
        IOTSmartDeviceManager.startIOTSharding(smartGridMonitoringSystem)
       
       if (port == "0") {
           
         val shardingRegion = ClusterSharding(smartGridMonitoringSystem).shardRegion(IOTSmartDeviceManager.shardName)
       
         
         val brokerUrl = configuration.getString("app.brokerUrl")
         
         val topic   = configuration.getString("app.topic")
         
         val inboundTelemetryManagerActor = smartGridMonitoringSystem.actorOf(InboundIoTMQTTTelemetryManagerActor.props(brokerUrl, topic, shardingRegion))
           
             inboundTelemetryManagerActor ! SubscribeToTopicRequest
          }
       } 
   }
   
      
}