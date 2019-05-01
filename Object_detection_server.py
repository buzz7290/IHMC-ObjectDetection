import Object_Detector
import socket
import threading

host = ""
localHost = "127.0.0.1"
port = 65535
numOfClients = 1
buff = 1024

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind((localHost, port))
s.listen(numOfClients)
print("Waiting for client...")
clientsocket, addr = s.accept()
print("Connected")
while True:
    try:
        Object_Detector.sendroi(clientsocket, buff)
    except:
        print('Client disconnected. (if not client issue, check unpack requirement in image_util.convertDimensionToInt)')
        print("Waiting for client...")
        clientsocket, addr = s.accept()
        print("Connected")
