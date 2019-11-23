package com.bigdataconcept.akka.distributed.iot.smart.grid.actors


import akka.actor.ActorLogging

import akka.persistence.SnapshotOffer
import akka.persistence.PersistentActor
import akka.actor.Props
import com.bigdataconcept.akka.distributed.iot.smart.grid.proto.SendIoTDataTelemetry
import com.bigdataconcept.akka.distributed.iot.smart.grid.proto.IoTEvent

/**
 * @author oluwaseyi Otun 
 * SmartDeviceActor is a persistent actor that event state that is save into cassandra data store. 
 * The Cassandra data store persist the Virtual Device Actor and will that
 */
object SmartDeviceActor{
   def props() : Props = Props(new SmartDeviceActor)
}


class SmartDeviceActor extends PersistentActor with ActorLogging {
  
   override def preStart(): Unit={
      super.preStart()
     log.info(s"SmartDeviceActor Device ID [${self.path.name}]")
    }
    
     override def persistenceId  = s"${self.path.name}"
   
     var ioTstate = IoTDeviceState()
     def updateState(event: IoTEvent): Unit ={
        ioTstate = ioTstate.updated(event)
       
     }
     def numEvents =  ioTstate.size
     
     case class IoTDeviceState(events: List[IoTEvent] = Nil) {
     def updated(evt: IoTEvent): IoTDeviceState = copy(evt :: events)
     def size: Int = events.length
     override def toString: String = events.reverse.toString
     }
    
    
     val receiveRecover: Receive = {
      case evt: IoTEvent => 
        updateState(evt)
       //log.info(s"SmartDeviceActor Reovering State SmartDeviceActor ID [${self.path.name}]") 
      case SnapshotOffer(_, snapshot: IoTDeviceState) => {
        println(s"offered state = $snapshot")
        ioTstate = snapshot
    }
  }
     
    
    val receiveCommand: Receive = {
       case msg @ SendIoTDataTelemetry(device,data) =>
       log.info(s"SmartDeviceActor >>> Received IOT Telemetry Data IoT Device ID Device-${self.path.name}  Data: ${data} logitude: ${device.get.longititude} latititude: ${device.get.latititude} DeviceType: ${device.get.deviceType} Number of Event: ${numEvents} ")
       persist(IoTEvent(msg.device,msg.iotData,numEvents + 1)) {event=>
       this.updateState(event) 
       this.publishIOTEvent(event)
       }
       case "snap"  => saveSnapshot(ioTstate)
       case "print" => println(ioTstate)
     }
  
  
    def  publishIOTEvent(iotEvent:IoTEvent) {
      this.context.system.eventStream.publish(iotEvent);
     }
    
}