import Tensorflow实战股票预测 as tf
import cv2
import numpy as np
import matplotlib.pyplot as plt
if __name__ == '__main__':
    # # 准备数据
    rand1 = np.array([[155,48],[159,50],[164,53],[168,56],[172,60]])
    rand2 = np.array([[152,53],[156,55],[160,56],[172,64],[176,65]])
    rand3 = np.array([[252,53],[256,55],[260,56],[272,64],[276,65]])
    # 2 label
    label = np.array([[0],[0],[0],[0],[0],[1],[1],[1],[1],[1],[2],[2],[2],[2],[2]])

    # 3 数据
    data = np.vstack((rand1,rand2,rand3))
    data = np.array(data,dtype='float32')

    # svm的label
    # 监督学习
    # 训练
    svm = cv2.ml.SVM_create()  #支持向量机
    #属性设置
    svm.setType(cv2.ml.SVM_C_SVC) # svm 类型
    svm.setKernel(cv2.ml.SVM_LINEAR) # 线性
    svm.setC(0.01)
    # 训练
    result = svm.train(data,cv2.ml.ROW_SAMPLE,label)
    # 预测
    pt_data = np.vstack([[250,55]]) #0 女生 1男生
    pt_data = np.array(pt_data,dtype='float32')
    (par1) = svm.predict(pt_data)
    print(par1)



