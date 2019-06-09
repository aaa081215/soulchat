import cv2
import tensorflow as tf
if __name__ == '__main__':
    img = cv2.imread('E:\\1.jpg',1)
    cv2.imwrite('E:\\1.jpg',img)
    # 压缩
    cv2.imwrite('E:\\2.jpg',img,[cv2.IMWRITE_JPEG_QUALITY,50])
    # 写入
    for i in range(1,100):
       img[10+i,i] = (255,0,0)
    cv2.imshow('image',img)
    cv2.waitKey(0)
