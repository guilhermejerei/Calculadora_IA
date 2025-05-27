import java.util.Scanner;

class NumeroComplexo {
    double real;
    double imaginario;

    public NumeroComplexo(double real, double imaginario) {
        this.real = real;
        this.imaginario = imaginario;
    }

    public NumeroComplexo somar(NumeroComplexo outro) {
        return new NumeroComplexo(this.real + outro.real, this.imaginario + outro.imaginario);
    }

    public NumeroComplexo subtrair(NumeroComplexo outro) {
        return new NumeroComplexo(this.real - outro.real, this.imaginario - outro.imaginario);
    }

    public NumeroComplexo multiplicar(NumeroComplexo outro) {
        double r = this.real * outro.real - this.imaginario * outro.imaginario;
        double i = this.real * outro.imaginario + this.imaginario * outro.real;
        return new NumeroComplexo(r, i);
    }

    public NumeroComplexo dividir(NumeroComplexo outro) {
        double denom = outro.real * outro.real + outro.imaginario * outro.imaginario;
        if (denom == 0) throw new ArithmeticException("Divisão por zero");
        double r = (this.real * outro.real + this.imaginario * outro.imaginario) / denom;
        double i = (this.imaginario * outro.real - this.real * outro.imaginario) / denom;
        return new NumeroComplexo(r, i);
    }

    public NumeroComplexo potencia(int expoente) {
        NumeroComplexo resultado = new NumeroComplexo(1, 0);
        NumeroComplexo base = new NumeroComplexo(this.real, this.imaginario);
        for (int i = 0; i < expoente; i++) {
            resultado = resultado.multiplicar(base);
        }
        return resultado;
    }

    public NumeroComplexo raizQuadrada() {
        double magnitude = Math.sqrt(Math.sqrt(real*real + imaginario*imaginario));
        double angle = Math.atan2(imaginario, real) / 2;
        double r = magnitude * Math.cos(angle);
        double i = magnitude * Math.sin(angle);
        return new NumeroComplexo(r, i);
    }

    @Override
    public String toString() {
        if (imaginario >= 0) {
            return String.format("%.2f + %.2fi", real, imaginario);
        } else {
            return String.format("%.2f - %.2fi", real, -imaginario);
        }
    }
}

public class Calculadora {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Calculadora Principal ===");
            System.out.println("1 - Calculadora de Números Reais");
            System.out.println("2 - Calculadora de Números Complexos");
            System.out.println("3 - Calculadora Matricial (Reais)");
            System.out.println("0 - Sair");
            System.out.print("Escolha o modo: ");
            int modo = scanner.nextInt();

