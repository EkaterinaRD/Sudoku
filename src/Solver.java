import org.sat4j.minisat.SolverFactory;
import org.sat4j.reader.DimacsReader;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.reader.Reader;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

import java.io.IOException;

public class Solver {

    private int[][] array;
    private int[] solve;
    private String cnf_file;
    private static int count_var = 729;
    public boolean error;

    public Solver(String cnf_file) {

        array = new int[9][9];
        solve = new int[count_var];
        this.cnf_file = cnf_file;
        error = false;
    }

    public void solve() {

        ISolver solver = SolverFactory.newDefault();
        solver.setTimeout(3600);

        Reader reader = new DimacsReader(solver);
        try {
            IProblem problem = reader.parseInstance(cnf_file);
            if (problem.isSatisfiable()) {
                System.out.println("Satisfiable!");
                solve = problem.model();
                error = false;

            } else {
                System.out.println("Unsatisfiable!");
                error = true;
            }

        } catch (ParseFormatException | IOException e) {
            e.printStackTrace();
        } catch (ContradictionException e) {
            System.out.println("Unsatisfiable (trivial)!");
            error = true;
        } catch (TimeoutException e) {
            System.out.println("Timeout, sorry!");
            error = true;
        }
    }

    private void getArray() {

        //solve();
            int col, row, val;
            for (int i = 0; i < count_var; i++) {
                if (solve[i] > 0) {
                    row = i / 81;
                    col = (i % 81) / 9;
                    val = (i % 9) + 1;
                    array[row][col] = val;
                }
            }

    }

    public void print() {

        getArray();
        for (int i = 0; i < 9; i++) {
          for (int j = 0; j < 9; j++) {
              System.out.print(array[i][j] + " ");
          }
          System.out.println();
        }
    }
}
