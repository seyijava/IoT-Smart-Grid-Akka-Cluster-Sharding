package com.bigdataconcept.akka.distributed.iot.smart.grid.actors

import akka.actor.ActorLogging
import akka.actor.Actor
import akka.actor.Props

object ReadSideEventProcessor {
  
   def props() : Props  = Props(new ReadSideEventProcessor)
}


class ReadSideEventProcessor() extends Actor with ActorLogging{
  
       override def receive: Receive = ???
       
       
       
       def prepareStatements(): Unit={
         
       }
}