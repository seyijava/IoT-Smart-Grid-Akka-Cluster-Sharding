package device

import (
	"fmt"
	"math/rand"
	"strings"
	"time"
)

type DeviceType int

type LightStatus int

const charset = "abcdefghijklmnopqrstuvwxyz" +
	"ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"

var seededRand *rand.Rand = rand.New(
	rand.NewSource(time.Now().UnixNano()))

const (
	SMARTMETER  DeviceType = iota
	WINDTURBINE DeviceType = 1
	SOLARPOWER  DeviceType = 2

	ON  DeviceType = iota
	OFF            = 1
)

func (deviceType DeviceType) ToString() string {
	return [...]string{"SMARTMETER", "WINDTURBINE", "SOLARPOWER"}[deviceType]
}

type GeoCity struct {
	Name     string
	Location Location
}

type TurpleGeo struct {
	City        string
	Latititude  float64
	Longititude float64
}
type CityLocationPoint struct {
	locationList []TurpleGeo
}

func NewCityLocationPoint() *CityLocationPoint {
	cityLocation := new(CityLocationPoint)
	cityLocation.locationList = []TurpleGeo{
		TurpleGeo{"IKEJA", 6.605874, 3.349149},
		TurpleGeo{"LEKKI", 6.458985, 3.601521},
		TurpleGeo{"MUSHIN", 6.5333312, 3.3499986},
		TurpleGeo{"SURULERE", 6.500000, 3.350000},
		TurpleGeo{"OSHODI/ISOLO", 6.514193, 3.308678},
		TurpleGeo{"ETI OSA", 6.458985, 3.601521},
		TurpleGeo{"IKOYI", 6.454884, 3.434684},
		TurpleGeo{"VICTORIAL ISLAND", 6.425331632, 3.409498362},
		TurpleGeo{"OBANLEDE", 6.446984, 3.411985},
		TurpleGeo{"LAGOS ISLAND", 6.4499982, 3.3999984},
		TurpleGeo{"EPE", 6.594595, 3.977639},
		TurpleGeo{"ALIMOSHO", 6.546609, 3.238251},
		TurpleGeo{"AGEGE", 6.615356, 3.323782},
		TurpleGeo{"BADAGRY", 6.431581, 2.887644},
		TurpleGeo{"AMUWO ODOFIN", 6.429302, 3.268421},
		TurpleGeo{"IKORODU", 6.619413, 3.510454},
		TurpleGeo{"APAPA", 6.45528, 3.364084},
		TurpleGeo{"KOSOFE", 6.561638, 3.384247},
		TurpleGeo{"SOMOLU", 6.534965, 3.389289},
		TurpleGeo{"BARIGA", 6.533333, 3.383333},
		TurpleGeo{"MAINLAND", 6.522274, 3.376779},
		TurpleGeo{"IFAKO IJAYE", 6.684968, 3.288546},
		TurpleGeo{"IBEJU", 6.496267, 3.596457},
	}
	return cityLocation
}

type Location struct {
	Latitude    float64
	Longititude float64
}

type Device struct {
	DeviceId     string  `json:"deviceId"`
	DeviceType   string  `json:"deviceType"`
	Longititude  float64 `json:"longititude"`
	Latititude   float64 `json:"latititude"`
	DeviceNumber string  `json:"deviceNumber"`
}

type DeviceManager struct {
}

func (dm *DeviceManager) GetDevices() []Device {

	deviceList := GetDeviceList(10)
	return deviceList
}

func GetGeoCitiesList() []GeoCity {
	geoCities := make([]GeoCity, 0)
	return geoCities
}

func GetDeviceList(numberOfDevice int) []Device {
	devices := make([]Device, numberOfDevice)
	cityLocation := NewCityLocationPoint()
	for i := 1; i < numberOfDevice; i++ {
		turpleGeo := cityLocation.NextLocation() //
		location := Location{Latitude: turpleGeo.Latititude + 0.00056, Longititude: turpleGeo.Longititude + 0.00046}
		deviceId := fmt.Sprint(i)
		devices[i] = Device{DeviceId: deviceId, DeviceNumber: StringWithCharset(5, charset), Longititude: location.Longititude,
			Latititude: location.Latitude,
			DeviceType: getDeviceType().ToString()}
	}

	return devices
}

func (loc *CityLocationPoint) NextLocation() TurpleGeo {

	turpleGeo := loc.locationList[rand.Intn(len(loc.locationList))]
	return turpleGeo
}

func getDeviceType() DeviceType {
	//rand.Seed(time.Now().UnixNano())
	deviceList := [3]DeviceType{SMARTMETER, SOLARPOWER, WINDTURBINE}

	return deviceList[seededRand.Intn(len(deviceList))]
}

func StringWithCharset(length int, charset string) string {
	b := make([]byte, length)
	for i := range b {
		b[i] = charset[seededRand.Intn(len(charset))]
	}
	return strings.ToUpper(string(b))
}
