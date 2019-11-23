package com.bigdataconcept.akka.distributed.iot.smart.grid.actors


import akka.cluster.sharding.ShardRegion

import com.bigdataconcept.akka.distributed.iot.smart.grid.proto.SendIoTDataTelemetry
import akka.cluster.sharding.{ClusterSharding, ClusterShardingSettings, ShardRegion}
import akka.actor.ActorSystem   

object IOTSmartDeviceManager
{
  
  
    def shardName = "IOTSharding"
    
     def SHARDS: Int = 10
    
    
   def calcShardName(uuid: String): String = {
      System.out.println("Number" + uuid)
      val num =   (uuid.toInt % SHARDS).toString()
      return num
   }
    
   val extractEntityId: ShardRegion.ExtractEntityId = {
       case d: SendIoTDataTelemetry => (d.getDevice.deviceId, d)
      }
   
    val extractShardId: ShardRegion.ExtractShardId = {
       case d: SendIoTDataTelemetry => calcShardName(d.getDevice.deviceId)
      }
    
    
    
    def startIOTSharding(system: ActorSystem){
      val settings = ClusterShardingSettings.create(system);
       ClusterSharding(system).start(
      typeName = "IOTSharding",
      entityProps = SmartDeviceActor.props,
      settings = settings,
      extractEntityId = extractEntityId,
      extractShardId = extractShardId
    )
  }
    
}



