
//消息队列类，线程之间进行通信

import java.util.LinkedList;

import static java.lang.Thread.sleep;


public class Main{
    public static void main(String[] args) {
        MessageQueue messageQueue=new MessageQueue(2);
        for(int i=0;i<3;i++){
            final int id=i;
            new Thread(()->{
            messageQueue.put(new Message(id,"value"+id));
            },"producer"+i).start();
        }

        new Thread(()->{
            while(true) {
                try {
                    sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Message message=messageQueue.take();
            }
        },"consumer").start();

    }
}

class MessageQueue {
    private LinkedList<Message> list=new LinkedList<>();
    private int capacity;
    public MessageQueue(int size){
        this.capacity=size;
    }
    public Message take(){
        synchronized (list) {
            while(list.isEmpty()){
                try {
                    System.out.println("消费者等待");
                    list.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
            //获取头部信息
            var res = list.removeFirst();
            System.out.println("已经消费"+res);
            list.notifyAll();
            return res;
        }
    }

    public void put(Message message){
        synchronized (list){
            while(list.size()==capacity){
                try {
                    System.out.println("生产者等待");
                    list.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            list.addLast(message);
            System.out.println("已经生产"+message);
            list.notifyAll();
        }

    }

}

final class Message{
    private Integer id;
    private Object value;

    public Message(Integer id, Object value){
        this.id=id;
        this.value=value;
    }
    public Integer getId(){
        return id;
    }
    public Object getValue(){
        return value;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
