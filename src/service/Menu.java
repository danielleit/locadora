package service;

import java.util.Scanner;

import models.enums.FileNameEnum;

public class Menu {

    Scanner scanner = new Scanner(System.in);
    FileEdit fileEdit = new FileEdit(scanner);

    public void main() {

        int choice;

        do {
            System.out.println("\nLocadora Menu:");
            System.out.println("1. Realizar/Listar Locacoes");
            System.out.println("2. Cadastrar/Listar Produtos");
            System.out.println("3. Cadastrar/Listar Clientes");
            System.out.println("4. Cadastrar/Listar Atendentes");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opcao: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    rentalTransactionSession(choice, FileNameEnum.TRANSACTION_FILE_NAME.getFileName());

                case 2:
                    productSession(choice, FileNameEnum.MEDIA_FILE_NAME.getFileName());

                case 3:
                    customerSession(choice, FileNameEnum.CUSTOMER_FILE_NAME.getFileName());

                case 4:
                    managerSession(choice, FileNameEnum.CLERKS_FILE_NAME.getFileName());

                case 0:
                    System.out.println("Saindo do programa.");
                    break;

                default:
                    System.out.println("Opcao invalida. Tente novamente.");
            }
        } while (choice != 0);
    }

    private void rentalTransactionSession(int choice, String transactionFileName) {

        String arrayName = "rentalTransactions"; // Array que vai ser acessado nesse menu
        String objType = "RentalTransaction"; // Tipo do objeto que vai ser acessado nesse menu

        do {
            System.out.println("\nSessao de Locacao");
            System.out.println("1. Realizar Locacao");
            System.out.println("2. Listar Locacoes");
            System.out.println("3. Listar Locacoes (Arquivo Fisico)");
            System.out.println("4. Persistir Dados (Salvar no Arquivo e Limpar Array)");
            System.out.println("5. Excluir Locacao (por Id)");
            System.out.println("6. Limpar arquivo fisico");
            System.out.println("7. Voltar para menu");
            System.out.print("Escolha uma opcao: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:

                    fileEdit.makeRentalTransaction();
                    break;

                case 2:
                    fileEdit.listArray(arrayName);
                    break;

                case 3:
                    fileEdit.listItemsFromFile(transactionFileName);
                    break;

                case 4:
                    fileEdit.saveDataToFiles(transactionFileName, arrayName);
                    break;

                case 5:
                    fileEdit.deleteItem(objType);
                    break;

                case 6:
                    fileEdit.clearFileContents(transactionFileName);
                    break;

                case 7:
                    main();

                default:
                    System.out.println("Opcao invalida. Tente novamente.");
            }
        } while (choice != 7);
    }

    private void customerSession(int choice, String customerFileName) {

        String arrayType = "customers"; // Array que vai ser acessado nesse menu
        String objType = "Customer"; // Tipo do objeto que vai ser acessado nesse menu

        do {
            System.out.println("\nSessao Cliente:");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("3. Listar Clientes (Arquivo Fisico)");
            System.out.println("4. Persistir Dados (Salvar no Arquivo e Limpar Array)");
            System.out.println("5. Excluir Cliente (por ID)");
            System.out.println("6. Limpar Arquivo Fisico");
            System.out.println("7. Voltar para menu");
            System.out.print("Escolha uma opcao: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    fileEdit.addObjOnArray(objType);
                    break;
                case 2:
                    fileEdit.listArray(arrayType);
                    break;
                case 3:
                    fileEdit.listItemsFromFile(customerFileName);
                    break;
                case 4:
                    fileEdit.saveDataToFiles(customerFileName, arrayType);
                    break;
                case 5:
                    fileEdit.deleteItem(objType);
                    break;
                case 6:
                    fileEdit.clearFileContents(customerFileName);
                    break;
                case 7:
                    main();
                default:
                    System.out.println("Opcao invalida. Tente novamente.");
            }
        } while (choice != 7);
    }

    private void productSession(int choice, String mediaItemFileName) {

        String arrayType = "mediaItems"; // Array que vai ser acessado nesse menu
        String objType = "MediaItem"; // Tipo do objeto que vai ser acessado nesse menu

        do {
            System.out.println("\nSessao Produto:");
            System.out.println("1. Cadastrar Item");
            System.out.println("2. Listar Itens");
            System.out.println("3. Listar Itens (Arquivo Fisico)");
            System.out.println("4. Persistir Dados (Salvar no Arquivo e Limpar Array)");
            System.out.println("5. Excluir Item (por ID)");
            System.out.println("6. Limpar Arquivo Fisico");
            System.out.println("7. Voltar para menu");
            System.out.print("Escolha uma opcao: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    fileEdit.addObjOnArray(objType);
                    break;
                case 2:
                    fileEdit.listArray(arrayType);
                    break;
                case 3:
                    fileEdit.listItemsFromFile(mediaItemFileName);
                    break;
                case 4:
                    fileEdit.saveDataToFiles(mediaItemFileName, arrayType);
                    break;
                case 5:
                    fileEdit.deleteItem(objType);
                    break;
                case 6:
                    fileEdit.clearFileContents(mediaItemFileName);
                    break;
                case 7:
                    main();
                default:
                    System.out.println("Opcao invalida. Tente novamente.");
            }
        } while (choice != 7);
    }

    private void managerSession(int choice, String clerksFileName) {

        String arrayType = "rentalClerks"; // Array que vai ser acessado nesse menu
        String objType = "RentalClerk"; // Tipo do objeto que vai ser acessado nesse menu

        do {
            System.out.println("\nSessao Gerencia:");
            System.out.println("1. Cadastrar Atendente");
            System.out.println("2. Listar Atendentes");
            System.out.println("3. Listar Atendentes (Arquivo Fisico)");
            System.out.println("4. Persistir Dados (Salvar no Arquivo e Limpar Array)");
            System.out.println("5. Excluir Atendente (por ID)");
            System.out.println("6. Limpar Arquivo Fisico");
            System.out.println("7. Voltar para Menu");
            System.out.print("Escolha uma opcao: ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    fileEdit.addObjOnArray(objType);
                    break;

                case 2:
                    fileEdit.listArray(arrayType);
                    break;

                case 3:
                    fileEdit.listItemsFromFile(clerksFileName);
                    break;

                case 4:
                    fileEdit.saveDataToFiles(clerksFileName, arrayType);
                    break;

                case 5:
                    fileEdit.deleteItem(objType);
                    break;

                case 6:
                    fileEdit.clearFileContents(clerksFileName);
                    break;

                case 7:
                    main();

                default:
                    System.out.println("Opcao invalida. Tente novamente.");
            }

        } while (choice != 7);
    }
}
