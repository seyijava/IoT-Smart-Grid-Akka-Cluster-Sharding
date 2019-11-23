package com.bigdataconcept.akka.distributed.iot.smart.grid.actors
import akka.actor.ActorLogging
import akka.actor.Actor
import akka.actor.Props
import akka.stream.alpakka.mqtt.MqttConnectionSettings
import akka.stream.alpakka.mqtt.MqttQoS
import akka.stream.alpakka.mqtt.MqttSubscriptions
import akka.stream.alpakka.mqtt.javadsl.MqttSource
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence
import com.bigdataconcept.akka.distributed.iot.smart.grid.domain.IOTData.SensorData
import com.bigdataconcept.akka.distributed.iot.smart.grid.util.MqttPayloadManager
import akka.stream.ActorMaterializer
import akka.actor.ActorRef
import com.bigdataconcept.akka.distributed.iot.smart.grid.proto.SendIoTDataTelemetry
import com.bigdataconcept.akka.distributed.iot.smart.grid.proto.Device
import java.util.UUID

/** 
 *  Oluwaseyi Otun 
 *  InboundIoTMQTTTelemetryManagerActor as a data reciever and proxy betweeen the Smart Device IoT to the banked end sharded SmartEntity  
 */

object InboundIoTMQTTTelemetryManagerActor{
  
  
  final case class SubscribeToTopicRequest(topic: String,  brokerUrl: String)
  
  def props(brokerUrl: String, topic: String, shardingProxy: ActorRef) : Props = 
    Props(new InboundIoTMQTTTelemetryManagerActor(brokerUrl,topic,shardingProxy))
  
}


class InboundIoTMQTTTelemetryManagerActor(borkerUrl:String,topic: String, shardRegionActor: ActorRef) extends Actor with ActorLogging {
  
  import InboundIoTMQTTTelemetryManagerActor.SubscribeToTopicRequest
  
       override def receive : Receive = {
              case  SubscribeToTopicRequest =>  subscribeToDevicesTopicOnMTTQBroker(topic,borkerUrl)
          }
        
       private def subscribeToDevicesTopicOnMTTQBroker(topic: String, brokerUrl: String){
         log.info(s"Starting IOTTelemetryManagerActor Actor Subcripting to MTTQ Topic: ${topic} Broker url : ${brokerUrl}")
         implicit val materializer = ActorMaterializer()
         val clientId = UUID.randomUUID().toString();
         val buffersize:Int = 20
         val  connectionSettings = MqttConnectionSettings.create(brokerUrl, clientId,  new MemoryPersistence()).withAutomaticReconnect(true)
         val subscriptions = MqttSubscriptions.create(topic, MqttQoS.AtLeastOnce);
         val mqttSource  = MqttSource.atMostOnce(connectionSettings, subscriptions, buffersize)
         mqttSource.map(mttqMessage => MqttPayloadManager.getPayloadFromMqttMessage(mttqMessage)).log("error logging")
          .runForeach(s => handleMqttMessageReceived(s), materializer)
       }
    
       
       private  def handleMqttMessageReceived(sensorData: SensorData){
         log.info(s"handleMqttMessageReceived >>> Received MQTT message: Sensor type : ${sensorData.device.deviceType} ; SensorId : ${sensorData.device.deviceId}")
         val device =  Device(deviceId=sensorData.device.deviceId,longititude=sensorData.device.latititude,latititude=sensorData.device.longititude,deviceType=sensorData.device.deviceType,deviceNumber=sensorData.device.deviceNumber)
         val  telData = SendIoTDataTelemetry(Some(device), sensorData.data)
         log.info("IoT Data Telemetry Data Sent to Sharded Entity Actor SmartDevice Actor" + sensorData.device.deviceId)
         shardRegionActor forward  telData
         log.info("IoT Data Telemetry Data Sent to Sharded Entity Actor SmartDevice Actor")
       }
          
         
      
}