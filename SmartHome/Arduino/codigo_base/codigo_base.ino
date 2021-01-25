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


int digital0 = D0;
int digital1 = D1;
int digital2 = D2;
int digital3 = D3;
int digital4 = D4;
int digital5 = D5;
//int digital6 = D6;
int digital7 = D7;
int digital8 = D8;
int digitalrx = D9;
//int digitaltx = D10;

int HumidityValue, TemperatureValue;




void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  dht.begin();

  pinMode(digital0, OUTPUT);
  pinMode(digital1, OUTPUT);
  pinMode(digital2, OUTPUT);
  pinMode(digital3, OUTPUT);
  pinMode(digital4, OUTPUT);
  pinMode(digital5, OUTPUT);
  //pinMode(digital6, OUTPUT);
  pinMode(digital7, OUTPUT);
  pinMode(digital8, OUTPUT);
  pinMode(digitalrx, OUTPUT);

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

  //digitalWrite(digital6, LOW);
  digitalWrite(digital7, LOW);
  digitalWrite(digital8, LOW);
  digitalWrite(digitalrx, LOW);

  //-----------------------------------------------------------------------TEMPERATURE AND HUMIDITY--------------------------------------------------------------

 HumidityValue = dht.readHumidity();
 TemperatureValue = dht.readTemperature();
 Firebase.set("Home/Sensor/Temperature", TemperatureValue);
 Firebase.set("Home/Sensor/Humidity", HumidityValue);
 Serial.println(TemperatureValue);          
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


  
}
