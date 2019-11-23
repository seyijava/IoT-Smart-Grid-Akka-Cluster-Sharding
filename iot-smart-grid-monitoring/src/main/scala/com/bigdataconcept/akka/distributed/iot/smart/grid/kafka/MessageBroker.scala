package com.bigdataconcept.akka.distributed.iot.smart.grid.kafka

import akka.actor.ActorLogging
import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.Props
import com.bigdataconcept.akka.distributed.iot.smart.grid.proto.IoTEvent


/**
 * @author otun Oluwaseyi
 * Message Broker Subscribe to IOT Event via the SmartActor Device
 */
                        
 object KafkaProtocol{
   
     trait KafkaCommand{}
     case class SendMessage[K,M](topic: String,key: K, msg: M) extends KafkaCommand
     
     def addIOTEvent(key: Long, msg:IoTEvent) : SendMessage[Long,IoTEvent] ={
         return  new SendMessage("IOT-Telemetry",key, msg)
     }
     
      case class SubscribeToTopic(topic: String) extends KafkaCommand
     
 }

object MessageBroker{
   def props(kafka: ActorRef) : Props = Props(new MessageBroker(kafka))
}

class MessageBroker(broker: ActorRef) extends Actor with ActorLogging 
{
   override def preStart():Unit ={
     super.preStart()
     log.info("MessageBroker PreStart subscribe To IoTEvent");
     context.system.eventStream.subscribe(self, classOf[IoTEvent])
   }
  
   override def receive : Receive = {
      case iotEvent: IoTEvent => handleIOTEvent(iotEvent)
   }
    
  def handleIOTEvent(evt: IoTEvent) {
    
    log.info("handling IOT event  Sending to KafkaActorProducer -> {}", evt);
    
    val msg = KafkaProtocol.addIOTEvent(0L, evt) 
    
    broker.tell(msg, self)
  }  
}