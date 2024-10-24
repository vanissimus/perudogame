package game;

import players.Player;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("""
                    Welcome to Perudo game
                    
                    Choose the option:
                    a. Play the game
                    b. Check the Rules
                    c. Exit
                    
                    Write the letter you need below:
                    
                    """);
        label:
        while (true) {

            String menuPoint;
            Scanner sc = new Scanner(System.in);
            menuPoint = sc.nextLine().substring(0, 1);

            List<Player> players = new ArrayList<>();

            switch (menuPoint) {
                case "a":
                    // Количество игроков
                    int playersQty = getNumberOfPlayers();
                    // Задать имя компьютерам
                    String[] namePlayersArray = generateRandomName(playersQty);
                    // Задать имя игроку
                    namePlayersArray[playersQty-1] = getNamePlayers();
                    System.out.println("Today's players list:");

                    // Начальные параметры игры
                    int[] diceCountsArray = initializeGame(namePlayersArray);
                    System.out.println("Let's the fight begin? If yes press 1 ?");
                    int y1n;
                    y1n = sc.nextInt();
                if (y1n == 1) {
                    // Старт игры
                    startGame(namePlayersArray, diceCountsArray);
                }
                    break;
                case "b":
                    getGameRules();
                    break;
                case "c":
                    break label;
            }
        }

    }
    // Начальные параметры игры, у каждого игрока по 5 костей
    private static int[] initializeGame(String[] namePlayersArray) {
        int[] diceCountsArray = new int[namePlayersArray.length];
        for (int i = 0; i < namePlayersArray.length; i++) {
            diceCountsArray[i] = 5;
        }
        return diceCountsArray;
    }

    // Начало игры, цикл
    private static void startGame(String[] namePlayersArray, int[] diceCountsArray) {
        Random rand = new Random();
        boolean hasWinner = false;

        // Жеребьёвка
        System.out.println("Start the game! Throw Dice!");
        while (!hasWinner) {
            int maxRoll = 0;
            List<String> playersWithMaxRoll = new ArrayList<>(); // Массив игроков, для переигровки

            for (String player : namePlayersArray) {
                int number = rand.nextInt(1, 7);
                System.out.println("Player: " + player + " get: " + number);

                // Определяем максимальное значение и игроков с ним
                if (number > maxRoll) {
                    maxRoll = number;
                    playersWithMaxRoll.clear();
                    playersWithMaxRoll.add(player);
                } else if (number == maxRoll) {
                    playersWithMaxRoll.add(player);
                }
            }

            // Проверка на наличие победителя
            if (playersWithMaxRoll.size() == 1) {
                System.out.println("We have a winner! Press 1 to check!");
                int y2n;
                Scanner sc = new Scanner(System.in);
                y2n = sc.nextInt();
                if (y2n == 1) {
                    hasWinner = true;
                    System.out.println("\n\n\n>>>>> And the Winner is: " + playersWithMaxRoll.get(0) + " <<<<<<\n\n\n");
                }
                // Получить индекс игрока по имени
                int winnerIndex = 0;
                for (int i = 0; i < namePlayersArray.length; i++) {
                    if (namePlayersArray[i].equals(playersWithMaxRoll.get(0))) {
                        winnerIndex = i;
                    }
                }

                // Сдвинуть победителя на первый индекс
                for (int i = winnerIndex; i > 0; i--) {
                    namePlayersArray[i] = namePlayersArray[i - 1];
                }
                namePlayersArray[0] = playersWithMaxRoll.get(0);

            } else {
                System.out.println("\nНичья! Переигровка между игроками: " + playersWithMaxRoll);
                // Переигровка среди игроков с максимальным значением

                namePlayersArray = playersWithMaxRoll.toArray(new String[0]);
            }
        }

        // Ход первого игрока

    }

    // Вывести правила в консоль
    public static void getGameRules() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/main/java/game/rules.txt"));
        String rules;
        while ((rules = br.readLine()) != null) {
            System.out.println(rules);
        }
        br.close();
    }

    // Указать количество игроков
    public static int getNumberOfPlayers() {
        Scanner sc = new Scanner(System.in);
        int playersQty;
        while (true) {
            try {
                System.out.println("\nHow many Players? (possible from 2 to 6):");
                playersQty = sc.nextInt();

                if (playersQty >= 2 && playersQty <= 6) {
                    break;
                } else {
                    System.out.println("Out of range qty. Type in again, please");
                }
            } catch (Exception e) {
                System.out.println("Type once again, please.");
            }
        }
        return playersQty;
    }

    // Генерация имён для компьютеров
    public static String[] generateRandomName(int playersQty) {
        Random rnd = new Random();

        // Массив имён для генерации
        String[] nameGeneration  = {"botVJ", "botOla", "botMaximus", "botBondJames", "botArthur", "bot4br", "botTheo", "botSKL", "botSim", "botBro", "botSister", "botBarmalei"};

        // Пустой массив имён для компьютеров
        String[] nameComputerArray = new String[playersQty];

        for (int i = 0; i < playersQty-1; i++){
            nameComputerArray[i] = nameGeneration[rnd.nextInt(nameGeneration.length)];
        }
        return nameComputerArray;
    }

    // Ввод или генерация имён для игрока
    public static String getNamePlayers() {
        Scanner sc = new Scanner(System.in);
        Random rnd = new Random();

        // Массив имён для генерации
        String[] nameGeneration  = {"Key", "J", "O", "M", "K", "Snowman", "Kolobok", "Ivan", "CiJin", "Kleo", "U", "E"};

        System.out.println("\nПридумайте или сгенерируйте имя");
        // Выбор имени для игрока
        String namePlayer = "";

        String menuPoint;

        try {
            while (true) {
                System.out.println("a. Write the name\n" +
                        "b. Create the name");

                menuPoint = sc.nextLine().substring(0, 1);

                if (menuPoint.equals("a")) {
                    namePlayer = sc.nextLine();
                    break;
                }
                else if (menuPoint.equals("b")) {
                    namePlayer = nameGeneration[rnd.nextInt(nameGeneration.length)];
                    break;
                }
                else {
                    System.out.println("Choose a or b please");
                }
            }
        } catch (Exception e) {
            System.out.println("Escho raz tak sdelaesh i game over.");
        }
        return namePlayer;
    }
}