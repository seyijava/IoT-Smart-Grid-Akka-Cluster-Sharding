package com.bigdataconcept.akka.distributed.iot.smart.grid.domain


import java.util.Date


object IOTCommands
{
  
  
  
     trait Cmd
  
    trait CommandType 
    
    case object RESTART extends CommandType      
    
    case object STOP extends CommandType
    
    case object UPDATE extends CommandType
    
    
   
  
    trait IOTCommandMessage{
      val topic: String
      val deviceId: String
    }
    
    case class StopIOTDeviceCommand(deviceId: String ,topic: String, command: Array[Byte]) extends IOTCommandMessage
    
    case class RestartIOTDeviceCommand(deviceId: String,topic: String, command: Array[Byte]) extends IOTCommandMessage
    
    case class UpdateIOTDeviceCommand(deviceId: String,topic: String, command: Array[Byte]) extends IOTCommandMessage
    
  
    case class DeviceRegistration(device: Device, registrationDate: Date)
    
    case class DeviceAlreadyRegisted(device: Device)
    
    case class DeviceRegisted(device: Device)
    
    case class SendIoTDataTelemetry(id: String, iotData: String) extends Cmd
    
    
    case class FowardIoTDataTelemetry(device: Device, iotData: String)
    
    case class RecieveIOTTelemtryData(device: Device, iotData: String)
   
     
   
    case class IOTCommand(topic: String, cmd: String, cmdType: CommandType)
    
    case class IOTCommandRequest(deviceId: String,cmd: IOTCommand)
    
    
    case class IOTTelemeryData(id:String, iotData: String)
    
    object PingDevice
    
    object StartIOTTelemetry
    
    case class SubscribeToTopicRequest(topic: String,  brokerUrl: String)
    
    case class SubscribeToTopicResponse(message: String)
    
  
    
    object TerminateIOTTelemetry
    
    object RestartIOTTelemetry
    
    
}

object IOTData{
  
 
  trait DeviceData
  
 case class SensorData(device: Device,data: String) 
 
 case class SmartDeviceData(device: Device,data: DeviceData)
 
  case class WindTurbineData(eventTime: String, windSpeed: String, electricVoltage: String,electricCurrent: String,mptt: String) extends DeviceData
  
  case class SolarPowerData(eventTime: String, current:Double) extends DeviceData
  
  case class MeterReadingData(watt: Double, volts: Double,amp:Double,eventTime: String) extends DeviceData

}

object Events
{
     
     trait IOTEvent 
     case class IOTTelemetryEvent(deviceId: String, eventData: String) extends IOTEvent
}
case class Device(
                        deviceId:  String,
                         longititude: Double,
                        latititude: Double,
                         deviceType:String,
                         deviceNumber:String)
                       
                        
                        
                        
 object KafkaProtocol{
   
    import Events.IOTEvent
   
     trait KafkaCommand{}
  
     case class SubscribeToTopic(topic: String) extends KafkaCommand
    
     case class SendMessage[K,M](topic: String,key: K, msg: M) extends KafkaCommand
     
     def addIOTEvent(key: Long, msg:IOTEvent) : SendMessage[Long,IOTEvent] ={
         return  new SendMessage("IOT-Telemetry",key, msg)
     }
     
 }