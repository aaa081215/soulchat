import cv2

# load xml 1 file name
face_xml = cv2.CascadeClassifier('haarcascade_frontalface_default.xml')
eye_xml = cv2.CascadeClassifier('haarcascade_eye.xml')
# load jpg
imgInit = cv2.imread('q.jpg')
imgInfo = imgInit.shape
height = imgInfo[0]
width = imgInfo[1]
mode = imgInfo[2]
dstHeight = int(height * 0.4)
dstWidth = int(width * 0.4)
imgshow = cv2.resize(imgInit, (dstWidth, dstHeight))
cv2.imshow('src', imgshow)
# haar gray
gray = cv2.cvtColor(imgInit, cv2.COLOR_BGR2GRAY)
# detect faces 1 data 2 scale 3 5
faces = face_xml.detectMultiScale(gray, 1.2, 3)
print('face=', len(faces))
# draw
for (x, y, w, h) in faces:
    cv2.rectangle(imgInit, (x, y), (x + w, y + h), (255, 0, 0), 2)
    roi_face = gray[y:y + h, x:x + w]
    roi_color = imgInit[y:y + h, x:x + w]
    # 1 gray
    eyes = eye_xml.detectMultiScale(roi_face)
    print('eye=', len(eyes))
    for (e_x, e_y, e_w, e_h) in eyes:
        cv2.rectangle(roi_color, (e_x, e_y), (e_x + e_w, e_y + e_h), (0, 255, 0), 2)
imgshowdst = cv2.resize(imgInit, (dstWidth, dstHeight))
text = 'all' + str(len(faces)) + 'boss'
org = (40, 80)
fontFace = cv2.FONT_HERSHEY_COMPLEX
fontScale = 1
fontcolor = (128, 0, 128)  # BGR
thickness = 1
lineType = 4
bottomLeftOrigin = 1
# cv.putText(img, text, org, fontFace, fontScale, fontcolor, thickness, lineType, bottomLeftOrigin)
cv2.putText(imgshowdst, text, org, fontFace, fontScale, fontcolor, thickness, lineType)
cv2.imshow('dst', imgshowdst)
cv2.waitKey(0)
