#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>
#include "DHT.h"
#define DHTTYPE DHT11
#define FIREBASE_HOST "smarthomebylanga-15287.firebaseio.com"
#define FIREBASE_AUTH "WwR7MPQGw6IUzF1uGYoaRlNO0HNM1VEWc956aRQr"
#define WIFI_SSID "Casa"
#define WIFI_PASSWORD "carolegui"
#define dht_dpin D6
DHT dht(dht_dpin, DHTTYPE);


int BathroomLight = D0;
int KitchenLight = D1;
int LivingRoomLight = D2;
int CoupleBedLight = D3;
int SingleBedLight = D4;
int LaundryLight = D5;
//int digital6 = D6;
int GarageLight = D7;
int ShaverLight = D8;
int HairDryerLight = D9;
//int digitaltx = D10;

int HumidityValue, TemperatureValue;

int BathroomValue, KitchenValue, LivingRoomValue, CoupleBedValue, SingleBedValue, LaundryValue, GarageValue, ShaverValue, HairDryerValue;



void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  dht.begin();

  pinMode(BathroomLight, OUTPUT);
  pinMode(KitchenLight, OUTPUT);
  pinMode(LivingRoomLight, OUTPUT);
  pinMode(CoupleBedLight, OUTPUT);
  pinMode(SingleBedLight, OUTPUT);
  pinMode(LaundryLight, OUTPUT);
  //pinMode(digital6, OUTPUT);
  pinMode(GarageLight, OUTPUT);
  pinMode(ShaverLight, OUTPUT);
  pinMode(HairDryerLight, OUTPUT);

  // connect to wifi.
WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
Serial.print("connecting");
while (WiFi.status() != WL_CONNECTED) {
Serial.print(".");
delay(500);
}
Serial.println();
Serial.print("connected: ");
Serial.println(WiFi.localIP());
Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
//Firebase.set("LED_STATUS/value", 0);
//Firebase.setInt("LIGHT/value", 0);*/

}

void loop() {
  // put your main code here, to run repeatedly:



  //-----------------------------------------------------------------------TEMPERATURE AND HUMIDITY--------------------------------------------------------------

 HumidityValue = dht.readHumidity();
 TemperatureValue = dht.readTemperature();
 Firebase.set("Home/Sensor/Temperature", TemperatureValue);
 Firebase.set("Home/Sensor/Humidity", HumidityValue);
 //Serial.println(TemperatureValue);          
 //delay(800);

 //-----------------------------------------------------------------------LIGHTS CONTROL--------------------------------------------------------------


//----------BATHROOM----------
BathroomValue = Firebase.getInt("Home/Light/Bathroom");
if (BathroomValue==1) {
//Serial.println("Bathroom ON");
digitalWrite(BathroomLight,HIGH);  
//return;
//delay(10);
}
else {
//Serial.println("Bathroom OFF");
digitalWrite(BathroomLight,LOW);  
}
//---------------------------------------

//----------KITCHEN----------
KitchenValue = Firebase.getInt("Home/Light/Kitchen");
if (BathroomValue==1) {
//Serial.println("Bathroom ON");
digitalWrite(KitchenLight,HIGH);  
//return;
//delay(10);
}
else {
//Serial.println("Bathroom OFF");
digitalWrite(KitchenLight,LOW);  
}
//---------------------------------------

//----------LIVINGROOM----------
LivingRoomValue = Firebase.getInt("Home/Light/LivingRoom");
if (LivingRoomValue==1) {
//Serial.println("Bathroom ON");
digitalWrite(LivingRoomLight,HIGH);  
//return;
//delay(10);
}
else {
//Serial.println("Bathroom OFF");
digitalWrite(LivingRoomLight,LOW);  
}

//----------COUPLEBED----------
CoupleBedValue = Firebase.getInt("Home/Light/CoupleBed");
if (CoupleBedValue==1) {
//Serial.println("Bathroom ON");
digitalWrite(CoupleBedLight,HIGH);  
//return;
//delay(10);
}
else {
//Serial.println("Bathroom OFF");
digitalWrite(CoupleBedLight,LOW);  
}

//----------SINGLEBED----------
SingleBedValue = Firebase.getInt("Home/Light/SingleBed");
if (SingleBedValue==1) {
//Serial.println("Bathroom ON");
digitalWrite(SingleBedLight,HIGH);  
//return;
//delay(10);
}
else {
//Serial.println("Bathroom OFF");
digitalWrite(SingleBedLight,LOW);  
}

//----------LAUNDRY----------
LaundryValue = Firebase.getInt("Home/Light/Laundry");
if (LaundryValue==1) {
//Serial.println("Bathroom ON");
digitalWrite(LaundryLight,HIGH);  
//return;
//delay(10);
}
else {
//Serial.println("Bathroom OFF");
digitalWrite(LaundryLight,LOW);  
}

//----------GARAGE----------
GarageValue = Firebase.getInt("Home/Light/Garage");
if (GarageValue==1) {
//Serial.println("Bathroom ON");
digitalWrite(GarageLight,HIGH);  
//return;
//delay(10);
}
else {
//Serial.println("Bathroom OFF");
digitalWrite(GarageLight,LOW);  
}

//-----------------------------------------------------------------------POWERPLUGS CONTROL--------------------------------------------------------------

//----------SHAVER----------
ShaverValue = Firebase.getInt("Home/PowerPlug/Shaver");
if (ShaverValue==1) {
//Serial.println("Bathroom ON");
digitalWrite(ShaverLight,HIGH);  
//return;
//delay(10);
}
else {
//Serial.println("Bathroom OFF");
digitalWrite(ShaverLight,LOW);  
}

//----------HAIRDRYER----------
HairDryerValue = Firebase.getInt("Home/PowerPlug/HairDryer");
if (HairDryerValue==1) {
//Serial.println("Bathroom ON");
digitalWrite(HairDryerLight,HIGH);  
//return;
//delay(10);
}
else {
//Serial.println("Bathroom OFF");
digitalWrite(HairDryerLight,LOW);  
}

  
}
