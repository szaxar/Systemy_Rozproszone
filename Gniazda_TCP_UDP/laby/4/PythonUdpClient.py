import socket;

serverIP = "127.0.0.1"
serverPort = 9008
msg = "2"

print('PYTHON UDP CLIENT')
client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
client.sendto(bytes(msg, 'utf-8'), (serverIP, serverPort))

while True:

    buff, address = client.recvfrom(1024)
    print(buff)


