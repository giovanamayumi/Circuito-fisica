import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Fisica extends JFrame {

    // limites minimo e maximo aceitos para os valores digitados
    static final double VALOR_MINIMO = 0.001; // nao pode ser zero nem negativo
    static final double VALOR_MAXIMO = 1000.0; // limite maximo razoavel pro problema

    // componentes da tela
    private JTextField campoR1;
    private JTextField campoR2;
    private JTextField campoEmf;
    private JLabel labelMensagem;
    private JLabel resultadoA;
    private JLabel resultadoB;
    private JLabel resultadoC;

    public Fisica() {

        setTitle("Circuito com 3 chaves - Problema 71 (Fig. 27.52)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(480, 380);
        setLocationRelativeTo(null); 
        setLayout(new BorderLayout(10, 10));

        JPanel painelEntrada = new JPanel(new GridLayout(4, 2, 8, 8));
        painelEntrada.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        painelEntrada.add(new JLabel("R1 (ohms):"));
        campoR1 = new JTextField();
        painelEntrada.add(campoR1);

        painelEntrada.add(new JLabel("R2 (ohms):"));
        campoR2 = new JTextField();
        painelEntrada.add(campoR2);

        painelEntrada.add(new JLabel("EMF - forca eletromotriz (volts):"));
        campoEmf = new JTextField();
        painelEntrada.add(campoEmf);

        JButton botaoCalcular = new JButton("Calcular corrente");
        painelEntrada.add(new JLabel()); // espaco vazio pra alinhar o botao
        painelEntrada.add(botaoCalcular);

        add(painelEntrada, BorderLayout.NORTH);

        JPanel painelSaida = new JPanel();
        painelSaida.setLayout(new BoxLayout(painelSaida, BoxLayout.Y_AXIS));
        painelSaida.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));

        labelMensagem = new JLabel(" ");
        labelMensagem.setFont(new Font("SansSerif", Font.BOLD, 13));

        resultadoA = new JLabel("(a) Somente S1 fechada: -");
        resultadoB = new JLabel("(b) S1 e S2 fechadas: -");
        resultadoC = new JLabel("(c) S1, S2 e S3 fechadas: -");

        Font fonteResultado = new Font("SansSerif", Font.PLAIN, 15);
        resultadoA.setFont(fonteResultado);
        resultadoB.setFont(fonteResultado);
        resultadoC.setFont(fonteResultado);

        painelSaida.add(labelMensagem);
        painelSaida.add(Box.createVerticalStrut(10));
        painelSaida.add(resultadoA);
        painelSaida.add(resultadoB);
        painelSaida.add(resultadoC);

        add(painelSaida, BorderLayout.CENTER);

        // ---------- acao do botao ----------
        botaoCalcular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                calcularECorrigirTela();
            }
        });
    }

   
    private void calcularECorrigirTela() {

        Double r1 = lerCampoValido(campoR1, "R1");
        if (r1 == null) return;

        Double r2 = lerCampoValido(campoR2, "R2");
        if (r2 == null) return;

        Double emf = lerCampoValido(campoEmf, "EMF");
        if (emf == null) return;

    
        double[] correntes = calcularCorrentes(r1, r2, emf);

        labelMensagem.setForeground(new Color(0, 130, 0)); // verde
        labelMensagem.setText("Calculo realizado com sucesso!");

        resultadoA.setText(String.format("(a) Somente S1 fechada .......... I = %.4f A", correntes[0]));
        resultadoB.setText(String.format("(b) S1 e S2 fechadas ............ I = %.4f A", correntes[1]));
        resultadoC.setText(String.format("(c) S1, S2 e S3 fechadas ......... I = %.4f A", correntes[2]));
    }

   
    private Double lerCampoValido(JTextField campo, String nomeDoCampo) {
        String texto = campo.getText().trim();
        double valor;

        try {
            valor = Double.parseDouble(texto);
        } catch (NumberFormatException erro) {
            mostrarErro("ERRO: o valor de " + nomeDoCampo + " nao e um numero valido.");
            return null;
        }

        if (valor < VALOR_MINIMO) {
            mostrarErro("ERRO: " + nomeDoCampo + " nao pode ser negativo ou igual a zero.");
            return null;
        }

        if (valor > VALOR_MAXIMO) {
            mostrarErro("ERRO: " + nomeDoCampo + " nao pode ser maior que " + VALOR_MAXIMO + ".");
            return null;
        }

        return valor;
    }

    // mostra a mensagem de erro em vermelho e limpa os resultados antigos da tela
    private void mostrarErro(String mensagem) {
        labelMensagem.setForeground(Color.RED);
        labelMensagem.setText(mensagem);
        resultadoA.setText("(a) Somente S1 fechada: -");
        resultadoB.setText("(b) S1 e S2 fechadas: -");
        resultadoC.setText("(c) S1, S2 e S3 fechadas: -");
    }


    private double[] calcularCorrentes(double r1, double r2, double emf) {

        
      
        double correnteA = emf / (2.0 * r1);

        double a11 = 2 * r2 + r1;
        double a12 = -r1;
        double b1 = r2 * emf;

        double a21 = -r1;
        double a22 = r1 + r2;
        double b2 = r2 * emf;

        double det2 = a11 * a22 - a12 * a21;
        double v1_b = (b1 * a22 - a12 * b2) / det2;
        double v2_b = (a11 * b2 - b1 * a21) / det2;

        double correnteB = (emf - v1_b) / r1 + (emf - v2_b) / r1;

        double[][] matriz = {
            { 2 * r2 + r1, -r1, 0 },
            { -r1, 2 * r1 + r2, -r1 },
            { 0, -r1, r1 + r2 }
        };
        double[] termos = { r2 * emf, r2 * emf, r2 * emf };

        double[] tensoes = resolverSistema3x3(matriz, termos);
        double v1_c = tensoes[0];
        double v2_c = tensoes[1];
        double v3_c = tensoes[2];

        double correnteC = (emf - v1_c) / r1 + (emf - v2_c) / r1 + (emf - v3_c) / r1;

        return new double[] { correnteA, correnteB, correnteC };
    }

    private double[] resolverSistema3x3(double[][] matriz, double[] termos) {
        double detPrincipal = determinante3x3(matriz);

        double[][] matrizX = copiarMatriz(matriz);
        matrizX[0][0] = termos[0];
        matrizX[1][0] = termos[1];
        matrizX[2][0] = termos[2];
        double x = determinante3x3(matrizX) / detPrincipal;

        double[][] matrizY = copiarMatriz(matriz);
        matrizY[0][1] = termos[0];
        matrizY[1][1] = termos[1];
        matrizY[2][1] = termos[2];
        double y = determinante3x3(matrizY) / detPrincipal;

        double[][] matrizZ = copiarMatriz(matriz);
        matrizZ[0][2] = termos[0];
        matrizZ[1][2] = termos[1];
        matrizZ[2][2] = termos[2];
        double z = determinante3x3(matrizZ) / detPrincipal;

        return new double[] { x, y, z };
    }

    private double determinante3x3(double[][] m) {
        double parte1 = m[0][0] * m[1][1] * m[2][2]
                       + m[0][1] * m[1][2] * m[2][0]
                       + m[0][2] * m[1][0] * m[2][1];

        double parte2 = m[0][2] * m[1][1] * m[2][0]
                       + m[0][0] * m[1][2] * m[2][1]
                       + m[0][1] * m[1][0] * m[2][2];

        return parte1 - parte2;
    }

    private double[][] copiarMatriz(double[][] original) {
        double[][] copia = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                copia[i][j] = original[i][j];
            }
        }
        return copia;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Fisica tela = new Fisica();
                tela.setVisible(true);
            }
        });
    }
}
