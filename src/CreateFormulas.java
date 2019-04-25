import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CreateFormulas {

    private String sudoku_file;
    private String cnf_file;
    private int count_clause;
    private static int count_var = 729;

    public CreateFormulas(String sudoku_file) {

        this.sudoku_file = sudoku_file;
        count_clause = 0;
        cnf_file = "formula.cnf";
    }

    public String createFormulas() {

        StringBuilder sb = new StringBuilder();

        try {

            FileReader reader = new FileReader(sudoku_file);
            int i = 0, c, digit;
            while ((c = reader.read()) != -1) {
                if ((char)c != '.' && (char)c != '\n' && (char)c != '\r') {
                    digit = c - '0';
                    digit = digit + i * 9;
                    sb.append(digit).append(" 0\n");
                    count_clause++;
                }
                if ((char)c != '\n' && (char)c != '\r') {
                    i++;
                }
            }
            reader.close();

        } catch (IOException e) {

            e.printStackTrace();
            System.exit(1);
        }

        int num1, num2;
        //1
        for (int k = 1; k <= 9; k++) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    for (int t = 1; t <= 9; t++) {
                        if (t != k) {
                            num1 = Add(i,j,k);
                            num2 = Add(i,j,t);
                            sb.append((-1) * num1 + " " + (-1) * num2 + " 0\n");
                            count_clause++;
                        }
                    }
                }
            }
        }

        //2
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                for (int k = 1; k <= 9; k++) {
                    num1 = Add(i, j, k);
                    sb.append(num1 + " ");
                }
                sb.append(" 0\n");
                count_clause++;
            }
        }

        //3
        for (int k = 1; k <=9; k++) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    for (int t = 0; t < 9; t++) {
                        if (t != j) {
                            num1 = Add(i,j,k);
                            num2 = Add(i,t,k);
                            sb.append((-1) * num1 + " " + (-1) * num2+" 0\n");
                            count_clause++;
                        }
                    }
                }
            }
        }

        //3'
        for (int i = 0; i < 9; i++) {
            for (int k = 1; k <= 9; k++) {
                for (int j = 0; j < 9; j++) {
                    num1 = Add(i, j, k);
                    sb.append(num1 + " ");
                }
                sb.append(" 0\n");
                count_clause++;
            }
        }

        //4
        for (int k = 1; k <=9; k++) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    for (int t = 0; t < 9; t++) {
                        if (t != i) {
                            num1 = Add(i,j,k);
                            num2 = Add(t,j,k);
                            sb.append((-1) * num1 + " " + (-1) * num2+" 0\n");
                            count_clause++;
                        }
                    }
                }
            }
        }

        //4'
        for (int k = 1; k <= 9; k++) {
            for (int j = 0; j < 9; j++) {
                for (int i = 0; i < 9; i++) {
                    num1 = Add(i, j, k);
                    sb.append(num1 + " ");
                }
                sb.append(" 0\n");
                count_clause++;
            }
        }

        //5
        for (int a = 0; a < 3; a++) {
            for (int b = 0; b < 3; b++) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        for (int k = 1; k <= 9; k++) {
                            for (int n = 0; n < 3; n++) {
                                for (int m = 0; m < 3; m++) {
                                    if (!(i == n && j == m)) {
                                        num1 = Add(3*a+i, 3*b+j, k);
                                        num2 = Add(3*a+n, 3*b+m, k);
                                        sb.append((-1) * num1 + " " + (-1) * num2 + " 0\n");
                                        count_clause++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


        try {
          sb.insert(0, "p " + "cnf " + count_var + " " + count_clause + "\n");
          FileWriter writer = new FileWriter(cnf_file, false);
          writer.write(sb.toString());
          writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cnf_file;
    }

    private int Add(int i, int j, int k) {

        int num = i * 81 + j * 9 + k;
        return num;
    }
}
