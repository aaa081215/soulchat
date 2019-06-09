import cv2
import numpy as np
img = cv2.imread('E:\\1.jpg',1)
imgInfo = img.shape
height = imgInfo[0]
width = imgInfo[1]
gray = cv2.cvtColor(img,cv2.COLOR_BGR2GRAY)
dst = np.zeros((height,width,1),np.uint8)
for i in range(0,height):
    for j in range(0,width):
        grayPixel = gray[i,j]
        dst[i,j] = 255-grayPixel
cv2.imshow('dst',dst)
cv2.waitKey(0)
