#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>
#define FIREBASE_HOST "smarthomebylanga-15287.firebaseio.com"
#define FIREBASE_AUTH "WwR7MPQGw6IUzF1uGYoaRlNO0HNM1VEWc956aRQr"
#define WIFI_SSID "Casa_PC's"
#define WIFI_PASSWORD "CaroleGui"


int  KitchenValue;

const int GPIO_0 = 0;

void setup() {
  // put your setup code here, to run once:
pinMode(GPIO_0, OUTPUT);
  
Serial.begin(9600);
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
}

void loop() {
  // put your main code here, to run repeatedly:





 //-----------------------------------------------------------------------LIGHTS CONTROL--------------------------------------------------------------




//----------KITCHEN----------
KitchenValue = Firebase.getInt("Home/Light/Kitchen");
if (KitchenValue==1) {
Serial.println("Bathroom ON");
digitalWrite(GPIO_0,HIGH);  
//return;
//delay(10);
}
else {
Serial.println("Bathroom OFF");
digitalWrite(GPIO_0,LOW);  
}
//---------------------------------------
Firebase.set("Teste/teste/teste", 0);
  
}
