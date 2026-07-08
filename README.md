# Circuito-fisica

# Solucionador de Circuitos - Problema 71 (Halliday Vol. 3)

Este projeto consiste em uma aplicação desktop desenvolvida em **Java (Swing)** criada para resolver e simular numericamente o **Problema 71 do Capítulo 27 do livro Fundamentos de Física (Halliday & Resnick)**, que aborda circuitos de corrente contínua e as Leis de Kirchhoff.

---

## 📋 Resumo do Problema

O problema apresenta um circuito elétrico malhado complexo alimentado por uma fonte ideal de força eletromotriz (fem) $\mathcal{E}$ e composto por resistores de valores $R_1$ e $R_2$. O circuito possui três chaves ($S_1$, $S_2$ e $S_3$) que podem ser abertas ou fechadas de forma independente. 

O objetivo principal é determinar a **corrente elétrica no ponto $a$** (saída imediata da fonte) sob três condições distintas:
1. **Caso (a):** Apenas a chave $S_1$ está fechada.
2. **Caso (b):** Apenas as chaves $S_1$ e $S_2$ estão fechadas.
3. **Caso (c):** As três chaves ($S_1$, $S_2$ e $S_3$) estão fechadas.

---

## 💻 O Código e a Solução Computacional

O programa automatiza os cálculos algébricos que manualmente exigiriam a resolução de sistemas lineares complexos à medida que mais chaves são acionadas.

### 🖼️ Interface Gráfica (GUI)
A interface foi construída utilizando a biblioteca **Java Swing**:
* **Entradas:** Campos de texto (`JTextField`) amigáveis para inserção dos valores de $R_1$, $R_2$ e da FEM ($\mathcal{E}$).
* **Ação:** Um botão "Calcular corrente" centraliza o fluxo de execução.
* **Saídas:** Labels que exibem dinamicamente os resultados dos três casos configurados com precisão de 4 casas decimais.

### 🧮 Lógica de Cálculo e Física aplicada
O código traduz o comportamento físico do circuito montando e resolvendo matrizes geradas pelas **Leis de Kirchhoff (Método das Tensões de Nó)**:
* **Caso (a):** Reduz-se a um circuito série simples ($I = \frac{\mathcal{E}}{2R_1}$).
* **Caso (b):** Resolve um sistema de 2 equações e 2 incógnitas.
* **Caso (c):** Configura um sistema linear de 3 equações e 3 incógnitas ($V_1, V_2, V_3$) resolvido de forma exata e nativa via **Regra de Cramer** e cálculo de determinantes 3x3 (Regra de Sarrus).

---

## ⚠️ Tratamento de Erros e Validação

Para garantir a estabilidade do programa e evitar falhas críticas (como quebras de interface ou divisões por zero), a aplicação conta com um sistema robusto de captura de exceções e validação de regras de negócio antes de efetuar qualquer cálculo.

As validações ocorrem na função `lerCampoValido` e cobrem os seguintes cenários:

* **Entrada Não-Numérica (`NumberFormatException`):** Captura erros caso o usuário digite letras, símbolos ou deixe o campo vazio. O programa impede o cálculo e avisa qual campo está incorreto.
* **Valores Negativos ou Nulos:** Numericamente, resistências nulas gerariam divisões por zero ($\infty$). Fisicamente, não existem resistores ou fontes negativas neste contexto. O limite mínimo aceito é de `0.001`.
* **Limite Máximo de Segurança:** Para evitar estouro de ponto flutuante ou valores incoerentes com o problema proposto, foi adotado um teto de limite máximo razoável de `1000.0` para todas as grandezas ($V$ ou $\Omega$).

> 💡 **Feedback Visual:** Sempre que um erro é disparado, a interface limpa os resultados anteriores e exibe uma mensagem explicativa na cor **vermelha**. Quando o cálculo é bem-sucedido, uma mensagem **verde** confirma a operação.
