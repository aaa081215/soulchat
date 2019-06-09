# coding:utf-8
import datetime
import random
from flask import Flask, render_template, request, redirect, url_for, jsonify
from werkzeug.utils import secure_filename
from flask_cors import CORS, cross_origin
import os
import cv2


app = Flask(__name__)
CORS(app, resources=r'/*')

@app.route('/upload', methods=['POST', 'GET'])
def upload():

    f = request.files['file']
    ext = f.filename.rsplit('.', 1)[1]
    new_filename =create_uuid() + '.' + ext
    basepath = os.path.dirname(__file__)  # 当前文件所在路径
    upload_path = os.path.join(basepath, 'static',secure_filename(new_filename))  #注意：没有的文件夹一定要先创建，不然会提示没有该路径
    f.save(upload_path)
    resapp=ai(new_filename)
    print(resapp)
    return new_filename+'##'+str(resapp)


def create_uuid(): #生成唯一的图片的名称字符串，防止图片显示时的重名问题
    nowTime = datetime.datetime.now().strftime("%Y%m%d%H%M%S");  # 生成当前时间
    randomNum = random.randint(0, 100);  # 生成的随机整数n，其中0<=n<=100
    if randomNum <= 10:
        randomNum = str(0) + str(randomNum);
    uniqueNum = str(nowTime) + str(randomNum);
    return uniqueNum;

def ai(new_filename):

    retres=''
    face_xml = cv2.CascadeClassifier('haarcascade_frontalface_default.xml')
    eye_xml = cv2.CascadeClassifier('haarcascade_eye.xml')
    # load jpg
    imgInit = cv2.imread('static\\'+new_filename,1)
    imgInfo = imgInit.shape
    height = imgInfo[0]
    width = imgInfo[1]
    mode = imgInfo[2]
    # haar gray
    gray = cv2.cvtColor(imgInit, cv2.COLOR_BGR2GRAY)
    # detect faces 1 data 2 scale 3 5
    faces = face_xml.detectMultiScale(gray, 1.3, 3)
    if len(faces)==0:
        retres='-100'
    # draw
    elif len(faces)>1:
        retres='100'

    for (x, y, w, h) in faces:
        cv2.rectangle(imgInit, (x, y), (x + w, y + h), (255, 0, 0), 2)
        cv2.line(imgInit, (x, y), (x + w, y + h), (255, 0, 0), 1)
        roi_face = gray[y:y + h, x:x + w]
        roi_color = imgInit[y:y + h, x:x + w]
        # 1 gray
        eyes = eye_xml.detectMultiScale(roi_face)
        if len(eyes)!=2:
            retres='-200'
        leay=eyes[0][0]+(eyes[0][2]/2)
        lh=eyes[0][1]+(eyes[0][3]/2)
        reay=eyes[1][0]+(eyes[1][2]/2)
        rh=eyes[1][1]+(eyes[1][3]/2)
        retres=(abs(leay-reay)/faces[0][2])
        cv2.line(roi_color, (int(leay), int(lh)), (int(reay), int(rh)), (0, 255, 0), 3) #5
        for (e_x, e_y, e_w, e_h) in eyes:
            cv2.rectangle(roi_color, (e_x, e_y), (e_x + e_w, e_y + e_h), (0, 255, 0), 3)

    text =  ''
    org = (40, 80)
    fontFace = cv2.FONT_HERSHEY_COMPLEX
    fontScale = 1
    fontcolor = (128, 0, 128)  # BGR
    thickness = 1
    lineType = 4
    cv2.putText(imgInit, text, org, fontFace, fontScale, fontcolor, thickness, lineType)

    cv2.imwrite('static\\p3\\'+new_filename,imgInit)
    return retres

if __name__ == '__main__':
    app.run(
        port= 80,
        debug=True)