import cv2
img = cv2.imread('E:\\1.jpg',1)
imgInfo = img.shape
print(imgInfo)
height = imgInfo[0]
width = imgInfo[1]
mode = imgInfo[2]
# 1 放大 缩小 2 等比例 非 2:3
dstHeight = int(height*0.5)
dstWidth = int(width*0.5)
#最近临域插值 双线性插值 像素关系重采样 立方插值
dst = cv2.resize(img,(dstWidth,dstHeight))
cv2.imshow('image',dst)
cv2.waitKey(0)