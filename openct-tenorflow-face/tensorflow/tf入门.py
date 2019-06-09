import cv2
import tensorflow as tf
import matplotlib.pyplot as plt

import numpy as np
if __name__ == '__main__':
    data1 = tf.constant(2.5)
    data2 = tf.Variable(10,name='var')
    sess = tf.Session();
    init = tf.global_variables_initializer()
    sess.run(init)
    # print(sess.run(data2))
    # print(data1)
    # print(data2)

    #加法
    data3 = tf.placeholder(tf.float32)
    data4 = tf.placeholder(tf.float32)
    dataAdd = tf.add(data3,data4)
    #print(sess.run(dataAdd,feed_dict={data3:1,data4:2}))

    #矩阵
    d1= tf.constant([[1,2]])
    d2= tf.constant([[1],[1]])
    d3=tf.constant([[1,2],[1,2],[1,2]])
    # print(d3.shape)
    # print(sess.run(d3[0]))
    # print(sess.run(d3[:,0]))
    # print(sess.run(d3))

    n1 = np.array({1,2,3,4})
    n2 = np.array([[1,2],[3,4]])

    n4 = np.ones([2,2])
    print(n4+n2)

    x= np.array([1,2,3,4,5,6])
    y= np.array([1,1,1,1,1,1])
    img = plt.plot(x,y,'r')
    plt.savefig('E:\\temp.png');
