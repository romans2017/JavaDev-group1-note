package ua.goit.notes;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

@Service
public class HtmlServiceImpl implements HtmlService{

    @Override
    public String markdownToHtml(String markdown) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }

    @Override
    public String markdownToText(String markdown) {
        String s = markdownToHtml(markdown);
        return Jsoup.parse(s).wholeText();
    }
}
