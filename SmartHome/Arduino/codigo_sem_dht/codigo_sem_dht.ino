#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>
#define FIREBASE_HOST "smarthomebylanga-15287.firebaseio.com"
#define FIREBASE_AUTH "WwR7MPQGw6IUzF1uGYoaRlNO0HNM1VEWc956aRQr"
#define WIFI_SSID "Casa_PC's"
#define WIFI_PASSWORD "CaroleGui"



int CoffeeMachinePlug = D0;
int ConsolePlug = D1;
int FridgePlug = D2;
int KitchenHoodPlug = D3;
int MicrowavePlug = D4;
int ModemPlug = D5;
int StovePlug = D6;
int TvBoxPlug = D7;
int TelephonePlug = D8;
int TelevisionPlug = D9;
//int digitaltx = D10;


int CoffeeMachineValue, ConsoleValue, FridgeValue, KitchenHoodValue, MicrowaveValue, ModemValue, StoveValue, TvBoxValue, TelephoneValue, TelevisionValue;



void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);

  pinMode(CoffeeMachinePlug, OUTPUT);
  pinMode(ConsolePlug, OUTPUT);
  pinMode(FridgePlug, OUTPUT);
  pinMode(KitchenHoodPlug, OUTPUT);
  pinMode(MicrowavePlug, OUTPUT);
  pinMode(ModemPlug, OUTPUT);
  pinMode(StovePlug, OUTPUT);
  pinMode(TvBoxPlug, OUTPUT);
  pinMode(TelephonePlug, OUTPUT);
  pinMode(TelevisionPlug, OUTPUT);

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




//-----------------------------------------------------------------------POWERPLUGS CONTROL--------------------------------------------------------------


//----------COFFEEMACHINE----------
CoffeeMachineValue = Firebase.getInt("Home/PowerPlug/CoffeeMachine");
if (CoffeeMachineValue==1) {
//Serial.println("Bathroom ON");
digitalWrite(CoffeeMachinePlug,HIGH);  
//return;
//delay(10);
}
else {
//Serial.println("Bathroom OFF");
digitalWrite(CoffeeMachinePlug,LOW);  
}
//---------------------------------------

//----------CONSOLE----------
ConsoleValue = Firebase.getInt("Home/PowerPlug/Console");
if (ConsoleValue==1) {
//Serial.println("Bathroom ON");
digitalWrite(ConsolePlug,HIGH);  
//return;
//delay(10);
}
else {
//Serial.println("Bathroom OFF");
digitalWrite(ConsolePlug,LOW);  
}
//---------------------------------------

//----------FRIDGE----------
FridgeValue = Firebase.getInt("Home/PowerPlug/Fridge");
if (FridgeValue==1) {
//Serial.println("Bathroom ON");
digitalWrite(FridgePlug,HIGH);  
//return;
//delay(10);
}
else {
//Serial.println("Bathroom OFF");
digitalWrite(FridgePlug,LOW);  
}

//----------KITCHENHOOD----------
KitchenHoodValue = Firebase.getInt("Home/PowerPlug/KitchenHood");
if (KitchenHoodValue==1) {
//Serial.println("Bathroom ON");
digitalWrite(KitchenHoodPlug,HIGH);  
//return;
//delay(10);
}
else {
//Serial.println("Bathroom OFF");
digitalWrite(KitchenHoodPlug,LOW);  
}

//----------MICROWAVE----------
MicrowaveValue = Firebase.getInt("Home/PowerPlug/Microwave");
if (MicrowaveValue==1) {
//Serial.println("Bathroom ON");
digitalWrite(MicrowavePlug,HIGH);  
//return;
//delay(10);
}
else {
//Serial.println("Bathroom OFF");
digitalWrite(MicrowavePlug,LOW);  
}

//----------MODEM----------
ModemValue = Firebase.getInt("Home/PowerPlug/Modem");
if (ModemValue==1) {
//Serial.println("Bathroom ON");
digitalWrite(ModemPlug,HIGH);  
//return;
//delay(10);
}
else {
//Serial.println("Bathroom OFF");
digitalWrite(ModemPlug,LOW);  
}

//----------STOVE----------
StoveValue = Firebase.getInt("Home/PowerPlug/Stove");
if (StoveValue==1) {
//Serial.println("Bathroom ON");
digitalWrite(StovePlug,HIGH);  
//return;
//delay(10);
}
else {
//Serial.println("Bathroom OFF");
digitalWrite(StovePlug,LOW);  
}


//----------TELEVISION----------
TelevisionValue = Firebase.getInt("Home/PowerPlug/Television");
if (TelevisionValue==1) {
//Serial.println("Bathroom ON");
digitalWrite(TelevisionPlug,HIGH);  
//return;
//delay(10);
}
else {
//Serial.println("Bathroom OFF");
digitalWrite(TelevisionPlug,LOW);  
}

//----------TVBOX----------
TvBoxValue = Firebase.getInt("Home/PowerPlug/TvBox");
if (TvBoxValue==1) {
//Serial.println("Bathroom ON");
digitalWrite(TvBoxPlug,HIGH);  
//return;
//delay(10);
}
else {
//Serial.println("Bathroom OFF");
digitalWrite(TvBoxPlug,LOW);  
}

//----------TELEPHONE----------
TelephoneValue = Firebase.getInt("Home/PowerPlug/Telephone");
if (TelephoneValue==1) {
//Serial.println("Bathroom ON");
digitalWrite(TelephonePlug,HIGH);  
//return;
//delay(10);
}
else {
//Serial.println("Bathroom OFF");
digitalWrite(TelephonePlug,LOW);  
}




  
}