            switch (modo) {
                case 0:
                    System.out.println("Encerrando programa...");
                    scanner.close();
                    return;

                case 1:
                    calculadoraReais(scanner);
                    break;

                case 2:
                    calculadoraComplexos(scanner);
                    break;

                case 3:
                    calculadoraMatricial(scanner);
                    break;

                default:
                    System.out.println("Opção inválida! Escolha entre 0 e 3.");
            }
        }
    }

    public static void calculadoraReais(Scanner scanner) {
        System.out.println("\n=== Calculadora de Números Reais ===");

        double resultado = 0;
        boolean usarResultadoAnterior = false;

        while (true) {
            double num1 = 0;

            if (usarResultadoAnterior) {
                num1 = resultado;
                System.out.println("Número 1 (resultado anterior): " + num1);
            } else {
                System.out.print("Digite o primeiro número (ou 0 para voltar ao menu): ");
                num1 = scanner.nextDouble();
                if (num1 == 0) {
                    System.out.println("Voltando ao menu principal...");
                    return;
                }
            }

            System.out.print("Escolha a operação (+, -, *, /, ^, r) ou 0 para voltar: ");
            char operacao = scanner.next().charAt(0);
            if (operacao == '0') {
                System.out.println("Voltando ao menu principal...");
                return;
            }

            double resultadoOperacao = 0;

            if (operacao == 'r') {
                if (num1 < 0) {
                    System.out.println("Erro: raiz quadrada de número negativo não é real.");
                    continue;
                }
                resultadoOperacao = Math.sqrt(num1);
            } else {
                System.out.print("Digite o segundo número (ou 0 para voltar): ");
                double num2 = scanner.nextDouble();
                if (num2 == 0) {
                    System.out.println("Voltando ao menu principal...");
                    return;
                }

                switch (operacao) {
                    case '+':
                        resultadoOperacao = num1 + num2;
                        break;
                    case '-':
                        resultadoOperacao = num1 - num2;
                        break;
                    case '*':
                        resultadoOperacao = num1 * num2;
                        break;
                    case '/':
                        if (num2 == 0) {
                            System.out.println("Erro: Divisão por zero!");
                            continue;
                        }
                        resultadoOperacao = num1 / num2;
                        break;
                    case '^':
                        resultadoOperacao = Math.pow(num1, num2);
                        break;
                    default:
                        System.out.println("Operação inválida!");
                        continue;
                }
            }

            resultado = resultadoOperacao;
            System.out.println("Resultado: " + resultado);

            int escolha = -1;
            while (true) {
                System.out.println("\nO que deseja fazer?");
                System.out.println("1 - Continuar com o resultado");
                System.out.println("2 - Recomeçar");
                System.out.println("0 - Voltar ao menu principal");
                System.out.print("Escolha: ");
                escolha = scanner.nextInt();

                if (escolha == 0 || escolha == 1 || escolha == 2) {
                    break;
                } else {
                    System.out.println("Opção inválida! Escolha 0, 1 ou 2.");
                }
            }

            if (escolha == 0) {
                System.out.println("Voltando ao menu principal...");
                return;
            }

            usarResultadoAnterior = (escolha == 1);
        }
    }

    public static void calculadoraComplexos(Scanner scanner) {
        System.out.println("\n=== Calculadora de Números Complexos ===");

        NumeroComplexo resultado = null;
        boolean usarResultadoAnterior = false;

        while (true) {
            NumeroComplexo num1;

            if (usarResultadoAnterior && resultado != null) {
                num1 = resultado;
                System.out.println("Número 1 (resultado anterior): " + num1);
            } else {
                System.out.print("Digite a parte real do primeiro número (ou 0 para voltar): ");
                double real1 = scanner.nextDouble();
                if (real1 == 0) {
                    System.out.println("Voltando ao menu principal...");
                    return;
                }
                System.out.print("Digite a parte imaginária do primeiro número: ");
                double imag1 = scanner.nextDouble();
                num1 = new NumeroComplexo(real1, imag1);
            }

            System.out.print("Escolha a operação (+, -, *, /, ^, r) ou 0 para voltar: ");
            char operacao = scanner.next().charAt(0);
            if (operacao == '0') {
                System.out.println("Voltando ao menu principal...");
                return;
            }

            NumeroComplexo resultadoOperacao;

            if (operacao == 'r') {
                resultadoOperacao = num1.raizQuadrada();
            } else {
                System.out.print("Digite a parte real do segundo número (ou 0 para voltar): ");
                double real2 = scanner.nextDouble();
                if (real2 == 0) {
                    System.out.println("Voltando ao menu principal...");
                    return;
                }
                System.out.print("Digite a parte imaginária do segundo número: ");
                double imag2 = scanner.nextDouble();
                NumeroComplexo num2 = new NumeroComplexo(real2, imag2);

                switch (operacao) {
                    case '+':
                        resultadoOperacao = num1.somar(num2);
                        break;
                    case '-':
                        resultadoOperacao = num1.subtrair(num2);
                        break;
                    case '*':
                        resultadoOperacao = num1.multiplicar(num2);
                        break;
                    case '/':
                        try {
                            resultadoOperacao = num1.dividir(num2);
                        } catch (ArithmeticException e) {
                            System.out.println("Erro: Divisão por zero!");
                            continue;
                        }
                        break;
                    case '^':
                        System.out.print("Digite o expoente inteiro: ");
                        int expoente = scanner.nextInt();
                        resultadoOperacao = num1.potencia(expoente);
                        break;
                    default:
                        System.out.println("Operação inválida!");
                        continue;
                }
            }

            resultado = resultadoOperacao;
            System.out.println("Resultado: " + resultado);

            int escolha = -1;
            while (true) {
                System.out.println("\nO que deseja fazer?");
                System.out.println("1 - Continuar com o resultado");
                System.out.println("2 - Recomeçar");
                System.out.println("0 - Voltar ao menu principal");
                System.out.print("Escolha: ");
                escolha = scanner.nextInt();

                if (escolha == 0 || escolha == 1 || escolha == 2) {
                    break;
                } else {
                    System.out.println("Opção inválida! Escolha 0, 1 ou 2.");
                }
            }

            if (escolha == 0) {
                System.out.println("Voltando ao menu principal...");
                return;
            }

            usarResultadoAnterior = (escolha == 1);
        }
    }

    public static void calculadoraMatricial(Scanner scanner) {
    System.out.println("\n=== Calculadora Matricial (Reais) ===");

    while (true) {
        System.out.println("\nEscolha a operação:");
        System.out.println("1 - Soma de matrizes");
        System.out.println("2 - Subtração de matrizes");
        System.out.println("3 - Multiplicação de matrizes");
        System.out.println("4 - Determinante de matriz");
        System.out.println("0 - Voltar ao menu principal");
        System.out.print("Opção: ");
        int opcao = scanner.nextInt();

        if (opcao == 0) {
            System.out.println("Voltando ao menu principal...");
            return;
        }

        System.out.println("Digite as dimensões da matriz:");
        int linhas = lerInteiroPositivo(scanner, "Linhas: ");
        int colunas = lerInteiroPositivo(scanner, "Colunas: ");

        if (opcao == 4 && linhas != colunas) {
            System.out.println("Erro: Matriz deve ser quadrada para calcular determinante.");
            continue;
        }

        System.out.println("Digite os elementos da matriz:");
        double[][] matriz = lerMatriz(scanner, linhas, colunas);

        switch (opcao) {
            case 1:
            case 2:
            case 3:
                System.out.println("Para essa operação, digite as dimensões da segunda matriz:");
                int linhas2 = lerInteiroPositivo(scanner, "Linhas: ");
                int colunas2 = lerInteiroPositivo(scanner, "Colunas: ");

                System.out.println("Digite os elementos da segunda matriz:");
                double[][] matriz2 = lerMatriz(scanner, linhas2, colunas2);

                double[][] resultado;

                if (opcao == 1) {
                    if (linhas != linhas2 || colunas != colunas2) {
                        System.out.println("Erro: Para somar, as matrizes devem ter as mesmas dimensões.");
                        continue;
                    }
                    resultado = somarMatrizes(matriz, matriz2);
                    System.out.println("Resultado da soma:");
                    imprimirMatriz(resultado);

                } else if (opcao == 2) {
                    if (linhas != linhas2 || colunas != colunas2) {
                        System.out.println("Erro: Para subtrair, as matrizes devem ter as mesmas dimensões.");
                        continue;
                    }
                    resultado = subtrairMatrizes(matriz, matriz2);
                    System.out.println("Resultado da subtração:");
                    imprimirMatriz(resultado);

                } else {
                    if (colunas != linhas2) {
                        System.out.println("Erro: Para multiplicar, o número de colunas da primeira matriz deve ser igual ao número de linhas da segunda.");
                        continue;
                    }
                    resultado = multiplicarMatrizes(matriz, matriz2);
                    System.out.println("Resultado da multiplicação:");
                    imprimirMatriz(resultado);
                }
                break;

            case 4:
                double det = calcularDeterminante(matriz);
                System.out.printf("Determinante: %.2f\n", det);
                break;

            default:
                System.out.println("Opção inválida!");
                break;
        }
    }
}

