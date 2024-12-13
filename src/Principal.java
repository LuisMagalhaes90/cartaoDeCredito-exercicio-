import Compras.Compras;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) throws IOException {
        Scanner leitura = new Scanner(System.in);
        System.out.println();
        System.out.println("====== *** SEJA BEM-VINDO AO BANCO LFM *** ====");
        System.out.println();
        try {
            System.out.print("Digite o limite do cartão: ");
            double limite = leitura.nextDouble();
            leitura.nextLine();
            CartaoDeCredito cartao = new CartaoDeCredito(limite);


            int sair = 1;
            while (sair != 0) {
                System.out.print("Digite a descrição da compra: ");
                String descricao = leitura.nextLine();

                System.out.print("Digite o valor da compra: ");
                double valor = leitura.nextDouble();
                leitura.nextLine();

                Compras compra = new Compras(descricao, valor);
                boolean compraRealizada = cartao.lancaCompra(compra);

                if (compraRealizada) {
                    System.out.println("Compra realizada!");
                    try (FileWriter writer = new FileWriter(descricao + ".txt")) {
                        writer.write("Descrição: " + descricao + "\n");
                        writer.write("Valor: R$ " + String.format("%.2f", valor) + "\n");
                        writer.write("Saldo restante: R$ " + String.format("%.2f", cartao.getSaldo()) + "\n");
                    } catch (IOException e) {
                        System.err.println("Erro ao gravar o arquivo: " + e.getMessage());
                    }

                    System.out.print("Digite 0 para sair ou 1 para continuar: ");
                    sair = leitura.nextInt();
                    leitura.nextLine(); // Consumir o restante da linha
                } else {
                    System.out.println("Saldo insuficiente!");
                    sair = 0;
                }
            }

            System.out.println("***********************");
            System.out.println("COMPRAS REALIZADAS:\n");
            Collections.sort(cartao.getCompras());
            for (Compras c : cartao.getCompras()) {
                System.out.println(c.getDescricao() + " - R$ " + c.getValor());
            }

            System.out.println("\n***********************");
            System.out.println("\nSaldo do cartão: R$ " + String.format("%.2f", cartao.getSaldo()));
        } catch (InputMismatchException e) {
            System.err.println("Erro: Entrada inválida. Reinicie o programa e insira valores válidos.");
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
        } finally {
            leitura.close();
            System.out.println("Programa encerrado.");
        }
    }
}