package workers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.util.List;

public class SingleThreadWorker {
    public List<String> run (List<String> lst) {
        // lambda is sick. im having fun
        lst.replaceAll(e -> {
            StringBuffer sb = new StringBuffer(e);
            String woeid = steal(e);
            sb.append(", " + woeid);
            return sb.toString();
        });
        return lst;
    }
    public String steal (String cityName) {
        String[] cityNameSplitted = cityName.split(", ");
        StringBuilder sb = new StringBuilder();
        for (String x : cityNameSplitted) {
            sb.append(x);
            sb.append("%20");
        }
        sb.delete(sb.lastIndexOf("%20"), sb.length());
        String cityNameURLFormatted = sb.toString();

        try {
            Document doc = Jsoup.connect("http://woeid.rosselliot.co.nz/lookup/" + cityNameURLFormatted).get();
            Element e = doc.getElementsByTag("body").first().getElementById("page_wrap").getElementById("wrapper")
                    .getElementById("content").getElementById("lookup_result").getElementById("woeid_results_table")
                    .getElementsByTag("tbody").first().getElementsByClass("woeid_row").first()
                    .getElementsByClass("data-woeid").first();

            Element e1 = doc.getElementsByTag("body").first().getElementById("page_wrap").getElementById("wrapper")
                    .getElementById("content").getElementById("lookup_result");
            String id = e1.getElementById("woeid_results_table")
                    .getElementsByTag("tbody").first().getElementsByClass("woeid_row").first()
                    .attr("data-woeid");

//            System.out.println("Stealing woeid for " + cityName + " which is " + id);
            return id;
        } catch (Exception e) {
            e.printStackTrace();
            return "-1";
        }
    }
}
