import cv2
import numpy as np
img = cv2.imread('E:\\1.png',1)
imgInfo = img.shape
height = imgInfo[0]
width = imgInfo[1]
cv2.imshow('src',img)
dst = np.zeros((height,width,3),np.uint8)
for i in range(0,height):
    for j in range(0,width):
        (b,g,r) = img[i,j]
        bb = int(b*1.3)+10
        gg = int(g*1.2)+15

        if bb>255:
            bb = 255
        if gg>255:
            gg = 255

        dst[i,j] = (bb,gg,r)
cv2.imshow('dst',dst)
cv2.waitKey(0)