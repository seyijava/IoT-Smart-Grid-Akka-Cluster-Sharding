package com.bigdataconcept.akka.distributed.iot.smart.grid.util

import akka.stream.alpakka.mqtt.MqttMessage;
import com.bigdataconcept.akka.distributed.iot.smart.grid.domain.IOTData.SensorData
import com.google.gson.Gson

object MqttPayloadManager {
  
  
     
       def  getPayloadFromMqttMessage(mqttMessage: MqttMessage): SensorData = {
       try 
       {
            val sensorData = new Gson().fromJson(new String(mqttMessage.payload.toArray), classOf[SensorData])
            return sensorData
       }
       catch{
            case a: Throwable => a.printStackTrace()
       }
       return null
     }
      
    

}