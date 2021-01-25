
#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>
//#include "DHT.h"
//#define DHTTYPE DHT11
#define FIREBASE_HOST "smarthomebylanga-15287.firebaseio.com"
#define FIREBASE_AUTH "WwR7MPQGw6IUzF1uGYoaRlNO0HNM1VEWc956aRQr"
#define WIFI_SSID "Casa"
#define WIFI_PASSWORD "carolegui"
//#define dht_dpin D6
//DHT dht(dht_dpin, DHTTYPE);

//PIN DECLARATION
int Central = D0;
//int Wall = D1;
//int Central = D2;
//int Fridge = D3;
//int KitchenHood = D4;
//int Stove = D5;
//int Microwave = D7;
//int WaterHeater = D8;
//int CoffeeMachine = D9;

//float gas = A0;

//LIGHTS VARIABLES

int CentralValue;
//int WallValue;
//int CentralValue;
//int FridgeValue;
//int KitchenHoodValue;
//int StoveValue;
//int MicrowaveValue;
//int WaterHeaterValue;
//int CoffeeMachineValue;

//int TemperatureValue, HumidityValue;



void setup() {
Serial.begin(9600);
//dht.begin();

pinMode(Central, OUTPUT); //D0
//pinMode(Wall, OUTPUT); //D1
//pinMode(Central, OUTPUT); //D2
//pinMode(Fridge, OUTPUT); //D3
//pinMode(KitchenHood, OUTPUT); //D4
//pinMode(Stove, OUTPUT); //D5
//pinMode(Microwave, OUTPUT); //D7
//pinMode(WaterHeater, OUTPUT); //D8
//pinMode(CoffeeMachine, OUTPUT); //D9
//pinMode(gas, INPUT); //A0

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
//Firebase.setInt("LIGHT/value", 0);
}


void loop() {

//-----------------------------------------------------------------------TEMPERATURE AND HUMIDITY--------------------------------------------------------------

/* HumidityValue = dht.readHumidity();
 TemperatureValue = dht.readTemperature();
 Firebase.set("Home/Sensor/Temperature", TemperatureValue);
 Firebase.set("Home/Sensor/Humidity", HumidityValue);           
    
  delay(800);
*/

//-----------------------------------------------------------------------GAS--------------------------------------------------------------

/*int gasvalue = analogRead(gas);
//Serial.println(gasvalue);*/
//-----------------------------------------------------------------------LIGHTS CONTROL--------------------------------------------------------------


//----------Island----------
CentralValue = Firebase.getInt("Home/LivingRoom/Light/Central");
if (CentralValue==1) {
//Serial.println("Light Master Bedroom ON");
digitalWrite(Central,HIGH);  
//return;
//delay(10);
}
else {
//Serial.println("Light Master Bedroom OFF");
digitalWrite(Central,LOW);  
}
//---------------------------------------



/*
//----------Wall----------
WallValue = Firebase.getInt("Home/Kitchen/Light/Wall");
if (WallValue==1) {
//Serial.println("Light Bedroom ON");
digitalWrite(Wall,HIGH);  
//return;
//delay(10);
}
else {
//Serial.println("Light Bedroom OFF");
digitalWrite(Wall,LOW);
}
//---------------------------------


//----------Center----------
CentralValue = Firebase.getInt("Home/Kitchen/Light/Central");
if (CentralValue==1) {
//Serial.println("Light Kitchen ON");
digitalWrite(Central,HIGH);  
//return;
//delay(10);
}
else {
//Serial.println("Light Kitchen OFF");
digitalWrite(Central,LOW);  
}
//---------------------------------

//-----------------------------------------------------------------------POWER PLUG CONTROL--------------------------------------------------------------

//----------Fridge----------
FridgeValue = Firebase.getInt("Home/Kitchen/PowerPlug/Fridge");
if (FridgeValue==1) {
//Serial.println("Light Kitchen ON");
digitalWrite(Fridge,HIGH);  
//return;
//delay(10);
}
else {
//Serial.println("Light Kitchen OFF");
digitalWrite(Fridge,LOW);  
}
//---------------------------------

//----------Kitchen Hood----------
KitchenHoodValue = Firebase.getInt("Home/Kitchen/PowerPlug/KitchenHood");
Serial.println(KitchenHoodValue);
if (KitchenHoodValue==1) {
digitalWrite(KitchenHood,HIGH);  
}
else {
digitalWrite(KitchenHood,LOW);
}
//------------------------------

//----------Stove----------
StoveValue = Firebase.getInt("Home/Kitchen/PowerPlug/Stove");
if (StoveValue==1) {
//Serial.println("Light Bedroom ON");
digitalWrite(Stove,HIGH);  
//return;
//delay(10);
}
else {
//Serial.println("Light Bedroom OFF");
digitalWrite(Stove,LOW);
}
//------------------------------

//----------Microwave----------
MicrowaveValue = Firebase.getInt("Home/Kitchen/PowerPlug/Microwave");
if (MicrowaveValue==1) {
//Serial.println("Light Bedroom ON");
digitalWrite(Microwave,HIGH);  
//return;
//delay(10);
}
else {
//Serial.println("Light Bedroom OFF");
digitalWrite(Microwave,LOW);
}
//------------------------------

//----------Water Heater----------
WaterHeaterValue = Firebase.getInt("Home/Kitchen/PowerPlug/WaterHeater");
if (WaterHeaterValue==1) {
Serial.println(WaterHeaterValue);
digitalWrite(WaterHeater,HIGH);  
//return;
//delay(10);
}
else {
//Serial.println("Light Bedroom OFF");
digitalWrite(WaterHeater,LOW);
}
//------------------------------

//----------Coffee Machine----------
CoffeeMachineValue = Firebase.getInt("Home/Kitchen/PowerPlug/CoffeeMachine");
if (CoffeeMachineValue==1) {
Serial.println(CoffeeMachineValue);
digitalWrite(CoffeeMachine,HIGH);  
//return;
//delay(10);
}
else {
//Serial.println("Light Bedroom OFF");
digitalWrite(CoffeeMachine,LOW);
}
//------------------------------

*/

//---------------------------------------------------------------------








}
