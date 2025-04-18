import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
/**
 * @author: ______your name here (SID)_________
 *
 *          For the instruction of the assignment please refer to the assignment
 *          GitHub.
 *
 *          Plagiarism is a serious offense and can be easily detected. Please
 *          don't share your code to your classmate even if they are threatening
 *          you with your friendship. If they don't have the ability to work on
 *          something that can compile, they would not be able to change your
 *          code to a state that we can't detect the act of plagiarism. For the
 *          first commit of plagiarism, regardless you shared your code or
 *          copied code from others, you will receive 0 with an addition of 5
 *          mark penalty. If you commit plagiarism twice, your case will be
 *          presented in the exam board and you will receive a F directly.
 *
 *          Terms about generative AI:
 *          You are not allowed to use any generative AI in this assignment.
 *          The reason is straight forward. If you use generative AI, you are
 *          unable to practice your coding skills. We would like you to get
 *          familiar with the syntax and the logic of the Java programming.
 *          We will examine your code using detection software as well as
 *          inspecting your code with our eyes. Using generative AI tool
 *          may fail your assignment.
 *
 *          If you cannot work out the logic of the assignment, simply contact
 *          us on Discord. The teaching team is more the eager to provide
 *          you help. We can extend your submission due if it is really
 *          necessary. Just please, don't give up.
 */
public class BlockPuzzle {

    public static final
    char[][][] PUZZLES = {{{'A'}},                       // A

            {{'B'},                        // B
                    {'B'}},                       // B

            {{'C', 'C'},                   // CC
                    {'C', 'C'}},                  // CC

            {{'D', 'D', 'D'},              // DDD
                    {'D', '.', 'D'}},             // D D

            {{'E', 'E', 'E'},              // EEE
                    {'.', 'E'}},                  //  E

            {{'F', 'F', 'F'},              // FFF
                    {'F'}},                       // F

            {{'G', 'G', 'G'},              // GGG
                    {'.', '.', 'G'}},             //   G

            {{'H', 'H', 'H'},              // HHH
                    {'H', 'H', 'H'},              // HHH
                    {'H', 'H', 'H'}},             // HHH

            {{'I', 'I', 'I', 'I', 'I'}}   // IIIII


    };

    public static final int ROW = 8;
    public static final int COL = 9;
    public static final int ROUND = 3;
    public static final char EMPTY = '.';

    public static final int[] SCORES = {0, 10, 30, 50, 100, 200, 500};

    public static void main(String[] args) {
        BlockPuzzle bp = new BlockPuzzle();
        bp.run();
    }

    /**
     * The main game loop. Given.
     * <p>
     * This method has been finished for you.
     * You are not allowed to change this method.
     */
    void run() {
        char[][] map = new char[ROW][COL];
        char[][][] puzzlesToPlace = new char[ROUND][][];
        init(map);
        int score = 0;
        while (true) {
            randomPuzzleIfNeeded(puzzlesToPlace);
            printMap(map);
            System.out.println("Your score: " + score);
            printPuzzles(puzzlesToPlace);
            if (gameOver(map, puzzlesToPlace)) {
                System.out.println("Game Over..\nYour score is " + score);
                return;
            }
            getInputAndPlacePuzzle(map, puzzlesToPlace);
            score += SCORES[cancelPuzzles(map)];
        }
    }