public static double[][] somarMatrizes(double[][] a, double[][] b) {
    int linhas = a.length;
    int colunas = a[0].length;
    double[][] resultado = new double[linhas][colunas];

    for (int i = 0; i < linhas; i++) {
        for (int j = 0; j < colunas; j++) {
            resultado[i][j] = a[i][j] + b[i][j];
        }
    }

    return resultado;
}

public static double[][] subtrairMatrizes(double[][] a, double[][] b) {
    int linhas = a.length;
    int colunas = a[0].length;
    double[][] resultado = new double[linhas][colunas];

    for (int i = 0; i < linhas; i++) {
        for (int j = 0; j < colunas; j++) {
            resultado[i][j] = a[i][j] - b[i][j];
        }
    }

    return resultado;
}

public static double[][] multiplicarMatrizes(double[][] a, double[][] b) {
    int linhas = a.length;
    int colunas = b[0].length;
    int comum = a[0].length;
    double[][] resultado = new double[linhas][colunas];

    for (int i = 0; i < linhas; i++) {
        for (int j = 0; j < colunas; j++) {
            for (int k = 0; k < comum; k++) {
                resultado[i][j] += a[i][k] * b[k][j];
            }
        }
    }

    return resultado;
}

public static void imprimirMatriz(double[][] matriz) {
    for (double[] linha : matriz) {
        for (double valor : linha) {
            System.out.printf("%.2f\t", valor);
        }
        System.out.println();
    }
}

public static double[][] lerMatriz(Scanner scanner, int linhas, int colunas) {
    double[][] matriz = new double[linhas][colunas];
    for (int i = 0; i < linhas; i++) {
        for (int j = 0; j < colunas; j++) {
            System.out.printf("Elemento [%d][%d]: ", i + 1, j + 1);
            matriz[i][j] = scanner.nextDouble();
        }
    }
    return matriz;
}

public static int lerInteiroPositivo(Scanner scanner, String mensagem) {
    int valor;
    do {
        System.out.print(mensagem);
        while (!scanner.hasNextInt()) {
            System.out.println("Valor inválido! Digite um número inteiro positivo.");
            System.out.print(mensagem);
            scanner.next(); // descarta entrada inválida
        }
        valor = scanner.nextInt();
        if (valor <= 0) {
            System.out.println("Valor deve ser maior que zero.");
        }
    } while (valor <= 0);
    return valor;
}


public static double calcularDeterminante(double[][] matriz) {
    int n = matriz.length;

    if (n == 1) {
        return matriz[0][0];
    }
    if (n == 2) {
        return matriz[0][0] * matriz[1][1] - matriz[0][1] * matriz[1][0];
    }

    double det = 0;
    for (int col = 0; col < n; col++) {
        det += Math.pow(-1, col) * matriz[0][col] * calcularDeterminante(menorMatriz(matriz, 0, col));
    }
    return det;
}

public static double[][] menorMatriz(double[][] matriz, int linhaExcluir, int colunaExcluir) {
    int n = matriz.length;
    double[][] menor = new double[n - 1][n - 1];
    int linhaMenor = 0;

    for (int i = 0; i < n; i++) {
        if (i == linhaExcluir) continue;
        int colunaMenor = 0;
        for (int j = 0; j < n; j++) {
            if (j == colunaExcluir) continue;
            menor[linhaMenor][colunaMenor] = matriz[i][j];
            colunaMenor++;
        }
        linhaMenor++;
    }
    return menor;
}
}