import cv2
import tensorflow as tf
if __name__ == '__main__':
    # img = cv2.imread('E:\\1.jpg',0)
    # print(img.shape)
    img = cv2.imread('E:\\1.jpg',1)
    # dst= cv2.cvtColor(img,cv2.COLOR_BGR2GRAY)

    cv2.imshow('dst',img)
    cv2.waitKey(0)
