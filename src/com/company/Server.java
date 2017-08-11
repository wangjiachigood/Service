package com.company;


import com.alibaba.fastjson.JSONArray;
import com.company.bean.OrderListBean;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by panic .
 * Time 2017/7/18
 * description:
 */
public class Server extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel panel;
    private JButton button;


    private MqttClient client;
    private String host = "tcp://10.15.235.28:61613";
    private String userName = "admin";
    private String passWord = "password";
    private MqttTopic topic;
    private MqttMessage message;


    private String myTopic = "test/topicc";
    private ArrayList<OrderListBean> orderListBeenList;
    private int count = 0;

    public Server() {


        try {
            client = new MqttClient(host, "com.company.Server", new MemoryPersistence());
            connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Container container = this.getContentPane();
        panel = new JPanel();
        button = new JButton("发布话题");
        button.addActionListener(ae -> {
            count++;
            try {
                if (count == 9) {
                    count = 0;
                    orderListBeenList.clear();
                    OrderListBean orderListBean = new OrderListBean();
                    orderListBean.eatNumber = "A100";
                    orderListBean.tableName = "大厅" + count + "号";
                    orderListBean.totalprice = 1.0*count;
                    orderListBean.isInvoice = 0;
                    orderListBean.createTime = new Date().getTime();
                    orderListBean.attribute = 0001 + "";
                    orderListBean.eatStyle = "2";
                    orderListBeenList.add(orderListBean);
                }

                if (!orderListBeenList.isEmpty()) {
                    OrderListBean orderListBean = new OrderListBean();
                    orderListBean.eatNumber = "A"+count+count+1;
                    orderListBean.tableName = "大厅" + count + "号";
                    orderListBean.totalprice = 1.0*count;
                    orderListBean.isInvoice = 0;
                    orderListBean.createTime = new Date().getTime();
                    orderListBean.attribute = 0001 + "";
                    orderListBean.eatStyle = "2";
                    orderListBeenList.add(orderListBean);
                    String s = JSONArray.toJSONString(orderListBeenList);
                    System.out.println("点击发布消息---->>>" + s);
                    message.setPayload(s.getBytes());
                }


                MqttDeliveryToken token = topic.publish(message);
                token.waitForCompletion();
                System.out.println(token.isComplete() + "========");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        panel.add(button);
        container.add(panel, "North");


    }


    private void connect() {


        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
        options.setUserName(userName);
        options.setPassword(passWord.toCharArray());
// 设置超时时间
        options.setConnectionTimeout(10);
// 设置会话心跳时间
        options.setKeepAliveInterval(20);
        try {
            client.setCallback(new MqttCallback() {


                @Override
                public void connectionLost(Throwable cause) {
                    System.out.println("connectionLost-----------");
                }


                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    System.out.println("deliveryComplete---------"
                            + token.isComplete());
                }


                @Override
                public void messageArrived(String topic, MqttMessage arg1)
                        throws Exception {
                    System.out.println("messageArrived----------");


                }
            });


            topic = client.getTopic(myTopic);


            message = new MqttMessage();
            message.setQos(1);
            message.setRetained(true);
            System.out.println(message.isRetained() + "------ratained状态");
//            String s = "{\"code\":200,\"model\":[{\"orderId\":10901000000021128,\"orderNo\":\"1707180000000045\",\"payType\":1,\"orderType\":4,\"phone\":\"15921848427\",\"createTime\":1500446734000,\"peopleNumber\":null,\"totalprice\":0.01,\"status\":4,\"isRead\":1,\"eatNumber\":\"K029\",\"fsShopGUID\":\"e22f0e09-dc23-4a3f-921e-dec57c17c7f6\",\"attribute\":\"0001\",\"tableName\":\"A01\",\"eatStyle\":1,\"eatTime\":null,\"isInvoice\":0,\"isPrint\":1,\"printType\":0}],\"extra\":{},\"message\":\"OK\",\"errorCode\":0,\"time\":1500458712997}";
            orderListBeenList = new ArrayList<>();
            for (int i = 0; i < 1; i++) {
                OrderListBean orderListBean = new OrderListBean();
                orderListBean.eatNumber = "A100";
                orderListBean.tableName = "大厅8号";
                orderListBean.totalprice = i;
                orderListBean.isInvoice = i % 2 == 0 ? 1 : 0;
                orderListBean.createTime = new Date().getTime();
                orderListBean.attribute = 0000 + "";
                orderListBean.eatStyle = i % 2 == 0 ? "1" : "3";
                orderListBeenList.add(orderListBean);
            }
            String s = JSONArray.toJSONString(orderListBeenList);
            System.out.println("初始化发布消息---->>>" + s);
            message.setPayload(s.getBytes());


            client.connect(options);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static void main(String[] args) {
        Server s = new Server();
        s.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        s.setSize(600, 370);
        s.setLocationRelativeTo(null);
        s.setVisible(true);
    }
}