import java.util.Scanner;
import java.util.Stack;

public class Calculadora {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce una expresión aritmética: ");
        String expresion = scanner.nextLine();

        try {
            // Convertimos la expresión infija a postfija
            String postfija = infijaAPostfija(expresion);
            System.out.println("Notación Postfija: " + postfija);

            // Evaluamos la expresión postfija
            double resultado = evaluarPostfija(postfija);
            System.out.println("Resultado: " + resultado);
        } catch (Exception e) {
            System.out.println("Error en la expresión: " + e.getMessage());
        }
    }

    // Método para convertir una expresión infija a notación postfija
    private static String infijaAPostfija(String expresion) {
        StringBuilder resultado = new StringBuilder();
        Stack<Character> pila = new Stack<>();

        for (int i = 0; i < expresion.length(); i++) {
            char c = expresion.charAt(i);

            // Si el carácter es un número, lo añadimos al resultado
            if (Character.isDigit(c)) {
                resultado.append(c);
            }
            // Si es un paréntesis de apertura, lo apilamos
            else if (c == '(') {
                pila.push(c);
            }
            // Si es un paréntesis de cierre, sacamos de la pila hasta encontrar el paréntesis de apertura
            else if (c == ')') {
                while (!pila.isEmpty() && pila.peek() != '(') {
                    resultado.append(pila.pop());
                }
                pila.pop(); // Sacamos el '(' de la pila
            }
            // Si es un operador
            else if (esOperador(c)) {
                while (!pila.isEmpty() && precedencia(c) <= precedencia(pila.peek())) {
                    resultado.append(pila.pop());
                }
                pila.push(c);
            }
        }

        // Sacamos todos los operadores restantes de la pila
        while (!pila.isEmpty()) {
            resultado.append(pila.pop());
        }

        return resultado.toString();
    }

    // Método para evaluar una expresión en notación postfija
    private static double evaluarPostfija(String expresion) {
        Stack<Double> pila = new Stack<>();

        for (int i = 0; i < expresion.length(); i++) {
            char c = expresion.charAt(i);

            // Si es un número, lo apilamos
            if (Character.isDigit(c)) {
                pila.push((double) (c - '0'));
            }
            // Si es un operador, sacamos los dos últimos números y aplicamos la operación
            else if (esOperador(c)) {
                double b = pila.pop();
                double a = pila.pop();
                switch (c) {
                    case '+': pila.push(a + b); break;
                    case '-': pila.push(a - b); break;
                    case '*': pila.push(a * b); break;
                    case '/': pila.push(a / b); break;
                    case '^': pila.push(Math.pow(a, b)); break;
                }
            }
        }

        return pila.pop(); // El último valor en la pila es el resultado
    }

    // Método para verificar si un carácter es un operador
    private static boolean esOperador(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    // Método para determinar la precedencia de los operadores
    private static int precedencia(char c) {
        switch (c) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
            default:
                return -1;
        }
    }
}
/*

Para manejar expresiones aritméticas con paréntesis y operadores, el enfoque común es usar el algoritmo
de Shunting Yard de Dijkstra para convertir la expresión infija a postfija (notación polaca inversa) y
luego evaluar la expresión postfija.  Esto nos permite manejar la precedencia de operadores y paréntesis.

Explicación del código

    El programa solicita al usuario que ingrese una expresión aritmética en formato infijo
    (por ejemplo, 3 + 5 * (2 ^ 3)).

    Usamos el algoritmo de Shunting Yard para convertir la expresión infija en una expresión postfija
    donde los operadores siguen a sus operandos (por ejemplo, 3 5 2 3 ^ * +).

    Una vez que tenemos la expresión en notación postfija, la evaluamos utilizando una pila,
    aplicando los operadores en el orden correcto.

    Operadores soportados: +, -, *, /, ^ (exponenciación) y los paréntesis.

 */