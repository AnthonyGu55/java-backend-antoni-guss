package bookapi.main.antoni.guss;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    /*
        Twoim zadaniem jest napisanie prostego programu do pobierania i transformowania danych
        udostępnianych przez API. Dokumentacje API możesz znależć pod poniższym linkiem:
        https://akai-recruitment.herokuapp.com/documentation.html
        Całe API zawiera jeden endpoint: https://akai-recruitment.herokuapp.com/book
        Endpoint ten zwraca liste książek zawierajacch informację takie jak:
        - id
        - tytuł
        - autor
        - ocena
        Twoim zadaniem jest:
        1. Stworzenie odpowiedniej klasy do przechowywania informacji o książce
        2. Sparsowanie danych udostępnianych przez endpoint. Aby ułatwić to zadanie,
           do projektu są dołaczone 3 najpopularniejsze biblioteki do parsowania JSONów
           do obiektów Javy - Gson, Org.Json, Jackson. Możesz wykorzystać dowolną z nich
        3. Po sparsowaniu JSONu do obiektów Javy, uzupełnij program o funkcję wypisującą 3 autorów z
           najwyższą średnią ocen. Na przykład, gdy osoba X jest autorem książki A z oceną 9 i B z oceną 8,
           to powinna zostać wyświetlona informacja: X - 8.5
       Projekt został utworzony przy użyciu najnowszej Javy 17,
       jednakże nic nie stoi na przeszkodzie użycia innej wersji jeśli chcesz
     */

    public static void main(String[] args) throws IOException {
        List<Book> bookList = getBookListFromApi();

        List<Author> authorList = new ArrayList<>();

        Map<String, List<Book>> collect = bookList.stream().collect(Collectors.groupingBy(Book::getAuthor));

        collect.forEach((author, books) -> authorList.add(new Author(author, books)));

        authorList.stream()
                .sorted((o1, o2) -> Double.compare(o1.getBooks().stream().mapToDouble(Book::getRating).average().getAsDouble(), o2.getBooks().stream().mapToDouble(Book::getRating).average().getAsDouble())*(-1))
                .limit(3)
                .forEach(author -> {
                    double ratingAverage = author.getBooks().stream().mapToDouble(Book::getRating).average().getAsDouble();

                    System.out.printf("%s - %.1f%n", author.getName(), ratingAverage);
                });
    }

    private static List<Book> getBookListFromApi() throws IOException {
        URL url = new URL("https://akai-recruitment.herokuapp.com/book");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        int responseCode = connection.getResponseCode();

        if (responseCode != 200) {
            throw new RuntimeException("Http Response Code: " + responseCode);
        }

        Scanner scanner = new Scanner(url.openStream());

        StringBuilder stringBuilder = new StringBuilder();
        while (scanner.hasNext()) {

            stringBuilder.append(scanner.nextLine());

        }

        scanner.close();


        Book[] books = new Gson().fromJson(stringBuilder.toString(), Book[].class);
        return new ArrayList<>(Arrays.asList(books));
    }
}