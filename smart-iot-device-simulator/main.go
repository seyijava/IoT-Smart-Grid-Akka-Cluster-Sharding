package main

import (
	"net/url"

	"github.com/bigdataconcept/iot/smart/meter/simulator/device"
	"github.com/bigdataconcept/iot/smart/meter/simulator/mttq"
	"github.com/jasonlvhit/gocron"
)

func RunIOTSimulatorTask(devices []device.Device) {

	iotSimulator := &mttq.IOTSimulator{Url: &url.URL{Host: "192.168.2.45:1883"}, Topic: "MeterIOT", ClientId: "IOTSmeterMeterGrp"}
	for i := range devices {

		iotDevice := devices[i]
		data := ""
		if iotDevice.DeviceType == device.SMARTMETER.ToString() {
			meterReading := iotSimulator.EnergyConsumptionRandomGenerator()
			data = mttq.ToJson(meterReading)

		} else if iotDevice.DeviceType == device.WINDTURBINE.ToString() {
			windTurbineData := iotSimulator.WindTurbineDataGenerator()
			data = mttq.ToJson(windTurbineData)
		} else if iotDevice.DeviceType == device.SOLARPOWER.ToString() {
			solarPower := iotSimulator.SolarPowerDataGenerator()
			data = mttq.ToJson(solarPower)
		}

		sendorData := mttq.SensorData{Device: iotDevice, Data: data}
		iotSimulator.SendIOTData(sendorData)
	}

}

func main() {

	deviceManager := &device.DeviceManager{}

	devices := deviceManager.GetDevices()

	s := gocron.NewScheduler()

	s.Every(5).Seconds().Do(RunIOTSimulatorTask, devices)

	<-s.Start()

}
