package com.tiffany.udpclient;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity  {

    private UDPClient client = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button start = (Button) findViewById(R.id.start);
        Button stop = (Button) findViewById(R.id.stop);
        Button send = (Button) findViewById(R.id.send);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent =new Intent(MainActivity.this,Myservice.class);
                startService(startIntent);//启动服务

                //建立线程池
//                udp连接
                ExecutorService exec = Executors.newCachedThreadPool();
                client = new UDPClient();
                exec.execute(client);
//                btn_UdpClose.setEnabled(true);
//                btn_UdpConn.setEnabled(false);
//                btn_Send.setEnabled(true);
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stopIntent =new Intent(MainActivity.this,Myservice.class);
                stopService(stopIntent);//关闭服务

//                udp关闭
                client.setUdpLife(false);
            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
//                        Message message = new Message();
//                        message.what = 2;
//                        if (edit_Send.getText().toString()!=""){
                            client.send("sss");
//                            message.obj = edit_Send.getText().toString();
//                            myHandler.sendMessage(message);
//                        }

                    }
                });
                thread.start();

//                turnOnUdpClient();
//                connectServerWithUDPSocket();
//                sendUdp();
            }

        });
    }

    protected void sendUdp(){
        Thread sendDate = new Thread() {
            @Override
            public void run() {
                String serverString = "10.71.66.2";
                int port = 9527;

                Log.d("adam", "Debug");

                DatagramSocket socket = null ;

                String msg = "Hello World!";

                try {
                    socket = new DatagramSocket() ;

                    InetAddress host = InetAddress.getByName(serverString);
                    byte [] data = msg.getBytes() ;
                    DatagramPacket packet = new DatagramPacket( data, data.length, host, port );
                    Log.d("adam", "Debug2");

                    socket.send(packet) ;

                    Log.d("adam", "Packe、t sent" );
                } catch( Exception e )
                {
                    Log.d("adam", "Exception");
                    Log.e("adam", Log.getStackTraceString(e));
                }
                finally
                {
                    Log.d("adam", "finally" );
                    if( socket != null ) {
                        socket.close();
                    }
                }
            }
        };
        sendDate.start();
    }

    protected void connectServerWithUDPSocket() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                DatagramSocket socket;
                try {
                    //创建DatagramSocket对象并指定一个端口号，注意，如果客户端需要接收服务器的返回数据,
                    //还需要使用这个端口号来receive，所以一定要记住
                    socket = new DatagramSocket(2502);
                    //使用InetAddress(Inet4Address).getByName把IP地址转换为网络地址
                    InetAddress serverAddress = InetAddress.getByName("127.0.0.1");
                    //Inet4Address serverAddress = (Inet4Address) Inet4Address.getByName("192.168.1.32");
                    String str = "[2143213;21343fjks;213]";//设置要发送的报文
                    byte data[] = str.getBytes();//把字符串str字符串转换为字节数组
                    //创建一个DatagramPacket对象，用于发送数据。
                    //参数一：要发送的数据  参数二：数据的长度  参数三：服务端的网络地址  参数四：服务器端端口号
                    DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, 9527);
                    socket.send(packet);//把数据发送到服务端。
                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void turnOnUdpClient() {
        final String hostIP = "127.0.0.1";
        final int port = 9527;

        new Thread(new Runnable() {
            @Override
            public void run() {
                DatagramSocket socket = null;
                try {
                    // 1、创建套接字
                    socket = new DatagramSocket(2501);

                    // 2、创建host的地址包装实例
                    SocketAddress socketAddr = new InetSocketAddress(hostIP, port);

                    // 3、创建数据报。包含要发送的数据、与目标主机地址
                    byte[] data = "Hello, I am Client".getBytes(Charset.forName("UTF-8"));
                    DatagramPacket packet = new DatagramPacket(data, data.length, socketAddr);

                    // 4、发送数据
                    socket.send(packet);

                    // 再次发送数据
                    packet.setData("Second information from client".getBytes(Charset.forName("UTF-8")));
                    socket.send(packet);
                    Log.e("udp","send");

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (null != socket) {
                        socket.close();
                    }
                }
            }
        }).start();
    }
}
