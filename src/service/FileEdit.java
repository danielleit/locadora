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

public class FileEdit {

    Scanner scanner;

    public FileEdit(Scanner scanner) {
        this.scanner = scanner;
    }

    private ArrayList<MediaItem> mediaItems = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<RentalClerk> rentalClerks = new ArrayList<>();
    private ArrayList<RentalTransaction> rentalTransactions = new ArrayList<>();

    protected void makeRentalTransaction() {

        System.out.print("Digite o ID dessa transação de aluguel: ");
        int id = scanner.nextInt();

        System.out.print("Digite o ID do atendente: ");
        RentalClerk clerk = findRentalClerk(rentalClerks, scanner.nextInt());

        System.out.print("Digite o ID do locador: ");
        Customer customer = findCustomer(customers, scanner.nextInt());

        System.out.print("Digite o ID do produto: ");
        MediaItem mediaItem = findMediaItem(mediaItems, scanner.nextInt());

        System.out.print("Digite a data de devolução (dd/mm/aaaa): ");
        LocalDate returnDate = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        long rentalDays = ChronoUnit.DAYS.between(LocalDate.now(), returnDate);

        RentalTransaction rentalTransaction = new RentalTransaction(id, clerk, customer, mediaItem, rentalDays);
        rentalTransactions.add(rentalTransaction);
    }

    private RentalClerk findRentalClerk(ArrayList<RentalClerk> rentalClerks, int idRentalClerk) {
        for (RentalClerk rentalClerk : rentalClerks) {
            if (rentalClerk.getId() == idRentalClerk) {
                return rentalClerk;
            }
        }
        return null;
    }

    private Customer findCustomer(ArrayList<Customer> customers, int idCustomer) {
        for (Customer customer : customers) {
            if (customer.getId() == idCustomer) {
                return customer;
            }
        }
        return null;
    }

    private MediaItem findMediaItem(ArrayList<MediaItem> mediaItems, int idMediaItem) {
        for (MediaItem mediaItem : mediaItems) {
            if (mediaItem.getId() == idMediaItem) {
                return mediaItem;
            }
        }
        return null;
    }

    protected void addObjOnArray(String objType) {
        switch (objType) {
            
            case "Customer":
                System.out.println("Complete os campos a seguir para cadastrar o cliente.");
                System.out.println("Digite o ID: ");
                int idCustomer = scanner.nextInt();

                System.out.println("Nome: ");
                String nameCustomer = scanner.nextLine();

                System.out.println("CPF: ");
                String cpfCustomer = scanner.nextLine();

                System.out.println("Data de nacimento (dd/mm/aaaa): ");
                LocalDate birthdayCustomer = LocalDate.parse(scanner.nextLine(),
                        DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                Customer customer = new Customer(idCustomer, nameCustomer, cpfCustomer, birthdayCustomer);
                customers.add(customer);

            case "MediaItem":
                System.out.println("\nEscolha o tipo de mídia:");
                System.out.println("1. Filme");
                System.out.println("2. Série de TV");
                System.out.print("Digite o número correspondente: ");
                int itemType = scanner.nextInt();

                System.out.print("Digite o ID: ");
                int id = scanner.nextInt();
                scanner.nextLine();

                System.out.print("Digite o título: ");
                String title = scanner.nextLine();

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

            case "RentalClerk":
                System.out.println("Complete os campos a seguir para cadastrar o cliente.");
                System.out.println("Digite o ID: ");
                int idClerk = scanner.nextInt();

                System.out.println("Nome: ");
                String nameClerk = scanner.nextLine();

                System.out.println("CPF: ");
                String cpfClerk = scanner.nextLine();

                System.out.println("Data de nacimento (dd/mm/aaaa): ");
                LocalDate birthdayClerk = LocalDate.parse(scanner.nextLine(),
                        DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                RentalClerk clerk = new RentalClerk(idClerk, nameClerk, cpfClerk, birthdayClerk);
                rentalClerks.add(clerk);
        }

    }

    protected void listArray(String arrayName) {
        switch (arrayName) {
            case "rentalTransactions":
                for (RentalTransaction rentalTransaction : rentalTransactions) {
                    System.out.println(rentalTransaction);
                }
            case "customers":
                for (Customer customer : customers) {
                    System.out.println(customer);
                }
            case "mediaItems":
                for (MediaItem mediaItem : mediaItems) {
                    System.out.println(mediaItem);
                }
            case "rentalClerks":
                for (RentalClerk rentalClerk : rentalClerks) {
                    System.out.println(rentalClerk);
                }
        }
    }

    protected void listItemsFromFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("Arquivo não existe! Salve os dados no arquivo.");
        }
        try (BufferedReader reader = createReader(fileName)) {
            String line;
            System.out.println("\nLista de Itens do Arquivo:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo.");
        }
    }

