import java.util.Scanner;

public class MainClass {

    public static void main(String[] args) {


        Scanner in = new Scanner(System.in);
        System.out.print("Input name file: ");
        String sudoku = in.nextLine() + ".txt";
        CreateFormulas cf  = new CreateFormulas(sudoku);

        Solver result = new Solver(cf.createFormulas());
        result.print();
    }
}
