package com.bigdataconcept.akka.distributed.iot.smart.grid.domain





object IOTCommands
{
  
    case class SubscribeToTopicRequest(topic: String,  brokerUrl: String)
    
    case class SubscribeToTopicResponse(message: String)
    
}

object IOTData{
  
 
  trait DeviceData
  
 case class SensorData(device: Device,data: String) 
 
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
                       
                        
                        
                        
 