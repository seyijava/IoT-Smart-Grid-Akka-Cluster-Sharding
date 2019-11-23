package mttq

import (
	"encoding/json"
	"fmt"
	"math"
	"math/rand"
	"net/url"
	"time"

	"github.com/bigdataconcept/iot/smart/meter/simulator/device"
	mqtt "github.com/eclipse/paho.mqtt.golang"
)

type SensorData struct {
	Device device.Device `json:"device"`
	Data   string        `json:"data"`
}

type IOTSimulator struct {
	BrokerUrl string
	Topic     string
	ClientId  string
	Url       *url.URL
}

type IOTData struct {
	UseageTime time.Time `json:"usesageTime"`
	Data       string    `json:"data"`
}

type StreetLightingData struct {
	Status device.LightStatus `json:"status"`
}

type WindTurbineData struct {
	EventTime       string  `json:"eventTime"`
	WindSpeed       float64 `json:"windspeed"`
	ElectricVoltage float64 `json:"electricVoltage"`
	ElectricCurrent float64 `json:"electricCurrent"`
	Mptt            float64 `json:"mptt"`
}

type SolarPowerData struct {
	EventTime string  `json:"eventTime"`
	Current   float64 `json:"current"`
}

type MeterReadingData struct {
	Watt      float64 `json:"watt"`
	Volts     float64 `json:"volts"`
	AMP       float64 `json:"amp"`
	EventTime string  `json:"eventTime"`
}

func ToJson(obj interface{}) string {

	data, _ := json.Marshal(obj)
	return string(data)
}

func (iot *IOTSimulator) connect(clientId string, uri *url.URL) mqtt.Client {

	opts := iot.createClientOptions(clientId, uri)
	client := mqtt.NewClient(opts)
	token := client.Connect()
	for !token.WaitTimeout(3 * time.Second) {
	}
	if err := token.Error(); err != nil {
		panic(err)
	}
	return client
}

func (iot *IOTSimulator) createClientOptions(clientId string, uris *url.URL) *mqtt.ClientOptions {
	//uri, _ := url.Parse("mqtt://mqtt-test:mqtt-test@192.168.2.45:1883")
	opts := mqtt.NewClientOptions()
	opts.AddBroker("tcp://192.168.2.45:1883")
	//opts.SetUsername("mqtt-test")
	//opts.SetPassword("mqtt-test")

	opts.SetClientID("sample")
	return opts
}

func (iot *IOTSimulator) SendIOTData(sensorData SensorData) {

	client := iot.connect(iot.ClientId, iot.Url)
	data, err := json.Marshal(sensorData)
	_ = data
	if err != nil {
		fmt.Println(err)
		return
	}

	println(string(data))

	if token := client.Publish("SmartGridIOT", 1, false, string(data)); token.Wait() && token.Error() != nil {
		fmt.Println(token.Error())
	}

	client.Disconnect(1000)

}

func (iot *IOTSimulator) EnergyConsumptionRandomGenerator() *MeterReadingData {

	wat := math.Ceil(rand.Float64()*100) / 100
	volt := math.Ceil(rand.Float64()*100) / 100
	amp := math.Ceil(rand.Float64()*100) / 100
	t := time.Now()
	formatted := fmt.Sprintf("%d-%02d-%02dT%02d:%02d:%02d",
		t.Year(), t.Month(), t.Day(),
		t.Hour(), t.Minute(), t.Second())
	return &MeterReadingData{Watt: wat, Volts: volt, AMP: amp, EventTime: formatted}
}

func (iot *IOTSimulator) SolarPowerDataGenerator() *SolarPowerData {
	t := time.Now()
	formatted := fmt.Sprintf("%d-%02d-%02dT%02d:%02d:%02d",
		t.Year(), t.Month(), t.Day(),
		t.Hour(), t.Minute(), t.Second())

	return &SolarPowerData{EventTime: formatted}
}

func (iot *IOTSimulator) WindTurbineDataGenerator() *WindTurbineData {

	windSpeed := math.Ceil(rand.Float64()*100) / 100
	electricVoltage := math.Ceil(rand.Float64()*100) / 100
	electricCurrent := math.Ceil(rand.Float64()*100) / 100
	mptt := math.Ceil(rand.Float64()*100) / 100
	t := time.Now()
	formatted := fmt.Sprintf("%d-%02d-%02dT%02d:%02d:%02d",
		t.Year(), t.Month(), t.Day(),
		t.Hour(), t.Minute(), t.Second())
	return &WindTurbineData{WindSpeed: windSpeed, ElectricVoltage: electricVoltage, ElectricCurrent: electricCurrent, Mptt: mptt, EventTime: formatted}
}
