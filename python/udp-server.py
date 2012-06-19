from socket import *

PORT = 12345 # arbitrary, just make it match in Android code
IP = "" # represents any IP address

sock = socket(AF_INET, SOCK_DGRAM) # SOCK_DGRAM means UDP socket
sock.setsockopt(SOL_SOCKET, SO_REUSEADDR, 1) # make socket reuseable
sock.bind((IP, PORT))

while True:
    print "Waiting for data..."
    data, addr = sock.recvfrom(1024) # blocking
    print "received: " + data
