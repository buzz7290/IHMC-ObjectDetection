import numpy as np
import cv2
from utils import object_enum as obj_enum

def convertDimensionsToInt(byteString):
    import struct
    intList = struct.unpack(">III", byteString)
    size = intList[0]
    width = intList[1]
    height = intList[2]
    return size, width, height


def receiveImageDataAsMat(clientsocket, buff):
    imageData = []
    imageSize, width, height = convertDimensionsToInt(clientsocket.recv(12))
    imageSizeCounter = imageSize
    imageSize_print_to_confirm = 0
    imageMat = np.zeros([height, width, 3])
    indexOffset = 0

    while imageSizeCounter > 0:
        imageDataChunk = clientsocket.recv(buff)
        imageData += imageDataChunk
        imageSizeCounter -= len(imageDataChunk)
        imageSize_print_to_confirm += len(imageDataChunk)
    print('image size ' + str(imageSize_print_to_confirm) + " is received")
    for r in range(height):
        for c in range(width):
            indexOffset = indexOffset + 1
            for z in range(3):
                imageMat[r][c][z] = imageData[indexOffset]
                indexOffset = indexOffset + 1
    return imageMat

def getDetectionBoxCoordinates(coordinates):
    split = ','
    num_object_detected = len(coordinates[0])
    if num_object_detected == 0:
        return "0,0,0,0,0" + '\n'
    coord_to_send = ''
    for object_index in range(num_object_detected):
        objects_in_number = obj_enum.obj_name_to_int(coordinates[4][object_index])
        coord_to_send = coord_to_send + str(objects_in_number) + split
        for x in range(4):
            coord_to_send = coord_to_send + str(coordinates[x][object_index]) + split
    return coord_to_send + '\n' #'\n' lets BufferedReader in java know this is the end of stream
