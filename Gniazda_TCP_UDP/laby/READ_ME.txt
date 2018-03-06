 zadanie 1
Zaimplementowaæ dwukierunkow¹ komunikacjê przez UDP Java-Java – Klient wysy³a wiadomoœæ i odczytuje odpowiedŸ – 
Serwer otrzymuje wiadomoœæ i wysy³a odpowiedŸ – Nale¿y pobraæ adres nadawcy z otrzymanego datagramu 

zadanie2
 Zaimplementowaæ komunikacjê przez UDP pomiêdzy jêzykami Java i Python – 
JavaUdpServer + PythonUdpClient – 
Nale¿y przes³aæ wiadomoœæ tekstow¹:  ‘¿ó³ta gêœ’ (uwaga na kodowanie) 

zadanie 3

 Zaimplementowaæ przesy³ wartoœci liczbowej w przypadku JavaUdpServer + PythonUdpClient – 
Symulujemy komunikacjê z platform¹ o innej kolejnoœci bajtów: klient Python ma wys³aæ nastêpuj¹cy ci¹g bajtów: 
msg_bytes = (300).to_bytes(4, byteorder='little') –
 Server Javy ma wypisaæ otrzyman¹ liczbê oraz odes³aæ liczbê zwiêkszon¹ o jeden

zadanie 4

Zaimplementowaæ serwer (Java lub Python) który rozpoznaje czy otrzyma³ wiadomoœæ od klienta Java czy od klienta Python 
i wysy³a im ró¿ne odpowiedzi (np. ‘Pong Java’, ‘Pong Python’) 