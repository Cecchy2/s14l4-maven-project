package dariocecchinato;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args) {
        Random random = new Random();
        Random random1 = new Random();
        Faker f = new Faker();
        LocalDate today = LocalDate.now();
        System.out.println("Today " + today);


        //-----------------------------------------------------Creo Lista Prodotti------------------------------------
        List<Product> prodotti = new ArrayList<>();
        prodotti.add(new Product(random.nextLong(1000000, 1900000), f.harryPotter().book(), "book", 120.00));
        prodotti.add(new Product(random.nextLong(1000000, 1900000), "Marching Powder", "book", 99.00));
        prodotti.add(new Product(random.nextLong(1000000, 1900000), "Pannolini", "baby", 20.00));
        prodotti.add(new Product(random.nextLong(1000000, 1900000), "Skateboard", "boys", 130.00));
        prodotti.add(new Product(random.nextLong(1000000, 1900000), "Couch", "furniture", 820.00));
        prodotti.add(new Product(random.nextLong(1000000, 1900000), "T-Shirt", "computer", 1920.00));
        prodotti.add(new Product(random.nextLong(1000000, 1900000), "bed", "furniture", 1100.00));
        prodotti.add(new Product(random.nextLong(1000000, 1900000), "The Hobbit", "book", 220.00));
        prodotti.add(new Product(random.nextLong(1000000, 1900000), "Biberon", "baby", 20.00));
        prodotti.add(new Product(random.nextLong(1000000, 1900000), "BabyBath", "baby", 160.00));
        prodotti.add(new Product(random.nextLong(1000000, 1900000), "SurfBoard", "boys", 320.00));

        //-----------------------------------------------------Creo Customers Random------------------------------------
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            customers.add(new Customer(random.nextLong(1000000, 1900000), f.backToTheFuture().character() + " " + f.elderScrolls().creature(), random1.nextInt(1, 3)));
        }
        System.out.println("---------------------------------Lista Customers Random------------------------------------");
        customers.forEach(System.out::println);

        //--------------------------------------------------LISTA ORDINI Random-----------------------------------------
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < 15; i++) {

            // Genera una lista casuale di prodotti per l'ordine
            List<Product> randomProducts = new ArrayList<>();

            // Numero casuale di prodotti per l'ordine
            int numProducts = random.nextInt(prodotti.size()) + 1;
            for (int j = 0; j < numProducts; j++) {
                randomProducts.add(prodotti.get(random.nextInt(prodotti.size())));
            }
            // Assegna un cliente casuale
            Customer randomCustomer = customers.get(random.nextInt(customers.size()));

            // Genera una data casuale per l'ordine e la data di consegna
            LocalDate orderDate = LocalDate.now().minusDays(random.nextInt(30));
            LocalDate deliveryDate = orderDate.plusDays(random.nextInt(10) + 1);

            // Crea l'ordine con dati casuali
            orders.add(new Order(random.nextLong(1000000, 1900000), Status.shipped, orderDate, deliveryDate, randomCustomer, randomProducts));
        }

        System.out.println("-----------------------------------Stampo ordini generati random--------------------------");
        for (Order order : orders) {
            System.out.println(order);
        }


        System.out.println("--------------------------------------------ESERCIZIO1------------------------------------");

        Map<Customer, List<Order>> ordersForClient = orders.stream()
                .collect(Collectors.groupingBy(Order::getCustomer));
        ordersForClient.forEach(((customer, order) -> System.out.println("Il cliente " + customer + " ha ordinato " + order)));


        System.out.println("--------------------------------------------ESERCIZIO2------------------------------------");

        Map<Customer, Double> customerTotal = orders.stream()
                .collect(Collectors.groupingBy(Order::getCustomer,
                        Collectors.summingDouble(order -> order
                                .getProducts()
                                .stream()
                                .mapToDouble(Product::getPrice)
                                .sum())));

        customerTotal.forEach((customer, total) -> System.out.println("Il totale speso dal cliente " + customer + "è di " + total));


        System.out.println("--------------------------------------------ESERCIZIO3------------------------------------");

        List<Product> prodottiPiuCostosi = prodotti
                .stream()
                .sorted(Comparator.comparingDouble(Product::getPrice)
                        .reversed())
                .limit(3)
                .toList();
        prodottiPiuCostosi.forEach(System.out::println);

        System.out.println("--------------------------------------------ESERCIZIO4------------------------------------");
        /*OptionalDouble averageTotalOrders= orders.stream()
                .map(Order::getProducts).mapToDouble(Product::getPrice)
                .*/

        System.out.println("--------------------------------------------ESERCIZIO5------------------------------------");

        List<Product> prodottiPerCategoria = prodotti.stream().sorted(Comparator.comparing(Product::getCategory)).toList();
        prodottiPerCategoria.forEach(System.out::println);

        Map<String, Double> sommaPrezziPerCategoria = prodottiPerCategoria.stream()
                .collect(Collectors.groupingBy(Product::getCategory, Collectors.summingDouble(Product::getPrice)));
        sommaPrezziPerCategoria.forEach((categoria, totale) -> System.out.println("Categoria " + categoria + " Totale " + totale));

    }
}