    /**
     * Initialize the map so that all cells
     * in the 2D array is assigned with the character '.'
     * <p>
     * The input map is a 2D array of characters.
     * You may assume it is a non-null, rectangular 2D array.
     */
    void init(char[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = '.';
            }
        }
    }

    /**
     * Print the puzzles to the console.
     * <p>
     * The input puzzles is an array of 2D arrays.
     * Print each of the 2D arrays.
     * If the 2D array is null, print "used" instead.
     * <p>
     * Assume the input puzzles is as follows:
     * puzzles[0] = {{'A'}}
     * puzzles[1] = null
     * puzzles[2] = {{'C', 'C'}, {'C', 'C'}}
     * The output should be:
     * Puzzle 0
     * A
     * Puzzle 1
     * used
     * Puzzle 2
     * CC
     * CC
     */
    void printPuzzles(char[][][] puzzles) {
        for (int i = 0; i < puzzles.length; i++) {
            System.out.printf("Puzzle %d\n", i);
            if (puzzles[i] == null) {
                System.out.println("used");
            } else {
                for (int j = 0; j < puzzles[i].length; j++) {
                    for (int k = 0; k < puzzles[i][j].length; k++) {
                        System.out.print(puzzles[i][j][k]);
                    }
                    System.out.println();
                }
            }
        }
    }

    /**
     * Print the map to the console.
     * <p>
     * The map should be printed as follows:
     * <p>
     *  012345678
     * a....CC...
     * b....CC...
     * c.........
     * d.........
     * e....IIIII
     * f.........
     * g.........
     * h.........
     * <p>
     * The first row should be the column index 0 to 8.
     * The first column should be the row index a to h.
     */
    void printMap(char[][] map) {
        System.out.println(" 012345678");
        for (int i = 0; i < map.length; i++) {
            System.out.print((char) ('a' + i));
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }


    /**
     * This method is used to get the input from the user and place the puzzle
     * <p>
     * The input map is a 2D array of characters, representing your game map.
     * The input puzzles is an array of 2D arrays, representing the puzzles that
     * you can place.
     * <p>
     * The method should get the input from the user, and place the puzzle to the
     * map. If the input is invalid, e.g. the !!!index!!! is out-of-bound, or the !!!position!!!
     * is invalid, the method should print some error message and ask the user to
     * to input again, until the user enter a valid input.
     * <p>
     * After the user has placed the puzzle, the method should update the map and
     * remove that puzzle from the puzzles array.
     */
    void getInputAndPlacePuzzle(char[][] map, char[][][] puzzles) {
        Scanner in = new Scanner(System.in);
        boolean invalidPlace;
        invalidPlace = true;
        //void randomPuzzleIfNeeded(char[][][] puzzles)
        //boolean gameOver(char[][] map, char[][][] puzzles)
        //boolean canPlace(char[][] map, char[][] puzzle, int r, int c)           Yes
        //void place(char[][] map, char[][] puzzle, int r, int c)                 Yes
        //boolean validateInput(String input)                                     Yes
        //int[] convertInputToRC(String input)                                    Yes
        //int cancelPuzzles(char[][] map)
        do {
            int index = -1;
            System.out.print("Enter the index of puzzle you want to place: ");
            try {
                index = in.nextInt();
                in.nextLine();
            }catch(Exception e){
                System.out.println("Error! Please enter an integer(0-2)");
                in.nextLine();
                continue;
            }
            //check index
            if (index >= 0 && index <= 2 && puzzles[index] != null) {
                System.out.print("Enter the position you want to place(a0-h8): ");
                String position = in.nextLine();
                int[] truePosition = convertInputToRC(position);
                int row = truePosition[0];
                int col = truePosition[1];
                //check position
                if(validateInput(position) && canPlace(map, puzzles[index], row, col)){
                    place(map, puzzles[index], row, col);
                    invalidPlace = false;
                    puzzles[index] = null;
                }else if(!(validateInput(position)))
                    System.out.println("Error! Please input the position in bound(a0-h8)!");
                else if(!(canPlace(map, puzzles[index], row, col)))
                    System.out.println("Error! Please input the position at empty place!");
            }else if(!(index >= 0 && index <= 2))
                System.out.println("Error! Please input the index in bound(0-2)!");
            else if(!(puzzles[index] != null))
                System.out.println("Error! Please input the index of non-used puzzle!");
        } while (invalidPlace);
    }

    /**
     * To randomize the puzzles if needed.
     * <p>
     * The input puzzles is an array of 2D arrays. When all the elements in the
     * array are null, the method should randomize the puzzles array.
     * <p>
     * After randomization, the method should assign the puzzles array with
     * the randomized puzzles. You should use PUZZLES - a constant 3D array
     * to get the puzzles.
     */
    void randomPuzzleIfNeeded(char[][][] puzzles) {
        boolean allUsed = true;
        for (char[][] i : puzzles) {
            if (i != null)
                allUsed = false;
        }
        if (allUsed) {
            for (int i = 0; i < puzzles.length; i++) {
                puzzles[i] = PUZZLES[ThreadLocalRandom.current().nextInt(0, 9)];
            }
        }

    }

    /**
     * To check if the game is over.
     * <p>
     * The gameOver condition is defined as when there is no more valid position
     * to place any puzzle from the arrays puzzles.
     * <p>
     * Return true if it is game over, otherwise return false.
     */
    boolean gameOver(char[][] map, char[][][] puzzles) {
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[i].length; j++){
                if(map[i][j]=='.'){
                    for(int k = 0; k < puzzles.length; k++){
                        if(puzzles[k]!=null&&canPlace(map, puzzles[k], i, j))
                            return false;
                    }
                }
            }

        }
        return true;
    }

    /**
     * Check if the puzzle can be placed at the position (r, c) of the map.
     * <p>
     * The input map is a 2D array of characters, representing the game map.
     * The input puzzle is a 2D array of characters, representing the puzzle.
     * The input r is an integer, representing the row index of the map.
     * The input c is an integer, representing the column index of the map.
     * <p>
     * A puzzle can be placed at the position (r, c) if the !!!non-empty cells of the
     * puzzle do not cover any non-empty cells of the map!!!. Also, the puzzle should
     * be placed !!!within the boundary of the map!!!.
     * <p>
     * <p>
     * The method should return true if the puzzle can be placed at the position (r, c)
     * of the map. Otherwise, return false.
     */
    boolean canPlace(char[][] map, char[][] puzzle, int r, int c) {
        boolean result;
        result = true;
        if(r + puzzle.length > 8 || c + puzzle[0].length > 9)
            return false;
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[i].length; j++) {
                if(puzzle[i][j]!='.'){
                    if(map[r+i][c+j]!='.') {//该位置不为空，在范围内，则不能
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * This method is to place the puzzle at the position (r, c) of the map.
     * You can assume that the puzzle can be placed at the position (r, c) of the map when this
     * method is called.
     *
     * The map will be updated after placing the puzzle.
     * The puzzle will not be updated after the puzzle is placed.
     */
    void place(char[][] map, char[][] puzzle, int r, int c) {
        for (int i = 0; i < puzzle.length; i++) {
            int tmp = c;
            for (int j = 0; j < puzzle[i].length; j++) {
                if(puzzle[i][j]!='.')
                    map[r][c] = puzzle[i][j];
                if (j != puzzle[i].length - 1)
                    c++;
            }
            r++;
            c = tmp;
        }
    }

    /**
     * Validate the input string.
     *
     * Check if the input string is a valid input.
     * The input string should be a string of length 2.
     * The first character should be a lowercase letter from 'a' to 'z'.
     * The second character should be a digit from '0' to '9'.
     *
     * Return true if the input string is valid, otherwise return false.
     *
     * You also need to figure out how to use this method in your code
     * properly, i.e., which method should call this method.
     */
    boolean validateInput(String input) {
        boolean result;
        /*int[] position = convertInputToRC(input);*/
        if(input.length()!=2) return false;
        int r = input.charAt(0) - 'a';
        int c = input.charAt(1) - '0';
        if(r>=0 && r <= 7  && c >= 0 && c <=8)
            return true;
        return false;
    }


    /**
     * Convert the input string to row and column index.
     *
     * The input string represents the position of the map, e.g. "a1", "b7", "d4", etc..
     *
     * The method should convert the input string to the row and column index.
     * row index is between 0 to 8
     * column index is between 0 to 8
     *
     * return an array of two integers, where the first element is the row index,
     * and the second element is the column index.
     *
     * Therefore, if the input string is "a1", the method should return an array of {0, 1}.
     * If the input string is "b7", the method should return an array of {1, 7}.
     */
    int[] convertInputToRC(String input) {
        int[] result = new int[2];
        result[0] = input.charAt(0) - 'a';
        result[1] = input.charAt(1) - '0';
        return result;
    }

    /**
     * Cancel the puzzles in the map.
     *
     * If a cell is cancelled, it will become the EMPTY symbol '.'
     * When a row or a column is all filled with non-empty cells, the row or column
     * will be cancelled.
     *
     * It is possible to cancel more than a row or a column at the same time.
     *
     * The method should return the number of rows and columns that are cancelled.
     */
    int cancelPuzzles(char[][] map) {
        boolean[] cancelRow = new boolean[ROW];
        boolean[] cancelCol = new boolean[COL];
        for(int i = 0; i < ROW; i++)
            cancelRow[i] = true;
        for(int j = 0; j < COL; j++)
            cancelCol[j] = true;
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[i].length; j++){
                if(map[i][j]=='.'){
                    cancelRow[i]=false;
                    cancelCol[j]=false;
                }
            }
        }
        int rowCount = 0;
        int colCount = 0;
        for(int i = 0; i < ROW; i++){
            if(cancelRow[i]){
                rowCount++;
                for(int j = 0; j< COL; j++)
                    map[i][j] = '.';
            }
        }
        for(int j = 0; j < COL; j++){
            if(cancelCol[j]){
                colCount++;
                for(int k = 0; k < ROW; k++)
                    map[j][k] = '.';
            }
        }
        return rowCount + colCount;
    }
}