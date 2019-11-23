// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO3

package com.bigdataconcept.akka.distributed.iot.smart.grid.proto

@SerialVersionUID(0L)
final case class IoTEvent(
    device: _root_.scala.Option[com.bigdataconcept.akka.distributed.iot.smart.grid.proto.Device] = _root_.scala.None,
    data: _root_.scala.Predef.String = "",
    eventNum: _root_.scala.Int = 0
    ) extends scalapb.GeneratedMessage with scalapb.Message[IoTEvent] with scalapb.lenses.Updatable[IoTEvent] {
    @transient
    private[this] var __serializedSizeCachedValue: _root_.scala.Int = 0
    private[this] def __computeSerializedValue(): _root_.scala.Int = {
      var __size = 0
      if (device.isDefined) {
        val __value = device.get
        __size += 1 + _root_.com.google.protobuf.CodedOutputStream.computeUInt32SizeNoTag(__value.serializedSize) + __value.serializedSize
      };
      
      {
        val __value = data
        if (__value != "") {
          __size += _root_.com.google.protobuf.CodedOutputStream.computeStringSize(2, __value)
        }
      };
      
      {
        val __value = eventNum
        if (__value != 0) {
          __size += _root_.com.google.protobuf.CodedOutputStream.computeInt32Size(3, __value)
        }
      };
      __size
    }
    final override def serializedSize: _root_.scala.Int = {
      var read = __serializedSizeCachedValue
      if (read == 0) {
        read = __computeSerializedValue()
        __serializedSizeCachedValue = read
      }
      read
    }
    def writeTo(`_output__`: _root_.com.google.protobuf.CodedOutputStream): _root_.scala.Unit = {
      device.foreach { __v =>
        val __m = __v
        _output__.writeTag(1, 2)
        _output__.writeUInt32NoTag(__m.serializedSize)
        __m.writeTo(_output__)
      };
      {
        val __v = data
        if (__v != "") {
          _output__.writeString(2, __v)
        }
      };
      {
        val __v = eventNum
        if (__v != 0) {
          _output__.writeInt32(3, __v)
        }
      };
    }
    def mergeFrom(`_input__`: _root_.com.google.protobuf.CodedInputStream): com.bigdataconcept.akka.distributed.iot.smart.grid.proto.IoTEvent = {
      var __device = this.device
      var __data = this.data
      var __eventNum = this.eventNum
      var _done__ = false
      while (!_done__) {
        val _tag__ = _input__.readTag()
        _tag__ match {
          case 0 => _done__ = true
          case 10 =>
            __device = Option(_root_.scalapb.LiteParser.readMessage(_input__, __device.getOrElse(com.bigdataconcept.akka.distributed.iot.smart.grid.proto.Device.defaultInstance)))
          case 18 =>
            __data = _input__.readString()
          case 24 =>
            __eventNum = _input__.readInt32()
          case tag => _input__.skipField(tag)
        }
      }
      com.bigdataconcept.akka.distributed.iot.smart.grid.proto.IoTEvent(
          device = __device,
          data = __data,
          eventNum = __eventNum
      )
    }
    def getDevice: com.bigdataconcept.akka.distributed.iot.smart.grid.proto.Device = device.getOrElse(com.bigdataconcept.akka.distributed.iot.smart.grid.proto.Device.defaultInstance)
    def clearDevice: IoTEvent = copy(device = _root_.scala.None)
    def withDevice(__v: com.bigdataconcept.akka.distributed.iot.smart.grid.proto.Device): IoTEvent = copy(device = Option(__v))
    def withData(__v: _root_.scala.Predef.String): IoTEvent = copy(data = __v)
    def withEventNum(__v: _root_.scala.Int): IoTEvent = copy(eventNum = __v)
    def getFieldByNumber(__fieldNumber: _root_.scala.Int): _root_.scala.Any = {
      (__fieldNumber: @_root_.scala.unchecked) match {
        case 1 => device.orNull
        case 2 => {
          val __t = data
          if (__t != "") __t else null
        }
        case 3 => {
          val __t = eventNum
          if (__t != 0) __t else null
        }
      }
    }
    def getField(__field: _root_.scalapb.descriptors.FieldDescriptor): _root_.scalapb.descriptors.PValue = {
      _root_.scala.Predef.require(__field.containingMessage eq companion.scalaDescriptor)
      (__field.number: @_root_.scala.unchecked) match {
        case 1 => device.map(_.toPMessage).getOrElse(_root_.scalapb.descriptors.PEmpty)
        case 2 => _root_.scalapb.descriptors.PString(data)
        case 3 => _root_.scalapb.descriptors.PInt(eventNum)
      }
    }
    def toProtoString: _root_.scala.Predef.String = _root_.scalapb.TextFormat.printToUnicodeString(this)
    def companion = com.bigdataconcept.akka.distributed.iot.smart.grid.proto.IoTEvent
}

