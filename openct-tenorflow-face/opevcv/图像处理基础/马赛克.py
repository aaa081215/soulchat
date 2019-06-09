import cv2
import numpy as np
img = cv2.imread('E:\\1.jpg',1)
imgInfo = img.shape
height = imgInfo[0]
width = imgInfo[1]
for m in range(100,500):
    for n in range(100,500):
        # pixel ->10*10
        if m%100 == 0 and n%100==0:
            for i in range(0,100):
                for j in range(0,100):
                    (b,g,r) = img[m,n]
                    img[i+m,j+n] = (b,g,r)
cv2.imshow('dst',img)
cv2.waitKey(0)