package com.bigdataconcept.akka.distributed.iot.smart.grid.kafka

import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.Props
import com.bigdataconcept.akka.distributed.iot.smart.grid.kafka.KafkaProtocol.SendMessage
import com.bigdataconcept.akka.distributed.iot.smart.grid.proto.IoTEvent
import akka.kafka.ProducerSettings;
import akka.kafka.javadsl.Producer;
import akka.stream.OverflowStrategy;
import akka.stream.scaladsl.{Source,SourceQueue}
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import akka.stream.ActorMaterializer
import com.google.gson.Gson

/**
 * @author Otun Oluwaseyi 
 * 
 * It is responsible for Sending IoT Event to Kafka Broker
 */
object KafkaProducerActor {
    def props(brokerUrl: String)(implicit mat: ActorMaterializer) : Props = Props(new KafkaProducerActor(brokerUrl)(mat))
 }


class KafkaProducerActor(brokerUrl: String)(implicit mat: ActorMaterializer)  extends Actor with ActorLogging {
 
  var queue : SourceQueue[SendMessage[Long,IoTEvent]] = null;

  override def preStart() : Unit = {
          super.preStart()
           log.info("Starting Kafka Messsage producer Actor And Sending IOTEvent To Kafka Broker");
           val mapper = new Gson();
            queue = Source.queue[SendMessage[Long,IoTEvent]](100, OverflowStrategy.backpressure)
                       .map(m => new ProducerRecord[Array[Byte], String](m.topic, mapper.toJson(m.msg)))
                       .to(Producer.plainSink(ProducerSettings(context.system, new ByteArraySerializer, new StringSerializer).withBootstrapServers(brokerUrl)))
                       .run()(mat)
     }
     
     override def receive : Receive = {
       case message : SendMessage[Long,IoTEvent] => sendMessage(message)
     }
     
     def  sendMessage(msg: SendMessage[Long, IoTEvent]){
        log.info("Adding IOTEvent Message to Queue Source");
        queue.offer(msg)
     }
  
}