object IoTEvent extends scalapb.GeneratedMessageCompanion[com.bigdataconcept.akka.distributed.iot.smart.grid.proto.IoTEvent] {
  implicit def messageCompanion: scalapb.GeneratedMessageCompanion[com.bigdataconcept.akka.distributed.iot.smart.grid.proto.IoTEvent] = this
  def fromFieldsMap(__fieldsMap: scala.collection.immutable.Map[_root_.com.google.protobuf.Descriptors.FieldDescriptor, _root_.scala.Any]): com.bigdataconcept.akka.distributed.iot.smart.grid.proto.IoTEvent = {
    _root_.scala.Predef.require(__fieldsMap.keys.forall(_.getContainingType() == javaDescriptor), "FieldDescriptor does not match message type.")
    val __fields = javaDescriptor.getFields
    com.bigdataconcept.akka.distributed.iot.smart.grid.proto.IoTEvent(
      __fieldsMap.get(__fields.get(0)).asInstanceOf[_root_.scala.Option[com.bigdataconcept.akka.distributed.iot.smart.grid.proto.Device]],
      __fieldsMap.getOrElse(__fields.get(1), "").asInstanceOf[_root_.scala.Predef.String],
      __fieldsMap.getOrElse(__fields.get(2), 0).asInstanceOf[_root_.scala.Int]
    )
  }
  implicit def messageReads: _root_.scalapb.descriptors.Reads[com.bigdataconcept.akka.distributed.iot.smart.grid.proto.IoTEvent] = _root_.scalapb.descriptors.Reads{
    case _root_.scalapb.descriptors.PMessage(__fieldsMap) =>
      _root_.scala.Predef.require(__fieldsMap.keys.forall(_.containingMessage == scalaDescriptor), "FieldDescriptor does not match message type.")
      com.bigdataconcept.akka.distributed.iot.smart.grid.proto.IoTEvent(
        __fieldsMap.get(scalaDescriptor.findFieldByNumber(1).get).flatMap(_.as[_root_.scala.Option[com.bigdataconcept.akka.distributed.iot.smart.grid.proto.Device]]),
        __fieldsMap.get(scalaDescriptor.findFieldByNumber(2).get).map(_.as[_root_.scala.Predef.String]).getOrElse(""),
        __fieldsMap.get(scalaDescriptor.findFieldByNumber(3).get).map(_.as[_root_.scala.Int]).getOrElse(0)
      )
    case _ => throw new RuntimeException("Expected PMessage")
  }
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = ModelsProto.javaDescriptor.getMessageTypes.get(1)
  def scalaDescriptor: _root_.scalapb.descriptors.Descriptor = ModelsProto.scalaDescriptor.messages(1)
  def messageCompanionForFieldNumber(__number: _root_.scala.Int): _root_.scalapb.GeneratedMessageCompanion[_] = {
    var __out: _root_.scalapb.GeneratedMessageCompanion[_] = null
    (__number: @_root_.scala.unchecked) match {
      case 1 => __out = com.bigdataconcept.akka.distributed.iot.smart.grid.proto.Device
    }
    __out
  }
  lazy val nestedMessagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]] = Seq.empty
  def enumCompanionForFieldNumber(__fieldNumber: _root_.scala.Int): _root_.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__fieldNumber)
  lazy val defaultInstance = com.bigdataconcept.akka.distributed.iot.smart.grid.proto.IoTEvent(
    device = _root_.scala.None,
    data = "",
    eventNum = 0
  )
  implicit class IoTEventLens[UpperPB](_l: _root_.scalapb.lenses.Lens[UpperPB, com.bigdataconcept.akka.distributed.iot.smart.grid.proto.IoTEvent]) extends _root_.scalapb.lenses.ObjectLens[UpperPB, com.bigdataconcept.akka.distributed.iot.smart.grid.proto.IoTEvent](_l) {
    def device: _root_.scalapb.lenses.Lens[UpperPB, com.bigdataconcept.akka.distributed.iot.smart.grid.proto.Device] = field(_.getDevice)((c_, f_) => c_.copy(device = Option(f_)))
    def optionalDevice: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Option[com.bigdataconcept.akka.distributed.iot.smart.grid.proto.Device]] = field(_.device)((c_, f_) => c_.copy(device = f_))
    def data: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Predef.String] = field(_.data)((c_, f_) => c_.copy(data = f_))
    def eventNum: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Int] = field(_.eventNum)((c_, f_) => c_.copy(eventNum = f_))
  }
  final val DEVICE_FIELD_NUMBER = 1
  final val DATA_FIELD_NUMBER = 2
  final val EVENTNUM_FIELD_NUMBER = 3
  def of(
    device: _root_.scala.Option[com.bigdataconcept.akka.distributed.iot.smart.grid.proto.Device],
    data: _root_.scala.Predef.String,
    eventNum: _root_.scala.Int
  ): _root_.com.bigdataconcept.akka.distributed.iot.smart.grid.proto.IoTEvent = _root_.com.bigdataconcept.akka.distributed.iot.smart.grid.proto.IoTEvent(
    device,
    data,
    eventNum
  )
}