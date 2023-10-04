package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

import models.Customer;
import models.MediaItem;
import models.Movie;
import models.RentalClerk;
import models.RentalTransaction;
import models.TVSeries;
import models.enums.FileNameEnum;

public class FileEdit {

    Scanner scanner;

    public FileEdit(Scanner scanner) {
        this.scanner = scanner;
        loadCustomers();
        loadMediaItems();
        loadRentalClerks();
        loadRentalTransactions();
    }

    private ArrayList<MediaItem> mediaItems = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<RentalClerk> rentalClerks = new ArrayList<>();
    private ArrayList<RentalTransaction> rentalTransactions = new ArrayList<>();
    private ArrayList<MediaItem> mediaItemsCopy = new ArrayList<>();
    private ArrayList<Customer> customersCopy = new ArrayList<>();
    private ArrayList<RentalClerk> rentalClerksCopy = new ArrayList<>();
    private ArrayList<RentalTransaction> rentalTransactionsCopy = new ArrayList<>();

    protected void makeRentalTransaction() {

        loadCustomers();
        loadMediaItems();
        loadRentalClerks();

        if (rentalClerksCopy.size() == 0 || customersCopy.size() == 0 || mediaItemsCopy.size() == 0) {
            System.out.println(
                    "\nHa pelo menos um arquivo sem dados. Clientes, produtos e itens estao cadastrados e salvos no arquivo?");
            return;
        }

        System.out.print("\nDigite o ID dessa transacao de aluguel: ");
        int id = scanner.nextInt();

        RentalClerk clerk = null;
        while (clerk == null) {
            System.out.print("Digite o ID do atendente: ");
            clerk = findRentalClerk(scanner.nextInt());
            if (clerk == null) {
                System.out.println("Atendente nao encontrado pelo ID!");
                System.out.println("Novo Registro?");
                System.out.println();
            }
        }

        Customer customer = null;
        while (customer == null) {
            System.out.print("Digite o ID do locador: ");
            customer = findCustomer(scanner.nextInt());
            if (customer == null) {
                System.out.println("Cliente nao encontrado pelo ID!");
                System.out.println("Favor digite o ID correto");
                System.out.println();
            }
        }

        MediaItem mediaItem = null;
        while (mediaItem == null) {
            System.out.print("Digite o ID do produto: ");
            mediaItem = findMediaItem(scanner.nextInt());
            if (mediaItem == null) {
                System.out.println("Item nao encontrado pelo ID!");
                System.out.println("Favor digite o ID correto");
                System.out.println();
            }
        }

        long rentalDays = 0;
        while (rentalDays <= 0) {
            System.out.print("Digite a data de devolucao (dd/mm/yyyy): ");
            String input = scanner.next();

            if (validaData(input)) {
                var date = converteData(input);
                rentalDays = calcularDiferencaDias(date);
            } else {
                System.out.println("Data invalida. Tente novamente. Digite no formato (dd/mm/yyyy)");
            }
            if (rentalDays <= 0) {
                System.out.println("Nao insira datas antes ou iguais a de hoje.");
            }
        }

        final RentalTransaction rentalTransaction = new RentalTransaction(id, clerk, customer, mediaItem, rentalDays);
        rentalTransactions.add(rentalTransaction);

        customers.clear();
        mediaItems.clear();
        rentalClerks.clear();
    }

    private long calcularDiferencaDias(LocalDate data) {
        return ChronoUnit.DAYS.between(LocalDate.now(), data);
    }

    private boolean validaData(String data) {
        return data.matches("\\d{2}/\\d{2}/\\d{4}");
    }

