import cv2

cap = cv2.VideoCapture("1.mp4")  # 获取一个视频打开cap 1 file name
isOpened = cap.isOpened  # 判断是否打开‘
print(isOpened)
fps = cap.get(cv2.CAP_PROP_FPS)  # 帧率
width = int(cap.get(cv2.CAP_PROP_FRAME_WIDTH))  # w h
height = int(cap.get(cv2.CAP_PROP_FRAME_HEIGHT))
print(fps, width, height)
i = 0
while (isOpened):
    if i == 820:
        break
    else:
        i = i + 1
    (flag, frame) = cap.read()  # 读取每一张 flag frame
    fileName = 'imgdate2/pos/'+str(i+1)+'.jpg'
    print(fileName)
    if flag == True:
        res=cv2.resize(frame,(64,128),interpolation=cv2.INTER_CUBIC)
        cv2.imwrite(fileName, res, [cv2.IMWRITE_JPEG_QUALITY, 100])
print('end!')
