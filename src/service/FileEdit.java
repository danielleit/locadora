package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

    protected void makeRentalTransaction() {

        loadCustomers();
        loadMediaItems();
        loadRentalClerks();

        System.out.print("\nDigite o ID dessa transação de aluguel: ");
        int id = scanner.nextInt();

        System.out.print("Digite o ID do atendente: ");
        RentalClerk clerk = findRentalClerk(scanner.nextInt());
        if (clerk == null) {
            System.out.println("Atendente não encontrado pelo ID!");
            return;
        }

        System.out.print("Digite o ID do locador: ");
        Customer customer = findCustomer(scanner.nextInt());
        if (customer == null) {
            System.out.println("Cliente não encontrado pelo ID!");
            return;
        }

        System.out.print("Digite o ID do produto: ");
        MediaItem mediaItem = findMediaItem(scanner.nextInt());
        if (mediaItem == null) {
            System.out.println("Item não encontrado pelo ID!");
            return;
        }

        long rentalDays = 0;
        do {
            System.out.print("Digite a data de devolução (dd/mm/aaaa): ");
            try {
                LocalDate returnDate = LocalDate.parse(scanner.next(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                rentalDays = ChronoUnit.DAYS.between(LocalDate.now(), returnDate);
            } catch (DateTimeParseException e) {
                System.out.println("Erro ao definir a data. Verifique se a data está no formato (dia/mês/ano).");
            }
            if (rentalDays <= 0) {
                System.out.println("Não insira datas antes ou iguais à de hoje.");
            }
        } while (rentalDays <= 0);

        RentalTransaction rentalTransaction = new RentalTransaction(id, clerk, customer, mediaItem, rentalDays);
        rentalTransactions.add(rentalTransaction);

        customers.clear();
        mediaItems.clear();
        rentalClerks.clear();
    }

    private RentalClerk findRentalClerk(int idRentalClerk) {
        for (RentalClerk rentalClerk : this.rentalClerks) {
            if (rentalClerk.getId() == idRentalClerk) {
                return rentalClerk;
            }
        }
        return null;
    }

    private Customer findCustomer(int idCustomer) {
        for (Customer customer : this.customers) {
            if (customer.getId() == idCustomer) {
                return customer;
            }
        }

        return null;
    }

    private MediaItem findMediaItem(int idMediaItem) {
        for (MediaItem mediaItem : this.mediaItems) {
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
                do {
                    System.out.print("Data de nacimento (dd/mm/aaaa): ");
                    try {
                        birthdayCustomer = LocalDate.parse(scanner.next(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    } catch (DateTimeParseException e) {
                        System.out.println("Erro ao definir data. Lembre-se de inserir no formato (dia/mês/ano)");
                    }
                    if(0 < ChronoUnit.DAYS.between(LocalDate.now(), birthdayCustomer)){
                        System.out.println("Insira um valor válido.");
                    }
                } while (birthdayCustomer != null || 0 < ChronoUnit.DAYS.between(LocalDate.now(), birthdayCustomer));

                Customer customer = new Customer(idCustomer, nameCustomer, cpfCustomer, birthdayCustomer);
                customers.add(customer);
                break;

            case "MediaItem":
                System.out.println("\nEscolha o tipo de mídia:");
                System.out.println("1. Filme");
                System.out.println("2. Série de TV");
                System.out.print("Digite o número correspondente: ");
                int itemType = scanner.nextInt();

                System.out.print("Digite o ID: ");
                int id = scanner.nextInt();
                scanner.next();

                System.out.print("Digite o título: ");
                String title = scanner.next();

                System.out.print("Digite o preço diário. Caso seja série, valor da temporada: ");
                double price = scanner.nextDouble();

                System.out.print("Digite a duração (em minutos). Caso seja série, soma de todos eps: ");
                int duration = scanner.nextInt();

                System.out.print("Digite o ano de lançamento: ");
                int releaseYear = scanner.nextInt();

                if (itemType == 1) {
                    MediaItem movie = new Movie(id, title, price, duration, releaseYear);
                    mediaItems.add(movie);
                } else if (itemType == 2) {
                    System.out.print("Digite o número de episódios: ");
                    int numberOfEpisodes = scanner.nextInt();
                    System.out.print("Digite o número de temporadas: ");
                    int numberOfSeasons = scanner.nextInt();
                    MediaItem tvSeries = new TVSeries(id, title, price, duration, releaseYear, numberOfEpisodes,
                            numberOfSeasons);
                    mediaItems.add(tvSeries);
                }

                System.out.println("Item adicionado com sucesso!");
                break;

            case "RentalClerk":
                System.out.println("\nComplete os campos a seguir para cadastrar um funcionário.");
                System.out.print("Digite o ID: ");
                int idClerk = scanner.nextInt();

                System.out.print("Nome: ");
                String nameClerk = scanner.next();

                System.out.print("CPF: ");
                String cpfClerk = scanner.next();

                System.out.print("Data de nacimento (dd/mm/aaaa): ");
                LocalDate birthdayClerk = LocalDate.parse(scanner.next(),
                        DateTimeFormatter.ofPattern("dd/MM/yyyy"));

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
            System.out.println("Arquivo não existe! Salve os dados no arquivo.");
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
                try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                    for (MediaItem item : this.mediaItems) {
                        writer.println(item);
                    }
                    System.out.println("Dados salvos no arquivo de mídia.");
                } catch (IOException e) {
                    System.out.println("Erro ao salvar os dados no arquivo de mídia.");
                }
                mediaItems.clear();
                System.out.println("Array de itens de mídia limpo.");
                break;

            case "rentalTransactions":
                try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                    for (RentalTransaction rentalTransaction : this.rentalTransactions) {
                        writer.println(rentalTransaction);
                    }
                    System.out.println("Dados salvos no arquivo de mídia.");
                } catch (IOException e) {
                    System.out.println("Erro ao salvar os dados no arquivo de mídia.");
                }
                rentalTransactions.clear();
                System.out.println("Array de itens de mídia limpo.");
                break;

            case "rentalClerks":
                try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                    for (RentalClerk rentalClerk : this.rentalClerks) {
                        writer.println(rentalClerk);
                    }
                    System.out.println("Dados salvos no arquivo de mídia.");
                } catch (IOException e) {
                    System.out.println("Erro ao salvar os dados no arquivo de mídia.");
                }
                rentalClerks.clear();
                System.out.println("Array de itens de mídia limpo.");
                break;

            case "customers":
                try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                    for (Customer customer : this.customers) {
                        writer.println(customer);
                    }
                    System.out.println("Dados salvos no arquivo de mídia.");
                } catch (IOException e) {
                    System.out.println("Erro ao salvar os dados no arquivo de mídia.");
                }
                customers.clear();
                System.out.println("Array de itens de mídia limpo.");
                break;
        }
    }

    protected void deleteItem(String objType) {
        System.out.print("\nDigite o ID do objeto a ser excluído: ");
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
            System.out.println("Objeto excluído com sucesso.");
        } else if (!found) {
            System.out.println("Item não encontrado.");
        }
    }

    protected void clearFileContents(String fileName) {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            writer.print("");
            System.out.println("Conteúdo do arquivo limpo com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao limpar o arquivo.");
        }
    }

    private void loadMediaItems() {
        this.mediaItems.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(FileNameEnum.MEDIA_FILE_NAME.getFileName()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                int id = Integer.parseInt(parts[0].replace("ID: ", "").trim());
                String title = parts[1].replace("Título: ", "").trim();
                double price = Double
                        .parseDouble(parts[2].replace("Preço(diário): R$ ", "").replaceAll("[^0-9.]", "").trim());
                int duration = Integer.parseInt(parts[3].replace("Duração: ", "").replaceAll("[^0-9]", "").trim());
                int releaseYear = Integer.parseInt(parts[4].replace("Ano de Lançamento: ", "").trim());

                if (parts.length == 5) {
                    MediaItem mediaItem = new MediaItem(id, title, price, duration, releaseYear);
                    this.mediaItems.add(mediaItem);
                } else if (parts.length == 7) {
                    int seasons = Integer.parseInt(parts[5].replace("Temporadas: ", "").trim());
                    int episodes = Integer.parseInt(parts[6].replace("Episódios: ", "").trim());
                    MediaItem mediaItem = new TVSeries(id, title, price, duration, releaseYear, episodes, seasons);
                    this.mediaItems.add(mediaItem);

                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo.");
        }
    }

    private void loadCustomers() {
        this.customers.clear();
        try (BufferedReader reader = new BufferedReader(
                new FileReader(FileNameEnum.CUSTOMER_FILE_NAME.getFileName()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                int id = Integer.parseInt(parts[0].replace("ID: ", "").trim());
                String name = parts[1].replace("Nome:", "").trim();
                String cpf = parts[2].replace("CPF: ", "");
                LocalDate data = LocalDate.parse(parts[3].replace("Ano de Nascimento: ", "").trim());

                Customer customer = new Customer(id, name, cpf, data);
                this.customers.add(customer);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo.");
        }
    }

    private void loadRentalTransactions() {
        this.rentalTransactions.clear();
        try (BufferedReader reader = new BufferedReader(
                new FileReader(FileNameEnum.TRANSACTION_FILE_NAME.getFileName()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                int idTransaction = Integer.parseInt(parts[0].replace("ID Locação: ", "").trim());
                int idCustomer = Integer.parseInt(parts[1].replace("ID Cliente: ", "").trim());
                int idClerk = Integer.parseInt(parts[3].replace("ID Atendente: ", "").trim());
                int idItem = Integer.parseInt(parts[5].replace("ID Item: ", "").trim());
                long locationDays = Long.parseLong(parts[7].replace("Dias de locação: ", "").trim());

                RentalTransaction rentalTransaction = new RentalTransaction(idTransaction,
                        this.findRentalClerk(idClerk), this.findCustomer(idCustomer), this.findMediaItem(idItem),
                        locationDays);
                rentalTransactions.add(rentalTransaction);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo.");
        }

    }

    private void loadRentalClerks() {
        this.rentalClerks.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(FileNameEnum.CLERKS_FILE_NAME.getFileName()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                int id = Integer.parseInt(parts[0].replace("ID: ", "").trim());
                String name = parts[1].replace("Nome:", "").trim();
                String cpf = parts[2].replace("CPF: ", "");
                LocalDate data = LocalDate.parse(parts[3].replace("Ano de Nascimento: ", "").trim());

                RentalClerk rentalClerk = new RentalClerk(id, name, cpf, data);
                this.rentalClerks.add(rentalClerk);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo.");
        }
    }
}
