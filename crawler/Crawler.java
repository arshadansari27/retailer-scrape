import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by arshad on 07/02/15.
 */
public class Crawler {

    private String url;
    private ExecutorService pool;
    public Crawler(String startUrl) {
        this.url = startUrl;
        this.pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 10);
    }

    public void crawl() {
        Queue<String> queue = new LinkedBlockingQueue<String>();
        Set<String> visited = new ConcurrentSkipListSet<String>();
        queue.offer(this.url);
        AtomicInteger stopCount = new AtomicInteger(0);
        while(!queue.isEmpty() || stopCount.get() > 0) {
            String _url = queue.poll();
            if (_url == null) {
                try {
                   Thread.currentThread().sleep(1000);
                    continue;
                } catch (InterruptedException e) {
                    System.out.println("Thread interrupted");
                }
            }
            System.out.println("[*]" + _url);
            System.out.println("[*] Visited: " + visited.size() + ", Queue: " + queue.size());
            if (visited.contains(_url))
                continue;
            visited.add(_url);
            Task t = new Task(_url, this.url, queue, visited, stopCount);
            stopCount.incrementAndGet();
            pool.submit(t);
        }


    }

    public static void main(String[] args) {
        String url = "http://www.aiktc.org";
        Crawler crawler = new Crawler(url);
        crawler.crawl();


    }

    public static class Task implements Runnable{

        private String url;
        private String startUrl;
        private Queue<String> queue;
        private Set<String> visited;
        private AtomicInteger stopCount;

        public Task(String url, String startUrl, Queue<String> queue, Set<String> visited, AtomicInteger stopCount) {
            this.url = url;
            this.startUrl = startUrl;
            this.queue = queue;
            this.visited = visited;
            this.stopCount = stopCount;
        }

        @Override
        public void run() {
            try {
                Document document = Jsoup.connect(this.url).timeout(30000).get();
                System.out.println(document.toString().length());
                Elements links = document.select("a[abs:href]");

                for(Element e: links) {
                    String link = e.attr("href");

                    if (link != null && link.startsWith("/")) {
                        link = this.startUrl + link;
                    }
                    if (link == null
                            || "".equals(link)
                            || link.startsWith("#")
                            || !link.contains(startUrl)
                            || visited.contains(link)) {
                        continue;
                    }
                    System.out.println("FOUND: "  + link);
                    queue.offer(link);
                }
            } catch(Exception e) {
                e.printStackTrace();
            } finally {
                stopCount.decrementAndGet();
            }

        }
    }
}

