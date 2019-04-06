package client;

import cityLst.Loader;
import org.jsoup.*;
import workers.MultithreadWorker;
import workers.SingleThreadWorker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Start {
    public static void main(String[] args) {
        Loader loader = new Loader();
        List<String> cityLst = loader.load2memory(false);
        // now we have a list of city strings
        // we split lst to 8 sublists. to feed 8 threads
        // threads feed results here
        List<String> pool = new ArrayList<>(cityLst.size());
        // now divide cityLst to 8 subLists
        List<String>[] feed = new List[8];

        for (int i = 0; i < feed.length; i++) {
            feed[i] = cityLst.subList(i * cityLst.size() / 8, (i+1) * cityLst.size() / 8);
        }
        //
        for (int i = 0; i < cityLst.size(); i++) {
            pool.add(null);
        }

        MultithreadWorker w0 = new MultithreadWorker(feed[0], pool, 0);
        MultithreadWorker w1 = new MultithreadWorker(feed[1], pool, 1);
        MultithreadWorker w2 = new MultithreadWorker(feed[2], pool, 2);
        MultithreadWorker w3 = new MultithreadWorker(feed[3], pool, 3);
        MultithreadWorker w4 = new MultithreadWorker(feed[4], pool, 4);
        MultithreadWorker w5 = new MultithreadWorker(feed[5], pool, 5);
        MultithreadWorker w6 = new MultithreadWorker(feed[6], pool, 6);
        MultithreadWorker w7 = new MultithreadWorker(feed[7], pool, 7);

        Thread t0 = new Thread(w0);
        Thread t1 = new Thread(w1);
        Thread t2 = new Thread(w2);
        Thread t3 = new Thread(w3);
        Thread t4 = new Thread(w4);
        Thread t5 = new Thread(w5);
        Thread t6 = new Thread(w6);
        Thread t7 = new Thread(w7);

        t0.start();
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();

        try {
            t0.join();
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            t5.join();
            t6.join();
            t7.join();
        } catch (InterruptedException ee) {
            ee.printStackTrace();
        }


        try {
            Writer.writeFile(pool);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("done");
    }
}
