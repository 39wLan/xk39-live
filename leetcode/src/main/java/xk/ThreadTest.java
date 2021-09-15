package xk;

import java.util.concurrent.TimeUnit;

public class ThreadTest {
    static class TaskA implements Runnable{
        public static Integer value=0;
    
        @Override
        public void run() {
            try{
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            value=300;
        }
    }
    static class TaskB implements Runnable{
        @Override
        public void run(){
            while (TaskA.value==0){
            }
            System.out.println("TaskA.value = " + TaskA.value);
        }
    }
    
    public static void main(String[] args) {
        Thread thread = new Thread(new TaskA());
        Thread thread1 = new Thread(new TaskB());
        thread.start();
        thread1.start();
    }
}
