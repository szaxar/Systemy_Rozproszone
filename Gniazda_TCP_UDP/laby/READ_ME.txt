 zadanie 1
Zaimplementowa� dwukierunkow� komunikacj� przez UDP Java-Java � Klient wysy�a wiadomo�� i odczytuje odpowied� � 
Serwer otrzymuje wiadomo�� i wysy�a odpowied� � Nale�y pobra� adres nadawcy z otrzymanego datagramu 

zadanie2
 Zaimplementowa� komunikacj� przez UDP pomi�dzy j�zykami Java i Python � 
JavaUdpServer + PythonUdpClient � 
Nale�y przes�a� wiadomo�� tekstow�:  ���ta g꜒ (uwaga na kodowanie) 

zadanie 3

 Zaimplementowa� przesy� warto�ci liczbowej w przypadku JavaUdpServer + PythonUdpClient � 
Symulujemy komunikacj� z platform� o innej kolejno�ci bajt�w: klient Python ma wys�a� nast�puj�cy ci�g bajt�w: 
msg_bytes = (300).to_bytes(4, byteorder='little') �
 Server Javy ma wypisa� otrzyman� liczb� oraz odes�a� liczb� zwi�kszon� o jeden

zadanie 4

Zaimplementowa� serwer (Java lub Python) kt�ry rozpoznaje czy otrzyma� wiadomo�� od klienta Java czy od klienta Python 
i wysy�a im r�ne odpowiedzi (np. �Pong Java�, �Pong Python�) 