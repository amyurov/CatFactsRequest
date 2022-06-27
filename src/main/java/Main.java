import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Comparator;
import java.util.List;


public class Main {

    public static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) {
        // Создаем клиент
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            URI uri = new URI("https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats");
            //Объект описывающий метод запроса
            HttpUriRequest httpGet = new HttpGet(uri);

            // Инициализируем объект запроса запуском клиента с параметром, где указываем метод запроса.
            // Он принимает ответ от сервера
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                HttpEntity entity = response.getEntity();

                List<CatFacts> catFacts = mapper.readValue(entity.getContent(), new TypeReference<>() {});
                catFacts.stream().filter(o -> o.getUpvotes() != null && o.getUpvotes() > 0)
                        .sorted(Comparator.comparing(CatFacts::getUpvotes))
                        .forEach(System.out::println);
            }

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

    }
}
