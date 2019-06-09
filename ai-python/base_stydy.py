import Tensorflow实战股票预测 as tf
import cv2
import numpy as np
if __name__ == '__main__':

    # m1=tf.constant([3,5])
    # m2=tf.Variable(2,dtype=tf.int32)
    # data1=tf.Variable(10,name='var')
    # res=tf.add(m1,m1)
    # sess=tf.Session()
    # print(sess.run(m1))
    # print(data1)
    # init = tf.global_variables_initializer()
    # img = cv2.imread('img/1.jpg')
    # print (img.shape)

    ##四则运算
    # m1=tf.constant(2)
    # m2=tf.constant(3)
    # dataAdd= tf.add(m1,m2)
    # dataMul= tf.multiply(m1,m2)
    # dataSub= tf.subtract(m1,m2)
    # dataDiv= tf.divide(m1,m2)
    #
    # with tf.Session() as sess:
    #     print(sess.run(dataAdd))
    #     print(sess.run(dataMul))
    #     print(sess.run(dataSub))
    #     print(sess.run(dataDiv))
    # print('end')

    #占位符
    data3 = tf.placeholder(tf.float32)
    data4 = tf.placeholder(tf.float32)
    dataAdd = tf.add(data3,data4)
    with tf.Session() as sess:
        print(sess.run(dataAdd,feed_dict={data3:6,data4:5}))
    print('end')

    #矩阵
    # d1= tf.constant([[1,2]])
    # d2= tf.constant([[1],[1]])
    # d3= tf.constant([[1,2],[1,2],[1,2]])
    # mat = tf.matmul(d1,d2)
    # with tf.Session() as sess:
    #     print(sess.run(mat))
    # print('end')

    # numpy
    n1 = np.array({1,2,3,4})
    n2 = np.array([[1,2],[3,4]])

    n4 = np.ones([2,2])
    print(n4+n2)
