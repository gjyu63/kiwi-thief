package workers;

import java.util.List;

public class MultithreadWorker implements Runnable{
    private List<String> subList;
    private List<String> pool;
    private int threadNo;
    SingleThreadWorker stw = new SingleThreadWorker();
    public MultithreadWorker(List<String> subList, List<String> pool, int threadNo) {
        this.subList = subList;
        this.pool = pool;
        this.threadNo = threadNo;
    }
    public void run() {
        List<String> buf = stw.run(subList);
        // copy buf to pool, at the designated place
        int startIndexOfPool = threadNo * pool.size() / 8;
        for (int i = startIndexOfPool; i < startIndexOfPool + pool.size()/8 && i < pool.size(); ++i) { // careful
            System.out.println("ID: " + Integer.toString(this.threadNo));
            pool.set(i, buf.get(i - startIndexOfPool));
        }
    }
}
