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

public class Menu {

    public Scanner scanner;
    public void FileName(Scanner scanner) {
        this.scanner = scanner;
    }

    private ArrayList<MediaItem> mediaItems = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<RentalClerk> rentalClerks = new ArrayList<>();
    private ArrayList<RentalTransaction> rentalTransactions = new ArrayList<>();

    public void main() {
        int choice;
        do {
            System.out.println("\nLocadora Menu:");
            System.out.println("1. Realizar Locação");
            System.out.println("2. Listar Locações");
            System.out.println("3. Listar Locações (Arquivo Físico)");
            System.out.println("4. Persistir Dados (Salvar no Arquivo e Limpar Array)");
            System.out.println("5. Excluir Locação (Ainda não salva no arquivo)");
            System.out.println("6. Limpar arquivo físico");
            System.out.println("7. Cadastrar/Listar Produtos");
            System.out.println("8. Cadastrar/Listar Clientes");
            System.out.println("9. Sessão Gerência");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            choice = scanner.nextInt();

            String transactionFileName = FileNameEnum.TRANSACTION_FILE_NAME.getFileName();
            switch (choice) {
                case 1:
                    makeRentalTransaction();
                    break;

                case 2:
                    listArray(rentalTransactions);
                    break;

                case 3:
                    listItemsFromFile(transactionFileName);
                    break;

                case 4:
                    saveDataToFiles(transactionFileName);
                    break;

                case 5:
                    deleteItem();
                    break;

                case 6:
                    clearFileContents(transactionFileName);
                    break;

                case 7:
                    productSession(choice, FileNameEnum.MEDIA_FILE_NAME.getFileName());

                case 8:
                    customerSession(choice, FileNameEnum.CUSTOMER_FILE_NAME.getFileName());

                case 9:
                    System.out.print("Senha:");
                    if (scanner.nextInt() == 123) {
                        managerSession(choice, FileNameEnum.CLERKS_FILE_NAME.getFileName());
                    } else {
                        System.out.println("Senha inválida.");
                        main();
                    }

                case 0:
                    System.out.println("Saindo do programa.");
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (choice != 0);
    }

    private void makeRentalTransaction() {

        System.out.println("Digite o ID do atendente:");
        RentalClerk clerk = findRentalClerk(rentalClerks, scanner.nextInt());

        System.out.println("Digite o ID do locador:");
        Customer customer = findCustomer(customers, scanner.nextInt());

        System.out.println("Digite o ID do produto:");
        MediaItem mediaItem = findMediaItem(mediaItems, scanner.nextInt());

        System.out.println("Digite a data de devolução:");
        LocalDate returnDate = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        long rentalDays = ChronoUnit.DAYS.between(LocalDate.now(), returnDate);

        RentalTransaction rentalTransaction = new RentalTransaction(clerk, customer, mediaItem, rentalDays);
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

    private void customerSession(int choice, String customerFileName) {
        do {
            System.out.println("\nSessão Cliente:");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("3. Listar Clientes (Arquivo Físico)");
            System.out.println("4. Persistir Dados (Salvar no Arquivo e Limpar Array)");
            System.out.println("5. Excluir Cliente (por ID)");
            System.out.println("6. Limpar Arquivo Físico");
            System.out.println("7. Voltar para menu");
            System.out.print("Escolha uma opção: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addItem();
                    break;
                case 2:
                    listArray(customers);
                    break;
                case 3:
                    listItemsFromFile(customerFileName);
                    break;
                case 4:
                    saveDataToFiles(customerFileName);
                    break;
                case 5:
                    deleteItem();
                    break;
                case 6:
                    clearFileContents(customerFileName);
                    break;
                case 7:
                    main();
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (choice != 7);
    }

    private void productSession(int choice, String mediaItemFileName) {
        do {
            System.out.println("\nSessão Produto:");
            System.out.println("1. Cadastrar Item");
            System.out.println("2. Listar Itens");
            System.out.println("3. Listar Itens (Arquivo Físico)");
            System.out.println("4. Persistir Dados (Salvar no Arquivo e Limpar Array)");
            System.out.println("5. Excluir Item (por ID)");
            System.out.println("6. Limpar Arquivo Físico");
            System.out.println("7. Voltar para menu");
            System.out.print("Escolha uma opção: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addItem();
                    break;
                case 2:
                    listArray(mediaItems);
                    break;
                case 3:
                    listItemsFromFile(mediaItemFileName);
                    break;
                case 4:
                    saveDataToFiles(mediaItemFileName);
                    break;
                case 5:
                    deleteItem();
                    break;
                case 6:
                    clearFileContents(mediaItemFileName);
                    break;
                case 7:
                    main();
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (choice != 7);
    }

    private void managerSession(int choice, String clerksFileName) {
        do {
            System.out.println("\nSessão Gerência:");
            System.out.println("1. Cadastrar Atendente");
            System.out.println("2. Listar Atendentes");
            System.out.println("3. Listar Atendentes (Arquivo Físico)");
            System.out.println("4. Persistir Dados (Salvar no Arquivo e Limpar Array)");
            System.out.println("5. Excluir Atendente (por ID)");
            System.out.println("6. Limpar Arquivo Físico");
            System.out.println("7. Voltar para Menu");
            System.out.print("Escolha uma opção: ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addItem();
                    break;

                case 2:
                    listArray(rentalClerks);
                    break;

                case 3:
                    listItemsFromFile(clerksFileName);
                    break;

                case 4:
                    saveDataToFiles(clerksFileName);

                case 5:
                    deleteItem();
                    break;

                case 6:
                    clearFileContents(clerksFileName);
                    break;

                case 7:
                    main();

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

        } while (choice != 7);
    }

    private void addItem() {

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
    }

    private void listArray(ArrayList<?> genericArray) {
        for (Object obj : genericArray) {
            if (obj instanceof MediaItem) {
                MediaItem item = (MediaItem) obj;
                System.out.println(item);
            } else if (obj instanceof Customer) {
                Customer costumer = (Customer) obj;
                System.out.println(costumer);
            } else if (obj instanceof RentalClerk) {
                RentalClerk rentalClerk = (RentalClerk) obj;
                System.out.println(rentalClerk);
            } else if (obj instanceof RentalTransaction) {
                RentalTransaction rentalTransaction = (RentalTransaction) obj;
                System.out.println(rentalTransaction);
            }
        }
    }

    private void listItemsFromFile(String fileName) {
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

    private void saveDataToFiles(String fileName) {
        File file = new File(fileName);

        if (!file.exists()) {
            try (PrintWriter writer = createWriter(fileName);) {
                for (MediaItem item : mediaItems) {
                    writer.println(item);
                }
                clearArrayMediaItems();
                System.out.println("Dados salvos no arquivo de mídia.");
            } catch (IOException e) {
                System.out.println("Erro ao salvar os dados no arquivo de mídia.");
            }
        } else {
            try (BufferedReader reader = createReader(fileName)) {
                createCopy(reader, fileName);
                clearArrayMediaItems();
            } catch (IOException e) {
                System.out.println("Erro ao ler o arquivo.");
            }
        }
    }

    private void createCopy(BufferedReader reader, String fileName) {
        File fileCopy = new File("copy_" + fileName);
        try (PrintWriter writerCopy = createWriter(fileCopy.getPath())) {
            String line;
            while ((line = reader.readLine()) != null) {
                writerCopy.println(line);
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados no arquivo de mídia.");
        } finally {
            writeCopyOnFile(fileCopy, fileName);
        }
    }

    private void writeCopyOnFile(File fileCopy, String fileName) {

        try (BufferedReader readerCopy = createReader(fileCopy.getPath());
                PrintWriter writer = createWriter(fileName)) {
            String line;
            while ((line = readerCopy.readLine()) != null) {
                writer.println(line);
            }
            for (MediaItem item : mediaItems) {
                writer.println(item);
            }
            clearArrayMediaItems();
            System.out.println("Dados salvos no arquivo de mídia.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados no arquivo de mídia.");
        } finally {
            fileCopy.delete();
        }
    }

    private void clearArrayMediaItems() {
        mediaItems.clear();
        System.out.println("Array de itens de mídia limpo.");
    }

    private void deleteItem() {
        System.out.print("Digite o ID do item a ser excluído: ");
        int idToDelete = scanner.nextInt();

        boolean found = false;
        for (int i = 0; i < mediaItems.size(); i++) {
            if (mediaItems.get(i).getId() == idToDelete) {
                mediaItems.remove(i);
                found = true;
                System.out.println("Item excluído com sucesso.");
                break;
            }
        }

        if (!found) {
            System.out.println("Item não encontrado.");
        }
    }

    private void clearFileContents(String fileName) {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            writer.print("");
            System.out.println("Conteúdo do arquivo limpo com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao limpar o arquivo.");
        }
    }
}