    private PrintWriter createWriter(String fileName) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(fileName));
        return writer;
    }

    private BufferedReader createReader(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        return reader;
    }

    protected void saveDataToFiles(String fileName, String arrayName) {
        File file = new File(fileName);

        if (!file.exists()) {
            try (PrintWriter writer = createWriter(fileName);) {
                for (MediaItem item : mediaItems) {
                    writer.println(item);
                }
                clearArrayMediaItems(arrayName);
                System.out.println("Dados salvos no arquivo de mídia.");
            } catch (IOException e) {
                System.out.println("Erro ao salvar os dados no arquivo de mídia.");
            }
        } else {
            try (BufferedReader reader = createReader(fileName)) {
                createCopy(reader, fileName, arrayName);
                clearArrayMediaItems(arrayName);
            } catch (IOException e) {
                System.out.println("Erro ao ler o arquivo.");
            }
        }
    }

    private void createCopy(BufferedReader reader, String fileName, String arrayName) {
        File fileCopy = new File("copy_" + fileName);
        try (PrintWriter writerCopy = createWriter(fileCopy.getPath())) {
            String line;
            while ((line = reader.readLine()) != null) {
                writerCopy.println(line);
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados no arquivo de mídia.");
        } finally {
            writeCopyOnFile(fileCopy, fileName, arrayName);
        }
    }

    private void writeCopyOnFile(File fileCopy, String fileName, String arrayName) {

        try (BufferedReader readerCopy = createReader(fileCopy.getPath());
                PrintWriter writer = createWriter(fileName)) {
            String line;
            while ((line = readerCopy.readLine()) != null) {
                writer.println(line);
            }
            for (MediaItem item : mediaItems) {
                writer.println(item);
            }
            clearArrayMediaItems(arrayName);
            System.out.println("Dados salvos no arquivo de mídia.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados no arquivo de mídia.");
        } finally {
            fileCopy.delete();
        }
    }

    private void clearArrayMediaItems(String arrayName) {
        switch (arrayName) {
            case "mediaItems":
                mediaItems.clear();
                System.out.println("Array de itens de mídia limpo.");

            case "rentalTransactions":
                rentalTransactions.clear();
                System.out.println("Array de locações limpo.");

            case "rentalClerks":
                rentalClerks.clear();
                System.out.println("Array de atendentes limpo.");

            case "customers":
                customers.clear();
                System.out.println("Array de clientes limpo.");
        }
    }

    protected void deleteItem(String objType) {
        System.out.print("Digite o ID do objeto a ser excluído: ");
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
            case "RentalTransaction":
                for (RentalTransaction rentalTransaction : rentalTransactions) {
                    if (rentalTransaction.getId() == idToDelete) {
                        rentalTransactions.remove(rentalTransaction);
                        found = true;
                    }
                }
            case "Customer":
                for (Customer customer : customers) {
                    if (customer.getId() == idToDelete) {
                        customers.remove(customer);
                        found = true;
                    }
                }
            case "RentalClerk":
                for (RentalClerk rentalClerk : rentalClerks) {
                    if (rentalClerk.getId() == idToDelete) {
                        rentalClerks.remove(rentalClerk);
                        found = true;
                    }
                }
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
}
