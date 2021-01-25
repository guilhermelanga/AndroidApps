
#include "FirebaseESP8266.h"
#include <ESP8266WiFi.h>
#include "DHT.h"
#define DHTTYPE DHT11
#define FIREBASE_HOST "https://smarthomebylanga-15287.firebaseio.com/"
#define FIREBASE_AUTH "WwR7MPQGw6IUzF1uGYoaRlNO0HNM1VEWc956aRQr"
#define WIFI_SSID "Casa"
#define WIFI_PASSWORD "carolegui"
#define dht_dpin D6
DHT dht(dht_dpin, DHTTYPE);

//PIN DECLARATION
int Island = D0;
int Wall = D1;
int Central = D2;
int Fridge = D3;
int KitchenHood = D4;
int Stove = D5;
int Microwave = D7;
int WaterHeater = D8;
int CoffeeMachine = D9;

String myString;

float gas = A0;

//LIGHTS VARIABLES

int IslandValue;
int WallValue;
int CentralValue;
int FridgeValue;
int KitchenHoodValue;
int StoveValue;
int MicrowaveValue;
int WaterHeaterValue;
int CoffeeMachineValue;

int TemperatureValue, HumidityValue;
 int a;


void setup() {
Serial.begin(9600);
dht.begin();

pinMode(Island, OUTPUT); //D0
pinMode(Wall, OUTPUT); //D1
pinMode(Central, OUTPUT); //D2
pinMode(Fridge, OUTPUT); //D3
pinMode(KitchenHood, OUTPUT); //D4
pinMode(Stove, OUTPUT); //D5
pinMode(Microwave, OUTPUT); //D7
pinMode(WaterHeater, OUTPUT); //D8
pinMode(CoffeeMachine, OUTPUT); //D9
pinMode(gas, INPUT); //A0

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

a=Firebase.getInt("logs/num");
if(Firebase.failed()){
  Serial.println(Firebase.error());
}else{
  Serial.println(a);
}




}