    private LocalDate converteData(String data) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(data, formato);
    }

    private RentalClerk findRentalClerk(int idRentalClerk) {
        for (RentalClerk rentalClerk : this.rentalClerksCopy) {
            if (rentalClerk.getId() == idRentalClerk) {
                return rentalClerk;
            }
        }
        return null;
    }

    private Customer findCustomer(int idCustomer) {
        for (Customer customer : this.customersCopy) {
            if (customer.getId() == idCustomer) {
                return customer;
            }
        }

        return null;
    }

    private MediaItem findMediaItem(int idMediaItem) {
        for (MediaItem mediaItem : this.mediaItemsCopy) {
            if (mediaItem.getId() == idMediaItem) {
                return mediaItem;
            }
        }
        return null;
    }

    protected void addObjOnArray(String objType) {
        switch (objType) {

            case "Customer":
                System.out.println("\nComplete os campos a seguir para cadastrar o cliente.");
                System.out.print("Digite o ID: ");
                int idCustomer = scanner.nextInt();

                System.out.print("Nome: ");
                String nameCustomer = scanner.next();

                System.out.print("CPF: ");
                String cpfCustomer = scanner.next();

                LocalDate birthdayCustomer = null;
                long daysDifferenceCustomer = 0;
                while (daysDifferenceCustomer >= 0) {
                    System.out.print("Digite a data de nascimento (dd/mm/yyyy): ");
                    String input = scanner.next();

                    if (validaData(input)) {
                        birthdayCustomer = converteData(input);
                        daysDifferenceCustomer = calcularDiferencaDias(birthdayCustomer);
                    } else {
                        System.out.println("Data invalida. Tente novamente. Digite no formato (dd/mm/yyyy)");
                    }
                    if (daysDifferenceCustomer >= 0) {
                        System.out.println("Nao insira datas futuras ou iguais a de hoje.");
                    }
                }

                Customer customer = new Customer(idCustomer, nameCustomer, cpfCustomer, birthdayCustomer);
                customers.add(customer);
                break;

            case "MediaItem":
                System.out.println("\nEscolha o tipo de midia:");
                System.out.println("1. Filme");
                System.out.println("2. Serie de TV");
                System.out.print("Digite o numero correspondente: ");
                int itemType = scanner.nextInt();

                System.out.print("Digite o ID: ");
                int id = scanner.nextInt();
                scanner.next();

                System.out.print("Digite o Titulo: ");
                String title = scanner.next();

                System.out.print("Digite o preco diario. Caso seja serie, valor da temporada: ");
                double price = scanner.nextDouble();

                System.out.print("Digite a duracao (em minutos). Caso seja serie, soma de todos eps: ");
                int duration = scanner.nextInt();

                System.out.print("Digite o ano de lancamento: ");
                int releaseYear = scanner.nextInt();

                if (itemType == 1) {
                    MediaItem movie = new Movie(id, title, price, duration, releaseYear);
                    mediaItems.add(movie);
                } else if (itemType == 2) {
                    System.out.print("Digite o numero de episodios: ");
                    int numberOfEpisodes = scanner.nextInt();
                    System.out.print("Digite o numero de temporadas: ");
                    int numberOfSeasons = scanner.nextInt();
                    MediaItem tvSeries = new TVSeries(id, title, price, duration, releaseYear, numberOfEpisodes,
                            numberOfSeasons);
                    mediaItems.add(tvSeries);
                }

                System.out.println("Item adicionado com sucesso!");
                break;

            case "RentalClerk":
                System.out.println("\nComplete os campos a seguir para cadastrar um funcionario.");
                System.out.print("Digite o ID: ");
                int idClerk = scanner.nextInt();

                System.out.print("Nome: ");
                String nameClerk = scanner.next();

                System.out.print("CPF: ");
                String cpfClerk = scanner.next();

                LocalDate birthdayClerk = null;
                long daysDifferenceClerk = 0;
                while (daysDifferenceClerk >= 0) {
                    System.out.print("Digite a data de devolucao (dd/mm/yyyy): ");
                    String input = scanner.next();

                    if (validaData(input)) {
                        birthdayClerk = converteData(input);
                        daysDifferenceClerk = calcularDiferencaDias(birthdayClerk);
                    } else {
                        System.out.println("Data invalida. Tente novamente. Digite no formato (dd/mm/yyyy)");
                    }
                    if (daysDifferenceClerk >= 0) {
                        System.out.println("Nao insira datas futuras ou iguais a de hoje.");
                    }
                }

                RentalClerk clerk = new RentalClerk(idClerk, nameClerk, cpfClerk, birthdayClerk);
                rentalClerks.add(clerk);
                break;
        }

    }

    protected void listArray(String arrayName) {
        switch (arrayName) {
            case "rentalTransactions":
                for (RentalTransaction rentalTransaction : rentalTransactions) {
                    System.out.println(rentalTransaction);
                }
                break;
            case "customers":
                for (Customer customer : customers) {
                    System.out.println(customer);
                }
                break;
            case "mediaItems":
                for (MediaItem mediaItem : mediaItems) {
                    System.out.println(mediaItem);
                }
                break;
            case "rentalClerks":
                for (RentalClerk rentalClerk : rentalClerks) {
                    System.out.println(rentalClerk);
                }
                break;
        }
    }

    protected void listItemsFromFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("Arquivo nao existe! Salve os dados no arquivo.");
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            System.out.println("\nLista de Itens do Arquivo:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo.");
        }
    }

    protected void saveDataToFiles(String fileName, String arrayName) {
        switch (arrayName) {
            case "mediaItems":
                loadMediaItems();
                try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                    for (MediaItem itemCopy : this.mediaItemsCopy) {
                        writer.println(itemCopy);
                    }
                    for (MediaItem item : this.mediaItems) {
                        writer.println(item);
                    }
                    System.out.println("Dados salvos no arquivo de midia.");
                } catch (IOException e) {
                    System.out.println("Erro ao salvar os dados no arquivo de midia.");
                }
                mediaItems.clear();
                System.out.println("Array de itens de midia limpo.");
                break;

            case "rentalTransactions":
                loadRentalTransactions();
                try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                    for (RentalTransaction rentalTransactionCopy : this.rentalTransactionsCopy) {
                        writer.println(rentalTransactionCopy);
                    }
                    for (RentalTransaction rentalTransaction : this.rentalTransactions) {
                        writer.println(rentalTransaction);
                    }
                    System.out.println("Dados salvos no arquivo de midia.");
                } catch (IOException e) {
                    System.out.println("Erro ao salvar os dados no arquivo de midia.");
                }
                rentalTransactions.clear();
                System.out.println("Array de transacoes limpo.");
                break;

            case "rentalClerks":
                loadRentalClerks();
                try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                    for (RentalClerk rentalClerkCopy : this.rentalClerksCopy) {
                        writer.println(rentalClerkCopy);
                    }
                    for (RentalClerk rentalClerk : this.rentalClerks) {
                        writer.println(rentalClerk);
                    }
                    System.out.println("Dados salvos no arquivo de midia.");
                } catch (IOException e) {
                    System.out.println("Erro ao salvar os dados no arquivo de midia.");
                }
                rentalClerks.clear();
                System.out.println("Array de atendentes limpo.");
                break;

            case "customers":
                loadCustomers();
                try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                    for (Customer customerCopy : this.customersCopy) {
                        writer.println(customerCopy);
                    }
                    for (Customer customer : this.customers) {
                        writer.println(customer);
                    }
                    System.out.println("Dados salvos no arquivo de midia.");
                } catch (IOException e) {
                    System.out.println("Erro ao salvar os dados no arquivo de midia.");
                }
                customers.clear();
                System.out.println("Array de clientes limpo.");
                break;
        }
    }

    protected void deleteItem(String objType) {
        System.out.print("\nDigite o ID do objeto a ser excluido: ");
        int idToDelete = scanner.nextInt();
        boolean found = false;

        switch (objType) {
            case "MediaItem":
                for (MediaItem mediaItem : mediaItems) {
                    if (mediaItem.getId() == idToDelete) {
                        mediaItems.remove(mediaItem);
                        found = true;
                    }
                }
                break;
            case "RentalTransaction":
                for (RentalTransaction rentalTransaction : rentalTransactions) {
                    if (rentalTransaction.getId() == idToDelete) {
                        rentalTransactions.remove(rentalTransaction);
                        found = true;
                    }
                }
                break;
            case "Customer":
                for (Customer customer : customers) {
                    if (customer.getId() == idToDelete) {
                        customers.remove(customer);
                        found = true;
                    }
                }
                break;
            case "RentalClerk":
                for (RentalClerk rentalClerk : rentalClerks) {
                    if (rentalClerk.getId() == idToDelete) {
                        rentalClerks.remove(rentalClerk);
                        found = true;
                    }
                }
                break;
        }

        if (found) {
            System.out.println("Objeto excluido com sucesso.");
        } else if (!found) {
            System.out.println("Item nao encontrado.");
        }
    }

    protected void clearFileContents(String fileName) {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            writer.print("");
            System.out.println("Conteudo do arquivo limpo com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao limpar o arquivo.");
        }
    }

    private void loadMediaItems() {
        this.mediaItemsCopy.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(FileNameEnum.MEDIA_FILE_NAME.getFileName()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                int id = Integer.parseInt(parts[0].replace("ID: ", "").trim());
                String title = parts[1].replace("Titulo: ", "").trim();
                double price = Double
                        .parseDouble(parts[2].replace("Preco(diario): R$ ", "").replaceAll("[^0-9.]", "").trim());
                int duration = Integer.parseInt(parts[3].replace("Duracao: ", "").replaceAll("[^0-9]", "").trim());
                int releaseYear = Integer.parseInt(parts[4].replace("Ano de Lancamento: ", "").trim());

                if (parts.length == 5) {
                    MediaItem mediaItem = new MediaItem(id, title, price, duration, releaseYear);
                    this.mediaItemsCopy.add(mediaItem);
                } else if (parts.length == 7) {
                    int seasons = Integer.parseInt(parts[5].replace("Temporadas: ", "").trim());
                    int episodes = Integer.parseInt(parts[6].replace("Episodios: ", "").trim());
                    MediaItem mediaItem = new TVSeries(id, title, price, duration, releaseYear, episodes, seasons);
                    this.mediaItemsCopy.add(mediaItem);

                }
            }
        } catch (IOException e) {
        }
    }

    private void loadCustomers() {
        this.customersCopy.clear();
        try (BufferedReader reader = new BufferedReader(
                new FileReader(FileNameEnum.CUSTOMER_FILE_NAME.getFileName()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                int id = Integer.parseInt(parts[0].replace("ID: ", "").trim());
                String name = parts[1].replace("Nome: ", "").trim();
                String cpf = parts[2].replace("CPF: ", "").trim();
                LocalDate data = LocalDate.parse(parts[3].replace("Ano de Nascimento: ", "").trim());

                Customer customer = new Customer(id, name, cpf, data);
                this.customersCopy.add(customer);
            }
        } catch (IOException e) {
        }
    }

    private void loadRentalTransactions() {
        this.rentalTransactionsCopy.clear();
        try (BufferedReader reader = new BufferedReader(
                new FileReader(FileNameEnum.TRANSACTION_FILE_NAME.getFileName()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                int idTransaction = Integer.parseInt(parts[0].replace("ID Locacao: ", "").trim());
                int idCustomer = Integer.parseInt(parts[1].replace("ID Cliente: ", "").trim());
                int idClerk = Integer.parseInt(parts[3].replace("ID Atendente: ", "").trim());
                int idItem = Integer.parseInt(parts[5].replace("ID Item: ", "").trim());
                long locationDays = Long.parseLong(parts[7].replace("Dias de locacao: ", "").trim());

                RentalTransaction rentalTransaction = new RentalTransaction(idTransaction,
                        this.findRentalClerk(idClerk), this.findCustomer(idCustomer), this.findMediaItem(idItem),
                        locationDays);
                rentalTransactionsCopy.add(rentalTransaction);
            }
        } catch (IOException e) {
        }

    }

    private void loadRentalClerks() {
        this.rentalClerksCopy.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(FileNameEnum.CLERKS_FILE_NAME.getFileName()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\\|");
                int id = Integer.parseInt(parts[0].replace("ID: ", "").trim());
                String name = parts[1].replace("Nome: ", "").trim();
                String cpf = parts[2].replace("CPF: ", "").trim();
                LocalDate data = LocalDate.parse(parts[3].replace("Ano de Nascimento: ", "").trim());

                RentalClerk rentalClerk = new RentalClerk(id, name, cpf, data);
                this.rentalClerksCopy.add(rentalClerk);
            }
        } catch (IOException e) {
        }
    }
}
