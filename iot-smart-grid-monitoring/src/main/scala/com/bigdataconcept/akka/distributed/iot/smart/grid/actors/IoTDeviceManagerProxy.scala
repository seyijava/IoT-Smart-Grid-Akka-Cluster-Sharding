package com.bigdataconcept.akka.distributed.iot.smart.grid.actors

import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.ActorRef
import akka.actor.Props
import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import scala.util.Random
import com.bigdataconcept.akka.distributed.iot.smart.grid.proto.Device
import com.bigdataconcept.akka.distributed.iot.smart.grid.proto.SendIoTDataTelemetry
import com.bigdataconcept.akka.distributed.iot.smart.grid.domain.IOTData.SensorData


object IOTDeviceManagerProxy  {
 
  def props(shardingRegion: ActorRef) : Props = Props(new IOTDeviceManagerProxy(shardingRegion))
  
  case object SendTelemtry
     
}


class IOTDeviceManagerProxy(shardingRegion: ActorRef) extends Actor with ActorLogging {

  import IOTDeviceManagerProxy.SendTelemtry
  implicit val ec: ExecutionContext = context.dispatcher
  context.system.scheduler.schedule(10.seconds, 1.second, self, SendTelemtry)
  
  val random = new Random()
  val numberOfDevices = 20
  
  
     override def receive : Receive = {
       
        case SendTelemtry => 
         log.info("Sending Telemetry Data to Smart IOT")
         val deviceId = random.nextInt(numberOfDevices)
         val device = Device(deviceId.toString(),9.900,48.99, "meter", "")
         val data =  "Temp"
         val sendIOTDataTelemetry = SendIoTDataTelemetry(Some(device), data)
         shardingRegion forward sendIOTDataTelemetry 
     }
  
}