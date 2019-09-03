import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*
 * code written
 * by Zoya Klocheva
 */

public class Main {
    /*
    простое приложение для тренировки работы с html страницами, возможность пропарсить страницу
    с использованием библиотеки jsoup,  использование классов Elements,Document, регулярных выражений,
    потоков данных...
   цель - найти все картинки (jpg, png) по аттрибутам и сохранить их (папка img в корне)
     */
    public static void main(String[] args) throws IOException {
        String path = "file.html";//путь к файлу, где ищем ссылки на картинки
        Document doc = Jsoup.parse(new File(path), "UTF-8");//экземпляр для того, чтобы пропарсить
        Elements elements = doc.select("img");//выбор css запроса
        Pattern img = Pattern.compile("^(.+)"); //паттерн картинки

        //цикл для извлечения отдельных элементов страницы
        for (Element element : elements) {
            Matcher matcher = img.matcher(element.attr("data-original"));

            if (matcher.matches()) {
                System.out.println(element.attr("data-original"));
                URL url = new URL(element.attr("data-original"));
                InputStream stream = url.openStream();//открытие потока для чтения данных по найденным адресам
                String str = element.attr("data-original");
                //открытие поток для записи данных в папку
                FileOutputStream outputStream = new FileOutputStream("img\\" + str.substring(str.lastIndexOf("/") + 1));
                for (; ; ) {
                    int code = stream.read();
                    if (code < 0) {
                        break;
                    }
                    outputStream.write(code); //запись
                }
                outputStream.flush();
                outputStream.close();//закрытие потока
            }
        }
    }
}

