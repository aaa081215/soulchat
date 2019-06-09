# coding:utf-8
import datetime
import random
from flask import Flask, render_template, request, redirect, url_for, jsonify
from werkzeug.utils import secure_filename
from flask_cors import CORS, cross_origin
import os
import cv2
import numpy as np

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
    #meibai(new_filename)
    #gaosi(new_filename)
    ai(new_filename)
    return new_filename

@app.route('/upload2', methods=['POST', 'GET'])
def upload():

    f = request.files['file']
    ext = f.filename.rsplit('.', 1)[1]
    new_filename =create_uuid() + '.' + ext
    basepath = os.path.dirname(__file__)  # 当前文件所在路径
    upload_path = os.path.join(basepath, 'static',secure_filename(new_filename))  #注意：没有的文件夹一定要先创建，不然会提示没有该路径
    f.save(upload_path)
    #meibai(new_filename)
    #gaosi(new_filename)
    ai(new_filename)
    return new_filename


@app.route('/upload3', methods=['POST', 'GET'])
def upload():

    f = request.files['file']
    ext = f.filename.rsplit('.', 1)[1]
    new_filename =create_uuid() + '.' + ext
    basepath = os.path.dirname(__file__)  # 当前文件所在路径
    upload_path = os.path.join(basepath, 'static',secure_filename(new_filename))  #注意：没有的文件夹一定要先创建，不然会提示没有该路径
    f.save(upload_path)
    #meibai(new_filename)
    #gaosi(new_filename)
    ai(new_filename)
    return new_filename

def create_uuid(): #生成唯一的图片的名称字符串，防止图片显示时的重名问题
    nowTime = datetime.datetime.now().strftime("%Y%m%d%H%M%S");  # 生成当前时间
    randomNum = random.randint(0, 100);  # 生成的随机整数n，其中0<=n<=100
    if randomNum <= 10:
        randomNum = str(0) + str(randomNum);
    uniqueNum = str(nowTime) + str(randomNum);
    return uniqueNum;

def meibai(new_filename):
    img = cv2.imread('static\\'+new_filename,1)
    imgInfo = img.shape
    height = imgInfo[0]
    width = imgInfo[1]
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
    cv2.imwrite('static\\p1\\'+new_filename,dst)
    print("meibai")

def gaosi(new_filename):
    img = cv2.imread('static\\'+new_filename,1)
    imgInfo = img.shape
    height = imgInfo[0]
    width = imgInfo[1]
    dst = np.zeros((height,width,3),np.uint8)
    for i in range(3,height-3):
        for j in range(3,width-3):
            sum_b = int(0)
            sum_g = int(0)
            sum_r = int(0)
            for m in range(-3,3):#-3 -2 -1 0 1 2
                for n in range(-3,3):
                    (b,g,r) = img[i+m,j+n]
                    sum_b = sum_b+int(b)
                    sum_g = sum_g+int(g)
                    sum_r = sum_r+int(r)

            b = np.uint8(sum_b/36)
            g = np.uint8(sum_g/36)
            r = np.uint8(sum_r/36)
            dst[i,j] = (b,g,r)
    cv2.imwrite('static\\p2\\'+new_filename,dst)
    print("gaosi")
def ai(new_filename):
    print("Ai...")
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

    text =  str(len(faces)) + 'face'
    print("1111")
    org = (40, 80)

    fontFace = cv2.FONT_HERSHEY_COMPLEX

    fontScale = 1

    fontcolor = (128, 0, 128)  # BGR

    thickness = 1

    lineType = 4
    bottomLeftOrigin = 1
    # cv.putText(img, text, org, fontFace, fontScale, fontcolor, thickness, lineType, bottomLeftOrigin)

    cv2.putText(imgInit, text, org, fontFace, fontScale, fontcolor, thickness, lineType)

    cv2.imwrite('static\\p3\\'+new_filename,imgInit)

if __name__ == '__main__':
    app.run(
        port= 80,
        debug=True)