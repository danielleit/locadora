import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;

import models.Customer;
import models.MediaItem;
import models.Movie;
import models.RentalTransaction;
import models.TVSeries;
import models.enums.FileNameEnum;

public class App {

    private static ArrayList<MediaItem> mediaItems = new ArrayList<>();
    private static ArrayList<Customer> customers = new ArrayList<>();
    private static ArrayList<RentalTransaction> rentalTransactions = new ArrayList<>();

    public static void main(String[] args) {
        loadMediaItems();
        loadCustomers();
        loadRentalTransactions();       

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nLocadora Menu:");
            System.out.println("1. Cadastrar Item");
            System.out.println("2. Listar Itens");
            System.out.println("3. Listar Itens (Arquivo Físico)");
            System.out.println("4. Persistir Dados (Salvar no Arquivo e Limpar Array)");
            System.out.println("5. Excluir Item (por ID)");
            System.out.println("6. Limpar Arquivo Físico");
            System.out.println("7. Sessão Gerência");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addItem();
                    break;
                case 2:
                    listItems(mediaItems);
                    break;
                case 3:
                    listItemsFromFile(FileNameEnum.MEDIA_FILE_NAME.getFileName());
                    break;
                case 4:
                    saveDataToFiles(FileNameEnum.MEDIA_FILE_NAME.getFileName());
                    break;
                case 5:
                    deleteItem();
                    break;
                case 6:
                    clearFileContents(FileNameEnum.MEDIA_FILE_NAME.getFileName());
                    break;
                case 7:
                    System.out.print("Senha:");
                    if(scanner.nextInt() == 123){
                        do {
                            System.out.println("1. Cadastrar Atendente");
                            System.out.println("2. Listar Atendentes");
                            System.out.println("3. Listar Atendentes (Arquivo Físico)");
                            System.out.println("4. Persistir Dados (Salvar no Arquivo e Limpar Array)");
                            System.out.println("5. Excluir Atendente (por ID)");
                            System.out.println("6. Limpar Arquivo Físico");                            
                        } while (choice != 0);
                    }
                case 0:
                    System.out.println("Saindo do programa.");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (choice != 0);
    }

    private static void addItem() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nEscolha o tipo de mídia:");
        System.out.println("1. Filme");
        System.out.println("2. Série de TV");
        System.out.print("Digite o número correspondente: ");
        int itemType = scanner.nextInt();

        System.out.print("Digite o ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

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

    private static void listItems(ArrayList<MediaItem> items) {
        System.out.println("\nLista de Itens:");
        for (MediaItem item : items) {
            System.out.println(item);
        }
    }

    private static void listItemsFromFile(String fileName) {
        // tratar erro: se não tiver arquivo txt salvo
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

    private static void saveDataToFiles(String fileName) {
        File file = new File(fileName);

        if (!file.exists()) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(FileNameEnum.MEDIA_FILE_NAME.getFileName()))) {
                for (MediaItem item : mediaItems) {
                    writer.println(item);
                }
                clearMediaItems();
                System.out.println("Dados salvos no arquivo de mídia.");
            } catch (IOException e) {
                System.out.println("Erro ao salvar os dados no arquivo de mídia.");
            }
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                saveMediaItemsToFile(reader);
                clearMediaItems();
            } catch (IOException e) {
                System.out.println("Erro ao ler o arquivo.");
            }
        }
    }

    private static void saveMediaItemsToFile(BufferedReader reader) {
        File file = new File("copy_" + FileNameEnum.MEDIA_FILE_NAME.getFileName());
        try (PrintWriter writerCopy = new PrintWriter(new FileWriter(file.getPath()))) {
            String line;
            // System.out.println("TESTE 1: " + reader.readLine());
            while ((line = reader.readLine()) != null) {
                writerCopy.println(line);
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados no arquivo de mídia.");
        } finally {
            saveMediaItemsToFile(file);
        }
    }

    private static void saveMediaItemsToFile(File file) {
        
            try (BufferedReader readerCopy = new BufferedReader(new FileReader(file.getPath()));
                    PrintWriter writer = new PrintWriter(new FileWriter(FileNameEnum.MEDIA_FILE_NAME.getFileName()))) {
                String line;
                // System.out.println("TESTEEEE: " + readerCopy.readLine());
                while ((line = readerCopy.readLine()) != null) {
                    writer.println(line);
                }
                for (MediaItem item : mediaItems) {
                    writer.println(item);
                }
                clearMediaItems();
                file.delete();
                System.out.println("Dados salvos no arquivo de mídia.");
            } catch (IOException e) {
                System.out.println("Erro ao salvar os dados no arquivo de mídia.");
            }
    }

    private static void clearMediaItems() {
        mediaItems.clear();
        System.out.println("Array de itens de mídia limpo.");
    }

    private static void deleteItem() {
        Scanner scanner = new Scanner(System.in);
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

    private static void clearFileContents(String fileName) {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            writer.print("");
            System.out.println("Conteúdo do arquivo limpo com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao limpar o arquivo.");
        }
    }

    private static void loadMediaItems() {
        // Implementar a lógica para carregar itens de mídia do arquivo, se aplicável.
    }

    private static void loadCustomers() {
        // Implementar a lógica para carregar clientes do arquivo, se aplicável.
    }

    private static void loadRentalTransactions() {
        // Implementar a lógica para carregar transações de locação do arquivo, se
        // aplicável.
    }
}