package net.job4j.grabber;

import net.job4j.grabber.utils.HabrCareerDateTimeParser;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDateTime;

public class HabrCareerParse {

    private static final String SOURCE_LINK = "https://career.habr.com";

    private static final String PAGE_LINK = String.format("%s/vacancies/java_developer", SOURCE_LINK);

    public static void main(String[] args) throws IOException {
        Connection connection = Jsoup.connect(PAGE_LINK);
        Document document = connection.get();
        Elements rows = document.select(".vacancy-card__inner");
        rows.forEach(row -> {
            Element titleElement = row.select(".vacancy-card__title").first();
            Element linkElement = titleElement.child(0);
            Element vacancyDate = row.select(".vacancy-card__date").first();
            String vacancyName = titleElement.text();
            Element vacancyDateName = vacancyDate.child(0);
            String link = String.format("%s%s", SOURCE_LINK, linkElement.attr("href"));
            HabrCareerDateTimeParser habrCareerDateTimeParser = new HabrCareerDateTimeParser();
            LocalDateTime localDateTime = habrCareerDateTimeParser.parse(vacancyDateName.attr("datetime"));
            System.out.printf("%s %s %s%n", vacancyName, link, localDateTime);
        });
    }

}
